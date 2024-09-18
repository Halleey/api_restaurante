package card.cardapio.controller;
import java.util.*;

import card.cardapio.dto.pedido.CartItemDTO;
import card.cardapio.entitie.Paypal;
import card.cardapio.entitie.Pedido;
import card.cardapio.entitie.Users;
import card.cardapio.repositories.AddressRepository;
import card.cardapio.repositories.PaymentRepository;
import card.cardapio.services.PedidoService;
import card.cardapio.services.UserService;
import com.paypal.api.payments.PaymentExecution;
import com.paypal.base.rest.APIContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import card.cardapio.dto.paypal.PaymentDTO;
import card.cardapio.payments.PayPalService;
@RestController
@RequestMapping("/api/paypal")
@CrossOrigin("http://localhost:5173")
public class PaypalController {


    private final PayPalService payPalService;
    private final PaymentRepository paymentRepository;
    private final UserService userService;
    private final PedidoService pedidoService;
    private final AddressRepository addressRepository;

    public PaypalController(PayPalService payPalService, PaymentRepository paymentRepository, UserService userService, PedidoService pedidoService, AddressRepository addressRepository) {
        this.payPalService = payPalService;
        this.paymentRepository = paymentRepository;
        this.userService = userService;
        this.pedidoService = pedidoService;
        this.addressRepository = addressRepository;
    }

    @GetMapping("/completed-payments")
    public ResponseEntity<?> getAllCompletedPayments() {
        List<Paypal> completedPayments = paymentRepository.findAllCompletedPayments();
        if (completedPayments.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No completed payments found.");
        }
        return ResponseEntity.ok(completedPayments);
    }

    @PostMapping("/create-payment")
    public ResponseEntity<?> createPayment(@RequestBody PaymentDTO paymentDTO) {
        try {
            Payment payment = payPalService.createPayment(
                    paymentDTO.getTotal(),
                    paymentDTO.getCurrency(),
                    paymentDTO.getMethod(),
                    paymentDTO.getIntent(),
                    paymentDTO.getDescription(),
                    paymentDTO.getCancelUrl(),
                    paymentDTO.getSuccessUrl()
            );

            List<Links> links = payment.getLinks();
            String approvalUrl = null;
            for (Links link : links) {
                if (link.getRel().equals("approval_url")) {
                    approvalUrl = link.getHref();
                    break;
                }
            }

            if (approvalUrl != null) {
                Long userIdLong = null;
                try {
                    userIdLong = Long.parseLong(paymentDTO.getUserId());
                } catch (NumberFormatException e) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid userId format.");
                }

                Optional<Users> userOptional = userService.findById(userIdLong);
                if (userOptional.isEmpty()) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado");
                }
                Users user = userOptional.get();

                // Criar a entidade Paypal e associar o usuário
                Paypal paymentEntity = getPaypal(payment, approvalUrl);
                paymentEntity.setUsers(user);

                // Criar e associar os pedidos ao pagamento
                List<Pedido> pedidos = new ArrayList<>();
                for (CartItemDTO item : paymentDTO.getCartItems()) {
                    Pedido pedido = new Pedido();
                    pedido.setTitle(item.getTitle());
                    pedido.setPrice(item.getPrice());
                    pedido.setUser(user);
                    pedido.setPaypal(paymentEntity);  // Associar o pedido ao pagamento
                    pedidos.add(pedido);
                }

                // Salvar os pedidos e o pagamento
                paymentEntity.setPedidos(pedidos);
                paymentRepository.save(paymentEntity); // Isso salva os pedidos junto com o pagamento (cascade)

                return ResponseEntity.ok().body(Collections.singletonMap("approvalUrl", approvalUrl));
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("A resposta da API do PayPal não contém approval_url.");
            }
        } catch (PayPalRESTException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    private static Paypal getPaypal(Payment payment, String approvalUrl) {
        Paypal paymentEntity = new Paypal();
        paymentEntity.setPaymentId(payment.getId());
        paymentEntity.setIntent(payment.getIntent());
        paymentEntity.setPaymentMethod(payment.getPayer().getPaymentMethod());
        paymentEntity.setState(payment.getState());
        paymentEntity.setCreateTime(payment.getCreateTime());
        paymentEntity.setCurrency(payment.getTransactions().get(0).getAmount().getCurrency());
        paymentEntity.setTotal(payment.getTransactions().get(0).getAmount().getTotal());
        paymentEntity.setDescription(payment.getTransactions().get(0).getDescription());
        paymentEntity.setApprovalUrl(approvalUrl);
        return paymentEntity;
    }

    @Autowired
    private APIContext apiContext;

    @PatchMapping("/payment-complete")
    public ResponseEntity<String> completePayment(@RequestParam String paymentId, @RequestParam String PayerID) {
        try {

            Payment payment = Payment.get(apiContext, paymentId);


            PaymentExecution paymentExecution = new PaymentExecution();
            paymentExecution.setPayerId(PayerID);


            Payment executedPayment = payment.execute(apiContext, paymentExecution);


            if ("approved".equals(executedPayment.getState())) {

                Optional<Paypal> paymentFromDb = paymentRepository.findByPaymentId(paymentId);

                if (paymentFromDb.isPresent()) {
                    Paypal paypal = paymentFromDb.get();
                    paypal.setState("approved");
                    paymentRepository.save(paypal);
                    return ResponseEntity.ok("Payment approved");
                } else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Payment record not found in database");
                }

            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Payment not approved");
            }

        } catch (PayPalRESTException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred: " + e.getMessage());
        }
    }
}
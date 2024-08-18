package card.cardapio.controller;
import java.util.*;

import card.cardapio.dto.address.AddressDTO;
import card.cardapio.dto.pedido.CartItemDTO;
import card.cardapio.entitie.Address;
import card.cardapio.entitie.Paypal;
import card.cardapio.entitie.Pedido;
import card.cardapio.entitie.Users;
import card.cardapio.repositories.AddressRepository;
import card.cardapio.repositories.PaymentRepository;
import card.cardapio.services.PedidoService;
import card.cardapio.services.UserService;
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

                List<Pedido> pedidos = new ArrayList<>();
                for (CartItemDTO item : paymentDTO.getCartItems()) {
                    Pedido pedido = new Pedido();
                    pedido.setTitle(item.getTitle());
                    pedido.setPrice(item.getPrice());
                    pedido.setUser(user);
                    pedidos.add(pedido);
                }
                pedidoService.savePedidos(pedidos);
                Paypal paymentEntity = getPaypal(payment, approvalUrl);
                paymentEntity.setUsers(user);

                paymentRepository.save(paymentEntity);

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
    @PatchMapping("/payment-complete")
    public ResponseEntity<?> completePayment(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId) {
        try {
            Payment payment = payPalService.executePayment(paymentId, payerId);
            if (payment.getState().equals("approved")) {
                Paypal paymentEntity = getPaypal(payment, null);
                paymentRepository.save(paymentEntity);
                return ResponseEntity.ok("Payment approved");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Payment not approved");
            }
        } catch (PayPalRESTException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
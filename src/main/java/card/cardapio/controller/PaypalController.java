package card.cardapio.controller;
import java.util.HashMap;
import java.util.List;
import card.cardapio.entitie.Paypal;
import card.cardapio.repositories.PaymentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import card.cardapio.dto.paypal.PaymentDTO;
import card.cardapio.payments.PayPalService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/paypal")
@RequiredArgsConstructor
@CrossOrigin("http://localhost:5173")
public class PaypalController {

    private final PayPalService payPalService;
    private final PaymentRepository paymentRepository;

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

                Paypal paymentEntity = getPaypal(payment, approvalUrl);
                paymentRepository.save(paymentEntity);

                String finalApprovalUrl = approvalUrl;
                return ResponseEntity.ok().body(new HashMap<String, String>() {{
                    put("approvalUrl", finalApprovalUrl);
                }});
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


}
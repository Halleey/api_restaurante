package card.cardapio.controller;

import card.cardapio.dto.paypal.PaymentDTO;
import card.cardapio.dto.paypal.PaymentResponse;
import card.cardapio.payments.PayPalService;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/paypal")
@RequiredArgsConstructor
@CrossOrigin("http://localhost:5173")
public class PaypalController {

    private final PayPalService payPalService;

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

    @PostMapping("/execute-payment")
    public ResponseEntity<?> executePayment(@RequestParam String paymentId, @RequestParam String payerId) {
        try {
            Payment payment = payPalService.executePayment(paymentId, payerId);
            return ResponseEntity.ok(new PaymentResponse(payment));
        } catch (PayPalRESTException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
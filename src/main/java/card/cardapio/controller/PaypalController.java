package card.cardapio.controller;

import card.cardapio.payments.PayPalService;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("paypal")
@CrossOrigin(origins = "http://localhost:5173")
@RequiredArgsConstructor
public class PaypalController {

    private final PayPalService payPalService;

    @PostMapping("/create-payment")
    public ResponseEntity<?> createPayment(
            @RequestParam double total,
            @RequestParam String currency,
            @RequestParam String method,
            @RequestParam String intent,
            @RequestParam String description,
            @RequestParam String cancelUrl,
            @RequestParam String successUrl
    ) {
        try {
            Payment payment = payPalService.createPayment(total, currency, method, intent, description, cancelUrl, successUrl);
            return new ResponseEntity<>(payment, HttpStatus.OK);
        } catch (PayPalRESTException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/execute-payment")
    public ResponseEntity<?> executePayment(
            @RequestParam String paymentId,
            @RequestParam String payerId
    ) {
        try {
            Payment payment = payPalService.executePayment(paymentId, payerId);
            return new ResponseEntity<>(payment, HttpStatus.OK);
        } catch (PayPalRESTException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
package card.cardapio.payments;
import card.cardapio.entitie.Paypal;
import card.cardapio.repositories.PaymentRepository;
import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
@Service
public class PayPalService {

    private final APIContext apiContext;
    private final PaymentRepository repository;

    public PayPalService(APIContext apiContext, PaymentRepository repository) {
        this.apiContext = apiContext;
        this.repository = repository;
    }

    public Payment createPayment(
            double total,
            String currency,
            String method,
            String intent,
            String description,
            String cancelUrl,
            String successUrl
    ) throws PayPalRESTException {
        Amount amount = new Amount();
        amount.setCurrency(currency);
        amount.setTotal(String.format(Locale.US, "%.2f", total));

        Transaction transaction = new Transaction();
        transaction.setDescription(description);
        transaction.setAmount(amount);

        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);

        com.paypal.api.payments.Payer payer = new com.paypal.api.payments.Payer();
        payer.setPaymentMethod(method);

        Payment payment = new Payment();
        payment.setIntent(intent);
        payment.setPayer(payer);
        payment.setTransactions(transactions);

        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl(cancelUrl);
        redirectUrls.setReturnUrl(successUrl);

        payment.setRedirectUrls(redirectUrls);

        Payment createdPayment = payment.create(apiContext);

        System.out.println("Created Payment: " + createdPayment);

        return createdPayment;
    }

    public Payment executePayment(
            String paymentId,
            String payerId
    ) throws PayPalRESTException {
        Payment payment = new Payment();
        payment.setId(paymentId);

        PaymentExecution paymentExecution = new PaymentExecution();
        paymentExecution.setPayerId(payerId);
        return payment.execute(apiContext, paymentExecution);
    }

   public List<Paypal> getAll() {
        return  repository.findAllCompletedPayments();
   }

    public Payment getPaymentDetails(String paymentId) throws PayPalRESTException {
        // Recupera os detalhes do pagamento com base no paymentId
        Payment payment = Payment.get(apiContext, paymentId);
        return payment;
    }

}
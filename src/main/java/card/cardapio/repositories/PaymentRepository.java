package card.cardapio.repositories;

import card.cardapio.entitie.Paypal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Paypal, Long> {

    @Query("select p from Paypal p where p.state = 'approved'")
    List<Paypal> findAllCompletedPayments();

    Optional<Paypal> findByPaymentId(String paymentId);
}
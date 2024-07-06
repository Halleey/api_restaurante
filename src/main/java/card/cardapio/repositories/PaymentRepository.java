package card.cardapio.repositories;

import card.cardapio.entitie.Paypal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Paypal, Long> {
}

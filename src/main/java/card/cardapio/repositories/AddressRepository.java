package card.cardapio.repositories;

import card.cardapio.entitie.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}

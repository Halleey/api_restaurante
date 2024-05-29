package card.cardapio.repositories;

import card.cardapio.entitie.Address;
import card.cardapio.entitie.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
    Address findByUsersAndAddressAndNumero(Users users, String address, String numero);
}

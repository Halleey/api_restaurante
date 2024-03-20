package card.cardapio.repositories;

import card.cardapio.entitie.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Long> {
    Users findByEmail(String email);

    Optional <Users>findByName(String name);

    @Query("select u.role from Users u where u.name = :name")
    Users.Role findByRoleUser(String name);
}

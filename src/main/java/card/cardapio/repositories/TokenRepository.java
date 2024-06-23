package card.cardapio.repositories;

import card.cardapio.entitie.TokenReset;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<TokenReset, Long> {
    TokenReset findByToken(String token);

    void deleteByToken(String token);
}

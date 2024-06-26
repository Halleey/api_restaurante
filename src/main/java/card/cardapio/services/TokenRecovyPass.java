package card.cardapio.services;

import card.cardapio.entitie.TokenReset;
import card.cardapio.entitie.Users;
import card.cardapio.repositories.TokenRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class TokenRecovyPass {
    private static final int EXPIRATION_HOURS = 24;
    private final TokenRepository tokenRepository;

    public TokenRecovyPass(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }
    public TokenReset createToken(Users users) {
        String token = UUID.randomUUID().toString();
        LocalDateTime expiryDate = LocalDateTime.now().plusHours(EXPIRATION_HOURS);

        TokenReset tokenReset = new TokenReset();
        tokenReset.setToken(token);
        tokenReset.setUser(users);
        tokenReset.setExpiryDate(expiryDate);

        return tokenRepository.save(tokenReset);
    }
    public TokenReset findByToken(String token) {
        return tokenRepository.findByToken(token);
    }

    public void deleteToken(TokenReset token) {
        tokenRepository.delete(token);
    }
}

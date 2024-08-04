package card.cardapio.controller;
import card.cardapio.dto.user.UserLoginDto;
import card.cardapio.jwt.JwtToken;
import card.cardapio.jwt.JwtUserDetailsService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/enter")
@CrossOrigin("http://localhost:5173")
public class AutenticacaoController {

    private final JwtUserDetailsService detailsService;
    private final AuthenticationManager authenticationManager;

    public AutenticacaoController(JwtUserDetailsService detailsService, AuthenticationManager authenticationManager) {
        this.detailsService = detailsService;
        this.authenticationManager = authenticationManager;
    }
    @PostMapping("/auth")
    public ResponseEntity<?> autenticar(@RequestBody  UserLoginDto dto, HttpServletRequest request) {
        log.info("Processo de autenticação pelo login {}", dto.getName());
        try {
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(dto.getName(), dto.getPassword());

            authenticationManager.authenticate(authenticationToken);
            
            JwtToken token = detailsService.getTokenAuthenticated(dto.getName());

            return ResponseEntity.ok(token);
        } catch (AuthenticationException ex) {
            log.warn("Bad Credentials from username '{}'", dto.getName());
        }
        return ResponseEntity
                .badRequest()
                .body("Credenciais inválidas");
    }
}

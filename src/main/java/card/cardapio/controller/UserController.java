package card.cardapio.controller;

import card.cardapio.dto.address.AddressDTO;
import card.cardapio.dto.user.UserRequestDto;
import card.cardapio.entitie.TokenReset;
import card.cardapio.entitie.Users;
import card.cardapio.services.EmailService;
import card.cardapio.services.TokenRecovyPass;
import card.cardapio.services.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
@RestController
@RequestMapping("public")
@CrossOrigin("http://localhost:5173")
@Validated
public class UserController {
    private final UserService userService;

    private final TokenRecovyPass tokenService;
    private final EmailService emailService;

    @Autowired
    public UserController(UserService userService,  TokenRecovyPass tokenService, EmailService emailService) {
        this.userService = userService;

        this.tokenService = tokenService;
        this.emailService = emailService;
    }

    @PatchMapping("/alter-address")
    public ResponseEntity<String> changeAddress(@RequestParam Long userId, @RequestBody AddressDTO addressDTO) {
        try {
            userService.changeAddress(userId, addressDTO);
            return ResponseEntity.ok("Endereço atualizado com sucesso!");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao atualizar o endereço");
        }
    }

    @GetMapping("/my-address")
    public ResponseEntity<AddressDTO> addressUser(@RequestParam Long userId) {
        try {
            AddressDTO address = userService.getAddress(userId);
            return ResponseEntity.ok(address);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping
    public List<Users> getUsers() {
        return userService.getUsers();
    }
    @PostMapping
    public void saveUser(@Validated @RequestBody UserRequestDto requestDTO) throws Exception {
        if (requestDTO.name().isEmpty() || requestDTO.lastName().isEmpty() || requestDTO.email().isEmpty() || requestDTO.password().isEmpty()) {
            throw new Exception("input not empty");
        } else {
            userService.saveUser(requestDTO);
        }
    }

    @PostMapping("/alert")
    public void finishedRequestService(@RequestParam  String email) {
        userService.sendRequestFinished(email);
    }


    @PostMapping("/alterar")
    public ResponseEntity<String> forgotPassword(@RequestBody Map<String, String> requestBody) {
        String email = requestBody.get("email");
        Users user = userService.findByEmail(email);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado para o e-mail fornecido.");
        }

        TokenReset resetToken = tokenService.createToken(user);

        String subject = "Recuperação de Senha";
        String message = "Olá " + user.getName() + ",\n\n"
                + "Você solicitou a recuperação de senha. Utilize o seguinte token para redefinir sua senha:\n\n"
                + resetToken.getToken();

        String result = emailService.enviarEmail(email, subject, message);
        return ResponseEntity.ok(result);
    }

    @PatchMapping("/change-password")
    public ResponseEntity<String> changePassword(@RequestBody Map<String, String> requestBody) {
        String email = requestBody.get("email");
        String token = requestBody.get("token");
        String newPassword = requestBody.get("newPassword");

        try {
            userService.alterPassword(email, token, newPassword);
            return ResponseEntity.ok("Senha alterada com sucesso");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

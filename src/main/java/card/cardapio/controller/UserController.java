package card.cardapio.controller;

import card.cardapio.dto.address.AddressDTO;
import card.cardapio.dto.user.UserRequestDto;
import card.cardapio.entitie.Address;
import card.cardapio.entitie.TokenReset;
import card.cardapio.entitie.Users;
import card.cardapio.services.AddressService;
import card.cardapio.services.EmailService;
import card.cardapio.services.TokenRecovyPass;
import card.cardapio.services.UserService;
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
    private final AddressService addressService;
    private final TokenRecovyPass tokenService;
    private final EmailService emailService;

    @Autowired
    public UserController(UserService userService, AddressService addressService, TokenRecovyPass tokenService, EmailService emailService) {
        this.userService = userService;
        this.addressService = addressService;
        this.tokenService = tokenService;
        this.emailService = emailService;
    }

    @PostMapping
    public void saveUser(@Validated @RequestBody UserRequestDto requestDTO) throws Exception {
        if (requestDTO.name().isEmpty() || requestDTO.lastName().isEmpty() || requestDTO.email().isEmpty() || requestDTO.password().isEmpty()) {
            throw new Exception("input not empty");
        } else {
            userService.saveUser(requestDTO);
        }
    }

    @PostMapping("/address")
    public void saveAddress(@RequestBody AddressDTO addressDTO, @RequestHeader("userId") Long userId) {
        addressService.saveAddress(userId, addressDTO);
    }

    @DeleteMapping("/address")
    public void removeAddress(@RequestBody AddressDTO addressDTO, @RequestHeader("userId") Long userId) {
        addressService.removeAddress(userId, addressDTO);
    }

    @GetMapping("/address")
    public List<Address> getAllAddress() {
        return addressService.getAddressAll();
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
    @PostMapping("/change-password")
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

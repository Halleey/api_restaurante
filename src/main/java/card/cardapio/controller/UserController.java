package card.cardapio.controller;
import card.cardapio.dto.comments.ComentarioRequestDto;
import card.cardapio.dto.user.UserRequestDto;
import card.cardapio.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("public")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public void saveUser(@RequestBody UserRequestDto requestDTO) {
        userService.saveUser(requestDTO);
    }

    @PatchMapping("/user/{userId}/mesa/{mesaId}")
    public void associarUsuarioAMesa(@PathVariable Long userId, @PathVariable Long mesaId) {
        userService.associarUsuarioAMesa(userId, mesaId);
    }

    @PatchMapping("user/{userId}/mesa")
    public void desassociarMesa(@PathVariable Long  userId) {
        userService.desassociarUsuarioDeMesa(userId);
    }
    @PostMapping("/comments")
    public void salvarComentario(@RequestBody ComentarioRequestDto dto) {
        
    }
}

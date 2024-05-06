package card.cardapio.controller;
import card.cardapio.dto.user.UserRequestDto;
import card.cardapio.entitie.Mesa;
import card.cardapio.entitie.Users;
import card.cardapio.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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



}

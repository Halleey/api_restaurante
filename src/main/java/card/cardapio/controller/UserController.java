package card.cardapio.controller;
import card.cardapio.dto.address.AddressDTO;
import card.cardapio.dto.address.AddressRequestDto;
import card.cardapio.dto.user.UserRequestDto;
import card.cardapio.entitie.Address;
import card.cardapio.services.AddressService;
import card.cardapio.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("public")
public class UserController {
    private final UserService userService;
    private final AddressService addressService;

    @Autowired
    public UserController(UserService userService, AddressService addressService) {
        this.userService = userService;
        this.addressService = addressService;
    }

    @PostMapping
    public void saveUser(@RequestBody UserRequestDto requestDTO) {
        userService.saveUser(requestDTO);
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
}

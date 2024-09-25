package card.cardapio.dto.user;

import card.cardapio.entitie.Users;

public record UserResponseDto(Long id, String name, String lastName, String password, String email, String address, String number) {

    public UserResponseDto(Users users) {
        this(users.getId(), users.getName(), users.getLastName(), users.getPassword(), users.getEmail(), users.getAddress(), users.getNumber());
    }
}

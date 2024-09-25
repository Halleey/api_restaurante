package card.cardapio.dto.user;

public record UserRequestDto(String name, String lastName, String password, String email, Long userId, String address, String number) {

}

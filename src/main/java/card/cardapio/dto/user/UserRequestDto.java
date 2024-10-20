package card.cardapio.dto.user;

public record UserRequestDto(String name, String lastName, String password, Long userId, String address, String number, String email) {

}

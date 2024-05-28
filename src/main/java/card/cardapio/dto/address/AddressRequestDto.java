package card.cardapio.dto.address;

public class AddressRequestDto {
    private Long userId;
    private AddressDTO addressDTO;

    public AddressRequestDto(Long userId, AddressDTO addressDTO) {
        this.userId = userId;
        this.addressDTO = addressDTO;
    }

    public Long getUserId() {
        return userId;
    }

    public AddressDTO getAddressDTO() {
        return addressDTO;
    }
}

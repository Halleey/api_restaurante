package card.cardapio.dto.address;

public class AddressDTO {
    private String address;
    private String number;
    private Long userId;


    public AddressDTO() {
    }

    public AddressDTO(String address, String number,Long userId) {
        this.address = address;
        this.number = number;
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}

package card.cardapio.dto.address;

public class AddressDTO {
    private String address;
    private String numero;
    private Long userId;


    public AddressDTO() {
    }

    public AddressDTO(String address, String numero,Long userId) {
        this.address = address;
        this.numero = numero;
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

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }
}

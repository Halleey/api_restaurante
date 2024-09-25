package card.cardapio.dto.pedido;

public class CartItemDTO {
    private String title;
    private String price;
    private String optionalAddress;
    private String optionalNumber;

    public String getOptionalAddress() {
        return optionalAddress;
    }

    public void setOptionalAddress(String optionalAddress) {
        this.optionalAddress = optionalAddress;
    }

    public String getOptionalNumber() {
        return optionalNumber;
    }

    public void setOptionalNumber(String optionalNumber) {
        this.optionalNumber = optionalNumber;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}

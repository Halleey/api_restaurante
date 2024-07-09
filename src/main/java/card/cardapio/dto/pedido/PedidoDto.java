package card.cardapio.dto.pedido;
public class PedidoDto {
    private long id;
    private String title;
    private String price;
    private String userName;
    private String userAddress;

    public PedidoDto(long id, String title, String price, String userName, String userAddress) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.userName = userName;
        this.userAddress = userAddress;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }
}

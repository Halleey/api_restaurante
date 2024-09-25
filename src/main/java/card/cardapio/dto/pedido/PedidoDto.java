package card.cardapio.dto.pedido;
public class PedidoDto {
    private long id;
    private String title;
    private String price;
    private String userName;
    private String userAddress;
    private String userNumber;
    private String optionalAddress;
    private String optionalNumber;



    public PedidoDto(long id, String title, String price, String userName, String userAddress, String userNumber, String optionalAddress, String optionalNumber) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.userName = userName;
        this.userAddress = userAddress;
        this.userNumber =userNumber;
        this.optionalNumber = optionalNumber;
        this.optionalAddress = optionalAddress;
    }

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

    public String getUserNumber() {
        return userNumber;
    }

    public void setUserNumber(String userNumber) {
        this.userNumber = userNumber;
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

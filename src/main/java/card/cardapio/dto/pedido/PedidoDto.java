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
    private String userEmail;


    public PedidoDto(long id, String title, String price, String userName,
                     String userAddress, String userNumber, String optionalAddress,
                     String optionalNumber, String userEmail) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.userName = userName;
        this.userAddress = userAddress;
        this.userNumber =userNumber;
        this.optionalNumber = optionalNumber;
        this.optionalAddress = optionalAddress;
        this.userEmail = userEmail;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
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

package card.cardapio.entitie;

import jakarta.persistence.*;
@Entity
@Table(name = "pedido")
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String title;
    private String price;
    private String optionalAddress;
    private String optionalNumber;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_id")
    private Paypal paypal;


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

    public Paypal getPaypal() {
        return paypal;
    }

    public void setPaypal(Paypal paypal) {
        this.paypal = paypal;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

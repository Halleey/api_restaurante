package card.cardapio.dto.paypal;
import card.cardapio.dto.pedido.CartItemDTO;
import java.util.List;
public class PaymentDTO {
    private Double total;
    private String currency;
    private String method;
    private String intent;
    private String description;
    private String cancelUrl;
    private String successUrl;
    private String userId;
    private List<CartItemDTO> cartItems;
    private Long addressId;
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

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    public List<CartItemDTO> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItemDTO> cartItems) {
        this.cartItems = cartItems;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSuccessUrl() {
        return successUrl;
    }

    public void setSuccessUrl(String successUrl) {
        this.successUrl = successUrl;
    }

    public String getCancelUrl() {
        return cancelUrl;
    }

    public void setCancelUrl(String cancelUrl) {
        this.cancelUrl = cancelUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIntent() {
        return intent;
    }

    public void setIntent(String intent) {
        this.intent = intent;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("PaymentDTO{");
        sb.append("total=").append(total);
        sb.append(", currency='").append(currency).append('\'');
        sb.append(", method='").append(method).append('\'');
        sb.append(", intent='").append(intent).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", cancelUrl='").append(cancelUrl).append('\'');
        sb.append(", successUrl='").append(successUrl).append('\'');
        sb.append(", userId='").append(userId).append('\'');
        sb.append(", cartItems=").append(cartItems);
        sb.append(", addressId=").append(addressId);
        sb.append(", optionalAddress='").append(optionalAddress).append('\'');
        sb.append(", optionalNumber='").append(optionalNumber).append('\'');
        sb.append('}');
        return sb.toString();
    }


}



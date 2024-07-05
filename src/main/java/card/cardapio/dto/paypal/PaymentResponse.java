package card.cardapio.dto.paypal;

import com.paypal.api.payments.Payment;

public class PaymentResponse {
    private String id;
    private String state;
    private String createTime;
    private String updateTime;

    public PaymentResponse(Payment payment) {
        this.id = payment.getId();
        this.state = payment.getState();
        this.createTime = payment.getCreateTime();
        this.updateTime = payment.getUpdateTime();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
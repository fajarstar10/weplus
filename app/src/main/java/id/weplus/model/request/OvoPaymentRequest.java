package id.weplus.model.request;

public class OvoPaymentRequest {
    private String order_code;
    private String phone;

    public OvoPaymentRequest(String order_code, String phone) {
        this.order_code = order_code;
        this.phone = phone;
    }
}

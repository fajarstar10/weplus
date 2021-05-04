package id.weplus.model.request;

public class PaymentNotificationRequest {

    private String image_url;

    public PaymentNotificationRequest(String image_url) {
        this.image_url = image_url;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }
}

package id.weplus.model.request;

public  class ChangePhoneRequest {
    private String phone;

    public ChangePhoneRequest(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}

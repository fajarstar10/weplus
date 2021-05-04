package id.weplus.model.response.emergencycontact;

public class EmergencyContactDetail {
    private String location;
    private String type;
    private String phone;
    private String phone_ext;
    private String detail;
    private String email;
    private String address;
    private String pic_name;
    private String pic_phone;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone_ext() {
        return phone_ext;
    }

    public void setPhone_ext(String phone_ext) {
        this.phone_ext = phone_ext;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPic_name() {
        return pic_name;
    }

    public void setPic_name(String pic_name) {
        this.pic_name = pic_name;
    }

    public String getPic_phone() {
        return pic_phone;
    }

    public void setPic_phone(String pic_phone) {
        this.pic_phone = pic_phone;
    }
}

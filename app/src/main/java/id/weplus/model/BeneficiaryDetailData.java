package id.weplus.model;

public class BeneficiaryDetailData {
    private String name;
    private String relasi;
    private String email;
    private String phone;

    @Override
    public String toString() {
        return "BeneficiaryDetailData{" +
                "name ='" + name +
                "relasi ='" + relasi +
                "email='" + email +
                "phone ='" + phone +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRelasi() {
        return relasi;
    }

    public void setRelasi(String relasi) {
        this.relasi = relasi;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}

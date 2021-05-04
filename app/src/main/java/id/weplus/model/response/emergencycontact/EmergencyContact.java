package id.weplus.model.response.emergencycontact;

public class EmergencyContact {
    private String partner_nama;
    private String partner_id;
    private String image;
    private EmergencyContactDetail detail;

    public String getPartner_nama() {
        return partner_nama;
    }

    public void setPartner_nama(String partner_nama) {
        this.partner_nama = partner_nama;
    }

    public String getPartner_id() {
        return partner_id;
    }

    public void setPartner_id(String partner_id) {
        this.partner_id = partner_id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public EmergencyContactDetail getDetail() {
        return detail;
    }

    public void setDetail(EmergencyContactDetail detail) {
        this.detail = detail;
    }
}

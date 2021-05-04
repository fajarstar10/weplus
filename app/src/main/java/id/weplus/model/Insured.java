package id.weplus.model;

public class Insured {
    private int id;
    private String product_name;
    private String order_code;
    private String updated_at;
    private String category_id;
    private String partner_id;
    private String category_name;
    private String image_url;
    private String date_active;
    private String total_payment;
    private String payment_channel;
    private String status;
    private String no_polis;
    private String insured_name;
    private String nominal;

    public Insured() {
    }

    public Insured(int id, String product_name, String order_code, String updated_at, String category_id, String partner_id, String category_name, String image_url, String date_active, String total_payment, String payment_channel, String status, String no_polis, String insured_name, String nominal) {
        this.id = id;
        this.product_name = product_name;
        this.order_code = order_code;
        this.updated_at = updated_at;
        this.category_id = category_id;
        this.partner_id = partner_id;
        this.category_name = category_name;
        this.image_url = image_url;
        this.date_active = date_active;
        this.total_payment = total_payment;
        this.payment_channel = payment_channel;
        this.status = status;
        this.no_polis = no_polis;
        this.insured_name = insured_name;
        this.nominal = nominal;
    }

    @Override
    public String toString() {
        return "insured {" +
                "id ='" + id + '\'' +
                ", product_name ='" + product_name + '\'' +
                ", order_code ='" + order_code + '\'' +
                ", updated_at ='" + updated_at + '\'' +
                ", category_id ='" + category_id + '\'' +
                ", partner_id ='" + partner_id + '\'' +
                ", category_name ='" + category_name + '\'' +
                ", image_url ='" + image_url + '\'' +
                ", date_active ='" + date_active + '\'' +
                ", total_payment ='" + total_payment + '\'' +
                ", payment_channel ='" + payment_channel + '\'' +
                ", status ='" + status + '\'' +
                ", no_polis ='" + no_polis + '\'' +
                ", insured_name ='" + insured_name + '\'' +
                ", nominal ='" + nominal + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name (String product_name) {
        this.product_name = product_name;
    }

    public String getOrder_code() {
        return order_code;
    }

    public void setOrder_code (String order_code) {
        this.order_code = order_code;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at (String updated_at) {
        this.updated_at = updated_at;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id (String category_id) {
        this.category_id = category_id;
    }

    public String getPartner_id() {
        return partner_id;
    }

    public void setPartner_id (String partner_id) {
        this.partner_id = partner_id;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name (String category_name) {
        this.category_name = category_name;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url (String image_url) {
        this.image_url = image_url;
    }

    public String getDate_active() {
        return date_active;
    }

    public void setDate_active (String date_active) {
        this.date_active = date_active;
    }

    public String getTotal_payment() {
        return total_payment;
    }

    public void setTotal_payment (String total_payment) {
        this.total_payment = total_payment;
    }

    public String getPayment_channel() {
        return payment_channel;
    }

    public void setPayment_channel (String payment_channel) {
        this.payment_channel= payment_channel;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus (String status) {
        this.status = status;
    }

    public String getNo_polis() {
        return no_polis;
    }

    public String getInsured_name() {
        return insured_name;
    }

    public String getNominal() {
        return nominal;
    }
}

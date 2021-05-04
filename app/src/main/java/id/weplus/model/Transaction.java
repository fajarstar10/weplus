package id.weplus.model;

import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Transaction implements Serializable {
    private int id;
    private String order_code;
    private String product_id;
    private String categori_id;
    private String partner_id;
    private String total;
    private String status;
    private String payment_channel;
    private String date_end;
    private String product_name;
    private String img_url;
    @SerializedName("count_down_timer")
    private String countDownTimer;
    @SerializedName("no_payment")
    private String noPayment;
    @SerializedName("step_payment")
    private String step_payment;
    @SerializedName("product_detail")
    private ProductDetail productDetail;

    @Override
    public String toString() {
        return "Transaction {" +
                "id ='" + id + '\'' +
                ", order_code ='" + order_code + '\'' +
                ", product_id ='" + product_id + '\'' +
                ", categori_id ='" + categori_id + '\'' +
                ", partner_id ='" + partner_id + '\'' +
                ", total ='" + total + '\'' +
                ", status ='" + status + '\'' +
                ", payment_channel ='" + payment_channel + '\'' +
                ", date_end ='" + date_end + '\'' +
                ", product_name ='" + product_name + '\'' +
                ", img_url ='" + img_url + '\'' +
                '}';
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrder_code() {
        return order_code;
    }

    public void setOrder_code (String order_code) {
        this.order_code = order_code;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id (String product_id) {
        this.product_id = product_id;
    }
    public String getCategori_id() {
        return categori_id;
    }

    public void setCategori_id (String categori_id) {
        this.categori_id = categori_id;
    }
    public String getPartner_id() {
        return partner_id;
    }

    public void setPartner_id (String partner_id) {
        this.partner_id = partner_id;
    }
    public String getTotal() {
        return total;
    }

    public void setTotal (String total) {
        this.total = total;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus (String status) {
        this.status = status;
    }
    public String getPayment_channel() {
        return payment_channel;
    }

    public void setPayment_channel (String partner_id) {
        this.partner_id = payment_channel;
    }
    public String getDate_end() {
        return date_end;
    }

    public void setDate_end (String date_end) {
        this.date_end = date_end;
    }
    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name (String product_name) {
        this.product_name = product_name;
    }
    public String getImg_url() {
        return img_url;
    }

    public void setImg_url (String img_url) {
        this.img_url = img_url;
    }
}


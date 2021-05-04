package id.weplus.model;

import android.os.Parcel;
import android.os.Parcelable;

import id.weplus.model.response.BillInquiryData;

public class BillTransaction implements Parcelable {
    private int id;
    private String order_code;
    private String status;
    private int product_id;
    private String product_name;
    private String customer_id;
    private String image;
    private int total;
    private String payment_channel;
    private String date_start;
    private String date_end;
    private int category_id;
    private String category_name;
    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public static Creator<BillTransaction> getCREATOR() {
        return CREATOR;
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

    public void setOrder_code(String order_code) {
        this.order_code = order_code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getPayment_channel() {
        return payment_channel;
    }

    public void setPayment_channel(String payment_channel) {
        this.payment_channel = payment_channel;
    }

    public String getDate_start() {
        return date_start;
    }

    public void setDate_start(String date_start) {
        this.date_start = date_start;
    }

    public String getDate_end() {
        return date_end;
    }

    public void setDate_end(String date_end) {
        this.date_end = date_end;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.order_code);
        dest.writeString(this.status);
        dest.writeInt(this.product_id);
        dest.writeString(this.product_name);
        dest.writeString(this.customer_id);
        dest.writeString(this.image);
        dest.writeInt(this.total);
        dest.writeString(this.payment_channel);
        dest.writeString(this.date_start);
        dest.writeString(this.date_end);
        dest.writeInt(this.category_id);
        dest.writeString(this.category_name);
        dest.writeString(this.code);
    }

    public BillTransaction() {
    }

    protected BillTransaction(Parcel in) {
        this.id = in.readInt();
        this.order_code = in.readString();
        this.status = in.readString();
        this.product_id = in.readInt();
        this.product_name = in.readString();
        this.customer_id = in.readString();
        this.image = in.readString();
        this.total = in.readInt();
        this.payment_channel = in.readString();
        this.date_start = in.readString();
        this.date_end = in.readString();
        this.category_id = in.readInt();
        this.category_name = in.readString();
        this.code = in.readString();
    }

    public static final Parcelable.Creator<BillTransaction> CREATOR = new Parcelable.Creator<BillTransaction>() {
        @Override
        public BillTransaction createFromParcel(Parcel source) {
            return new BillTransaction(source);
        }

        @Override
        public BillTransaction[] newArray(int size) {
            return new BillTransaction[size];
        }
    };

    public BillInquiryData toBillInquiry(){
        BillInquiryData data = new BillInquiryData();

        return data;
    }
}

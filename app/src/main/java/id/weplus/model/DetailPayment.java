package id.weplus.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class DetailPayment implements Parcelable {
    private String image_url;
    private String date_payment;
    private String payment_channel;
    private int product_nominal;
    private int admin_fee;
    private int discount;
    private int processing_fee;
    private int total_payment;
    private String token;
    @SerializedName("redirect_url")
    private String redirectUrl;

    // Getter Methods
    public String getToken() {
        return token;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public String getImage_url() {
        return image_url;
    }

    public String getDate_payment() {
        return date_payment;
    }

    public String getPayment_channel() {
        return payment_channel;
    }

    public float getProduct_nominal() {
        return product_nominal;
    }

    public float getAdmin_fee() {
        return admin_fee;
    }

    public float getDiscount() {
        return discount;
    }

    public float getProcessing_fee() {
        return processing_fee;
    }

    public float getTotal_payment() {
        return total_payment;
    }

    // Setter Methods

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public void setDate_payment(String date_payment) {
        this.date_payment = date_payment;
    }

    public void setPayment_channel(String payment_channel) {
        this.payment_channel = payment_channel;
    }

    public void setProduct_nominal(int product_nominal) {
        this.product_nominal = product_nominal;
    }

    public void setAdmin_fee(int admin_fee) {
        this.admin_fee = admin_fee;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public void setProcessing_fee(int processing_fee) {
        this.processing_fee = processing_fee;
    }

    public void setTotal_payment(int total_payment) {
        this.total_payment = total_payment;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.image_url);
        dest.writeString(this.date_payment);
        dest.writeString(this.payment_channel);
        dest.writeInt(this.product_nominal);
        dest.writeInt(this.admin_fee);
        dest.writeInt(this.discount);
        dest.writeInt(this.processing_fee);
        dest.writeInt(this.total_payment);
    }

    public DetailPayment() {
    }

    protected DetailPayment(Parcel in) {
        this.image_url = in.readString();
        this.date_payment = in.readString();
        this.payment_channel = in.readString();
        this.product_nominal = in.readInt();
        this.admin_fee = in.readInt();
        this.discount = in.readInt();
        this.processing_fee = in.readInt();
        this.total_payment = in.readInt();
    }

    public static final Parcelable.Creator<DetailPayment> CREATOR = new Parcelable.Creator<DetailPayment>() {
        @Override
        public DetailPayment createFromParcel(Parcel source) {
            return new DetailPayment(source);
        }

        @Override
        public DetailPayment[] newArray(int size) {
            return new DetailPayment[size];
        }
    };
}

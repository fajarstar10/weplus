package id.weplus.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Payment implements Parcelable {
    private String id;
    private String name;
    private String image;
    @SerializedName("param_value")
    private String paramValue;
    @SerializedName("service_code")
    private String serviceCode;
    @SerializedName("callback_url")
    private String callbackUrl;
    @SerializedName("admin_fee")
    private String adminFee;
    @SerializedName("payment_fee")
    private String paymentFee;
    private String nominal;
    private String discount;
    private String total;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getParamValue() {
        return paramValue;
    }

    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public String getCallbackUrl() {
        return callbackUrl;
    }

    public void setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
    }

    public String getAdminFee() {
        return adminFee;
    }

    public void setAdminFee(String adminFee) {
        this.adminFee = adminFee;
    }

    public String getPaymentFee() {
        return paymentFee;
    }

    public void setPaymentFee(String paymentFee) {
        this.paymentFee = paymentFee;
    }

    public String getNominal() {
        return nominal;
    }

    public void setNominal(String nominal) {
        this.nominal = nominal;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeString(this.image);
        dest.writeString(this.paramValue);
        dest.writeString(this.serviceCode);
        dest.writeString(this.callbackUrl);
        dest.writeString(this.adminFee);
        dest.writeString(this.paymentFee);
        dest.writeString(this.nominal);
        dest.writeString(this.discount);
        dest.writeString(this.total);
    }

    public Payment() {
    }

    protected Payment(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.image = in.readString();
        this.paramValue = in.readString();
        this.serviceCode = in.readString();
        this.callbackUrl = in.readString();
        this.adminFee = in.readString();
        this.paymentFee = in.readString();
        this.nominal = in.readString();
        this.discount = in.readString();
        this.total = in.readString();
    }

    public static final Creator<Payment> CREATOR = new Creator<Payment>() {
        @Override
        public Payment createFromParcel(Parcel source) {
            return new Payment(source);
        }

        @Override
        public Payment[] newArray(int size) {
            return new Payment[size];
        }
    };
}

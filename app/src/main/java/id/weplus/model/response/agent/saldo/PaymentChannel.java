package id.weplus.model.response.agent.saldo;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class PaymentChannel implements Parcelable {
    @SerializedName("payment_channel")
    private String paymentChannel;
    @SerializedName("admin_fee")
    private String adminFee;
    @SerializedName("transfer_date")
    private String transferDate;
    @SerializedName("text")
    private String text;

    public String getPaymentChannel() {
        return paymentChannel;
    }

    public void setPaymentChannel(String paymentChannel) {
        this.paymentChannel = paymentChannel;
    }

    public String getAdminFee() {
        return adminFee;
    }

    public void setAdminFee(String adminFee) {
        this.adminFee = adminFee;
    }

    public String getTransferDate() {
        return transferDate;
    }

    public void setTransferDate(String transferDate) {
        this.transferDate = transferDate;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.paymentChannel);
        dest.writeString(this.adminFee);
        dest.writeString(this.transferDate);
        dest.writeString(this.text);
    }

    public PaymentChannel() {
    }

    protected PaymentChannel(Parcel in) {
        this.paymentChannel = in.readString();
        this.adminFee = in.readString();
        this.transferDate = in.readString();
        this.text = in.readString();
    }

    public static final Parcelable.Creator<PaymentChannel> CREATOR = new Parcelable.Creator<PaymentChannel>() {
        @Override
        public PaymentChannel createFromParcel(Parcel source) {
            return new PaymentChannel(source);
        }

        @Override
        public PaymentChannel[] newArray(int size) {
            return new PaymentChannel[size];
        }
    };
}

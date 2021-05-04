package id.weplus.model.request;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class BillTransactionRequest implements Parcelable {
    private String code;
    @SerializedName("bill_no")
    private String billNo;
    @SerializedName("payment_channel")
    private String paymentChannel;


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public String getPaymentChannel() {
        return paymentChannel;
    }

    public void setPaymentChannel(String paymentChannel) {
        this.paymentChannel = paymentChannel;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.code);
        dest.writeString(this.billNo);
        dest.writeString(this.paymentChannel);
    }

    public BillTransactionRequest() {
    }

    protected BillTransactionRequest(Parcel in) {
        this.code = in.readString();
        this.billNo = in.readString();
        this.paymentChannel = in.readString();
    }

    public static final Parcelable.Creator<BillTransactionRequest> CREATOR = new Parcelable.Creator<BillTransactionRequest>() {
        @Override
        public BillTransactionRequest createFromParcel(Parcel source) {
            return new BillTransactionRequest(source);
        }

        @Override
        public BillTransactionRequest[] newArray(int size) {
            return new BillTransactionRequest[size];
        }
    };
}

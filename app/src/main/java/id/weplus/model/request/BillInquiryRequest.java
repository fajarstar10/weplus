package id.weplus.model.request;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class BillInquiryRequest implements Parcelable {
    private String code;
    @SerializedName("bill_no")
    private String billNo;

    public BillInquiryRequest(String code, String billNo) {
        this.code = code;
        this.billNo = billNo;
    }

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.code);
        dest.writeString(this.billNo);
    }

    public BillInquiryRequest() {
    }

    protected BillInquiryRequest(Parcel in) {
        this.code = in.readString();
        this.billNo = in.readString();
    }

    public static final Creator<BillInquiryRequest> CREATOR = new Creator<BillInquiryRequest>() {
        @Override
        public BillInquiryRequest createFromParcel(Parcel source) {
            return new BillInquiryRequest(source);
        }

        @Override
        public BillInquiryRequest[] newArray(int size) {
            return new BillInquiryRequest[size];
        }
    };
}

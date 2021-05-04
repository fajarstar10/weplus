package id.weplus.model;

import android.os.Parcel;
import android.os.Parcelable;

public class BillTransactionInquiry implements Parcelable {
    private String rq_uuid;
    private String rs_datetime;
    private String error_code;
    private String error_desc;
    private String order_id;
    private String amount;
    private String bill_amount;
    private String admin_fee;
    private BillTransactionInquiryData data;

    public String getRq_uuid() {
        return rq_uuid;
    }

    public void setRq_uuid(String rq_uuid) {
        this.rq_uuid = rq_uuid;
    }

    public String getRs_datetime() {
        return rs_datetime;
    }

    public void setRs_datetime(String rs_datetime) {
        this.rs_datetime = rs_datetime;
    }

    public String getError_code() {
        return error_code;
    }

    public void setError_code(String error_code) {
        this.error_code = error_code;
    }

    public String getError_desc() {
        return error_desc;
    }

    public void setError_desc(String error_desc) {
        this.error_desc = error_desc;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getBill_amount() {
        return bill_amount;
    }

    public void setBill_amount(String bill_amount) {
        this.bill_amount = bill_amount;
    }

    public String getAdmin_fee() {
        return admin_fee;
    }

    public void setAdmin_fee(String admin_fee) {
        this.admin_fee = admin_fee;
    }

    public BillTransactionInquiryData getData() {
        return data;
    }

    public void setData(BillTransactionInquiryData data) {
        this.data = data;
    }

    public static Creator<BillTransactionInquiry> getCREATOR() {
        return CREATOR;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.rq_uuid);
        dest.writeString(this.rs_datetime);
        dest.writeString(this.error_code);
        dest.writeString(this.error_desc);
        dest.writeString(this.order_id);
        dest.writeString(this.amount);
        dest.writeString(this.bill_amount);
        dest.writeString(this.admin_fee);
        dest.writeParcelable(this.data, flags);
    }

    public BillTransactionInquiry() {
    }

    protected BillTransactionInquiry(Parcel in) {
        this.rq_uuid = in.readString();
        this.rs_datetime = in.readString();
        this.error_code = in.readString();
        this.error_desc = in.readString();
        this.order_id = in.readString();
        this.amount = in.readString();
        this.bill_amount = in.readString();
        this.admin_fee = in.readString();
        this.data = in.readParcelable(BillTransactionInquiryData.class.getClassLoader());
    }

    public static final Parcelable.Creator<BillTransactionInquiry> CREATOR = new Parcelable.Creator<BillTransactionInquiry>() {
        @Override
        public BillTransactionInquiry createFromParcel(Parcel source) {
            return new BillTransactionInquiry(source);
        }

        @Override
        public BillTransactionInquiry[] newArray(int size) {
            return new BillTransactionInquiry[size];
        }
    };
}

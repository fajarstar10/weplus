package id.weplus.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class BillTransactionInquiryData implements Parcelable {
    private String customer_id;
    private String customer_name;
    @SerializedName("class")
    private String kelas;
    private String token_unsold1;
    private String token_unsold2;
    private String admin_fee;
    private String bill_amount;
    private String bill_period;

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getKelas() {
        return kelas;
    }

    public void setKelas(String kelas) {
        this.kelas = kelas;
    }

    public String getToken_unsold1() {
        return token_unsold1;
    }

    public void setToken_unsold1(String token_unsold1) {
        this.token_unsold1 = token_unsold1;
    }

    public String getToken_unsold2() {
        return token_unsold2;
    }

    public void setToken_unsold2(String token_unsold2) {
        this.token_unsold2 = token_unsold2;
    }

    public String getAdmin_fee() {
        return admin_fee;
    }

    public void setAdmin_fee(String admin_fee) {
        this.admin_fee = admin_fee;
    }

    public String getBill_amount() {
        return bill_amount;
    }

    public void setBill_amount(String bill_amount) {
        this.bill_amount = bill_amount;
    }

    public String getBill_period() {
        return bill_period;
    }

    public void setBill_period(String bill_period) {
        this.bill_period = bill_period;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.customer_id);
        dest.writeString(this.customer_name);
        dest.writeString(this.kelas);
        dest.writeString(this.token_unsold1);
        dest.writeString(this.token_unsold2);
        dest.writeString(this.admin_fee);
        dest.writeString(this.bill_amount);
        dest.writeString(this.bill_period);
    }

    public BillTransactionInquiryData() {
    }

    protected BillTransactionInquiryData(Parcel in) {
        this.customer_id = in.readString();
        this.customer_name = in.readString();
        this.kelas = in.readString();
        this.token_unsold1 = in.readString();
        this.token_unsold2 = in.readString();
        this.admin_fee = in.readString();
        this.bill_amount = in.readString();
        this.bill_period = in.readString();
    }

    public static final Parcelable.Creator<BillTransactionInquiryData> CREATOR = new Parcelable.Creator<BillTransactionInquiryData>() {
        @Override
        public BillTransactionInquiryData createFromParcel(Parcel source) {
            return new BillTransactionInquiryData(source);
        }

        @Override
        public BillTransactionInquiryData[] newArray(int size) {
            return new BillTransactionInquiryData[size];
        }
    };
}

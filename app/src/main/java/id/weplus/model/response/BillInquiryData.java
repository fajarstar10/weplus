package id.weplus.model.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import id.weplus.model.Payment;

public class BillInquiryData implements Parcelable {
    private String customer_id;
    private String customer_name;
    private float admin_price;
    private float billing_price;
    private float total_price;
    private String item;
    private String item_name;
    private String va_number;
    private float family_number;
    private String bill_period;
    private String remain_payment;
    private String reff_code;
    private String branch_code;
    private String branch_name;
    private String remark;
    private String total_bill;
    private String meter_serial;
    @SerializedName("class")
    private String kelas;
    private String token_unsold1;
    private String token_unsold2;
    private String reff_no;
    private String image;
    @SerializedName("payment_list")
    private ArrayList<Payment> paymentList = new ArrayList();

    public String getKelas() {
        return kelas;
    }

    public void setKelas(String kelas) {
        this.kelas = kelas;
    }

    public ArrayList<Payment> getPaymentList() {
        return paymentList;
    }

    public void setPaymentList(ArrayList<Payment> paymentList) {
        this.paymentList = paymentList;
    }

// Getter Methods

    public String getCustomer_id() {
        return customer_id;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public float getAdmin_price() {
        return admin_price;
    }

    public float getBilling_price() {
        return billing_price;
    }

    public float getTotal_price() {
        return total_price;
    }

    public String getItem() {
        return item;
    }

    public String getItem_name() {
        return item_name;
    }

    public String getVa_number() {
        return va_number;
    }

    public float getFamily_number() {
        return family_number;
    }

    public String getBill_period() {
        return bill_period;
    }

    public String getRemain_payment() {
        return remain_payment;
    }

    public String getReff_code() {
        return reff_code;
    }

    public String getBranch_code() {
        return branch_code;
    }

    public String getBranch_name() {
        return branch_name;
    }

    public String getRemark() {
        return remark;
    }

    public String getTotal_bill() {
        return total_bill;
    }

    public String getMeter_serial() {
        return meter_serial;
    }


    public String getToken_unsold1() {
        return token_unsold1;
    }

    public String getToken_unsold2() {
        return token_unsold2;
    }

    public String getReff_no() {
        return reff_no;
    }

    public String getImage() {
        return image;
    }

    // Setter Methods

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public void setAdmin_price(float admin_price) {
        this.admin_price = admin_price;
    }

    public void setBilling_price(float billing_price) {
        this.billing_price = billing_price;
    }

    public void setTotal_price(float total_price) {
        this.total_price = total_price;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public void setVa_number(String va_number) {
        this.va_number = va_number;
    }

    public void setFamily_number(float family_number) {
        this.family_number = family_number;
    }

    public void setBill_period(String bill_period) {
        this.bill_period = bill_period;
    }

    public void setRemain_payment(String remain_payment) {
        this.remain_payment = remain_payment;
    }

    public void setReff_code(String reff_code) {
        this.reff_code = reff_code;
    }

    public void setBranch_code(String branch_code) {
        this.branch_code = branch_code;
    }

    public void setBranch_name(String branch_name) {
        this.branch_name = branch_name;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setTotal_bill(String total_bill) {
        this.total_bill = total_bill;
    }

    public void setMeter_serial(String meter_serial) {
        this.meter_serial = meter_serial;
    }

    public void setToken_unsold1(String token_unsold1) {
        this.token_unsold1 = token_unsold1;
    }

    public void setToken_unsold2(String token_unsold2) {
        this.token_unsold2 = token_unsold2;
    }

    public void setReff_no(String reff_no) {
        this.reff_no = reff_no;
    }

    public void setImage(String image) {
        this.image = image;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.customer_id);
        dest.writeString(this.customer_name);
        dest.writeFloat(this.admin_price);
        dest.writeFloat(this.billing_price);
        dest.writeFloat(this.total_price);
        dest.writeString(this.item);
        dest.writeString(this.item_name);
        dest.writeString(this.va_number);
        dest.writeFloat(this.family_number);
        dest.writeString(this.bill_period);
        dest.writeString(this.remain_payment);
        dest.writeString(this.reff_code);
        dest.writeString(this.branch_code);
        dest.writeString(this.branch_name);
        dest.writeString(this.remark);
        dest.writeString(this.total_bill);
        dest.writeString(this.meter_serial);
        dest.writeString(this.kelas);
        dest.writeString(this.token_unsold1);
        dest.writeString(this.token_unsold2);
        dest.writeString(this.reff_no);
        dest.writeString(this.image);
        dest.writeList(this.paymentList);
    }

    public BillInquiryData() {
    }

    protected BillInquiryData(Parcel in) {
        this.customer_id = in.readString();
        this.customer_name = in.readString();
        this.admin_price = in.readFloat();
        this.billing_price = in.readFloat();
        this.total_price = in.readFloat();
        this.item = in.readString();
        this.item_name = in.readString();
        this.va_number = in.readString();
        this.family_number = in.readFloat();
        this.bill_period = in.readString();
        this.remain_payment = in.readString();
        this.reff_code = in.readString();
        this.branch_code = in.readString();
        this.branch_name = in.readString();
        this.remark = in.readString();
        this.total_bill = in.readString();
        this.meter_serial = in.readString();
        this.kelas = in.readString();
        this.token_unsold1 = in.readString();
        this.token_unsold2 = in.readString();
        this.reff_no = in.readString();
        this.image = in.readString();
        this.paymentList = new ArrayList<Payment>();
        in.readList(this.paymentList, Payment.class.getClassLoader());
    }

    public static final Creator<BillInquiryData> CREATOR = new Creator<BillInquiryData>() {
        @Override
        public BillInquiryData createFromParcel(Parcel source) {
            return new BillInquiryData(source);
        }

        @Override
        public BillInquiryData[] newArray(int size) {
            return new BillInquiryData[size];
        }
    };
}

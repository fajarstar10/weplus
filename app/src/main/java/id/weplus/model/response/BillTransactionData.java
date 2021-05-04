package id.weplus.model.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import id.weplus.model.BillTransaction;
import id.weplus.model.BillTransactionInquiry;

public class BillTransactionData implements Parcelable {
    private float id;
    private String order_code;
    private String bill_number;
    private String invoice_code = null;
    private float nominal;
    private float discount;
    private float admin_fee;
    private float processing_fee;
    private float total;
    private String payment_channel;
    private String payment_name;
    private String payment_image;
    private String status_payment;
    private String status_vendor;
    private float product_id;
    private String product_name;
    private String image;
    private String date_start;
    private String date_end;
    private String result = null;
    private float category_id;
    private String category_name;
    private String code;
    private String customer_name;
    private String redirectURL;
    private BillTransactionInquiry inquiry;


    public float getId() {
        return id;
    }

    public void setId(float id) {
        this.id = id;
    }

    public String getOrder_code() {
        return order_code;
    }

    public void setOrder_code(String order_code) {
        this.order_code = order_code;
    }

    public String getBill_number() {
        return bill_number;
    }

    public void setBill_number(String bill_number) {
        this.bill_number = bill_number;
    }

    public String getInvoice_code() {
        return invoice_code;
    }

    public void setInvoice_code(String invoice_code) {
        this.invoice_code = invoice_code;
    }

    public float getNominal() {
        return nominal;
    }

    public void setNominal(float nominal) {
        this.nominal = nominal;
    }

    public float getDiscount() {
        return discount;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }

    public float getAdmin_fee() {
        return admin_fee;
    }

    public void setAdmin_fee(float admin_fee) {
        this.admin_fee = admin_fee;
    }

    public float getProcessing_fee() {
        return processing_fee;
    }

    public void setProcessing_fee(float processing_fee) {
        this.processing_fee = processing_fee;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public String getPayment_channel() {
        return payment_channel;
    }

    public void setPayment_channel(String payment_channel) {
        this.payment_channel = payment_channel;
    }

    public String getPayment_name() {
        return payment_name;
    }

    public void setPayment_name(String payment_name) {
        this.payment_name = payment_name;
    }

    public String getPayment_image() {
        return payment_image;
    }

    public void setPayment_image(String payment_image) {
        this.payment_image = payment_image;
    }

    public String getStatus_payment() {
        return status_payment;
    }

    public void setStatus_payment(String status_payment) {
        this.status_payment = status_payment;
    }

    public String getStatus_vendor() {
        return status_vendor;
    }

    public void setStatus_vendor(String status_vendor) {
        this.status_vendor = status_vendor;
    }

    public float getProduct_id() {
        return product_id;
    }

    public void setProduct_id(float product_id) {
        this.product_id = product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public float getCategory_id() {
        return category_id;
    }

    public void setCategory_id(float category_id) {
        this.category_id = category_id;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getRedirectURL() {
        return redirectURL;
    }

    public void setRedirectURL(String redirectURL) {
        this.redirectURL = redirectURL;
    }

    public BillTransactionInquiry getInquiry() {
        return inquiry;
    }

    public void setInquiry(BillTransactionInquiry inquiry) {
        this.inquiry = inquiry;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeFloat(this.id);
        dest.writeString(this.order_code);
        dest.writeString(this.bill_number);
        dest.writeString(this.invoice_code);
        dest.writeFloat(this.nominal);
        dest.writeFloat(this.discount);
        dest.writeFloat(this.admin_fee);
        dest.writeFloat(this.processing_fee);
        dest.writeFloat(this.total);
        dest.writeString(this.payment_channel);
        dest.writeString(this.payment_name);
        dest.writeString(this.payment_image);
        dest.writeString(this.status_payment);
        dest.writeString(this.status_vendor);
        dest.writeFloat(this.product_id);
        dest.writeString(this.product_name);
        dest.writeString(this.image);
        dest.writeString(this.date_start);
        dest.writeString(this.date_end);
        dest.writeString(this.result);
        dest.writeFloat(this.category_id);
        dest.writeString(this.category_name);
        dest.writeString(this.code);
        dest.writeString(this.customer_name);
        dest.writeString(this.redirectURL);
        dest.writeParcelable(this.inquiry, flags);
    }

    public BillTransactionData() {
    }

    protected BillTransactionData(Parcel in) {
        this.id = in.readFloat();
        this.order_code = in.readString();
        this.bill_number = in.readString();
        this.invoice_code = in.readString();
        this.nominal = in.readFloat();
        this.discount = in.readFloat();
        this.admin_fee = in.readFloat();
        this.processing_fee = in.readFloat();
        this.total = in.readFloat();
        this.payment_channel = in.readString();
        this.payment_name = in.readString();
        this.payment_image = in.readString();
        this.status_payment = in.readString();
        this.status_vendor = in.readString();
        this.product_id = in.readFloat();
        this.product_name = in.readString();
        this.image = in.readString();
        this.date_start = in.readString();
        this.date_end = in.readString();
        this.result = in.readString();
        this.category_id = in.readFloat();
        this.category_name = in.readString();
        this.code = in.readString();
        this.customer_name = in.readString();
        this.redirectURL = in.readString();
        this.inquiry = in.readParcelable(BillTransactionInquiry.class.getClassLoader());
    }

    public static final Parcelable.Creator<BillTransactionData> CREATOR = new Parcelable.Creator<BillTransactionData>() {
        @Override
        public BillTransactionData createFromParcel(Parcel source) {
            return new BillTransactionData(source);
        }

        @Override
        public BillTransactionData[] newArray(int size) {
            return new BillTransactionData[size];
        }
    };
}

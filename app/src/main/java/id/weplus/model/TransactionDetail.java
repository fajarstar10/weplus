package id.weplus.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import id.weplus.model.response.RefundData;

public class TransactionDetail implements Parcelable {
    private int id;
    private String order_code;
    private int product_id;
    private int categori_id;
    private int partner_id;
    private String date_end;
    private int count_down_timer;
    private String status;
    private String no_payment;
    private String step_payment;
    @SerializedName("product_detail")
    ProductDetail productDetail;
    @SerializedName("cicilan_detail")
    ArrayList<Cicilan> detailCicilan = new ArrayList<>();
    @SerializedName("detail_payment")
    DetailPayment detailPayment;
    @SerializedName("refund")
    RefundData refundData;

    public RefundData getRefundData() {
        return refundData;
    }

    public void setRefundData(RefundData refundData) {
        this.refundData = refundData;
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

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getCategori_id() {
        return categori_id;
    }

    public void setCategori_id(int categori_id) {
        this.categori_id = categori_id;
    }

    public int getPartner_id() {
        return partner_id;
    }

    public void setPartner_id(int partner_id) {
        this.partner_id = partner_id;
    }

    public String getDate_end() {
        return date_end;
    }

    public void setDate_end(String date_end) {
        this.date_end = date_end;
    }

    public int getCount_down_timer() {
        return count_down_timer;
    }

    public void setCount_down_timer(int count_down_timer) {
        this.count_down_timer = count_down_timer;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNo_payment() {
        return no_payment;
    }

    public void setNo_payment(String no_payment) {
        this.no_payment = no_payment;
    }

    public String getStep_payment() {
        return step_payment;
    }

    public void setStep_payment(String step_payment) {
        this.step_payment = step_payment;
    }

    public ProductDetail getProductDetail() {
        return productDetail;
    }

    public void setProductDetail(ProductDetail productDetail) {
        this.productDetail = productDetail;
    }

    public DetailPayment getDetailPayment() {
        return detailPayment;
    }

    public void setDetailPayment(DetailPayment detailPayment) {
        this.detailPayment = detailPayment;
    }

    public ArrayList<Cicilan> getDetailCicilan() {
        return detailCicilan;
    }

    public void setDetailCicilan(ArrayList<Cicilan> detailCicilan) {
        this.detailCicilan = detailCicilan;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.order_code);
        dest.writeInt(this.product_id);
        dest.writeInt(this.categori_id);
        dest.writeInt(this.partner_id);
        dest.writeString(this.date_end);
        dest.writeInt(this.count_down_timer);
        dest.writeString(this.status);
        dest.writeString(this.no_payment);
        dest.writeString(this.step_payment);
        dest.writeSerializable(this.productDetail);
        dest.writeTypedList(this.detailCicilan);
        dest.writeParcelable(this.detailPayment, flags);
    }

    public TransactionDetail() {
    }

    protected TransactionDetail(Parcel in) {
        this.id = in.readInt();
        this.order_code = in.readString();
        this.product_id = in.readInt();
        this.categori_id = in.readInt();
        this.partner_id = in.readInt();
        this.date_end = in.readString();
        this.count_down_timer = in.readInt();
        this.status = in.readString();
        this.no_payment = in.readString();
        this.step_payment = in.readString();
        this.productDetail = (ProductDetail) in.readSerializable();
        this.detailCicilan = in.createTypedArrayList(Cicilan.CREATOR);
        this.detailPayment = in.readParcelable(DetailPayment.class.getClassLoader());
    }

    public static final Parcelable.Creator<TransactionDetail> CREATOR = new Parcelable.Creator<TransactionDetail>() {
        @Override
        public TransactionDetail createFromParcel(Parcel source) {
            return new TransactionDetail(source);
        }

        @Override
        public TransactionDetail[] newArray(int size) {
            return new TransactionDetail[size];
        }
    };
}

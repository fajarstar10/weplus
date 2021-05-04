package id.weplus.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Voucher implements Parcelable {
    private int id;
    private String type;
    private String image_url;
    private String expired_date;
    private boolean status;

    private String voucher;

    @Override
    public String toString() {
        return "BillPaymentTransactionListData {" +
                "id='" + id + '\'' +
                ", type=" + type +
                ", image_url='" + image_url + '\'' +
                ", expired_date=" + expired_date +
                ", status=" + status +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVoucher() {
        return voucher;
    }

    public void setVoucher (String voucher) {
        this.voucher = voucher;
    }

    public String getType() {
        return type;
    }

    public void setType (String type) {
        this.type = type;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getExpired_date() {
        return expired_date;
    }

    public void setExpired_date(String expired_date) {
        this.expired_date = expired_date;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.type);
        dest.writeString(this.image_url);
        dest.writeString(this.expired_date);
        dest.writeByte(this.status ? (byte) 1 : (byte) 0);
        dest.writeString(this.voucher);
    }

    public Voucher() {
    }

    protected Voucher(Parcel in) {
        this.id = in.readInt();
        this.type = in.readString();
        this.image_url = in.readString();
        this.expired_date = in.readString();
        this.status = in.readByte() != 0;
        this.voucher = in.readString();
    }

    public static final Parcelable.Creator<Voucher> CREATOR = new Parcelable.Creator<Voucher>() {
        @Override
        public Voucher createFromParcel(Parcel source) {
            return new Voucher(source);
        }

        @Override
        public Voucher[] newArray(int size) {
            return new Voucher[size];
        }
    };
}

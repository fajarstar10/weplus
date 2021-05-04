package id.weplus.model.request;

import android.os.Parcel;
import android.os.Parcelable;

public class HealthProductListRequest implements Parcelable {
    private int partner_weplus_id;
    private String nik;
    private int category_id;
    private int partner_id;
    private int is_smoking;
    private String dob;
    private String sex;
    private int payment_type;
    private int group_type;
    private int adult;
    private int child;


    public int getAdult() {
        return adult;
    }

    public void setAdult(int adult) {
        this.adult = adult;
    }

    public int getChild() {
        return child;
    }

    public void setChild(int child) {
        this.child = child;
    }

    public int getPartner_weplus_id() {
        return partner_weplus_id;
    }

    public void setPartner_weplus_id(int partner_weplus_id) {
        this.partner_weplus_id = partner_weplus_id;
    }

    public String getNik() {
        return nik;
    }

    public void setNik(String nik) {
        this.nik = nik;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public int getPartner_id() {
        return partner_id;
    }

    public void setPartner_id(int partner_id) {
        this.partner_id = partner_id;
    }

    public int getIs_smoking() {
        return is_smoking;
    }

    public void setIs_smoking(int is_smoking) {
        this.is_smoking = is_smoking;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getPayment_type() {
        return payment_type;
    }

    public void setPayment_type(int payment_type) {
        this.payment_type = payment_type;
    }

    public int getGroup_type() {
        return group_type;
    }

    public void setGroup_type(int group_type) {
        this.group_type = group_type;
    }

    public HealthProductListRequest(int partner_weplus_id, String nik, int category_id, int partner_id, int is_smoking, String dob, String sex, int payment_type, int group_type) {
        this.partner_weplus_id = partner_weplus_id;
        this.nik = nik;
        this.category_id = category_id;
        this.partner_id = partner_id;
        this.is_smoking = is_smoking;
        this.dob = dob;
        this.sex = sex;
        this.payment_type = payment_type;
        this.group_type = group_type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.partner_weplus_id);
        dest.writeString(this.nik);
        dest.writeInt(this.category_id);
        dest.writeInt(this.partner_id);
        dest.writeInt(this.is_smoking);
        dest.writeString(this.dob);
        dest.writeString(this.sex);
        dest.writeInt(this.payment_type);
        dest.writeInt(this.group_type);
        dest.writeInt(this.adult);
        dest.writeInt(this.child);
    }

    public HealthProductListRequest() {
    }

    protected HealthProductListRequest(Parcel in) {
        this.partner_weplus_id = in.readInt();
        this.nik = in.readString();
        this.category_id = in.readInt();
        this.partner_id = in.readInt();
        this.is_smoking = in.readInt();
        this.dob = in.readString();
        this.sex = in.readString();
        this.payment_type = in.readInt();
        this.group_type = in.readInt();
        this.adult = in.readInt();
        this.child = in.readInt();
    }

    public static final Parcelable.Creator<HealthProductListRequest> CREATOR = new Parcelable.Creator<HealthProductListRequest>() {
        @Override
        public HealthProductListRequest createFromParcel(Parcel source) {
            return new HealthProductListRequest(source);
        }

        @Override
        public HealthProductListRequest[] newArray(int size) {
            return new HealthProductListRequest[size];
        }
    };
}

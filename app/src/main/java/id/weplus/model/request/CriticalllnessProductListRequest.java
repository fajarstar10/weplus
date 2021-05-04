package id.weplus.model.request;

import android.os.Parcel;
import android.os.Parcelable;

public class CriticalllnessProductListRequest implements Parcelable {
    private int partner_weplus_id;
    private String nik;
    private int category_id;
    private int partner_id;
    private String dob;
    private String sex;

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

    public CriticalllnessProductListRequest(int partner_weplus_id, String nik, int category_id, int partner_id, String dob, String sex) {
        this.partner_weplus_id = partner_weplus_id;
        this.nik = nik;
        this.category_id = category_id;
        this.partner_id = partner_id;
        this.dob = dob;
        this.sex = sex;
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
        dest.writeString(this.dob);
        dest.writeString(this.sex);
    }

    public CriticalllnessProductListRequest() {
    }

    protected CriticalllnessProductListRequest(Parcel in) {
        this.partner_weplus_id = in.readInt();
        this.nik = in.readString();
        this.category_id = in.readInt();
        this.partner_id = in.readInt();
        this.dob = in.readString();
        this.sex = in.readString();
    }

    public static final Parcelable.Creator<CriticalllnessProductListRequest> CREATOR = new Parcelable.Creator<CriticalllnessProductListRequest>() {
        @Override
        public CriticalllnessProductListRequest createFromParcel(Parcel source) {
            return new CriticalllnessProductListRequest(source);
        }

        @Override
        public CriticalllnessProductListRequest[] newArray(int size) {
            return new CriticalllnessProductListRequest[size];
        }
    };
}

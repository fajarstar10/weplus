package id.weplus.model.response.afiliasibengkel;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class AffiliasiBengkelModel implements Parcelable {
    private String partner;
    private String area;
    private String name;
    private String detail;
    private String address;
    ArrayList < String > phone = new ArrayList<String>();

    public String getPartner() {
        return partner;
    }

    public void setPartner(String partner) {
        this.partner = partner;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public ArrayList<String> getPhone() {
        return phone;
    }

    public void setPhone(ArrayList<String> phone) {
        this.phone = phone;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.partner);
        dest.writeString(this.area);
        dest.writeString(this.name);
        dest.writeString(this.detail);
        dest.writeString(this.address);
        dest.writeStringList(this.phone);
    }

    public AffiliasiBengkelModel() {
    }

    protected AffiliasiBengkelModel(Parcel in) {
        this.partner = in.readString();
        this.area = in.readString();
        this.name = in.readString();
        this.detail = in.readString();
        this.address = in.readString();
        this.phone = in.createStringArrayList();
    }

    public static final Parcelable.Creator<AffiliasiBengkelModel> CREATOR = new Parcelable.Creator<AffiliasiBengkelModel>() {
        @Override
        public AffiliasiBengkelModel createFromParcel(Parcel source) {
            return new AffiliasiBengkelModel(source);
        }

        @Override
        public AffiliasiBengkelModel[] newArray(int size) {
            return new AffiliasiBengkelModel[size];
        }
    };
}

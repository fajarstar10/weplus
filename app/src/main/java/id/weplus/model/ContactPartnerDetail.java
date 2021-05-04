package id.weplus.model;

import android.os.Parcel;
import android.os.Parcelable;

public class ContactPartnerDetail implements Parcelable {

    private String location;
    private String type;
    private String phone;
    private String phone_ext;
    private String detail;
    private String email;
    private String address;
    private String pic_name;
    private String pic_phone;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone_ext() {
        return phone_ext;
    }

    public void setPhone_ext(String phone_ext) {
        this.phone_ext = phone_ext;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPic_name() {
        return pic_name;
    }

    public void setPic_name(String pic_name) {
        this.pic_name = pic_name;
    }

    public String getPic_phone() {
        return pic_phone;
    }

    public void setPic_phone(String pic_phone) {
        this.pic_phone = pic_phone;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.location);
        dest.writeString(this.type);
        dest.writeString(this.phone);
        dest.writeString(this.phone_ext);
        dest.writeString(this.detail);
        dest.writeString(this.email);
        dest.writeString(this.address);
        dest.writeString(this.pic_name);
        dest.writeString(this.pic_phone);
    }

    public ContactPartnerDetail() {
    }

    protected ContactPartnerDetail(Parcel in) {
        this.location = in.readString();
        this.type = in.readString();
        this.phone = in.readString();
        this.phone_ext = in.readString();
        this.detail = in.readString();
        this.email = in.readString();
        this.address = in.readString();
        this.pic_name = in.readString();
        this.pic_phone = in.readString();
    }

    public static final Parcelable.Creator<ContactPartnerDetail> CREATOR = new Parcelable.Creator<ContactPartnerDetail>() {
        @Override
        public ContactPartnerDetail createFromParcel(Parcel source) {
            return new ContactPartnerDetail(source);
        }

        @Override
        public ContactPartnerDetail[] newArray(int size) {
            return new ContactPartnerDetail[size];
        }
    };
}

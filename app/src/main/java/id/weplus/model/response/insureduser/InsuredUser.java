package id.weplus.model.response.insureduser;

import android.os.Parcel;
import android.os.Parcelable;

public class InsuredUser implements Parcelable {
    private float id;
    private String fullname;
    private String phone;
    private String email;
    private String address;
    private String dob;
    private String sex;
    private String zip_code;
    private String province;
    private String city;
    private String identification_no;

    public InsuredUser(){

    }

    public InsuredUser(float id, String fullname, String phone, String email, String address, String dob, String sex, String zip_code, String province, String city, String identification_no) {
        this.id = id;
        this.fullname = fullname;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.dob = dob;
        this.sex = sex;
        this.zip_code = zip_code;
        this.province = province;
        this.city = city;
        this.identification_no = identification_no;
    }

    public float getId() {
        return id;
    }

    public void setId(float id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public String getZip_code() {
        return zip_code;
    }

    public void setZip_code(String zip_code) {
        this.zip_code = zip_code;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getIdentification_no() {
        return identification_no;
    }

    public void setIdentification_no(String identification_no) {
        this.identification_no = identification_no;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeFloat(this.id);
        dest.writeString(this.fullname);
        dest.writeString(this.phone);
        dest.writeString(this.email);
        dest.writeString(this.address);
        dest.writeString(this.dob);
        dest.writeString(this.sex);
        dest.writeString(this.zip_code);
        dest.writeString(this.province);
        dest.writeString(this.city);
        dest.writeString(this.identification_no);
    }

    protected InsuredUser(Parcel in) {
        this.id = in.readFloat();
        this.fullname = in.readString();
        this.phone = in.readString();
        this.email = in.readString();
        this.address = in.readString();
        this.dob = in.readString();
        this.sex = in.readString();
        this.zip_code = in.readString();
        this.province = in.readString();
        this.city = in.readString();
        this.identification_no = in.readString();
    }

    public static final Parcelable.Creator<InsuredUser> CREATOR = new Parcelable.Creator<InsuredUser>() {
        @Override
        public InsuredUser createFromParcel(Parcel source) {
            return new InsuredUser(source);
        }

        @Override
        public InsuredUser[] newArray(int size) {
            return new InsuredUser[size];
        }
    };
}

package id.weplus.model.request;

import android.os.Parcel;
import android.os.Parcelable;

public class TravelBuyRequest implements Parcelable {
    private float product_id;
    private String payment_channel;
    private String fullname;
    private String email;
    private String dob;
    private String phone;
    private String identification_no;
    private String address;
    private String province;
    private String city;
    private String sex;
    private String addition_protection;
    private String destination;
    private String duration;
    private String departure_date;
    private String return_date;
    private float package_type;
    private String type_group;
    private String beneficiary_name;
    private String beneficiary_relation;
    private String departure_city;
    private String addition_insured;


    // Getter Methods

    public float getProduct_id() {
        return product_id;
    }

    public String getPayment_channel() {
        return payment_channel;
    }

    public String getFullname() {
        return fullname;
    }

    public String getEmail() {
        return email;
    }

    public String getDob() {
        return dob;
    }

    public String getPhone() {
        return phone;
    }

    public String getIdentification_no() {
        return identification_no;
    }

    public String getAddress() {
        return address;
    }

    public String getProvince() {
        return province;
    }

    public String getCity() {
        return city;
    }

    public String getSex() {
        return sex;
    }

    public String getAddition_protection() {
        return addition_protection;
    }

    public String getDestination() {
        return destination;
    }

    public String getDuration() {
        return duration;
    }

    public String getDeparture_date() {
        return departure_date;
    }

    public String getReturn_date() {
        return return_date;
    }

    public float getPackage_type() {
        return package_type;
    }

    public String getType_group() {
        return type_group;
    }

    public String getBeneficiary_name() {
        return beneficiary_name;
    }

    public String getBeneficiary_relation() {
        return beneficiary_relation;
    }

    public String getDeparture_city() {
        return departure_city;
    }

    public String getAddition_insured() {
        return addition_insured;
    }

    // Setter Methods

    public void setProduct_id(float product_id) {
        this.product_id = product_id;
    }

    public void setPayment_channel(String payment_channel) {
        this.payment_channel = payment_channel;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setIdentification_no(String identification_no) {
        this.identification_no = identification_no;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setAddition_protection(String addition_protection) {
        this.addition_protection = addition_protection;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public void setDeparture_date(String departure_date) {
        this.departure_date = departure_date;
    }

    public void setReturn_date(String return_date) {
        this.return_date = return_date;
    }

    public void setPackage_type(float package_type) {
        this.package_type = package_type;
    }

    public void setType_group(String type_group) {
        this.type_group = type_group;
    }

    public void setBeneficiary_name(String beneficiary_name) {
        this.beneficiary_name = beneficiary_name;
    }

    public void setBeneficiary_relation(String beneficiary_relation) {
        this.beneficiary_relation = beneficiary_relation;
    }

    public void setDeparture_city(String departure_city) {
        this.departure_city = departure_city;
    }

    public void setAddition_insured(String addition_insured) {
        this.addition_insured = addition_insured;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeFloat(this.product_id);
        dest.writeString(this.payment_channel);
        dest.writeString(this.fullname);
        dest.writeString(this.email);
        dest.writeString(this.dob);
        dest.writeString(this.phone);
        dest.writeString(this.identification_no);
        dest.writeString(this.address);
        dest.writeString(this.province);
        dest.writeString(this.city);
        dest.writeString(this.sex);
        dest.writeString(this.addition_protection);
        dest.writeString(this.destination);
        dest.writeString(this.duration);
        dest.writeString(this.departure_date);
        dest.writeString(this.return_date);
        dest.writeFloat(this.package_type);
        dest.writeString(this.type_group);
        dest.writeString(this.beneficiary_name);
        dest.writeString(this.beneficiary_relation);
        dest.writeString(this.departure_city);
        dest.writeString(this.addition_insured);
    }

    public TravelBuyRequest() {
    }

    protected TravelBuyRequest(Parcel in) {
        this.product_id = in.readFloat();
        this.payment_channel = in.readString();
        this.fullname = in.readString();
        this.email = in.readString();
        this.dob = in.readString();
        this.phone = in.readString();
        this.identification_no = in.readString();
        this.address = in.readString();
        this.province = in.readString();
        this.city = in.readString();
        this.sex = in.readString();
        this.addition_protection = in.readString();
        this.destination = in.readString();
        this.duration = in.readString();
        this.departure_date = in.readString();
        this.return_date = in.readString();
        this.package_type = in.readFloat();
        this.type_group = in.readString();
        this.beneficiary_name = in.readString();
        this.beneficiary_relation = in.readString();
        this.departure_city = in.readString();
        this.addition_insured = in.readString();
    }

    public static final Parcelable.Creator<TravelBuyRequest> CREATOR = new Parcelable.Creator<TravelBuyRequest>() {
        @Override
        public TravelBuyRequest createFromParcel(Parcel source) {
            return new TravelBuyRequest(source);
        }

        @Override
        public TravelBuyRequest[] newArray(int size) {
            return new TravelBuyRequest[size];
        }
    };
}

package id.weplus.model.request;

import android.os.Parcel;
import android.os.Parcelable;

public class TravelProductListRequest implements Parcelable {
    private int partner_weplus_id;
    private String nik;
    private int category_id;
    private int partner_id;
    private int is_sport;
    private int is_cruise;
    private int is_activity;
    private String destination;
    private String duration;
    private String departure_date;
    private String addition_protection;
    private String return_date;
    private int package_type;
    private String type_group;
    private String departure_city;
    private String type_group_name;

    public String getType_group_name() {
        return type_group_name;
    }

    public void setType_group_name(String type_group_name) {
        this.type_group_name = type_group_name;
    }

    public String getDeparture_city() {
        return departure_city;
    }

    public void setDeparture_city(String departure_city) {
        this.departure_city = departure_city;
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

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getDeparture_date() {
        return departure_date;
    }

    public void setDeparture_date(String departure_date) {
        this.departure_date = departure_date;
    }

    public String getReturn_date() {
        return return_date;
    }

    public void setReturn_date(String return_date) {
        this.return_date = return_date;
    }

    public int getPackage_type() {
        return package_type;
    }

    public void setPackage_type(int package_type) {
        this.package_type = package_type;
    }

    public String getType_group() {
        return type_group;
    }

    public void setType_group(String type_group) {
        this.type_group = type_group;
    }

    public int getIs_sport() {
        return is_sport;
    }

    public void setIs_sport(int is_sport) {
        this.is_sport = is_sport;
    }

    public int getIs_cruise() {
        return is_cruise;
    }

    public void setIs_cruise(int is_cruise) {
        this.is_cruise = is_cruise;
    }

    public int getIs_activity() {
        return is_activity;
    }

    public void setIs_activity(int is_activity) {
        this.is_activity = is_activity;
    }



    public String getAddition_protection() {
        return addition_protection;
    }

    public void setAddition_protection(String addition_protection) {
        this.addition_protection = addition_protection;
    }

    public TravelProductListRequest(int partner_weplus_id, String nik, int category_id, int partner_id, int is_sport, int is_cruise, int is_activity, String destination, String duration, String departure_date, String return_date, int package_type, String type_group) {
        this.partner_weplus_id = partner_weplus_id;
        this.nik = nik;
        this.category_id = category_id;
        this.partner_id = partner_id;
        this.is_sport = is_sport;
        this.is_cruise = is_cruise;
        this.is_activity = is_activity;
        this.destination = destination;
        this.duration = duration;
        this.departure_date = departure_date;
        this.return_date = return_date;
        this.package_type = package_type;
        this.type_group = type_group;
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
        dest.writeInt(this.is_sport);
        dest.writeInt(this.is_cruise);
        dest.writeInt(this.is_activity);
        dest.writeString(this.destination);
        dest.writeString(this.duration);
        dest.writeString(this.departure_date);
        dest.writeString(this.return_date);
        dest.writeInt(this.package_type);
        dest.writeString(this.type_group);
        dest.writeString(this.departure_city);
        dest.writeString(this.type_group_name);
        dest.writeString(this.addition_protection);
    }

    public TravelProductListRequest() {
    }

    protected TravelProductListRequest(Parcel in) {
        this.partner_weplus_id = in.readInt();
        this.nik = in.readString();
        this.category_id = in.readInt();
        this.partner_id = in.readInt();
        this.is_sport = in.readInt();
        this.is_cruise = in.readInt();
        this.is_activity = in.readInt();
        this.destination = in.readString();
        this.duration = in.readString();
        this.departure_date = in.readString();
        this.return_date = in.readString();
        this.package_type = in.readInt();
        this.type_group = in.readString();
        this.departure_city=in.readString();
        this.type_group_name=in.readString();
        this.addition_protection = in.readString();
    }

    public static final Parcelable.Creator<TravelProductListRequest> CREATOR = new Parcelable.Creator<TravelProductListRequest>() {
        @Override
        public TravelProductListRequest createFromParcel(Parcel source) {
            return new TravelProductListRequest(source);
        }

        @Override
        public TravelProductListRequest[] newArray(int size) {
            return new TravelProductListRequest[size];
        }
    };
}

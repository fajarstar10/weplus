package id.weplus.model.request;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class CarProductListRequest implements Parcelable {
    private int partner_weplus_id;
    private String nik;
    private int category_id;
    private int partner_id;
    private int car_id;
    private int car_price;
    private int number_of_passenger;
    private int accessories_price;
    private int addition_driver_incident;
    private int addition_passenger_incident;
    private int addition_tpl;
    private String addition_protection;
    private int type_insurance;
    private int plat_id;
    private String duration;
    private String brand;
    private String plateName;
    private String productionYear;
    private String series;
    private String accessories;
    @SerializedName("is_agent")
    private int isAgent;

    public void setIsAgent(int isAgent) {
        this.isAgent = isAgent;
    }

    public String getAccessories() {
        return accessories;
    }

    public void setAccessories(String accessories) {
        this.accessories = accessories;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getPlateName() {
        return plateName;
    }

    public void setPlateName(String plateName) {
        this.plateName = plateName;
    }

    public String getProductionYear() {
        return productionYear;
    }

    public void setProductionYear(String productionYear) {
        this.productionYear = productionYear;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
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

    public int getCar_id() {
        return car_id;
    }

    public void setCar_id(int car_id) {
        this.car_id = car_id;
    }

    public int getCar_price() {
        return car_price;
    }

    public void setCar_price(int car_price) {
        this.car_price = car_price;
    }

    public int getNumber_of_passenger() {
        return number_of_passenger;
    }

    public void setNumber_of_passenger(int number_of_passenger) {
        this.number_of_passenger = number_of_passenger;
    }

    public int getAccessories_price() {
        return accessories_price;
    }

    public void setAccessories_price(int accessories_price) {
        this.accessories_price = accessories_price;
    }

    public int getAddition_driver_incident() {
        return addition_driver_incident;
    }

    public void setAddition_driver_incident(int addition_driver_incident) {
        this.addition_driver_incident = addition_driver_incident;
    }

    public int getAddition_passenger_incident() {
        return addition_passenger_incident;
    }

    public void setAddition_passenger_incident(int addition_passenger_incident) {
        this.addition_passenger_incident = addition_passenger_incident;
    }

    public int getAddition_tpl() {
        return addition_tpl;
    }

    public void setAddition_tpl(int addition_tpl) {
        this.addition_tpl = addition_tpl;
    }

    public String getAddition_protection() {
        return addition_protection;
    }

    public void setAddition_protection(String addition_protection) {
        this.addition_protection = addition_protection;
    }

    public int getType_insurance() {
        return type_insurance;
    }

    public void setType_insurance(int type_insurance) {
        this.type_insurance = type_insurance;
    }

    public int getPlat_id() {
        return plat_id;
    }

    public void setPlat_id(int plat_id) {
        this.plat_id = plat_id;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public CarProductListRequest(int partner_weplus_id, String nik, int category_id, int partner_id, int car_id, int car_price, int number_of_passenger, int accessories_price, int addition_driver_incident, int addition_passenger_incident, int addition_tpl, String addition_protection, int type_insurance, int plat_id, String duration) {
        this.partner_weplus_id = partner_weplus_id;
        this.nik = nik;
        this.category_id = category_id;
        this.partner_id = partner_id;
        this.car_id = car_id;
        this.car_price = car_price;
        this.number_of_passenger = number_of_passenger;
        this.accessories_price = accessories_price;
        this.addition_driver_incident = addition_driver_incident;
        this.addition_passenger_incident = addition_passenger_incident;
        this.addition_tpl = addition_tpl;
        this.addition_protection = addition_protection;
        this.type_insurance = type_insurance;
        this.plat_id = plat_id;
        this.duration = duration;

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
        dest.writeInt(this.car_id);
        dest.writeInt(this.car_price);
        dest.writeInt(this.number_of_passenger);
        dest.writeInt(this.accessories_price);
        dest.writeInt(this.addition_driver_incident);
        dest.writeInt(this.addition_passenger_incident);
        dest.writeInt(this.addition_tpl);
        dest.writeString(this.addition_protection);
        dest.writeInt(this.type_insurance);
        dest.writeInt(this.plat_id);
        dest.writeString(this.duration);
        dest.writeString(this.brand);
        dest.writeString(this.plateName);
        dest.writeString(this.productionYear);
        dest.writeString(this.series);
        dest.writeString(this.accessories);


    }

    public CarProductListRequest() {
    }

    protected CarProductListRequest(Parcel in) {
        this.partner_weplus_id = in.readInt();
        this.nik = in.readString();
        this.category_id = in.readInt();
        this.partner_id = in.readInt();
        this.car_id = in.readInt();
        this.car_price = in.readInt();
        this.number_of_passenger = in.readInt();
        this.accessories_price = in.readInt();
        this.addition_driver_incident = in.readInt();
        this.addition_passenger_incident = in.readInt();
        this.addition_tpl = in.readInt();
        this.addition_protection = in.readString();
        this.type_insurance = in.readInt();
        this.plat_id = in.readInt();
        this.duration = in.readString();
        this.brand=in.readString();
        this.plateName=in.readString();
        this.productionYear=in.readString();
        this.series=in.readString();
        this.accessories=in.readString();
    }

    public static final Parcelable.Creator<CarProductListRequest> CREATOR = new Parcelable.Creator<CarProductListRequest>() {
        @Override
        public CarProductListRequest createFromParcel(Parcel source) {
            return new CarProductListRequest(source);
        }

        @Override
        public CarProductListRequest[] newArray(int size) {
            return new CarProductListRequest[size];
        }
    };
}

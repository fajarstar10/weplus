package id.weplus.model.request;

import android.os.Parcel;
import android.os.Parcelable;

public class MotorProductListRequest implements Parcelable {
    private int partner_weplus_id;
    private String nik;
    private int category_id;
    private int partner_id;
    private int motorcycle_id;
    private String series;
    private int addition_driver_incident;
    private int addition_passenger_incident;
    private String addition_protection;
    private int type_insurance;
    private int plat_id;
    private int is_commercial;
    private int is_micro;
    private String brand;
    private String plateName;
    private String productionYear;

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

    public int getMotorcycle_id() {
        return motorcycle_id;
    }

    public void setMotorcycle_id(int motorcycle_id) {
        this.motorcycle_id = motorcycle_id;
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

    public int getIs_commercial() {
        return is_commercial;
    }

    public void setIs_commercial(int is_commercial) {
        this.is_commercial = is_commercial;
    }

    public int getIs_micro() {
        return is_micro;
    }

    public void setIs_micro(int is_micro) {
        this.is_micro = is_micro;
    }

    public MotorProductListRequest(int partner_weplus_id, String nik, int category_id, int partner_id,
                                   int motorcycle_id, String series,int addition_driver_incident,
                                   int addition_passenger_incident, String addition_protection,
                                   int type_insurance, int plat_id, int is_commercial, int is_micro,
                                   String brand,String plateName,String productionYear
                                   ) {
        this.partner_weplus_id = partner_weplus_id;
        this.nik = nik;
        this.category_id = category_id;
        this.partner_id = partner_id;
        this.motorcycle_id = motorcycle_id;
        this.series=series;
        this.addition_driver_incident = addition_driver_incident;
        this.addition_passenger_incident = addition_passenger_incident;
        this.addition_protection = addition_protection;
        this.type_insurance = type_insurance;
        this.plat_id = plat_id;
        this.is_commercial = is_commercial;
        this.is_micro = is_micro;
        this.brand=brand;
        this.plateName=plateName;
        this.productionYear=productionYear;
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
        dest.writeInt(this.motorcycle_id);
        dest.writeString(this.series);
        dest.writeInt(this.addition_driver_incident);
        dest.writeInt(this.addition_passenger_incident);
        dest.writeString(this.addition_protection);
        dest.writeInt(this.type_insurance);
        dest.writeInt(this.plat_id);
        dest.writeInt(this.is_commercial);
        dest.writeInt(this.is_micro);
        dest.writeString(this.brand);
        dest.writeString(this.plateName);
        dest.writeString(this.productionYear);
    }

    public MotorProductListRequest() {
    }

    protected MotorProductListRequest(Parcel in) {
        this.partner_weplus_id = in.readInt();
        this.nik = in.readString();
        this.category_id = in.readInt();
        this.partner_id = in.readInt();
        this.motorcycle_id = in.readInt();
        this.series=in.readString();
        this.addition_driver_incident = in.readInt();
        this.addition_passenger_incident = in.readInt();
        this.addition_protection = in.readString();
        this.type_insurance = in.readInt();
        this.plat_id = in.readInt();
        this.is_commercial = in.readInt();
        this.is_micro = in.readInt();
        this.brand = in.readString();
        this.plateName=in.readString();
        this.productionYear=in.readString();
    }

    public static final Parcelable.Creator<MotorProductListRequest> CREATOR = new Parcelable.Creator<MotorProductListRequest>() {
        @Override
        public MotorProductListRequest createFromParcel(Parcel source) {
            return new MotorProductListRequest(source);
        }

        @Override
        public MotorProductListRequest[] newArray(int size) {
            return new MotorProductListRequest[size];
        }
    };
}

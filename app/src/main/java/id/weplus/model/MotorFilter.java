package id.weplus.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MotorFilter {

    @SerializedName("category_name")
    private String catName;
    @SerializedName("category_id")
    private int catId;
    private ArrayList<Years> year;
    private ArrayList<Plat> plat;
    @SerializedName("addition_driver_incident")
    private ArrayList<BaseFilter> driverIncidents;
    @SerializedName("addition_passenger_incident")
    private ArrayList<BaseFilter> passagerIncidents;
    @SerializedName("addition_protection")
    private ArrayList<BaseFilter> addProtection;
    @SerializedName("type_insurance")
    private ArrayList<BaseFilter> typeInsurance;

    public int getCatId() {
        return catId;
    }

    public void setCatId(int catId) {
        this.catId = catId;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public ArrayList<Years> getYear() {
        return year;
    }

    public void setYear(ArrayList<Years> year) {
        this.year = year;
    }

    public ArrayList<Plat> getPlat() {
        return plat;
    }

    public void setPlat(ArrayList<Plat> plat) {
        this.plat = plat;
    }

    public ArrayList<BaseFilter> getDriverIncidents() {
        return driverIncidents;
    }

    public void setDriverIncidents(ArrayList<BaseFilter> driverIncidents) {
        this.driverIncidents = driverIncidents;
    }

    public ArrayList<BaseFilter> getPassagerIncidents() {
        return passagerIncidents;
    }

    public void setPassagerIncidents(ArrayList<BaseFilter> passagerIncidents) {
        this.passagerIncidents = passagerIncidents;
    }

    public ArrayList<BaseFilter> getAddProtection() {
        return addProtection;
    }

    public void setAddProtection(ArrayList<BaseFilter> addProtection) {
        this.addProtection = addProtection;
    }

    public ArrayList<BaseFilter> getTypeInsurance() {
        return typeInsurance;
    }

    public void setTypeInsurance(ArrayList<BaseFilter> typeInsurance) {
        this.typeInsurance = typeInsurance;
    }
}

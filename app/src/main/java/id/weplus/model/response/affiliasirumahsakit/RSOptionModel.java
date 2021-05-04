package id.weplus.model.response.affiliasirumahsakit;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class RSOptionModel {
    @SerializedName("partner_id")
    private String partnerId;
     @SerializedName("insurance_name")
     private String insuranceName;
     @SerializedName("province")
     private ArrayList<RSProvinceModel> province;

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public String getInsuranceName() {
        return insuranceName;
    }

    public void setInsuranceName(String insuranceName) {
        this.insuranceName = insuranceName;
    }

    public ArrayList<RSProvinceModel> getProvince() {
        return province;
    }

    public void setProvince(ArrayList<RSProvinceModel> province) {
        this.province = province;
    }
}

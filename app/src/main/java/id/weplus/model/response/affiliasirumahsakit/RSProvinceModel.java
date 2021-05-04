package id.weplus.model.response.affiliasirumahsakit;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class RSProvinceModel {
    @SerializedName("province_name")
    private String provinceName;
    private ArrayList<String> city;

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public ArrayList<String> getCity() {
        return city;
    }

    public void setCity(ArrayList<String> city) {
        this.city = city;
    }
}

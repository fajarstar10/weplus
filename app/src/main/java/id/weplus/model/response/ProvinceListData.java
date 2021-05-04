package id.weplus.model.response;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import id.weplus.model.Province;

public class ProvinceListData {


    @SerializedName("province")
    private ArrayList<Province> countryArrayList;

    public ArrayList<Province> getCountryArrayList() {
        return countryArrayList;
    }

    public void setCountryArrayList(ArrayList<Province> countryArrayList) {
        this.countryArrayList = countryArrayList;
    }
}

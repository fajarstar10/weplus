package id.weplus.model;


import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class SubDistrictListData {
    @SerializedName("kelurahan")
    private ArrayList<City> cityArrayList;

    public ArrayList<City> getCityArrayList() {
        return cityArrayList;
    }

    public void setCityArrayList(ArrayList<City> cityArrayList) {
        this.cityArrayList = cityArrayList;
    }
}

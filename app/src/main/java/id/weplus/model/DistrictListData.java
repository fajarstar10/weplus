package id.weplus.model;


import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class DistrictListData {
    @SerializedName("kecamatan")
    private ArrayList<City> cityArrayList;

    public ArrayList<City> getCityArrayList() {
        return cityArrayList;
    }

    public void setCityArrayList(ArrayList<City> cityArrayList) {
        this.cityArrayList = cityArrayList;
    }
}
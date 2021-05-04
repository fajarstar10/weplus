package id.weplus.model.response;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import id.weplus.model.City;

public class CityListData {
    @SerializedName("city")
    private ArrayList<City> cityArrayList;

    public ArrayList<City> getCityArrayList() {
        return cityArrayList;
    }

    public void setCityArrayList(ArrayList<City> cityArrayList) {
        this.cityArrayList = cityArrayList;
    }
}

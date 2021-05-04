package id.weplus.model.response;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import id.weplus.model.Country;

public class CountryListData {
    @SerializedName("country")
    private ArrayList<Country> countryArrayList;

    public ArrayList<Country> getCountryArrayList() {
        return countryArrayList;
    }

    public void setCountryArrayList(ArrayList<Country> countryArrayList) {
        this.countryArrayList = countryArrayList;
    }
}

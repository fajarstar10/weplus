package id.weplus.model.response.gadget;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GadgetBrand {
    @SerializedName("brand")
    private ArrayList<Brand> brands;

    public ArrayList<Brand> getBrands() {
        return brands;
    }

    public void setBrands(ArrayList<Brand> brands) {
        this.brands = brands;
    }
}

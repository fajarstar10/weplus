package id.weplus.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ParokiData {
    @SerializedName("paroki")
    private ArrayList<Paroki> listParoki;

    public ArrayList<Paroki> getListParoki() {
        return listParoki;
    }

    public void setListParoki(ArrayList<Paroki> listParoki) {
        this.listParoki = listParoki;
    }
}

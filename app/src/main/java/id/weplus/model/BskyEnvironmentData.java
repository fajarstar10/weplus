package id.weplus.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class BskyEnvironmentData {
    @SerializedName("lingkungan")
    private ArrayList<BskyEnvironment> bskyEnvironmentArrayList;

    public ArrayList<BskyEnvironment> getBskyEnvironmentArrayList() {
        return bskyEnvironmentArrayList;
    }

    public void setBskyEnvironmentArrayList(ArrayList<BskyEnvironment> bskyEnvironmentArrayList) {
        this.bskyEnvironmentArrayList = bskyEnvironmentArrayList;
    }
}

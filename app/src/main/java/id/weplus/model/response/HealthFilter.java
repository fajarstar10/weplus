package id.weplus.model.response;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import id.weplus.model.BaseFilter;

public class HealthFilter {
    @SerializedName("category_id")
    private int catId;
    @SerializedName("category_name")
    private String catName;
    @SerializedName("min_age")
    private int minAge;
    @SerializedName("max_age")
    private int maxAge;
    @SerializedName("type_group")
    private ArrayList<BaseFilter> groupTypes;
    @SerializedName("payment_type")
    private ArrayList<BaseFilter> paymentTypes;

    public int getCatId() {
        return catId;
    }

    public void setCatId(int catId) {
        this.catId = catId;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public int getMinAge() {
        return minAge;
    }

    public void setMinAge(int minAge) {
        this.minAge = minAge;
    }

    public int getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(int maxAge) {
        this.maxAge = maxAge;
    }

    public ArrayList<BaseFilter> getGroupTypes() {
        return groupTypes;
    }

    public void setGroupTypes(ArrayList<BaseFilter> groupTypes) {
        this.groupTypes = groupTypes;
    }

    public ArrayList<BaseFilter> getPaymentTypes() {
        return paymentTypes;
    }

    public void setPaymentTypes(ArrayList<BaseFilter> paymentTypes) {
        this.paymentTypes = paymentTypes;
    }
}

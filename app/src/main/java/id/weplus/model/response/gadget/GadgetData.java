package id.weplus.model.response.gadget;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import id.weplus.model.BaseFilter;

public class GadgetData {
    @SerializedName("category_id")
    private String categoryId;
    @SerializedName("category_type")
    private String categoryType;
    @SerializedName("gadget_type")
    private ArrayList<BaseFilter> gadgetType;
    @SerializedName("list_age")
    private ArrayList<BaseFilter> listAge;
    @SerializedName("list_price")
    private ArrayList<BaseFilter> listPrice;

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(String categoryType) {
        this.categoryType = categoryType;
    }

    public ArrayList<BaseFilter> getGadgetType() {
        return gadgetType;
    }

    public void setGadgetType(ArrayList<BaseFilter> gadgetType) {
        this.gadgetType = gadgetType;
    }

    public ArrayList<BaseFilter> getListAge() {
        return listAge;
    }

    public void setListAge(ArrayList<BaseFilter> listAge) {
        this.listAge = listAge;
    }

    public ArrayList<BaseFilter> getListPrice() {
        return listPrice;
    }

    public void setListPrice(ArrayList<BaseFilter> listPrice) {
        this.listPrice = listPrice;
    }
}

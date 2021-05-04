package id.weplus.model.response;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import id.weplus.model.BaseFilter;

public class TravelFilterData {
    @SerializedName("category_id")
    private int categoryId;
    @SerializedName("category_name")
    private String categoryName;
    @SerializedName("addition_travel")
    private ArrayList<BaseFilter> additionTravel;
    @SerializedName("type_group")
    private ArrayList<BaseFilter> typeGroup;

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public ArrayList<BaseFilter> getAdditionTravel() {
        return additionTravel;
    }

    public void setAdditionTravel(ArrayList<BaseFilter> additionTravel) {
        this.additionTravel = additionTravel;
    }

    public ArrayList<BaseFilter> getTypeGroup() {
        return typeGroup;
    }

    public void setTypeGroup(ArrayList<BaseFilter> typeGroup) {
        this.typeGroup = typeGroup;
    }
}

package id.weplus.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class BillsCategory {
    @SerializedName("bills_category")
    private ArrayList<BillCategory> billCategoryArrayList;

    public ArrayList<BillCategory> getBillCategoryArrayList() {
        return billCategoryArrayList;
    }

    public void setBillCategoryArrayList(ArrayList<BillCategory> billCategoryArrayList) {
        this.billCategoryArrayList = billCategoryArrayList;
    }
}

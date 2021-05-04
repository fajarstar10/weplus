package id.weplus.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import id.weplus.model.response.BillPaymentCategoryProduct;

public class BillCategory {
    private int id;
    private String name;
    private String status;
    private String image;
    private ArrayList<String> desc;
    @SerializedName("category_product")
    private ArrayList<BillPaymentCategoryProduct> categoryProduct;

    public ArrayList<BillPaymentCategoryProduct> getCategoryProduct() {
        return categoryProduct;
    }

    public void setCategoryProduct(ArrayList<BillPaymentCategoryProduct> categoryProduct) {
        this.categoryProduct = categoryProduct;
    }

    public ArrayList<String> getDesc() {
        return desc;
    }

    public void setDesc(ArrayList<String> desc) {
        this.desc = desc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}

package id.weplus.model.request;

import com.google.gson.annotations.SerializedName;

public class BillPaymentProductRequest {
    @SerializedName("category_id")
    String categoryProduct;

    public String getCategoryProduct() {
        return categoryProduct;
    }

    public void setCategoryProduct(String categoryProduct) {
        this.categoryProduct = categoryProduct;
    }
}

package id.weplus.model.response;

import com.google.gson.annotations.SerializedName;

import id.weplus.model.ProductDetail;
import id.weplus.model.ProductListData;

public class ProductDetailResponse {
    private String code;
    private boolean status;
    private String message;
    @SerializedName("data")
    private ProductDetail productDetail;

    public String getCode() {
        return code;
    }

    public boolean isStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public ProductDetail getProductDetail() {
        return productDetail;
    }
}

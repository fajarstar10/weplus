package id.weplus.model.response;

import java.util.ArrayList;

public class BillPaymentCategoryProduct {
    private String id;
    private String name;
    private String image;
    private String alias;
    private String code;
    private ArrayList<BillPaymentProduct> product;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public ArrayList<BillPaymentProduct> getProduct() {
        return product;
    }

    public void setProduct(ArrayList<BillPaymentProduct> product) {
        this.product = product;
    }
}

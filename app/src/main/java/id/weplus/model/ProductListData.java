package id.weplus.model;

import java.util.List;

public class ProductListData{
    public List<PartnerTravel> partner;
    public List<Product> product;

    @Override
    public String toString() {
        return "ProductLisData{" +
                "partner='" + partner + '\'' +
                ", product=" + product +  '\'' +
                '}';
    }

    public List<PartnerTravel> getPartner() {
        return partner;
    }

    public void setPartner (List<PartnerTravel> partner) {
        this.partner = partner;
    }

    public List<Product> getProduct() {
        return product;
    }

    public void setProduct (List<ProductListModel.ProductTravel> partner) {
        this.product = product;
    }
}

package id.weplus.Query;

public class ProductListQuery {

    private String category_id;
    private  String partner_id;

    @Override
    public String toString() {
        return "ProductListQuery{" +
                " category_id='" + category_id + '\'' +
                ", partner_id='" + partner_id + '\'' +
                '}';
    }


    public void setCategory_id (String category_id) {
        this.category_id = category_id;
    }

    public void setPartner_id (String partner_id) {
        this.partner_id = partner_id;
    }


}

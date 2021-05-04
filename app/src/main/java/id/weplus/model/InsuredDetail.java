package id.weplus.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import id.weplus.detailpolis.DataMobilModel;

public class InsuredDetail {
    private int id;
    private String category_id;
    private String partner_id;
    private String category_name;
    private String order_code;
    private String no_polis;
    private String date_active;
    private String status;
    @SerializedName("product_detail")
    ProductDetailPolisSaya productDetail;
    @SerializedName("insured_detail")
    List< InsuredDetailData> insured_detail;
    @SerializedName("items_details")
    ItemsDetailData items_details;
    @SerializedName("beneficiary_detail")
    BeneficiaryDetailData beneficiary_detail;
    @SerializedName("detail_payment")
    DetailPayment detailPayment;
    @SerializedName("duration_polis")
    private String durationPolis;
    @SerializedName("contact_partner")
    private ArrayList<ContactPartner> contactPartner;
    @SerializedName("list_menu")
    private ArrayList<DataMobilModel> listMenu;


    @Override
    public String toString() {
        String detail = items_details==null?"null":items_details.toString();
        return "InsuredDetailData{" +
                "id='" + id + '\'' +
                ", category_id=" + category_id +
                ", partner_id='" + partner_id + '\'' +
                ", category_name=" + category_name +
                ", order_code=" + order_code +
                ", no_polis='" + no_polis + '\'' +
                ", date_active=" + date_active +
                ", status=" + status +
                ", product_detail='" + productDetail + '\'' +
                ", insured_detail=" + insured_detail +
                ", items_details=" + items_details +
                ", beneficiary_detail='" + beneficiary_detail + '\'' +
                ", detail_payment=" + detailPayment +
                ", item_detail=" + detail +

                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public String getPartner_id() {
        return partner_id;
    }

    public void setPartner_id(String partner_id) {
        this.partner_id = partner_id;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getOrder_code() {
        return order_code;
    }

    public void setOrder_code(String order_code) {
        this.order_code = order_code;
    }

    public String getNo_polis() {
        return no_polis;
    }

    public void setNo_polis(String no_polis) {
        this.no_polis = no_polis;
    }

    public String getDate_active() {
        return date_active;
    }

    public void setDate_active(String date_active) {
        this.date_active = date_active;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ProductDetailPolisSaya getProductDetail() {
        return productDetail;
    }

    public List<InsuredDetailData> getInsured_detail() {
        return insured_detail;
    }

    public void setInsured_detail(List<InsuredDetailData> insured_detail) {
        this.insured_detail = insured_detail;
    }

    public ItemsDetailData getItems_details() {
        return items_details;
    }

    public void setItems_details(ItemsDetailData items_details) {
        this.items_details = items_details;
    }

    public BeneficiaryDetailData getBeneficiary_detail() {
        return beneficiary_detail;
    }

    public void setBeneficiary_detail(BeneficiaryDetailData beneficiary_detail) {
        this.beneficiary_detail = beneficiary_detail;
    }

    public DetailPayment getDetailPayment() {
        return detailPayment;
    }

    public void setDetailPayment(DetailPayment detailPayment) {
        this.detailPayment = detailPayment;
    }

    public ArrayList<ContactPartner> getContactPartner() {
        return contactPartner;
    }

    public void setContactPartner(ArrayList<ContactPartner> contactPartner) {
        this.contactPartner = contactPartner;
    }

    public String getDurationPolis() {
        return durationPolis;
    }

    public void setDurationPolis(String durationPolis) {
        this.durationPolis = durationPolis;
    }

    public ArrayList<DataMobilModel> getListMenu() {
        return listMenu;
    }

    public void setListMenu(ArrayList<DataMobilModel> listMenu) {
        this.listMenu = listMenu;
    }
}

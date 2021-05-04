package id.weplus.model.request;

import com.google.gson.annotations.SerializedName;

public class ProductListRequest {
    @SerializedName("category_id")
    private int categoryId;

    @SerializedName("partner_id")
    private int partnerId;

    @SerializedName("partner_weplus_id")
    private int partnerWePlusId=0;

    @SerializedName("dob")
    private String dob;

    @SerializedName("sex")
    private String sex;

    @SerializedName("nik")
    private String nik="";

    @SerializedName("is_company_base")
    private int is_company_base;

    @SerializedName("is_agent")
    private int isAgent=0;

    public int getIsAgent() {
        return isAgent;
    }

    public void setIsAgent(int isAgent) {
        this.isAgent = isAgent;
    }

    public ProductListRequest(int categoryId, int partnerId) {
        this.categoryId = categoryId;
        this.partnerId=partnerId;
    }

    public int getIs_company_base() {
        return is_company_base;
    }

    public void setIs_company_base(int is_company_base) {
        this.is_company_base = is_company_base;
    }

    public ProductListRequest(int categoryId, int partnerId, int partnerWePlusId, String dob, String sex, String nik) {
        this.categoryId = categoryId;
        this.partnerId = partnerId;
        this.partnerWePlusId = partnerWePlusId;
        this.dob = dob;
        this.sex = sex;
        this.nik = nik;


    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(int partnerId) {
        this.partnerId = partnerId;
    }
}

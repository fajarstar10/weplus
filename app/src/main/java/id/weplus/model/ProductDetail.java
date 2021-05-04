package id.weplus.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;


public class ProductDetail implements Serializable {
    private int id;
    @SerializedName("product_name")
    private String name;
    @SerializedName("name")
    private String nama;
    private ArrayList<String> desc;
    @SerializedName("image_url")
    private String image;
    @SerializedName("image")
    private String imageNew;
    private ArrayList<String> general;
    private ArrayList<String> resume;
    @SerializedName("benefits")
    private ArrayList<String> benefits;
    private String tnc;
    private String claim;
    private String duration;
    @SerializedName("additional_text")
    private String additionalText;
    @SerializedName("admin_fee")
    private String adminFee;
    private String discount;
    private String price;
    @SerializedName("min_insured_age")
    private int minInsuredAge;
    @SerializedName("max_insured_age")
    private int maxInsuredAge;
    @SerializedName("min_holder_age")
    private int minHolderAge;
    @SerializedName("max_holder_age")
    private int maxHolderAge;
    @SerializedName("message_insured_age")
    private String messageInsuredAge;
    @SerializedName("category_id")
    private int catId;
    @SerializedName("category_name")
    private String catName;
    @SerializedName("category_image")
    private String catImage;
    @SerializedName("partner_id")
    private int partnerId;
    @SerializedName("partner_name")
    private String partnerName;
    @SerializedName("partner_image")
    private String partnerImage;
    @SerializedName("partner_address")
    private String partnerAddress;
    @SerializedName("total_payment")
    private String totalPayment;
    @SerializedName("remaing_payment_cicilan")
    private String remainingPaymentCicilan;

    public void setTotalPayment(String totalPayment) {
        this.totalPayment = totalPayment;
    }

    public String getRemainingPaymentCicilan() {
        return remainingPaymentCicilan;
    }

    public void setRemainingPaymentCicilan(String remainingPaymentCicilan) {
        this.remainingPaymentCicilan = remainingPaymentCicilan;
    }

    public String getTotalPayment() {
        return totalPayment;
    }

    public void setPayment(String getTotalPayment) {
        this.totalPayment = getTotalPayment;
    }

    public String getName() {
        return name;
    }

    public ArrayList<String> getBenefits() {
        return benefits;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getImage() {
        return image;
    }

    public ArrayList<String> getDesc(){
        return desc;
    }

    public ArrayList<String> getResume(){
        return resume;
    }

    public ArrayList<String> getGeneral() {
        return general;
    }

    public String getClaim() {
        return claim;
    }

    public String getDuration() {
        return duration;
    }

    public String getAdditionalText() {
        return additionalText;
    }

    public String getAdminFee() {
        return adminFee;
    }

    public int getMinInsuredAge() {
        return minInsuredAge;
    }

    public int getMaxInsuredAge() {
        return maxInsuredAge;
    }

    public int getMinHolderAge() {
        return minHolderAge;
    }

    public int getMaxHolderAge() {
        return maxHolderAge;
    }

    public String getMessageInsuredAge() {
        return messageInsuredAge;
    }

    public int getCatId() {
        return catId;
    }

    public String getCatName() {
        return catName;
    }

    public String getCatImage() {
        return catImage;
    }

    public int getPartnerId() {
        return partnerId;
    }

    public String getPrice() {
        return price;
    }

    public String getPartnerName() {
        return partnerName;
    }

    public String getPartnerImage() {
        return partnerImage;
    }

    public String getPartnerAddress() {
        return partnerAddress;
    }

    public String getImageNew() {
        return imageNew;
    }

    public void setImageNew(String imageNew) {
        this.imageNew = imageNew;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDesc(ArrayList<String> desc) {
        this.desc = desc;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setGeneral(ArrayList<String> general) {
        this.general = general;
    }

    public void setResume(ArrayList<String> resume) {
        this.resume = resume;
    }

    public void setBenefits(ArrayList<String> benefits) {
        this.benefits = benefits;
    }

    public void setTnc(String tnc) {
        this.tnc = tnc;
    }

    public void setClaim(String claim) {
        this.claim = claim;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public void setAdditionalText(String additionalText) {
        this.additionalText = additionalText;
    }

    public void setAdminFee(String adminFee) {
        this.adminFee = adminFee;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setMinInsuredAge(int minInsuredAge) {
        this.minInsuredAge = minInsuredAge;
    }

    public void setMaxInsuredAge(int maxInsuredAge) {
        this.maxInsuredAge = maxInsuredAge;
    }

    public void setMinHolderAge(int minHolderAge) {
        this.minHolderAge = minHolderAge;
    }

    public void setMaxHolderAge(int maxHolderAge) {
        this.maxHolderAge = maxHolderAge;
    }

    public void setMessageInsuredAge(String messageInsuredAge) {
        this.messageInsuredAge = messageInsuredAge;
    }

    public void setCatId(int catId) {
        this.catId = catId;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public void setCatImage(String catImage) {
        this.catImage = catImage;
    }

    public void setPartnerId(int partnerId) {
        this.partnerId = partnerId;
    }

    public void setPartnerName(String partnerName) {
        this.partnerName = partnerName;
    }

    public void setPartnerImage(String partnerImage) {
        this.partnerImage = partnerImage;
    }

    public void setPartnerAddress(String partnerAddress) {
        this.partnerAddress = partnerAddress;
    }
}

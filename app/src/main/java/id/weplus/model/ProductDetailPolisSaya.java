package id.weplus.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class ProductDetailPolisSaya implements Serializable {

    private int id;
    @SerializedName("product_name")
    private String name;
    @SerializedName("name")
    private String nama;
    private String desc;
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

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDesc(String desc) {
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

    public String getDesc(){
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

    public ProductDetail toProductDetail(){
        ProductDetail productDetail = new ProductDetail();

        productDetail.setId(this.id);
        productDetail.setName(this.name);
        productDetail.setNama(this.nama);
        productDetail.setImage(this.image);
        productDetail.setImageNew(this.imageNew);
//        ArrayList<String> generals = new ArrayList<>();
//        generals.add(this.general);
        productDetail.setGeneral(general);
//        ArrayList<String> resumes = new ArrayList<>();
//        resumes.add(this.resume);
        productDetail.setResume(resume);
//        ArrayList<String> benefits = new ArrayList<>();
//        benefits.add(this.benefits);
        productDetail.setBenefits(benefits);
        productDetail.setTnc(this.tnc);
        productDetail.setClaim(this.claim);
        productDetail.setDuration(this.duration);
        productDetail.setAdditionalText(this.additionalText);
        productDetail.setAdminFee(this.adminFee);
        productDetail.setDiscount(this.discount);
        productDetail.setPrice(this.price);
        productDetail.setMinInsuredAge(this.minInsuredAge);
        productDetail.setMaxInsuredAge(this.maxInsuredAge);
        productDetail.setMinHolderAge(this.minHolderAge);
        productDetail.setMaxHolderAge(this.maxHolderAge);
        productDetail.setMessageInsuredAge(this.messageInsuredAge);
        productDetail.setCatId(this.catId);
        productDetail.setCatName(this.catName);
        productDetail.setCatImage(this.catImage);
        productDetail.setPartnerId(this.partnerId);
        productDetail.setPartnerImage(this.partnerImage);
        productDetail.setPartnerAddress(this.partnerAddress);

        return productDetail;
    }


}

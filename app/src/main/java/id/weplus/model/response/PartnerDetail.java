package id.weplus.model.response;

import java.util.ArrayList;

import id.weplus.model.BuyPolisModel;

public class PartnerDetail {
    private String id;
    private String name;
    private String image;
    private String address;
    private String desc;
    private String about;
    private ArrayList<BuyPolisModel.Categori> category;

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public ArrayList<BuyPolisModel.Categori> getCategory() {
        return category;
    }

    public void setCategory(ArrayList<BuyPolisModel.Categori> category) {
        this.category = category;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }
}

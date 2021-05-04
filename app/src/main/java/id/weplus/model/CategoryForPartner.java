package id.weplus.model;

import java.util.ArrayList;

public class CategoryForPartner {
    private int id;
    private String nik;
    private String name;
    private String desc;
    private int partner_id;
    private boolean status;
    private ArrayList<BuyPolisModel.Categori> category;
    private ArrayList<PartnerBanner> banner;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNik() {
        return nik;
    }

    public void setNik(String nik) {
        this.nik = nik;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getPartner_id() {
        return partner_id;
    }

    public void setPartner_id(int partner_id) {
        this.partner_id = partner_id;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public ArrayList<BuyPolisModel.Categori> getCategory() {
        return category;
    }

    public void setCategory(ArrayList<BuyPolisModel.Categori> category) {
        this.category = category;
    }

    public ArrayList<PartnerBanner> getBanner() {
        return banner;
    }

    public void setBanner(ArrayList<PartnerBanner> banner) {
        this.banner = banner;
    }
}

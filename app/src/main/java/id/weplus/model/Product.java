package id.weplus.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

public class Product implements Parcelable {
    private int id;
    private String name;
    private String image;
    private ArrayList<String> desc;
    private String additional_text;
    private String price;
    private String category_id;
    private String partner_id;
    private String partner_name;
    private String sum_insured;
    private String nominal;
    private String admin_fee;
    private String discount;

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getSum_insured() {
        return sum_insured;
    }

    public void setSum_insured(String sum_insured) {
        this.sum_insured = sum_insured;
    }

    public String getNominal() {
        return nominal;
    }

    public void setNominal(String nominal) {
        this.nominal = nominal;
    }

    public String getAdmin_fee() {
        return admin_fee;
    }

    public void setAdmin_fee(String admin_fee) {
        this.admin_fee = admin_fee;
    }

    public String getPartner_name() {
        return partner_name;
    }

    public void setPartner_name(String partner_name) {
        this.partner_name = partner_name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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


    public String getAdditional_text() {
        return additional_text;
    }

    public void setAdditional_text(String additional_text) {
        this.additional_text = additional_text;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
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

    public ArrayList<String> getDesc() {
        return desc;
    }

    public void setDesc(ArrayList<String> desc) {
        this.desc = desc;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeString(this.image);
        dest.writeStringList(this.desc);
        dest.writeString(this.additional_text);
        dest.writeString(this.price);
        dest.writeString(this.category_id);
        dest.writeString(this.partner_id);
        dest.writeString(this.partner_name);
        dest.writeString(this.sum_insured);
        dest.writeString(this.nominal);
        dest.writeString(this.admin_fee);
        dest.writeString(this.discount);
    }

    public Product() {
    }

    protected Product(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.image = in.readString();
        this.desc = in.createStringArrayList();
        this.additional_text = in.readString();
        this.price = in.readString();
        this.category_id = in.readString();
        this.partner_id = in.readString();
        this.partner_name = in.readString();
        this.sum_insured = in.readString();
        this.nominal = in.readString();
        this.admin_fee = in.readString();
        this.discount = in.readString();
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel source) {
            return new Product(source);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };
}

package id.weplus.model.request;

import android.os.Parcel;
import android.os.Parcelable;

public class GadgetProductRequest implements Parcelable {
    private String is_bundling;
    private String partner_weplus_id;
    private String nik;
    private String category_id;
    private String partner_id;
    private String is_company_base;
    private String gadget_price;
    private String gadget_age;
    private String gadget_type;
    private String gadget_name;
    private String gadget_brand;


    public GadgetProductRequest() {}

    public GadgetProductRequest(String category_id, String gadget_price) {
        this.category_id = category_id;
        this.gadget_price = gadget_price;
    }

    public String getGadget_age() {
        return gadget_age;
    }

    public void setGadget_age(String gadget_age) {
        this.gadget_age = gadget_age;
    }

    public String getGadget_type() {
        return gadget_type;
    }

    public void setGadget_type(String gadget_type) {
        this.gadget_type = gadget_type;
    }

    public String getGadget_name() {
        return gadget_name;
    }

    public void setGadget_name(String gadget_name) {
        this.gadget_name = gadget_name;
    }

    public String getGadget_brand() {
        return gadget_brand;
    }

    public void setGadget_brand(String gadget_brand) {
        this.gadget_brand = gadget_brand;
    }

    public String getIs_bundling() {
        return is_bundling;
    }

    public void setIs_bundling(String is_bundling) {
        this.is_bundling = is_bundling;
    }

    public String getPartner_weplus_id() {
        return partner_weplus_id;
    }

    public void setPartner_weplus_id(String partner_weplus_id) {
        this.partner_weplus_id = partner_weplus_id;
    }

    public String getNik() {
        return nik;
    }

    public void setNik(String nik) {
        this.nik = nik;
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

    public String getIs_company_base() {
        return is_company_base;
    }

    public void setIs_company_base(String is_company_base) {
        this.is_company_base = is_company_base;
    }

    public String getGadget_price() {
        return gadget_price;
    }

    public void setGadget_price(String gadget_price) {
        this.gadget_price = gadget_price;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.is_bundling);
        dest.writeString(this.partner_weplus_id);
        dest.writeString(this.nik);
        dest.writeString(this.category_id);
        dest.writeString(this.partner_id);
        dest.writeString(this.is_company_base);
        dest.writeString(this.gadget_price);
        dest.writeString(this.gadget_age);
        dest.writeString(this.gadget_type);
        dest.writeString(this.gadget_name);
        dest.writeString(this.gadget_brand);
    }

    protected GadgetProductRequest(Parcel in) {
        this.is_bundling = in.readString();
        this.partner_weplus_id = in.readString();
        this.nik = in.readString();
        this.category_id = in.readString();
        this.partner_id = in.readString();
        this.is_company_base = in.readString();
        this.gadget_price = in.readString();
        this.gadget_age = in.readString();
        this.gadget_type = in.readString();
        this.gadget_name = in.readString();
        this.gadget_brand = in.readString();
    }

    public static final Parcelable.Creator<GadgetProductRequest> CREATOR = new Parcelable.Creator<GadgetProductRequest>() {
        @Override
        public GadgetProductRequest createFromParcel(Parcel source) {
            return new GadgetProductRequest(source);
        }

        @Override
        public GadgetProductRequest[] newArray(int size) {
            return new GadgetProductRequest[size];
        }
    };
}

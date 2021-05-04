package id.weplus.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class ContactPartner implements Parcelable {
    private String partner_nama;
    private float partner_id;
    private String image;
    private ArrayList<ContactPartnerDetail> detail;

    public String getPartner_nama() {
        return partner_nama;
    }

    public void setPartner_nama(String partner_nama) {
        this.partner_nama = partner_nama;
    }

    public float getPartner_id() {
        return partner_id;
    }

    public void setPartner_id(float partner_id) {
        this.partner_id = partner_id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public ArrayList<ContactPartnerDetail> getDetail() {
        return detail;
    }

    public void setDetail(ArrayList<ContactPartnerDetail> detail) {
        this.detail = detail;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.partner_nama);
        dest.writeFloat(this.partner_id);
        dest.writeString(this.image);
        dest.writeTypedList(this.detail);
    }

    public ContactPartner() {
    }

    protected ContactPartner(Parcel in) {
        this.partner_nama = in.readString();
        this.partner_id = in.readFloat();
        this.image = in.readString();
        this.detail = in.createTypedArrayList(ContactPartnerDetail.CREATOR);
    }

    public static final Parcelable.Creator<ContactPartner> CREATOR = new Parcelable.Creator<ContactPartner>() {
        @Override
        public ContactPartner createFromParcel(Parcel source) {
            return new ContactPartner(source);
        }

        @Override
        public ContactPartner[] newArray(int size) {
            return new ContactPartner[size];
        }
    };
}

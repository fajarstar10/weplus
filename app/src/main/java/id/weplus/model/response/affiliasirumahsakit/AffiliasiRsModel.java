package id.weplus.model.response.affiliasirumahsakit;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class AffiliasiRsModel implements Parcelable {
    private String name;
    private String address;
    private String province;
    private String city;
    ArrayList< String > phone = new ArrayList < String > ();
    private boolean is_inpatient;
    private boolean is_outpatient;
    private boolean is_dental;
    private boolean is_lab;
    private boolean is_phar;
    private boolean is_optic;
    private boolean is_individual;
    private boolean is_bpjs_faskes;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public ArrayList<String> getPhone() {
        return phone;
    }

    public void setPhone(ArrayList<String> phone) {
        this.phone = phone;
    }

    public boolean isIs_inpatient() {
        return is_inpatient;
    }

    public void setIs_inpatient(boolean is_inpatient) {
        this.is_inpatient = is_inpatient;
    }

    public boolean isIs_outpatient() {
        return is_outpatient;
    }

    public void setIs_outpatient(boolean is_outpatient) {
        this.is_outpatient = is_outpatient;
    }

    public boolean isIs_dental() {
        return is_dental;
    }

    public void setIs_dental(boolean is_dental) {
        this.is_dental = is_dental;
    }

    public boolean isIs_lab() {
        return is_lab;
    }

    public void setIs_lab(boolean is_lab) {
        this.is_lab = is_lab;
    }

    public boolean isIs_phar() {
        return is_phar;
    }

    public void setIs_phar(boolean is_phar) {
        this.is_phar = is_phar;
    }

    public boolean isIs_optic() {
        return is_optic;
    }

    public void setIs_optic(boolean is_optic) {
        this.is_optic = is_optic;
    }

    public boolean isIs_individual() {
        return is_individual;
    }

    public void setIs_individual(boolean is_individual) {
        this.is_individual = is_individual;
    }

    public boolean isIs_bpjs_faskes() {
        return is_bpjs_faskes;
    }

    public void setIs_bpjs_faskes(boolean is_bpjs_faskes) {
        this.is_bpjs_faskes = is_bpjs_faskes;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.address);
        dest.writeString(this.province);
        dest.writeString(this.city);
        dest.writeStringList(this.phone);
        dest.writeByte(this.is_inpatient ? (byte) 1 : (byte) 0);
        dest.writeByte(this.is_outpatient ? (byte) 1 : (byte) 0);
        dest.writeByte(this.is_dental ? (byte) 1 : (byte) 0);
        dest.writeByte(this.is_lab ? (byte) 1 : (byte) 0);
        dest.writeByte(this.is_phar ? (byte) 1 : (byte) 0);
        dest.writeByte(this.is_optic ? (byte) 1 : (byte) 0);
        dest.writeByte(this.is_individual ? (byte) 1 : (byte) 0);
        dest.writeByte(this.is_bpjs_faskes ? (byte) 1 : (byte) 0);
    }

    public AffiliasiRsModel() {
    }

    protected AffiliasiRsModel(Parcel in) {
        this.name = in.readString();
        this.address = in.readString();
        this.province = in.readString();
        this.city = in.readString();
        this.phone = in.createStringArrayList();
        this.is_inpatient = in.readByte() != 0;
        this.is_outpatient = in.readByte() != 0;
        this.is_dental = in.readByte() != 0;
        this.is_lab = in.readByte() != 0;
        this.is_phar = in.readByte() != 0;
        this.is_optic = in.readByte() != 0;
        this.is_individual = in.readByte() != 0;
        this.is_bpjs_faskes = in.readByte() != 0;
    }

    public static final Parcelable.Creator<AffiliasiRsModel> CREATOR = new Parcelable.Creator<AffiliasiRsModel>() {
        @Override
        public AffiliasiRsModel createFromParcel(Parcel source) {
            return new AffiliasiRsModel(source);
        }

        @Override
        public AffiliasiRsModel[] newArray(int size) {
            return new AffiliasiRsModel[size];
        }
    };
}

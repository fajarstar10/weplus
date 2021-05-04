package id.weplus.model.response.affiliasirumahsakit;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class AffiliasiRsData implements Parcelable {
    @SerializedName("hospital")
    private ArrayList<AffiliasiRsModel> hospital;

    public ArrayList<AffiliasiRsModel> getHospital() {
        return hospital;
    }

    public void setHospital(ArrayList<AffiliasiRsModel> hospital) {
        this.hospital = hospital;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(this.hospital);
    }

    public AffiliasiRsData() {
    }

    protected AffiliasiRsData(Parcel in) {
        this.hospital = new ArrayList<AffiliasiRsModel>();
        in.readList(this.hospital, AffiliasiRsModel.class.getClassLoader());
    }

    public static final Parcelable.Creator<AffiliasiRsData> CREATOR = new Parcelable.Creator<AffiliasiRsData>() {
        @Override
        public AffiliasiRsData createFromParcel(Parcel source) {
            return new AffiliasiRsData(source);
        }

        @Override
        public AffiliasiRsData[] newArray(int size) {
            return new AffiliasiRsData[size];
        }
    };
}

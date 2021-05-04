package id.weplus.model.response.afiliasibengkel;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class AffiliasiBengkelData implements Parcelable {

    @SerializedName("bengkel")
    private ArrayList<AffiliasiBengkelModel> bengkel;

    public ArrayList<AffiliasiBengkelModel> getBengkel() {
        return bengkel;
    }

    public void setBengkel(ArrayList<AffiliasiBengkelModel> bengkel) {
        this.bengkel = bengkel;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(this.bengkel);
    }

    public AffiliasiBengkelData() {
    }

    protected AffiliasiBengkelData(Parcel in) {
        this.bengkel = new ArrayList<AffiliasiBengkelModel>();
        in.readList(this.bengkel, AffiliasiBengkelModel.class.getClassLoader());
    }

    public static final Parcelable.Creator<AffiliasiBengkelData> CREATOR = new Parcelable.Creator<AffiliasiBengkelData>() {
        @Override
        public AffiliasiBengkelData createFromParcel(Parcel source) {
            return new AffiliasiBengkelData(source);
        }

        @Override
        public AffiliasiBengkelData[] newArray(int size) {
            return new AffiliasiBengkelData[size];
        }
    };
}

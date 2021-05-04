package id.weplus.model.response.agent;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class AgentData implements Parcelable {
    @SerializedName("image_profile")
    private String imageProfile;
    @SerializedName("fullname")
    private String fullName;
    @SerializedName("saldo")
    private String saldo;
    @SerializedName("total_komisi")
    private String totalKomisi;
    @SerializedName("total_transaksi")
    private String totalTransakti;
    @SerializedName("total_transaksi_triwulan")
    private String totalTransaksiTriwulan;
    @SerializedName("total_polis_aktif")
    private String totalPolisAktif;
    @SerializedName("total_nominal_triwulan")
    private String totalMonimalTriwulan;
    @SerializedName("target_triwulan")
    private String targetTriwulan;
    @SerializedName("progress_transaksi_triwulan")
    private String progressTransaksiTriwulan;
    @SerializedName("date_end")
    private String dateEnd;
    @SerializedName("list_menu")
    private ArrayList<AgentMenu> listMenu;


    public String getImageProfile() {
        return imageProfile;
    }

    public void setImageProfile(String imageProfile) {
        this.imageProfile = imageProfile;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getSaldo() {
        return saldo;
    }

    public void setSaldo(String saldo) {
        this.saldo = saldo;
    }

    public String getTotalKomisi() {
        return totalKomisi;
    }

    public void setTotalKomisi(String totalKomisi) {
        this.totalKomisi = totalKomisi;
    }

    public String getTotalTransakti() {
        return totalTransakti;
    }

    public void setTotalTransakti(String totalTransakti) {
        this.totalTransakti = totalTransakti;
    }

    public String getTotalTransaksiTriwulan() {
        return totalTransaksiTriwulan;
    }

    public void setTotalTransaksiTriwulan(String totalTransaksiTriwulan) {
        this.totalTransaksiTriwulan = totalTransaksiTriwulan;
    }

    public String getTotalPolisAktif() {
        return totalPolisAktif;
    }

    public void setTotalPolisAktif(String totalPolisAktif) {
        this.totalPolisAktif = totalPolisAktif;
    }

    public String getTotalMonimalTriwulan() {
        return totalMonimalTriwulan;
    }

    public void setTotalMonimalTriwulan(String totalMonimalTriwulan) {
        this.totalMonimalTriwulan = totalMonimalTriwulan;
    }

    public String getTargetTriwulan() {
        return targetTriwulan;
    }

    public void setTargetTriwulan(String targetTriwulan) {
        this.targetTriwulan = targetTriwulan;
    }

    public String getProgressTransaksiTriwulan() {
        return progressTransaksiTriwulan;
    }

    public void setProgressTransaksiTriwulan(String progressTransaksiTriwulan) {
        this.progressTransaksiTriwulan = progressTransaksiTriwulan;
    }

    public String getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(String dateEnd) {
        this.dateEnd = dateEnd;
    }

    public ArrayList<AgentMenu> getListMenu() {
        return listMenu;
    }

    public void setListMenu(ArrayList<AgentMenu> listMenu) {
        this.listMenu = listMenu;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.imageProfile);
        dest.writeString(this.fullName);
        dest.writeString(this.saldo);
        dest.writeString(this.totalKomisi);
        dest.writeString(this.totalTransakti);
        dest.writeString(this.totalTransaksiTriwulan);
        dest.writeString(this.totalPolisAktif);
        dest.writeString(this.totalMonimalTriwulan);
        dest.writeString(this.targetTriwulan);
        dest.writeString(this.progressTransaksiTriwulan);
        dest.writeString(this.dateEnd);
        dest.writeList(this.listMenu);
    }

    public AgentData() {
    }

    protected AgentData(Parcel in) {
        this.imageProfile = in.readString();
        this.fullName = in.readString();
        this.saldo = in.readString();
        this.totalKomisi = in.readString();
        this.totalTransakti = in.readString();
        this.totalTransaksiTriwulan = in.readString();
        this.totalPolisAktif = in.readString();
        this.totalMonimalTriwulan = in.readString();
        this.targetTriwulan = in.readString();
        this.progressTransaksiTriwulan = in.readString();
        this.dateEnd = in.readString();
        this.listMenu = new ArrayList<AgentMenu>();
        in.readList(this.listMenu, AgentMenu.class.getClassLoader());
    }

    public static final Parcelable.Creator<AgentData> CREATOR = new Parcelable.Creator<AgentData>() {
        @Override
        public AgentData createFromParcel(Parcel source) {
            return new AgentData(source);
        }

        @Override
        public AgentData[] newArray(int size) {
            return new AgentData[size];
        }
    };
}

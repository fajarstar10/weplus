package id.weplus.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;


public class Cicilan implements Parcelable {
    private String name;
    @SerializedName("transaction_id")
    private String transactionId;
    private String status;
    private String nominal;
    @SerializedName("jatuh_tempo")
    private String jatuhTempo;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNominal() {
        return nominal;
    }

    public void setNominal(String nominal) {
        this.nominal = nominal;
    }

    public String getJatuhTempo() {
        return jatuhTempo;
    }

    public void setJatuhTempo(String jatuhTempo) {
        this.jatuhTempo = jatuhTempo;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.transactionId);
        dest.writeString(this.status);
        dest.writeString(this.nominal);
        dest.writeString(this.jatuhTempo);
    }


    protected Cicilan(Parcel in) {
        this.name = in.readString();
        this.transactionId = in.readString();
        this.status = in.readString();
        this.nominal = in.readString();
        this.jatuhTempo = in.readString();
    }

    public static final Creator<Cicilan> CREATOR = new Creator<Cicilan>() {
        @Override
        public Cicilan createFromParcel(Parcel source) {
            return new Cicilan(source);
        }

        @Override
        public Cicilan[] newArray(int size) {
            return new Cicilan[size];
        }
    };
}

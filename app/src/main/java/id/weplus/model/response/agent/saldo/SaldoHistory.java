package id.weplus.model.response.agent.saldo;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class SaldoHistory implements Parcelable {
    @SerializedName("request_withdraw_id")
    private String requestWithdrawId;
    private String title;
    private String desc;
    private String total;
    private String type;
    private String date;
    private String image;
    private String status;

    public String getRequestWithdrawId() {
        return requestWithdrawId;
    }

    public void setRequestWithdrawId(String requestWithdrawId) {
        this.requestWithdrawId = requestWithdrawId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.requestWithdrawId);
        dest.writeString(this.title);
        dest.writeString(this.desc);
        dest.writeString(this.total);
        dest.writeString(this.type);
        dest.writeString(this.date);
        dest.writeString(this.image);
        dest.writeString(this.status);
    }

    public SaldoHistory() {
    }

    protected SaldoHistory(Parcel in) {
        this.requestWithdrawId = in.readString();
        this.title = in.readString();
        this.desc = in.readString();
        this.total = in.readString();
        this.type = in.readString();
        this.date = in.readString();
        this.image = in.readString();
        this.status = in.readString();
    }

    public static final Parcelable.Creator<SaldoHistory> CREATOR = new Parcelable.Creator<SaldoHistory>() {
        @Override
        public SaldoHistory createFromParcel(Parcel source) {
            return new SaldoHistory(source);
        }

        @Override
        public SaldoHistory[] newArray(int size) {
            return new SaldoHistory[size];
        }
    };
}

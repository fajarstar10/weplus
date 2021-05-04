package id.weplus.model.response.agent;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import id.weplus.model.response.BengkelAreaData;

public class AgentResponse implements Parcelable {
    private String code;
    private boolean status;
    private String message;
    @SerializedName("data")
    private AgentData data;


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public AgentData getData() {
        return data;
    }

    public void setData(AgentData data) {
        this.data = data;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.code);
        dest.writeByte(this.status ? (byte) 1 : (byte) 0);
        dest.writeString(this.message);
        dest.writeParcelable(this.data, flags);
    }

    public AgentResponse() {
    }

    protected AgentResponse(Parcel in) {
        this.code = in.readString();
        this.status = in.readByte() != 0;
        this.message = in.readString();
        this.data = in.readParcelable(AgentData.class.getClassLoader());
    }

    public static final Parcelable.Creator<AgentResponse> CREATOR = new Parcelable.Creator<AgentResponse>() {
        @Override
        public AgentResponse createFromParcel(Parcel source) {
            return new AgentResponse(source);
        }

        @Override
        public AgentResponse[] newArray(int size) {
            return new AgentResponse[size];
        }
    };
}

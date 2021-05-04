package id.weplus.model.response.affiliasirumahsakit;

import com.google.gson.annotations.SerializedName;

public class RsOptionResponse {
    private String code;
    private boolean status;
    private String message;
    @SerializedName("data")
    private RsOptionData data;

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

    public RsOptionData getData() {
        return data;
    }

    public void setData(RsOptionData data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "RsOptionResponse{" +
                "code='" + code + '\'' +
                ", status=" + status +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}

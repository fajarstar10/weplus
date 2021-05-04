package id.weplus.model.response.affiliasirumahsakit;

import com.google.gson.annotations.SerializedName;

public class AffiliasiRsResponse {
    private String code;
    private boolean status;
    private String message;
    @SerializedName("data")
    private AffiliasiRsData data;

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

    public AffiliasiRsData getData() {
        return data;
    }

    public void setData(AffiliasiRsData data) {
        this.data = data;
    }
}

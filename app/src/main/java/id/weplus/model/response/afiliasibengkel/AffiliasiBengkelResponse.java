package id.weplus.model.response.afiliasibengkel;

import com.google.gson.annotations.SerializedName;

import id.weplus.model.response.BengkelAreaData;

public class AffiliasiBengkelResponse {
    private String code;
    private boolean status;
    private String message;
    @SerializedName("data")
    private AffiliasiBengkelData data;

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

    public AffiliasiBengkelData getData() {
        return data;
    }

    public void setData(AffiliasiBengkelData data) {
        this.data = data;
    }
}

package id.weplus.model.response.insureduser;

import com.google.gson.annotations.SerializedName;

import id.weplus.model.response.BengkelAreaData;

public class InsuredUserResponse {
    private String code;
    private boolean status;
    private String message;
    @SerializedName("data")
    private InsuredUserData data;

    public String getCode() {
        return code;
    }

    public boolean isStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public InsuredUserData getData() {
        return data;
    }
}

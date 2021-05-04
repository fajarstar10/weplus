package id.weplus.model.response;

import com.google.gson.annotations.SerializedName;

import id.weplus.model.ProductListData;

public class TravelFilterResponse {
    private String code;
    private boolean status;
    private String message;
    @SerializedName("data")
    private TravelFilterData data;

    public TravelFilterData getData() {
        return data;
    }

    public void setData(TravelFilterData data) {
        this.data = data;
    }

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

}

package id.weplus.model.response.gadget;

import com.google.gson.annotations.SerializedName;

public class GadgetBrandResponse {
    private String code;
    private boolean status;
    private String message;
    @SerializedName("data")
    private GadgetBrand data;

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

    public GadgetBrand getData() {
        return data;
    }

    public void setData(GadgetBrand data) {
        this.data = data;
    }
}

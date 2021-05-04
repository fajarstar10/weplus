package id.weplus.model.response.gadget;

import com.google.gson.annotations.SerializedName;

public class GadgetCategoryResponse {
    private String code;
    private boolean status;
    private String message;
    @SerializedName("data")
    private GadgetData data;

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

    public GadgetData getData() {
        return data;
    }

    public void setData(GadgetData data) {
        this.data = data;
    }
}

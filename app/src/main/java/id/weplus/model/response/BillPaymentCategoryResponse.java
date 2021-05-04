package id.weplus.model.response;

import com.google.gson.annotations.SerializedName;

import id.weplus.model.BillsCategory;

public class BillPaymentCategoryResponse {
    private String code;
    private boolean status;
    private String message;
    @SerializedName("data")
    private BillsCategory data;

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

    public BillsCategory getData() {
        return data;
    }

    public void setData(BillsCategory data) {
        this.data = data;
    }
}

package id.weplus.model.response;

import com.google.gson.annotations.SerializedName;

public class BillInquiryResponse {
    private String code;
    private boolean status;
    private String message;
    @SerializedName("data")
    private BillInquiryData data;

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

    public BillInquiryData getData() {
        return data;
    }

    public void setData(BillInquiryData data) {
        this.data = data;
    }
}

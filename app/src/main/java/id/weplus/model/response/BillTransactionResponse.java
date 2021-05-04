package id.weplus.model.response;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import id.weplus.model.BillTransaction;
import id.weplus.model.BillsCategory;

public class BillTransactionResponse {
    private String code;
    private boolean status;
    private String message;
    @SerializedName("data")
    private BillTransactionData data;

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

    public BillTransactionData getData() {
        return data;
    }

    public void setData(BillTransactionData data) {
        this.data = data;
    }


}

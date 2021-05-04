package id.weplus.model.response;

import com.google.gson.annotations.SerializedName;

import id.weplus.model.ProductDetail;

public class WithdrawSaldoResponse {
    private String code;
    private boolean status;
    private String message;

    public String getCode() {
        return code;
    }

    public boolean isStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

}

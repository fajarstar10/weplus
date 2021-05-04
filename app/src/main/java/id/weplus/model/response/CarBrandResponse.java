package id.weplus.model.response;

import java.util.ArrayList;

import id.weplus.model.Brand;

public class CarBrandResponse {
    private String code;
    private boolean status;
    private String message;
    private BrandData data;


    // Getter Methods

    public String getCode() {
        return code;
    }

    public boolean getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public BrandData getData() {
        return data;
    }

    // Setter Methods

    public void setCode(String code) {
        this.code = code;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setData(BrandData dataObject) {
        this.data = dataObject;
    }
}


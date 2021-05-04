package id.weplus.model.response;

import id.weplus.model.CategoryForPartner;

public class CategoryForPartnerResponse {
    private String code;
    private boolean status;
    private String message;
    private CategoryForPartner data;

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

    public CategoryForPartner getData() {
        return data;
    }

    public void setData(CategoryForPartner data) {
        this.data = data;
    }
}

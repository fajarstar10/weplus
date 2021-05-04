package id.weplus.model.response;

import id.weplus.model.CarFilter;

public class HealthFilterResponse {
    private String code;
    private boolean status;
    private String message;
    private HealthFilter data;

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

    public HealthFilter getData() {
        return data;
    }

    public void setData(HealthFilter data) {
        this.data = data;
    }
}

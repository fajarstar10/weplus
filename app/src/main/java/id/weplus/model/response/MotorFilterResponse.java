package id.weplus.model.response;

import id.weplus.model.MotorFilter;

public class MotorFilterResponse {
    private String code;
    private boolean status;
    private String message;
    private MotorFilter data;

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

    public MotorFilter getData() {
        return data;
    }

    public void setData(MotorFilter data) {
        this.data = data;
    }
}

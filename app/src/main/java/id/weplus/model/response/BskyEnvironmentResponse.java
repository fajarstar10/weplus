package id.weplus.model.response;

import id.weplus.model.BskyEnvironmentData;
import id.weplus.model.MotorFilter;

public class BskyEnvironmentResponse {
    private String code;
    private boolean status;
    private String message;
    private BskyEnvironmentData data;

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

    public BskyEnvironmentData getData() {
        return data;
    }

    public void setData(BskyEnvironmentData data) {
        this.data = data;
    }
}

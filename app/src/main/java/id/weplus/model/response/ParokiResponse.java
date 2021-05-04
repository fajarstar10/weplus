package id.weplus.model.response;

import id.weplus.model.MotorFilter;
import id.weplus.model.ParokiData;

public class ParokiResponse {
    private String code;
    private boolean status;
    private String message;

    public ParokiData getData() {
        return data;
    }

    public void setData(ParokiData data) {
        this.data = data;
    }

    private ParokiData data;

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


}

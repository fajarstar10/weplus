package id.weplus.model.response;

import id.weplus.model.CheckResult;

public class CheckEmployeeResponse {
    private String code;
    private boolean status;
    private String message;
    private CheckResult data;


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

    public CheckResult getData() {
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

    public void setData(CheckResult dataObject) {
        this.data = dataObject;
    }
}

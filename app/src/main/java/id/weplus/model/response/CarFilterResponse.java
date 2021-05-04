package id.weplus.model.response;

import id.weplus.model.CarFilter;

public class CarFilterResponse {
    private String code;
    private boolean status;
    private String message;
    private CarFilter data;

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

    public CarFilter getData() {
        return data;
    }

    public void setData(CarFilter data) {
        this.data = data;
    }
}

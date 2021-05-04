package id.weplus.model.response;

public class CarTypeAndPriceResponse {
    private String code;
    private boolean status;
    private String message;
    private CarData data;

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

    public CarData getData() {
        return data;
    }

    public void setData(CarData data) {
        this.data = data;
    }
}

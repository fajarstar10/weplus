package id.weplus.model.response;

public class FlightCheckerResponse {
    private String code;
    private boolean status;
    private String message;
    private FlightListData data;

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

    public FlightListData getData() {
        return data;
    }

    public void setData(FlightListData data) {
        this.data = data;
    }
}

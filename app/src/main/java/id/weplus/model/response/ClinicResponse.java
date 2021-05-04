package id.weplus.model.response;

public class ClinicResponse {
    private String code;
    private boolean status;
    private String message;
    private ClinicData data;

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

    public ClinicData getData() {
        return data;
    }

    public void setData(ClinicData data) {
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}

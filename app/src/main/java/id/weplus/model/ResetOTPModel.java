package id.weplus.model;

public class ResetOTPModel {
    private String code;
    private boolean status;
    private String message;
    public ResetOTPData data;

    @Override
    public String toString() {
        return "ResetOTP{" +
                "code='" + code + '\'' +
                ", status='" + status + '\'' +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
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

    public ResetOTPData getData() {
        return data;
    }

    public void setData(ResetOTPData data) {
        this.data = data;
    }

    public class ResetOTPData {

    }
}

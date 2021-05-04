package id.weplus.model;

public class ResetPasswordModel {
    private String code;
    private boolean status;
    private String message;
    public ResetPasswordData data;

    @Override
    public String toString() {
        return "ResetPassword{" +
                "code='" + code + '\'' +
                ", status=" + status +
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

    public ResetPasswordData getData() {
        return data;
    }

    public void setData(ResetPasswordData data) {
        this.data = data;
    }

    public class ResetPasswordData {

    }
}

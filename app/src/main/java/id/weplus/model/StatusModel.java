package id.weplus.model;

public class StatusModel {
    private String code;
    private boolean status;
    private String message;

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

    @Override
    public String toString() {
        return "StatusModel{" +
                "code='" + code + '\'' +
                ", status=" + status +
                ", message='" + message + '\'' +
                '}';
    }
}

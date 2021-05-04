package id.weplus.model;

public class RedeemCodeProtectionZurichModel {
    private String code;
    private boolean status;
    private String message;
    public ProtectionZurichData data;

    @Override
    public String toString() {
        return "ProtectionZurich{" +
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

    public ProtectionZurichData getData() {
        return data;
    }

    public void setData(ProtectionZurichData data) {
        this.data = data;
    }

    public class ProtectionZurichData {

    }
}

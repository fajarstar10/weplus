package id.weplus.model;

public class RedeemCodeProtectionMudikAcaModel {
    private String code;
    private boolean status;
    private String message;
    public ProtectionMudikAcaData data;

    @Override
    public String toString() {
        return "ProtectionMudikAca{" +
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

    public ProtectionMudikAcaData getData() {
        return data;
    }

    public void setData(ProtectionMudikAcaData data) {
        this.data = data;
    }

    public class ProtectionMudikAcaData {

    }
}

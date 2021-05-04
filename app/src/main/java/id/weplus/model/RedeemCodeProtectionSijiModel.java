package id.weplus.model;

public class RedeemCodeProtectionSijiModel {
    private String code;
    private boolean status;
    private String message;
    public ProtectionSijiData data;

    @Override
    public String toString() {
        return "ProtectionSiji{" +
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

    public ProtectionSijiData getData() {
        return data;
    }

    public void setData(ProtectionSijiData data) {
        this.data = data;
    }

    public class ProtectionSijiData {

    }

}

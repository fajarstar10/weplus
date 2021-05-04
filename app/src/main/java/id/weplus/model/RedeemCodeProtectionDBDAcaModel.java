package id.weplus.model;

public class RedeemCodeProtectionDBDAcaModel {
    private String code;
    private boolean status;
    private String message;
    public ProtectionDBDData data;

    @Override
    public String toString() {
        return "ProtectionDBD{" +
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

    public ProtectionDBDData getData() {
        return data;
    }

    public void setData(ProtectionDBDData data) {
        this.data = data;
    }

    public class ProtectionDBDData {

    }
}

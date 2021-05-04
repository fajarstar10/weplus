package id.weplus.model;

public class RedeemCodeProtectionFlightDelayModel {
    private String code;
    private boolean status;
    private String message;
    public ProtectionFlightDelayData data;

    @Override
    public String toString() {
        return "ProtectionFlightDelay{" +
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

    public ProtectionFlightDelayData getData() {
        return data;
    }

    public void setData(ProtectionFlightDelayData data) {
        this.data = data;
    }

    public class ProtectionFlightDelayData {

    }
}

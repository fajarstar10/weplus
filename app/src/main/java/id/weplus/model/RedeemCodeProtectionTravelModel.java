package id.weplus.model;

public class RedeemCodeProtectionTravelModel {
    private String code;
    private boolean status;
    private String message;
    public ProtectionTravelData data;

    @Override
    public String toString() {
        return "ProtectionTravel{" +
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

    public ProtectionTravelData getData() {
        return data;
    }

    public void setData(ProtectionTravelData data) {
        this.data = data;
    }

    public class ProtectionTravelData {

    }
}

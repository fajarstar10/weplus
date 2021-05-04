package id.weplus.model.response;

import id.weplus.model.TransactionDetail;

public class TransactionDetailResponse {
    private String code;
    private boolean status;
    private String message;
    public TransactionDetail data;

    @Override
    public String toString() {
        return "TransactionDetail{" +
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

    public TransactionDetail getData() {
        return data;
    }

    public void setData(TransactionDetail data) {
        this.data = data;
    }
}

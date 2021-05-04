package id.weplus.model.response;

import id.weplus.model.Insured;
import id.weplus.model.InsuredDetail;
import id.weplus.model.InsuredDetailModel;
import id.weplus.model.TransactionDetail;

public class InsuredDetailRespon {
    private String code;
    private boolean status;
    private String message;
    public InsuredDetail data;

    @Override
    public String toString() {
        return "InsuredDetail{" +
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

    public InsuredDetail getData() {
        return data;
    }

    public void setData(InsuredDetail data) {
        this.data = data;
    }
}

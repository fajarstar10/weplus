package id.weplus.model;

import java.util.List;

public class InsuredListModel {
    private String code;
    private boolean status;
    private String message;
    public InsuredData data;

    @Override
    public String toString() {
        return "InsuredListModel{" +
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


    public List<Insured> getData() {
        return data.getInsured();
    }

    public void setData(List<Insured> data) {
        this.data.setInsured(data);
    }



}

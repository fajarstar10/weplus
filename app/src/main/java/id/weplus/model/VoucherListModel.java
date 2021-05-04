package id.weplus.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class VoucherListModel {
    private String code;
    private boolean status;
    private String message;
    public VoucherListData data;

    @Override
    public String toString() {
        return "VoucherListModel{" +
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

    public List<Voucher> getData() {
        return data.getVoucher();
    }

    public void setData(List<Voucher> data) {
        this.data.setVoucher(data);
    }


}

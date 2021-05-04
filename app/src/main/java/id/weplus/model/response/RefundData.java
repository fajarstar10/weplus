package id.weplus.model.response;

import com.google.gson.annotations.SerializedName;

public class RefundData {
    @SerializedName("data")
    private RefundDetail data;

    public RefundDetail getData() {
        return data;
    }

    public void setData(RefundDetail data) {
        this.data = data;
    }
}

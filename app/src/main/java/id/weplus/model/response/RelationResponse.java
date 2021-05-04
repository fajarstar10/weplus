package id.weplus.model.response;

import com.google.gson.annotations.SerializedName;

import id.weplus.model.ProductListData;
import id.weplus.model.RelationData;

public class RelationResponse {
    private String code;
    private boolean status;
    private String message;
    @SerializedName("data")
    private RelationData data;

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

    public RelationData getData() {
        return data;
    }

    public void setData(RelationData data) {
        this.data = data;
    }
}

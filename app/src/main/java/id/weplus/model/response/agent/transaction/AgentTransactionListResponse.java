package id.weplus.model.response.agent.transaction;

import com.google.gson.annotations.SerializedName;

import id.weplus.model.response.agent.AgentData;

public class AgentTransactionListResponse {
    private String code;
    private boolean status;
    private String message;
    @SerializedName("data")
    private AgentTransactionListData data;

    public String getCode() {
        return code;
    }

    public boolean isStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public AgentTransactionListData getData() {
        return data;
    }
}

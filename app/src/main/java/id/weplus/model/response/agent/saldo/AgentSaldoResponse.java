package id.weplus.model.response.agent.saldo;

import com.google.gson.annotations.SerializedName;

import id.weplus.model.response.agent.AgentData;

public class AgentSaldoResponse {
    private String code;
    private boolean status;
    private String message;
    @SerializedName("data")
    private AgentSaldoData data;

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

    public AgentSaldoData getData() {
        return data;
    }

    public void setData(AgentSaldoData data) {
        this.data = data;
    }
}

package id.weplus.model.response.mudik;

import id.weplus.model.MotorFilter;

public class MudikSimasResponse {
    private String code;
    private boolean status;
    private String message;
    private MudikSimasData data;

    public String getCode() {
        return code;
    }

    public boolean isStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public MudikSimasData getData() {
        return data;
    }
}

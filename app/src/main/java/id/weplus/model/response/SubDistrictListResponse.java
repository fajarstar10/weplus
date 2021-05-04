package id.weplus.model.response;


import id.weplus.model.SubDistrictListData;

public class SubDistrictListResponse {
    private String code;
    private boolean status;
    private String message;
    private SubDistrictListData data;

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

    public SubDistrictListData getData() {
        return data;
    }

    public void setData(SubDistrictListData data) {
        this.data = data;
    }
}


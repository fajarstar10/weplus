package id.weplus.model.response;


public class ProvinceListResponse {
    private String code;
    private boolean status;
    private String message;
    private ProvinceListData data;

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

    public ProvinceListData getData() {
        return data;
    }

    public void setData(ProvinceListData data) {
        this.data = data;
    }
}

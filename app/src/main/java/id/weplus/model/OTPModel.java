package id.weplus.model;

public class OTPModel {

    private String code;
    private String status;
    private String message;
    private DataOTP data;

    @Override
    public String toString() {
        return "OTPModel{" +
                "code='" + code + '\'' +
                ", status='" + status + '\'' +
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataOTP getData() {
        return data;
    }

    public void setData(DataOTP data) {
        this.data = data;
    }

    public class DataOTP{
        private String otp;

        @Override
        public String toString() {
            return "DataOTP{" +
                    "otp='" + otp + '\'' +
                    '}';
        }

        public String getOtp() {
            return otp;
        }

        public void setOtp(String otp) {
            this.otp = otp;
        }
    }
}

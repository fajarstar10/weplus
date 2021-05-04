package id.weplus.Query;

public class RegisterConfirmQuery {
    private String otp;

    @Override
    public String toString() {
        return "RegisterConfirmQuery{" +
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

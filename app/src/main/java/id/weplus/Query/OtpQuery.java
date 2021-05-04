package id.weplus.Query;

public class OtpQuery {
    private String otp;

    @Override
    public String toString() {
        return "OtpQuery{" +
                "otp='" + otp + '\'';
    }


    public void
    setOtp(String otp) {
        this.otp = otp;
    }
}

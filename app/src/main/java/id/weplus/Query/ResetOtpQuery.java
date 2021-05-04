package id.weplus.Query;

public class ResetOtpQuery {
    public class OtpQuery {
        private String otp;

        @Override
        public String toString() {
            return "ResetOtpQuery{" +
                    "otp='" + otp + '\'';
        }


        public void
        setOtp(String otp) {
            this.otp = otp;
        }
    }
}

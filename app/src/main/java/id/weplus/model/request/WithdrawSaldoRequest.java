package id.weplus.model.request;

public class WithdrawSaldoRequest {
    private String payment_channel;
    private String nominal;

    public WithdrawSaldoRequest(String payment_channel, String nominal) {
        this.payment_channel = payment_channel;
        this.nominal = nominal;
    }
}

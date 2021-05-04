package id.weplus.model.request;

import com.google.gson.annotations.SerializedName;

public class PaymentMethodRequest {
    private int nominal;
    @SerializedName("product_id")
    private int productId;
    @SerializedName("voucher_code")
    private String voucherCode;

    public int getNominal() {
        return nominal;
    }

    public void setNominal(int nominal) {
        this.nominal = nominal;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getVoucherCode() {
        return voucherCode;
    }

    public void setVoucherCode(String voucherCode) {
        this.voucherCode = voucherCode;
    }
}

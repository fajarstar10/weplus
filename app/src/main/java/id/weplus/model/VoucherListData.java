package id.weplus.model;

import java.util.List;

public class VoucherListData {
    private List<Voucher> voucher;

    public VoucherListData(List<Voucher> voucher) {
        this.voucher = voucher;
    }

    public List<Voucher> getVoucher() {
        return voucher;
    }

    public void setVoucher(List<Voucher> voucher) {
        this.voucher = voucher;
    }
}

package id.weplus.transaksi;

import android.os.Bundle;

import butterknife.ButterKnife;
import id.weplus.BaseActivity;
import id.weplus.R;

public class PetunjukPembayaran extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_petunjuk_pembayaran);
        ButterKnife.bind(this);
    }
}

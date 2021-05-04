package id.weplus.Tagihan;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.weplus.BaseActivity;
import id.weplus.R;
import id.weplus.model.Payment;
import id.weplus.pembayaran.OnPaymentClicked;
import id.weplus.pembayaran.PembayaranAdapter;

public class BayarBPJSActivity extends BaseActivity implements OnPaymentClicked {
    @BindView(R.id.viewback_title) TextView title;
    @BindView(R.id.viewback_description) TextView description;
    @BindView(R.id.recycleview_pembayaran) RecyclerView recyclerViewPembayaran;
    @BindView(R.id.pembayaran_kode_voucher_tdk_ditemukan) TextView kodeVoucherTdkDitemukan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bayar_bpjs);
        ButterKnife.bind(this);
        title.setText(getString(R.string.bayartagihanbpjs));
        description.setText(getString(R.string.lakukanberbagaipembayaranmelaluiweplus));

        recyclerViewPembayaran.setNestedScrollingEnabled(true);
        PembayaranAdapter pembAdapter = new PembayaranAdapter(this,this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewPembayaran.setLayoutManager(mLayoutManager);
        recyclerViewPembayaran.setAdapter(pembAdapter);
    }

    @OnClick(R.id.viewback_buttonback)
    public void actionBackPembayaran(){finish();}

    @OnClick(R.id.pembayaran_btn_beli_skrg)
    public void beliSekarang(){
        Intent intent = new Intent(BayarBPJSActivity.this, KonfirmasiTagihanBpjsActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onPaymentClicked(Payment payment) {

    }
}

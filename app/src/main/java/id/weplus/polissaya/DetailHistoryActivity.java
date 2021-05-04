package id.weplus.polissaya;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.weplus.BaseActivity;
import id.weplus.R;

public class DetailHistoryActivity extends BaseActivity {
    @BindView(R.id.viewback_title) TextView title;
    @BindView(R.id.viewback_description) TextView description;
    @BindView(R.id.detailhistory_statuspembayaran_tag) TextView statusPembayaran;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_history);
        ButterKnife.bind(this);
        title.setText("Transaksi Saya");
        description.setText("Melihat daftar transaksi yang dilakukan");

        Intent intent = getIntent();
        String tag_history = intent.getStringExtra("tag_history");
        if (tag_history.equals("Gagal")) {
            statusPembayaran.setText("Gagal");
            statusPembayaran.setTextColor(getResources().getColor(R.color.red));
        } else {
            statusPembayaran.setText("Berhasil");
            statusPembayaran.setTextColor(getResources().getColor(R.color.green));
        }
    }

    @OnClick(R.id.viewback_buttonback)
    public void backHistory(){finish();}

//    @OnClick(R.id.detailhistory_btn_selesai)
//    public void selesaiHistory(){finish();}

}

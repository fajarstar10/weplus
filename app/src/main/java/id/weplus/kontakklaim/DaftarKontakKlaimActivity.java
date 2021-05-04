package id.weplus.kontakklaim;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.weplus.BaseActivity;
import id.weplus.R;

public class DaftarKontakKlaimActivity extends BaseActivity {
    @BindView(R.id.viewback_title) TextView title;
    @BindView(R.id.viewback_description) TextView description;
    @BindView(R.id.recycleview) RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_kontak_klaim);
        ButterKnife.bind(this);
        title.setText("ACA");
        description.setText(getString(R.string.daftarkontaklayananpelangganasuransi) + " ACA");
        recyclerView.setNestedScrollingEnabled(true);
        LinearLayoutManager lm = new LinearLayoutManager(this, LinearLayout.VERTICAL, false);
        recyclerView.setLayoutManager(lm);
        DaftarKontakKlaimAdapter adapter = new DaftarKontakKlaimAdapter(this);
        recyclerView.setAdapter(adapter);
    }
}

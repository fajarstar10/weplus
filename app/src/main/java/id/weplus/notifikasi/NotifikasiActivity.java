package id.weplus.notifikasi;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.weplus.BaseActivity;
import id.weplus.R;

public class NotifikasiActivity extends BaseActivity {
    @BindView(R.id.viewback_title_no_desc) TextView title;
    @BindView(R.id.notifikasi_rec) RecyclerView notifikasiRecycle;
    @BindView(R.id.notifikasi_layout_empty) RelativeLayout emptyNotifikasi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifikasi);
        ButterKnife.bind(this);
        title.setText(getString(R.string.notifikasi));
        emptyNotifikasi.setVisibility(View.VISIBLE);
        notifikasiRecycle.setVisibility(View.GONE);

        notifikasiRecycle.setNestedScrollingEnabled(true);
        NotifikasiAdapter adapter = new NotifikasiAdapter(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        notifikasiRecycle.setLayoutManager(linearLayoutManager);
        notifikasiRecycle.setAdapter(adapter);
    }

    @OnClick(R.id.viewback_buttonback_no_desc)
    public void back(){finish();}

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}

package id.weplus.affiliasi;

import android.os.Bundle;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.weplus.BaseActivity;
import id.weplus.R;

public class DaftarBengkelRekananActivity  extends BaseActivity {
    @BindView(R.id.viewback_title) TextView title;
    @BindView(R.id.viewback_description) TextView desciption;
    @BindView(R.id.recycleview) RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_bengkelrekanan);
        ButterKnife.bind(this);
        title.setText(getString(R.string.daftarbengkelrekanan));
        desciption.setText(getString(R.string.memudahkanandamenghubungi));

        recyclerView.setNestedScrollingEnabled(true);
        LinearLayoutManager lm = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(lm);
        DaftarBengkelRekananAdapter adapter =new DaftarBengkelRekananAdapter(this);
        recyclerView.setAdapter(adapter);
    }

    @OnClick(R.id.viewback_buttonback)
    public void actionBack(){finish();}
}

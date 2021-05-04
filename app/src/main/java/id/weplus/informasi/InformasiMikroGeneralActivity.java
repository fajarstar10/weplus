package id.weplus.informasi;

import android.os.Bundle;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.weplus.BaseActivity;
import id.weplus.R;

public class InformasiMikroGeneralActivity extends BaseActivity {
    @BindView(R.id.viewback_title) TextView title;
    @BindView(R.id.viewback_description) TextView description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informasi);
        ButterKnife.bind(this);
        title.setText(getResources().getString(R.string.informasi));
        description.setText(getResources().getString(R.string.apaituplanasuransimikro));
    }

    @OnClick(R.id.viewback_buttonback)
    public void back(){finish();}

    @OnClick(R.id.informasi_btn_kembali)
    public void btnKembali(){finish();}

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}

package id.weplus.belipolis.motor;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.weplus.BaseActivity;
import id.weplus.R;

public class AsuransiMotorMikroActivity extends BaseActivity {
    @BindView(R.id.viewback_title) TextView title;
    @BindView(R.id.viewback_description) TextView description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asuransi_motor_mikro);
        ButterKnife.bind(this);
        title.setText("Motor");
        description.setText(getResources().getString(R.string.urutkansesuaidatamotor));
    }
    @OnClick(R.id.viewback_buttonback)
    public void actionBackPembayaran(){finish();}

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @OnClick(R.id.tipeasuransimotor_btn_lanjutkan)
    public void lanjutkan(){
        Intent intent = new Intent(AsuransiMotorMikroActivity.this, TambahanPerlindunganAsuransiMotorActivity.class);
        startActivity(intent);
    }

}

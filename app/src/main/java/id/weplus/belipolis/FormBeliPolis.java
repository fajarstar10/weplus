package id.weplus.belipolis;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.weplus.BaseActivity;
import id.weplus.R;

public class FormBeliPolis extends BaseActivity {
    @BindView(R.id.viewback_title) TextView title;
    @BindView(R.id.viewback_description) TextView description;
    @BindView(R.id.formbelipolis_tglkeberangkatan) EditText tglKeberangkatan;
    @BindView(R.id.formbelipolis_tglkedatangan) EditText tglKedatangan;
    @BindView(R.id.formbelipolis_tipe_perjalanan_spinner) Spinner tipePerjalanan;
    @BindView(R.id.formbelipolis_durasi_perjalanan_spiner) Spinner durasiPerjalanan;
    @BindView(R.id.formbelipolis_destinasi_perjalanan_spiner) Spinner destinasiPerjalanan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_beli_polis);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.formbelipolis_btn_lanjutkan)
    public void actionlanjutkan(){

    }
}

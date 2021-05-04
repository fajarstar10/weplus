package id.weplus.belipolis.perjalanan;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.weplus.BaseActivity;
import id.weplus.R;
import id.weplus.net.WeplusSharedPreference;
import id.weplus.pemegangpolis.FormPemegangPolisModelData;

public class DataAhliWarisActivity extends BaseActivity {

    @BindView(R.id.viewback_title) TextView title;
    @BindView(R.id.viewback_description) TextView descript;
    @BindView(R.id.pemegangpolis_name) EditText name;
    @BindView(R.id.pemegangpolis_no_ktp) EditText ktp;
    @BindView(R.id.pemegangpolis_dob) EditText dob;
    @BindView(R.id.pemegangpolis_gender_pria) TextView pria;
    @BindView(R.id.pemegangpolis_gender_wanita) TextView wanita;
    @BindView(R.id.pemegangpolis_no_telp) EditText noTelp;
    @BindView(R.id.pemegangpolis_email) EditText email;
    @BindView(R.id.pemegangpolis_address) EditText address;
    @BindView(R.id.pemegangpolis_provinsi_spinner) Spinner provinsi;
    @BindView(R.id.pemegangpolis_kota_spinner) Spinner kota;
    private String v_gender = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_ahli_waris);
        ButterKnife.bind(this);
        title.setText(getString(R.string.isidataahliwarisperjalanan));
        descript.setText(getString(R.string.lakukanpengisiandatauntukmelanjutkandata));
    }
    @OnClick(R.id.pemegangpolis_gender_wanita)
    public void onWomenClicked(){
        wanita.setBackground(getResources().getDrawable(R.drawable.border_grey_medium));
        wanita.setTextColor(getResources().getColor(R.color.black));
        pria.setBackground(getResources().getDrawable(R.drawable.border_grey_stroke_greymedium));
        v_gender = getResources().getString(R.string.wanita);
    }

    @OnClick(R.id.pemegangpolis_gender_pria)
    public void onManClicked(){
        wanita.setBackground(getResources().getDrawable(R.drawable.border_grey_stroke_greymedium));
        pria.setBackground(getResources().getDrawable(R.drawable.border_grey_medium));
        pria.setTextColor(getResources().getColor(R.color.black));
        v_gender = getResources().getString(R.string.pria);
    }

    @OnClick(R.id.pemegangpolis_btn_simpan)
    public void simpanFormPemegangPolis(){
        String v_name = name.getText().toString();
        String v_no_ktp = ktp.getText().toString();
        String v_dob = dob.getText().toString();
        String v_ph = noTelp.getText().toString();
        String v_email = email.getText().toString();

        FormPemegangPolisModelData fppmd = new FormPemegangPolisModelData();
        fppmd.setName(v_name);
        fppmd.setKtp(v_no_ktp);
        fppmd.setDob(v_dob);
        fppmd.setPhone(v_ph);
        fppmd.setEmail(v_email);
        fppmd.setGender(v_gender.toLowerCase());
        WeplusSharedPreference.setFormPemegangPolis(this, fppmd);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @OnClick(R.id.viewback_buttonback)
    public void actionback(){finish();}

    @OnClick(R.id.pemegangpolis_btn_simpan)
    public void simpan(){finish();}
}

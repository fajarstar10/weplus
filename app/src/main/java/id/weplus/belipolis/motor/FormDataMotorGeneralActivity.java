//package id.weplus.belipolis.motor;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.util.Log;
//import android.widget.EditText;
//import android.widget.Spinner;
//import android.widget.TextView;
//
//import butterknife.BindView;
//import butterknife.ButterKnife;
//import butterknife.OnClick;
//import id.weplus.BaseActivity;
//import id.weplus.R;
//
//public class FormDataMotorGeneralActivity extends BaseActivity {
//    private static String TAG = FormDataMotorGeneralActivity.class.getName();
//    @BindView(R.id.viewback_title) TextView title;
//    @BindView(R.id.viewback_description) TextView description;
//    @BindView(R.id.formmotor_general_merek_name) TextView namaMerek;
//    @BindView(R.id.formmotor_general_serimotor) TextView seriMotor;
//    @BindView(R.id.formmotor_general_kondisimotor) Spinner kondisiMotor;
//    @BindView(R.id.formmotor_general_warnamotor) Spinner warnaMotor;
//    @BindView(R.id.formmotor_general_platmotor) EditText platMotor;
//    @BindView(R.id.formmotor_general_nomor_rangka) EditText nomorRangka;
//    @BindView(R.id.formmotor_general_nama_stnk) EditText namaStnk;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_form_data_motor_general);
//        ButterKnife.bind(this);
//        title.setText("Lengkapi Data Motor");
//        description.setText(getResources().getString(R.string.pilihberbagaijenisasuransiyangtersedia));
//    }
//
//    @OnClick(R.id.formmotor_general_btn_lanjutkan)
//    public void actionGeneralMotorBtnLanjutkan(){
//        String nama_merek = namaMerek.getText().toString();
//        String seri_motor = seriMotor.getText().toString();
//        String plat_motor = platMotor.getText().toString();
//        String nomor_rangka = nomorRangka.getText().toString();
//        String nama_stnk = namaStnk.getText().toString();
//        Log.i(TAG, "Nama Merek ::" + nama_merek
//                + "\nSeri Motor ::" + seri_motor + "\nPlat Nomor ::" + plat_motor
//                + "\nNomor Rangka ::" + nomor_rangka + "\nNama STNK ::" + nama_stnk);
//
//        Intent intent = new Intent(FormDataMotorGeneralActivity.this, FormDataMotorActivity.class);
//        startActivity(intent);
//    }
//
//
//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//    }
//
//    @OnClick(R.id.viewback_buttonback)
//    public void actionBackMotorGeneral(){finish();}
//}

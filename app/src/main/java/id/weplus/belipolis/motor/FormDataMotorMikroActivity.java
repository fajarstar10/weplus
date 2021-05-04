//package id.weplus.belipolis.motor;
//
//import android.content.Intent;
//import android.os.Bundle;
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
//public class FormDataMotorMikroActivity  extends BaseActivity {
//    @BindView(R.id.viewback_title) TextView title;
//    @BindView(R.id.viewback_description) TextView description;
////    @BindView(R.id.formmotor_mikro_merek_name) TextView merekMotor;
////    @BindView(R.id.formmotor_mikro_serimotor) TextView seriMotor;
////    @BindView(R.id.formmotor_mikro_kondisimotor) Spinner kondisiMotor;
////    @BindView(R.id.formmotor_mikro_platmotor) EditText platMotor;
////    @BindView(R.id.formmotor_mikro_nomor_rangka) EditText nomorRangkaMotor;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_form_data_motor_mikro);
//        ButterKnife.bind(this);
//        title.setText("Motor");
//        description.setText(getResources().getString(R.string.pilihberbagaijenisasuransiyangtersedia));
//    }
//
//    @OnClick(R.id.formmotor_mikro_btn_lanjutkan)
//    public void actionMotorMikroBtnlanjutkan(){
//        Intent intent = new Intent(FormDataMotorMikroActivity.this, FormDataMotorActivity.class);
//        startActivity(intent);
//    }
//
//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//    }
//
//
//    @OnClick(R.id.viewback_buttonback)
//    public void onBackMotorMikro(){finish();}
//}

//package id.weplus.belipolis.motor;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.v7.app.AlertDialog;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.Switch;
//import android.widget.TextView;
//
//import butterknife.BindView;
//import butterknife.ButterKnife;
//import butterknife.OnClick;
//import id.weplus.BaseActivity;
//import id.weplus.R;
//import id.weplus.net.WeplusSharedPreference;
//import id.weplus.pembayaran.PembayaranActivity;
//import id.weplus.pemegangpolis.FormPemegangPolisActivity;
//import id.weplus.pemegangpolis.FormPemegangPolisModelData;
//
//public class FormDataMotorActivity extends BaseActivity {
//    @BindView(R.id.viewback_title) TextView title;
//    @BindView(R.id.viewback_description) TextView decsription;
//    @BindView(R.id.formmotor_icon) ImageView icInsurance;
//    @BindView(R.id.formmotor_insurance_name) TextView insuranceName;
//    @BindView(R.id.formmotor_insurance_price) TextView insurancePrice;
//    @BindView(R.id.formmotor_nama_pemesan) TextView buyerName;
//    @BindView(R.id.formmotor_email_pemesan) TextView buyerMail;
//    @BindView(R.id.formmotor_telp_pemesan) TextView buyerPhone;
//    @BindView(R.id.formmotor_data_pemegang_polis) TextView dataPemegangPolis;
//    @BindView(R.id.formmotor_data_motor_polis) TextView dataMotorPolis;
//    @BindView(R.id.formmotor_switch) Switch switchMotor;
//
//    private boolean isMikro = false;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_form_data_motor);
//        ButterKnife.bind(this);
//        title.setText("Beli Polis");
//        decsription.setText(getResources().getString(R.string.isidata2ygdiperlukan));
//        FormPemegangPolisModelData formPemegangPolisModelData = WeplusSharedPreference.getDataFormPemegangPolis(this);
//        Intent bundle = getIntent();
//        isMikro = bundle.getBooleanExtra("is_mikro", false);
//    }
//
//    @OnClick(R.id.formmotor_switch)
//    public void actionSwicthMotor(){
//        if (switchMotor.isChecked()){
//            dataPemegangPolis.setText("David" + "\ndavid@weplus.id" + "\n08123456789" );
//        } else {
//            dataPemegangPolis.setText("");
//        }
//    }
//
//    @OnClick(R.id.formmotor_edit_data_pemegang_polis)
//    public void editDataPemegangPolis(){
//        Intent intent = new Intent(FormDataMotorActivity.this, FormPemegangPolisActivity.class);
//        startActivity(intent);
//    }
//
//    @OnClick(R.id.formmotor_edit_data_motor_polis)
//    public void editDataMotorPolis(){
//        Intent intent = new Intent(FormDataMotorActivity.this, FormDataMotorGeneralActivity.class);
//        startActivity(intent);
//
//    }
//
//    @OnClick(R.id.formmotor_btn_lakukan_pembayaran)
//    public void actionLakukanPembayaranMotor(){
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//
//        // set the custom layout
//        final View customLayout = getLayoutInflater().inflate(R.layout.view_lanjutkan_pembayaran, null);
//        builder.setView(customLayout);
//
//        final AlertDialog dialog = builder.create();
//        dialog.show();
//
//        TextView btnBatal = customLayout.findViewById(R.id.dialog_btn_batal);
//        TextView btnYa = customLayout.findViewById(R.id.dialog_btn_ya);
//        btnBatal.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.cancel();
//            }
//        });
//        btnYa.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intentPembayaran = new Intent(FormDataMotorActivity.this, PembayaranActivity.class);
//                startActivity(intentPembayaran);
//                dialog.dismiss();
//            }
//        });
//
//
//
//
//    }
//
//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//    }
//
//    @OnClick(R.id.viewback_buttonback)
//    public void onBackFormDataMotor(){finish();}
//
//}

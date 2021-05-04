//package id.weplus.belipolis.motor;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.EditText;
//import android.widget.RelativeLayout;
//import android.widget.Spinner;
//import android.widget.TextView;
//
//import butterknife.BindView;
//import butterknife.ButterKnife;
//import butterknife.OnClick;
//import id.weplus.BaseActivity;
//import id.weplus.R;
//import id.weplus.informasi.InformasiMikroGeneralActivity;
//
//public class PolisMotorActivity extends BaseActivity {
//    @BindView(R.id.viewback_title) TextView title;
//    @BindView(R.id.viewback_description) TextView description;
//    @BindView(R.id.btn_general) TextView btnGeneral;
//    @BindView(R.id.btn_mikro) TextView btnMikro;
//    @BindView(R.id.motor_layout_general) RelativeLayout layoutGeneral;
//    private boolean isMikro = true;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_polis_motor);
//        ButterKnife.bind(this);
//        title.setText("Motor");
//        description.setText(getResources().getString(R.string.pilihberbagaijenisasuransiyangtersedia));
//    }
//
//    @OnClick(R.id.btn_general)
//    public void selectedGeneral(){
//        isMikro = false;
//        layoutGeneral.setVisibility(View.VISIBLE);
//        btnMikro.setBackground(getResources().getDrawable(R.drawable.border_grey_stroke_greymedium));
//        btnGeneral.setBackground(getResources().getDrawable(R.drawable.border_grey));
//        btnGeneral.setTextColor(getResources().getColor(R.color.black));
//        btnMikro.setTextColor(getResources().getColor(R.color.grey_medium));
//    }
//
//    @OnClick(R.id.btn_mikro)
//    public void selectedMikro(){
//        isMikro = true;
//        layoutGeneral.setVisibility(View.GONE);
//        btnMikro.setBackground(getResources().getDrawable(R.drawable.border_grey));
//        btnGeneral.setBackground(getResources().getDrawable(R.drawable.border_grey_stroke_greymedium));
//        btnGeneral.setTextColor(getResources().getColor(R.color.grey_medium));
//        btnMikro.setTextColor(getResources().getColor(R.color.black));
//    }
//
//    @OnClick(R.id.motor_desc)
//    public void descriptionMotor(){
//        Intent intent = new Intent(PolisMotorActivity.this, InformasiMikroGeneralActivity.class);
//        startActivity(intent);
//    }
//
//    @OnClick(R.id.motor_arrow_tambahan)
//    public void tambahPerlindungan(){
//        Intent tambahPerlindungan = new Intent(PolisMotorActivity.this, TambahanPerlindunganActivity.class);
//        startActivity(tambahPerlindungan);
//    }
//
//    @OnClick(R.id.motor_btn_lanjutkan)
//    public void actionMotorbtnLanjut(){
//        // ada 2 keadaan mikro dan general
//        if (isMikro == true){
//            Intent mikro = new Intent(PolisMotorActivity.this, FormDataMotorMikroActivity.class);
//            startActivity(mikro);
//        } else {
//            Intent general = new Intent(PolisMotorActivity.this, FormDataMotorGeneralActivity.class);
//            startActivity(general);
//        }
//
//    }
//
//    @OnClick(R.id.viewback_buttonback)
//    public void backPolisMotor(){finish();}
//
//}

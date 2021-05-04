package id.weplus.belipolis.motor;

import android.content.Intent;
import androidx.core.content.ContextCompat;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.weplus.BaseActivity;
import id.weplus.R;

public class AsuransiMotorActivity extends BaseActivity {
    private static String TAG = "Kesehatan Activity";
    @BindView(R.id.menu_konvesional) Button btnKonvensional;
    @BindView(R.id.menu_mikro) Button btnMikro;
    @BindView(R.id.asuransimotor_btn_lanjutkan) Button btnNext;
    @BindView(R.id.layoutDetailType) RelativeLayout insuranceInformation;
    @BindView(R.id.txt_informasi_1) TextView insuranceInformationText;
    @BindView(R.id.viewback_title) TextView title;
    @BindView(R.id.viewback_description) TextView description;
    @BindView(R.id.viewback_buttonback)
    ImageView backButton;
//    @BindView(R.id.rec_kesehatan) RecyclerView recyclerViewAsuransiMotor;
    private int insuranceType=-1;
    private int catId=0;
    private int partnerId=0;
    private int partnerWeplusId=0;
    private String nik="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asuransi_motor);
        ButterKnife.bind(this);
        catId=getIntent().getIntExtra("cat_id",0);
        partnerId=getIntent().getIntExtra("partner_id",0);
        partnerWeplusId=getIntent().getIntExtra("partner_weplus_id",0);
        nik=getIntent().getStringExtra("nik");
        setupButtonClickBehaviour();
        setupToolbar();


//        recyclerViewAsuransiMotor.setNestedScrollingEnabled(true);
//        AsuransiMotorAdapter asuransiMotorAdapter = new AsuransiMotorAdapter(this);
//        LinearLayoutManager asuransiMotorAdapterLM = new LinearLayoutManager(getApplication().getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
//        recyclerViewAsuransiMotor.setLayoutManager(asuransiMotorAdapterLM);
//        recyclerViewAsuransiMotor.setAdapter(asuransiMotorAdapter);
//        recyclerViewAsuransiMotor.addOnItemTouchListener(new RecyclerItemClickListener(getApplication().getApplicationContext(),
//        recyclerViewAsuransiMotor, new RecyclerItemClickListener.OnItemClickListener() {
//            @Override
//            public void onItemClick(View view, int position) {
//                switch (position) {
//                }
//            }
//
//            @Override
//            public void onLongItemClick(View view, int position) {
//
//            }
//        }));
    }

    private void setupToolbar(){
        title.setText("Motor");
        description.setText("Pilih Tipe Asuransi");
        backButton.setOnClickListener(view -> finish());
    }

    private void setupButtonClickBehaviour(){
        btnKonvensional.setOnClickListener(view->{
            if(insuranceType==-1){
                btnKonvensional.setBackground(ContextCompat.getDrawable(this,R.drawable.border_fill_red));
                btnKonvensional.setTextColor(ContextCompat.getColor(AsuransiMotorActivity.this,R.color.white));
                insuranceInformation.setVisibility(View.VISIBLE);
            }else if(insuranceType==2){
                btnKonvensional.setBackground(ContextCompat.getDrawable(this,R.drawable.border_fill_red));
                btnKonvensional.setTextColor(ContextCompat.getColor(AsuransiMotorActivity.this,R.color.white));
                btnMikro.setBackgroundColor(ContextCompat.getColor(this,R.color.grey_bg_border));
                btnMikro.setTextColor(ContextCompat.getColor(AsuransiMotorActivity.this,R.color.black_7f7f7f));
            }
            insuranceInformationText.setText(getString(R.string.asuransiMotorGeneral));
            insuranceType=1;
            if(!btnNext.isEnabled()){
                btnNext.setBackground(ContextCompat.getDrawable(this,R.drawable.border_fill_red));
                btnNext.setTextColor(ContextCompat.getColor(AsuransiMotorActivity.this,R.color.white));
                btnNext.setEnabled(true);
            }

        });

        btnMikro.setOnClickListener(view -> {
            if(insuranceType==-1){
                btnMikro.setBackground(ContextCompat.getDrawable(this,R.drawable.border_fill_red));
                btnMikro.setTextColor(ContextCompat.getColor(AsuransiMotorActivity.this,R.color.white));
                insuranceInformation.setVisibility(View.VISIBLE);
            }else if(insuranceType==1){
                btnMikro.setBackground(ContextCompat.getDrawable(this,R.drawable.border_red));
                btnMikro.setTextColor(ContextCompat.getColor(AsuransiMotorActivity.this,R.color.white));
                btnKonvensional.setBackgroundColor(ContextCompat.getColor(this,R.color.grey_bg_border));
                btnKonvensional.setTextColor(ContextCompat.getColor(AsuransiMotorActivity.this,R.color.black_7f7f7f));
            }
            if(!btnNext.isEnabled()){
                btnNext.setBackground(ContextCompat.getDrawable(this,R.drawable.border_fill_red));
                btnNext.setTextColor(ContextCompat.getColor(AsuransiMotorActivity.this,R.color.white));
                btnNext.setEnabled(true);
            }
            insuranceInformationText.setText(getString(R.string.asuransiMotorMikro));
            insuranceType=2;
        });
    }

    @OnClick(R.id.asuransimotor_btn_lanjutkan)
    public void lanjutkan(){
        switch (insuranceType){
            case -1:  Toast.makeText(this,"Pilih salah satu tipe asuransi",Toast.LENGTH_LONG).show();break;
            case 1:goToFormKonvensional();break;
            case 2:goToFormMikro();break;
        }
    }

    private void goToFormKonvensional(){
        Intent productIntent = new Intent(this, AsuransiMotorKonvensionalActivity.class);
        productIntent.putExtra("type","konvensional");
        productIntent.putExtra("partner_id",partnerId);
        productIntent.putExtra("partner_weplus_id",partnerWeplusId);
        productIntent.putExtra("nik",nik);
        startActivity(productIntent);
    }

    private void goToFormMikro(){
        Intent productIntent = new Intent(this, AsuransiMotorKonvensionalActivity.class);
        productIntent.putExtra("type","Mikro");
        productIntent.putExtra("partner_id",partnerId);
        productIntent.putExtra("partner_weplus_id",partnerWeplusId);
        productIntent.putExtra("nik",nik);
        startActivity(productIntent);
    }


}

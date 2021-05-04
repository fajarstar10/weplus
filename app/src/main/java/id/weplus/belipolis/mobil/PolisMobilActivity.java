package id.weplus.belipolis.mobil;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.weplus.BaseActivity;
import id.weplus.R;

public class PolisMobilActivity extends BaseActivity {
    @BindView(R.id.viewback_title) TextView title;
    @BindView(R.id.viewback_description) TextView description;
    @BindView(R.id.mobil_icon_asuransi) ImageView iconAsuransi;
    @BindView(R.id.mobil_title_asuransi) TextView titleAsuransi;
    @BindView(R.id.mobil_price_asuransi) TextView priceAsuransi;
    @BindView(R.id.mobil_nama_pemesan) TextView namaPemesanAsuransi;
    @BindView(R.id.mobil_email_pemesan) TextView emailPemesanAsuransi;
    @BindView(R.id.mobil_telp_pemesan) TextView telpPemesanAsuransi;
    @BindView(R.id.mobil_switch) Switch switchSameDataPemesan;
    @BindView(R.id.mobil_data_pemegang_polis) TextView dataPemegangPolis;
    @BindView(R.id.mobil_data_mobil_polis) TextView dataMobilPolis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_polis_mobil);
        ButterKnife.bind(this);
        title.setText(getResources().getString(R.string.belipolis));
        description.setText(getResources().getString(R.string.isidata2ygdiperlukan));

        switchSameDataPemesan.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){

                } else {

                }

            }
        });
    }

    @OnClick(R.id.viewback_buttonback)
    public void onBackMobil(){finish();}

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @OnClick(R.id.mobil_edit_data_pemegang_polis)
    public void btnEditDataPemegangPolis(){

    }

    @OnClick(R.id.mobil_edit_data_mobil_polis)
    public void btnEditDataMobilPolis(){
        Intent intent = new Intent(PolisMobilActivity.this, FormDataMobilActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.mobil_btn_lakukan_pembayaran)
    public void btnLakukanPembayaranPolisMobil(){

    }

}

package id.weplus.polissaya;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import net.cachapa.expandablelayout.ExpandableLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.weplus.BaseActivity;
import id.weplus.R;

public class DetailTertanggungActivity extends BaseActivity {
    private static final String TAG = "InsuredDetail";
    @BindView(R.id.viewback_title) TextView title;
    @BindView(R.id.viewback_description) TextView desciption;
    @BindView(R.id.viewback_buttonback) ImageView btnBack;
    @BindView(R.id.nama_lengkap) TextView namaLengkap;
    @BindView(R.id.nomor_ktp) TextView nomorKtp;
    @BindView(R.id.email) TextView email;
    @BindView(R.id.nomor_telepon) TextView noTelepon;
    @BindView(R.id.alamat_lengkap) TextView alamatLengkap;
    @BindView(R.id.kota)TextView kota;
    @BindView(R.id.provinsi)TextView provinsi;
    @BindView(R.id.jenis_kelamin)TextView jenisKelamin;
    @BindView(R.id.tanggal_lahir)TextView tglLahir;
    @BindView(R.id.nama_lengkap1)TextView namalengkap1;

    @BindView(R.id.layout_toggle)
    RelativeLayout toggle1;
    @BindView(R.id.layout_toggle2) RelativeLayout toggle2;
    @BindView(R.id.ivArrow)
    ImageView arrow1;
    @BindView(R.id.ivArrow2) ImageView arrow2;
    @BindView(R.id.expand)
    ExpandableLayout expand1;
    @BindView(R.id.expand2) ExpandableLayout expand2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_tertanggung);
        ButterKnife.bind(this);

        title.setText(getString(R.string.detailtertanggung));
        desciption.setText(getString(R.string.detaildesctertanggung));
        btnBack.setOnClickListener(view -> finish());

        initView();
    }

    private void initView() {

        if(getIntent().getExtras() != null){
         //   namaLengkap.setText("fajar");
            namaLengkap.setText(getIntent().getStringExtra("nama"));
            namalengkap1.setText(getIntent().getStringExtra("nama"));
            nomorKtp.setText(getIntent().getStringExtra("nik"));
            noTelepon.setText(getIntent().getStringExtra("phone"));
            email.setText(getIntent().getStringExtra("email"));
            tglLahir.setText(getIntent().getStringExtra("dob"));
            kota.setText(getIntent().getStringExtra("city"));
            provinsi.setText(getIntent().getStringExtra("province"));
            alamatLengkap.setText(getIntent().getStringExtra("address"));
            jenisKelamin.setText(getIntent().getStringExtra("sex"));

        }

        toggle1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(expand1.isExpanded()){
                    expand1.collapse();
                    arrow1.setImageResource(R.drawable.ic_baseline_expand_less_24px);
                }else {
                    expand1.expand();
                    arrow1.setImageResource(R.drawable.ic_baseline_expand_more_24px);
                }
            }
        });

        toggle2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(expand2.isExpanded()){
                    expand2.collapse();
                    arrow2.setImageResource(R.drawable.ic_baseline_expand_less_24px);
                }else {
                    expand2.expand();
                    arrow2.setImageResource(R.drawable.ic_baseline_expand_more_24px);
                }
            }
        });
    }
}
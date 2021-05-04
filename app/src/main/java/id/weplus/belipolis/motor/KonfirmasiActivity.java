//package id.weplus.belipolis.motor;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.v7.app.AlertDialog;
//import android.view.View;
//import android.widget.LinearLayout;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
//import butterknife.BindView;
//import butterknife.ButterKnife;
//import butterknife.OnClick;
//import id.weplus.BaseActivity;
//import id.weplus.R;
//import id.weplus.transaksi.PetunjukPembayaran;
//
//public class KonfirmasiActivity extends BaseActivity {
//
//    @BindView(R.id.viewback_title) TextView title;
//    @BindView(R.id.viewback_description) TextView description;
//    @BindView(R.id.transaksionclik_statuspembayaran_tag) TextView tagStatus;
//    @BindView(R.id.transaksionclik_status_pembayaran) TextView labelPembayaran;
//    @BindView(R.id.transaksionclik_status_time) LinearLayout linearLayoutTime;
//    @BindView(R.id.layout_petunjuk_cicilan) RelativeLayout layoutPetunjukTrf;
//    @BindView(R.id.transaksionclik_des_type_pemb) TextView desType;
//    @BindView(R.id.transaksionclik_norek) TextView norek;
//    @BindView(R.id.transaksionclik_salin_norek) TextView salinNorek;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_transaksi_saya_detail);
//        ButterKnife.bind(this);
//        title.setText(getString(R.string.transaksisaya));
//        description.setText(getString(R.string.melihatdaftartransaksiygdilakukan));
//        Intent intent = getIntent();
//        String tag = intent.getStringExtra("tag");
//        if (tag!=null) {
//            if (tag.equals("sukses")) {
//                labelPembayaran.setVisibility(View.GONE);
//                linearLayoutTime.setVisibility(View.GONE);
//                layoutPetunjukTrf.setVisibility(View.GONE);
//                tagStatus.setVisibility(View.VISIBLE);
//                tagStatus.setText("Sukses");
//            } else if (tag.equals("gagal")) {
//                labelPembayaran.setVisibility(View.GONE);
//                linearLayoutTime.setVisibility(View.GONE);
//                layoutPetunjukTrf.setVisibility(View.GONE);
//                tagStatus.setVisibility(View.VISIBLE);
//                tagStatus.setText("Gagal");
//                tagStatus.setTextColor(getResources().getColor(R.color.red));
//            }
//        }else {
//            tagStatus.setVisibility(View.GONE);
//            labelPembayaran.setVisibility(View.VISIBLE);
//            linearLayoutTime.setVisibility(View.VISIBLE);
//            layoutPetunjukTrf.setVisibility(View.VISIBLE);
//        }
//    }
//
//    @OnClick(R.id.viewback_buttonback)
//    public void transaktionBack(){finish();}
//
//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//    }
//
//
//    public void showAlertDialogGotoBeranda(View view) {
//        // create an alert builder
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//
//        // set the custom layout
//        final View customLayout = getLayoutInflater().inflate(R.layout.view_dialog_terimakasih, null);
//        builder.setView(customLayout);
//
//        final AlertDialog dialog = builder.create();
//        dialog.show();
//
//        TextView btnPeriksa = customLayout.findViewById(R.id.dialog_beranda);
//        btnPeriksa.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
//
//    }
//
//    @OnClick(R.id.transaksionclik_icon_petunjuk_pemb)
//    public void petunjukPembayaran(){
//        Intent petunjukpay = new Intent(KonfirmasiActivity.this, PetunjukPembayaran.class);
//        startActivity(petunjukpay);
//    }
//
//}

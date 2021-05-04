package id.weplus.Tagihan;

import android.annotation.SuppressLint;
import android.content.Intent;

import androidx.appcompat.app.AlertDialog;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.iwgang.countdownview.CountdownView;
import id.weplus.BaseActivity;
import id.weplus.MainActivity;
import id.weplus.R;
//import id.weplus.belipolis.motor.KonfirmasiActivity;
import id.weplus.belipolis.PaymentInfoActivity;
import id.weplus.model.response.BillTransactionData;
import id.weplus.model.response.BillTransactionResponse;
import id.weplus.transaksi.PetunjukPembayaran;

import static id.weplus.utility.TextHelper.currencyFormatter;

public class KonfirmasiTagihanListrikActivity extends BaseActivity {

    @BindView(R.id.viewback_title)
    TextView title;
    @BindView(R.id.viewback_description)
    TextView description;
    @BindView(R.id.transaksionclik_statuspembayaran_tag)
    TextView tagStatus;
    @BindView(R.id.transaksionclik_status_pembayaran)
    TextView labelPembayaran;
    @BindView(R.id.layout_petunjuk_trf)
    RelativeLayout layoutPetunjukTrf;
    @BindView(R.id.transaksionclik_des_type_pemb)
    TextView desType;
    @BindView(R.id.transaksionclik_norek)
    TextView norek;
    @BindView(R.id.transaksionclik_salin_norek)
    TextView salinNorek;
    @BindView(R.id.transaksionclik_id_transaksi)
    TextView tvOrderCode;
    @BindView(R.id.tvIdPelanggan)
    TextView tvIdPelanggan;
    @BindView(R.id.tvNamaPelanggan)
    TextView tvNamaPelanggan;
    @BindView(R.id.tvJenisLayanan)
    TextView tvJenisLayanan;
    @BindView(R.id.tvPeriodePembayaran)
    TextView tvPeriodePembayaran;
    @BindView(R.id.tvHarga)
    TextView tvHarga;
    @BindView(R.id.tvBiayaLayanan)
    TextView tvBiayaLayanan;
    @BindView(R.id.tvBiayaAdmin)
    TextView tvBiayaAdmin;
    @BindView(R.id.tvTotalBiaya)
    TextView tvTotalBiaya;
    @BindView(R.id.countdown)
    CountdownView countdownView;
    @BindView(R.id.tvProductName)
    TextView tvProductName;
    @BindView(R.id.productIcon)
    ImageView productIcon;
    @BindView(R.id.transaksionclik_btn_selesai)
    Button btnSelesai;
    @BindView(R.id.transaksionclik_metode_pembayaran)
            TextView tvPaymentChannelName;

    BillTransactionData data;
    private int billCategory=1;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_konfirmasi_tagihan_listrik);
        ButterKnife.bind(this);
        setupToolbar();
        Intent intent = getIntent();
        billCategory = intent.getIntExtra("bill_category",1);

        String tag = intent.getStringExtra("tag");
        data = intent.getParcelableExtra("bill_transaction");
        //if(billCategory==2){
            Glide.with(this).load(data.getImage()).into(productIcon);
            //productIcon.setImageResource(R.drawable.ic_bpjs_3);
        //}
        Log.d("data", "data received : " + data.getBill_number());
        if (tag != null) {
            if (tag.equals("sukses")) {
                labelPembayaran.setVisibility(View.GONE);
                //linearLayoutTime.setVisibility(View.GONE);
                layoutPetunjukTrf.setVisibility(View.GONE);
                tagStatus.setVisibility(View.VISIBLE);
                tagStatus.setText("Sukses");
            } else if (tag.equals("gagal")) {
                labelPembayaran.setVisibility(View.GONE);
                //linearLayoutTime.setVisibility(View.GONE);
                layoutPetunjukTrf.setVisibility(View.GONE);
                tagStatus.setVisibility(View.VISIBLE);
                tagStatus.setText("Gagal");
                tagStatus.setTextColor(getResources().getColor(R.color.red));
            }
        } else {
            tagStatus.setVisibility(View.GONE);
            labelPembayaran.setVisibility(View.VISIBLE);
            //linearLayoutTime.setVisibility(View.VISIBLE);
            layoutPetunjukTrf.setVisibility(View.VISIBLE);
        }

        if (data != null) {
            tvProductName.setText(data.getProduct_name());
            tvOrderCode.setText(data.getOrder_code());
            tvIdPelanggan.setText(data.getBill_number());
            tvNamaPelanggan.setText(data.getCustomer_name());
            tvJenisLayanan.setText(data.getProduct_name());
            tvHarga.setText("Rp " + currencyFormatter("" + data.getNominal()));
            tvBiayaAdmin.setText("Rp " + currencyFormatter("" + data.getProcessing_fee()));
            tvBiayaLayanan.setText("Rp " + currencyFormatter("" + data.getAdmin_fee()));
            tvPeriodePembayaran.setText(data.getInquiry().getData().getBill_period());
            tvTotalBiaya.setText("Rp " + currencyFormatter("" + data.getTotal()));
            tvPaymentChannelName.setText(data.getPayment_name());
            try {
                @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
                Date date = format.parse(data.getDate_end());
                long date1 = date.getTime();
                long now = System.currentTimeMillis();
                long diff = Math.abs(date1 - now);
                countdownView.start(diff);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if(data.getPayment_name().toLowerCase().contains("bca")){
                layoutPetunjukTrf.setVisibility(View.VISIBLE);
            }else{
                layoutPetunjukTrf.setVisibility(View.VISIBLE);
                desType.setText("Kode Pembayaran di "+data.getPayment_name());
                salinNorek.setVisibility(View.GONE);
                norek.setText(data.getOrder_code());
            }
        }

        btnSelesai.setOnClickListener(view -> finishTransaction());
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        AlertDialog.Builder builder = new AlertDialog.Builder(KonfirmasiTagihanListrikActivity.this);
        builder.setTitle("Terima Kasih");
        builder.setMessage("Untuk melihat status transaksi Polis yang aktif. Silahkan cek dari beranda");

        // add a button
        builder.setPositiveButton("Beranda", (dialog, id) -> {
            Intent intent = new Intent(KonfirmasiTagihanListrikActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        // create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }


    private void setupToolbar() {
        title.setText(getString(R.string.konfirmasitagihanlistrik));
        description.setText(getString(R.string.lakukanpembayarandenganmetodepembayaran));
    }

    private void finishTransaction(){
        AlertDialog.Builder builder = new AlertDialog.Builder(KonfirmasiTagihanListrikActivity.this);
        builder.setTitle("Terima Kasih");
        builder.setMessage("Untuk melihat status transaksi Polis yang aktif. Silahkan cek dari beranda");

        // add a button
        builder.setPositiveButton("Beranda", (dialog, id) -> {
            Intent intent = new Intent(KonfirmasiTagihanListrikActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        // create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @OnClick(R.id.viewback_buttonback)
    public void transaktionBack() {
        finish();
    }


    public void showAlertDialogGotoBeranda(View view) {
        // create an alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // set the custom layout
        final View customLayout = getLayoutInflater().inflate(R.layout.view_dialog_terimakasih, null);
        builder.setView(customLayout);

        final AlertDialog dialog = builder.create();
        dialog.show();

        TextView btnPeriksa = customLayout.findViewById(R.id.dialog_beranda);
        btnPeriksa.setOnClickListener(v -> finish());

    }

    @OnClick(R.id.transaksionclik_icon_petunjuk_pemb)
    public void petunjukPembayaran() {
        Intent petunjukpay = new Intent(KonfirmasiTagihanListrikActivity.this, PetunjukPembayaran.class);
        startActivity(petunjukpay);
    }

}

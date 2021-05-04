package id.weplus.belipolis;

import androidx.appcompat.app.AlertDialog;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.iwgang.countdownview.CountdownView;
import id.weplus.BaseActivity;
import id.weplus.MainActivity;
import id.weplus.R;
import id.weplus.WelcomeActivity;
import id.weplus.model.Cicilan;
import id.weplus.model.TransactionDetail;
import id.weplus.model.response.TransactionDetailResponse;
import id.weplus.net.ErrorCode;
import id.weplus.net.NetworkManager;
import id.weplus.net.WeplusSharedPreference;
import id.weplus.transaksi.DetailCicilanActivity;
import id.weplus.transaksi.UploadTransferFileActivity;
import id.weplus.utility.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static id.weplus.utility.TextHelper.currencyFormatter;

public class PaymentInfoActivity extends BaseActivity {

    private static final String TAG = "TransactionDetail";
    @BindView(R.id.viewback_title)
    TextView title;
    @BindView(R.id.viewback_description)
    TextView description;
    @BindView(R.id.transaksionclik_statuspembayaran_tag)
    TextView tagStatus;
    @BindView(R.id.transaksionclik_status_time)
    LinearLayout linearLayoutTime;
    @BindView(R.id.layout_petunjuk_cicilan)
    RelativeLayout layoutPetunjukCicilan;
    @BindView(R.id.jenis_cicilan)
    TextView jenisCicilan;
    @BindView(R.id.sistem_cicilan)
    TextView sistemCicilan;
    @BindView(R.id.sisa_cicilan)
    TextView sisaCicilan;
    @BindView(R.id.transaksi_img)
    ImageView insuranceImg;
    @BindView(R.id.transaksionclik_name)
    TextView insuranceName;
    @BindView(R.id.transaksionclick_duration)
    TextView insuranceDuration;
    @BindView(R.id.transaksi_amount)
    TextView insurancePrice;
    @BindView(R.id.transaksionclik_id_transaksi)
    TextView insuranceId;
    @BindView(R.id.transaksionclik_status_pembayaran)
    TextView insuranceStatus;
    @BindView(R.id.transaksionclik_metode_pembayaran)
    TextView paymentMethod;
    @BindView(R.id.tv_biaya_admin)
    TextView insuranceAdminCost;
    @BindView(R.id.tv_biaya_asuransi)
    TextView insuranceCost;
    @BindView(R.id.tv_biaya_product)
    TextView insuranceProductCost;
    @BindView(R.id.tv_biaya_total)
    TextView insuranceTotalCost;
    @BindView(R.id.tv_pot_voucher)
    TextView insuranceVoucherDiscount;
    @BindView(R.id.countdown)
    CountdownView countdown;
    @BindView(R.id.layout_petunjuk_trf)
    RelativeLayout trfWrapper;
    @BindView(R.id.tvSalin)
    TextView tvSalin;
    @BindView(R.id.tvBiayaCicilan)
    TextView tvBiayaCicilan;
    @BindView(R.id.trfTitle)
    TextView tvTrfTitle;
    @BindView(R.id.trfRek)
    TextView tvTrfRek;
    @BindView(R.id.imgTrfMethod)
    ImageView imgTrf;
    @BindView(R.id.transaksionclik_line_layout_trf)
    View separatorTransferMethod;
    @BindView(R.id.transaksionclik_btn_selesai)
    Button buttonDone;
    @BindView(R.id.btnUploadBca)
    Button btnUploadBca;
    @BindView(R.id.tvSisaCicilan)
    TextView tvSisaCicilan;

    private TransactionDetail transaction;
    private ArrayList<Cicilan> detailCicilan = new ArrayList<>();
    private boolean isHistory = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_info);
        ButterKnife.bind(this);
        title.setText(getString(R.string.transaksisaya));
        description.setText(getString(R.string.melihatdaftartransaksiygdilakukan));
        getArguments();
        fetchTransactionDetail();
        tvSalin.setOnClickListener(view -> {
            ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("simple text", "0353250229");
            clipboard.setPrimaryClip(clip);
            Toast.makeText(PaymentInfoActivity.this, "No rek telah di salin", Toast.LENGTH_LONG).show();
        });
        setupButtonDone();
        checkGopayCallback();
    }

    private void checkGopayCallback(){
        Log.d("gopay","onresume called");
        Intent intent = getIntent();
        Uri data = intent.getData();


        if (data != null && data.isHierarchical()) {
            Log.d("gopay","check uri : "+data.toString());
            final String orderId = data.getQueryParameter("order_id");
            final String result = data.getQueryParameter("result");
            Log.d("gopay","order id : "+orderId);
            Log.d("gopay","status : "+result);
//            Intent intentToResult = new Intent(DemoConfigActivity.this, GopayStatusActivity.class);
//            intentToResult.putExtra(INTENT_ORDERID, orderId);
//            intentToResult.putExtra(INTENT_AMOUNT, "10000");
//            intentToResult.putExtra(INTENT_TYPE, "GoPay");
//            intentToResult.putExtra(INTENT_STATUS, result);
//            startActivity(intentToResult);
        }
    }

    private void setupButtonDone() {
        // setup the alert builder
        buttonDone.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(PaymentInfoActivity.this);
            builder.setTitle("Terima Kasih, Silahkan Cek Halaman Polis");
            builder.setMessage("Untuk melihat status transaksi Polis yang aktif. Silahkan cek dari beranda");

            // add a button
            builder.setPositiveButton("Beranda", (dialog, id) -> {
                Intent intent = new Intent(PaymentInfoActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            });

            // create and show the alert dialog
            AlertDialog dialog = builder.create();
            dialog.show();
        });
    }


    private void getArguments() {
        Intent intent = getIntent();
        transaction = (TransactionDetail) intent.getParcelableExtra("transaction_detail");
        isHistory = intent.getBooleanExtra("history", false);
    }

    @SuppressLint("SetTextI18n")
    private void populateView(TransactionDetail transaction) {
        Log.d("image", "image : " + transaction.getProductDetail().getImage());
        Glide.with(this)
                .load(transaction.getProductDetail().getImageNew())
                .into(insuranceImg);

        insuranceName.setText(transaction.getProductDetail().getNama());
        insuranceDuration.setText(transaction.getDate_end());
        insurancePrice.setText("Rp." + currencyFormatter(""+transaction.getDetailPayment().getProduct_nominal()));
        if(transaction.getStatus().toLowerCase().equals("waiting payment")){
            insuranceStatus.setText("Menunggu pembayaran");
        }else{
            insuranceStatus.setText(transaction.getStatus());
        }

        insuranceId.setText("" + transaction.getOrder_code());
        String paymentChannel = transaction.getDetailPayment().getPayment_channel();
        paymentMethod.setText(paymentChannel);
        if (paymentChannel.toLowerCase().equals("gopay")) {
            tvTrfTitle.setVisibility(View.GONE);
            tvTrfRek.setVisibility(View.GONE);
            tvSalin.setVisibility(View.GONE);
            imgTrf.setImageResource(R.drawable.ic_logo_gopay);
        } else if (paymentChannel.toLowerCase().equals("ovo")) {
            tvTrfTitle.setVisibility(View.GONE);
            imgTrf.setImageResource(R.drawable.ic_logo_ovo_purple);
            tvTrfRek.setVisibility(View.GONE);
            tvSalin.setVisibility(View.GONE);
        } else if (paymentChannel.toLowerCase().contains("cicilan")) {
            trfWrapper.setVisibility(View.GONE);
            separatorTransferMethod.setVisibility(View.GONE);
            layoutPetunjukCicilan.setVisibility(View.VISIBLE);
            tvBiayaCicilan.setText("Rp." + currencyFormatter(transaction.getProductDetail().getTotalPayment()));
            tvSisaCicilan.setText("Rp. "+currencyFormatter(transaction.getProductDetail().getRemainingPaymentCicilan()));
        } else if (paymentChannel.toLowerCase().contains("alfa")) {
            tvTrfTitle.setText("Kode Pembayaran di Alfa Group");
            imgTrf.setImageResource(R.drawable.ic_alfamart);
            tvTrfRek.setText(transaction.getOrder_code());
            tvSalin.setVisibility(View.GONE);
        } else if (paymentChannel.toLowerCase().contains("indomaret")) {
            imgTrf.setImageResource(R.drawable.logo_indomaret);
            tvTrfTitle.setText("Kode Pembayaran di Indomaret");
            tvTrfRek.setText(transaction.getOrder_code());
            tvSalin.setVisibility(View.GONE);
        }else if(paymentChannel.toLowerCase().contains("bca transfer")){
            btnUploadBca.setVisibility(View.VISIBLE);
            btnUploadBca.setOnClickListener(view -> {
                Intent intent = new Intent(PaymentInfoActivity.this, UploadTransferFileActivity.class);
                intent.putExtra("order_code", transaction.getOrder_code());
                startActivity(intent);
            });
        }
        tvSisaCicilan.setText("Rp. "+currencyFormatter(transaction.getProductDetail().getRemainingPaymentCicilan()));
        insuranceAdminCost.setText(currencyFormatter("" + transaction.getDetailPayment().getProcessing_fee()));
        insuranceCost.setText(currencyFormatter("" + transaction.getDetailPayment().getAdmin_fee()));
        insuranceProductCost.setText(currencyFormatter("" + transaction.getDetailPayment().getProduct_nominal()));
        if (transaction.getDetailPayment().getDiscount() > 0) {
            insuranceVoucherDiscount.setText(currencyFormatter("-" + transaction.getDetailPayment().getDiscount()));
        } else {
            insuranceVoucherDiscount.setText(currencyFormatter("" + transaction.getDetailPayment().getDiscount()));

        }
        insuranceTotalCost.setText("" + currencyFormatter("" + transaction.getDetailPayment().getTotal_payment()));
        if (!isHistory) {
            countdown.setVisibility(View.VISIBLE);
            setCountdown(transaction.getCount_down_timer());
            SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
            try {
                Date date = format.parse(transaction.getDate_end());
                long date1 = date.getTime();
                long now = System.currentTimeMillis();
                long diff = Math.abs(date1 - now);
                setCountdown(diff);
            } catch (ParseException e) {
                e.printStackTrace();
            }

        } else {
        }
    }


    private void setCountdown(long countdownMillis) {
        countdown.start(countdownMillis);
    }


    private void fetchTransactionDetail() {
        boolean isNetworkAvailable = Util.isNetworkAvailable(this);
        if (isNetworkAvailable) {
            String token = WeplusSharedPreference.getToken(this);
            Call<String> call = NetworkManager
                    .getNetworkServiceWithHeader(this, token)
                    .getTransactionDetail(transaction.getId());
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    Gson gson = new Gson();
                    TransactionDetailResponse resp = gson.fromJson(response.body(), TransactionDetailResponse.class);
                    try {
                        if (resp.getCode().equals(ErrorCode.ERROR_00)) {
                            populateView(resp.data);
                            detailCicilan = resp.data.getDetailCicilan();
                        } else if(resp.getCode().equals(ErrorCode.ERROR_03)){
                            Intent intent = new Intent(PaymentInfoActivity.this, WelcomeActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }else {
                            new SweetAlertDialog(getApplicationContext(), SweetAlertDialog.NORMAL_TYPE)
                                    .setTitleText("")
                                    .setContentText(resp.getMessage())
                                    .show();
                        }
                    } catch (Exception e) {
                        Log.i(TAG, "asu: " + e.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    try {
                        new SweetAlertDialog(getApplicationContext(), SweetAlertDialog.NORMAL_TYPE)
                                .setTitleText("")
                                .setContentText("Time Out")
                                .show();
                    } catch (Exception e) {
                        e.getMessage();
                    }
                    Log.i(TAG, "ON FAILURE : " + t.getMessage());
                }
            });
        } else {
            new SweetAlertDialog(this, SweetAlertDialog.NORMAL_TYPE)
                    .setTitleText(" ")
                    .setContentText(getString(R.string.network_error))
                    .show();
        }
    }

    @OnClick(R.id.viewback_buttonback)
    public void transaktionBack() {
        AlertDialog.Builder builder = new AlertDialog.Builder(PaymentInfoActivity.this);
        builder.setTitle("Terima Kasih, Silahkan Cek Halaman Polis");
        builder.setMessage("Untuk melihat status transaksi Polis yang aktif. Silahkan cek dari beranda");

        // add a button
        builder.setPositiveButton("Beranda", (dialog, id) -> {
            Intent intent = new Intent(PaymentInfoActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        // create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();
        //finish();
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        AlertDialog.Builder builder = new AlertDialog.Builder(PaymentInfoActivity.this);
        builder.setTitle("Terima Kasih, Silahkan Cek Halaman Polis");
        builder.setMessage("Untuk melihat status transaksi Polis yang aktif. Silahkan cek dari beranda");

        // add a button
        builder.setPositiveButton("Beranda", (dialog, id) -> {
            Intent intent = new Intent(PaymentInfoActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        // create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }


    @OnClick(R.id.transaksionclik_icon_petunjuk_cicilan)
    public void petunjukCicilan() {

        Intent petunjukcil = new Intent(PaymentInfoActivity.this, DetailCicilanActivity.class);
        petunjukcil.putParcelableArrayListExtra("cicilan", detailCicilan);
        startActivity(petunjukcil);
    }
}
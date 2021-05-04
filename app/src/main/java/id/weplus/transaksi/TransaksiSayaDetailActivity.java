package id.weplus.transaksi;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.iwgang.countdownview.CountdownView;
import id.weplus.BaseActivity;
import id.weplus.R;
import id.weplus.Tagihan.BayarTagihanListrikActivity;
import id.weplus.WelcomeActivity;
import id.weplus.model.Cicilan;
import id.weplus.model.Transaction;
import id.weplus.model.TransactionDetail;
import id.weplus.model.response.RefundData;
import id.weplus.model.response.RefundDetail;
import id.weplus.model.response.TransactionDetailResponse;
import id.weplus.net.ErrorCode;
import id.weplus.net.NetworkManager;
import id.weplus.net.WeplusSharedPreference;
import id.weplus.utility.Util;
import id.weplus.voucher.VoucherDetailActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static id.weplus.utility.TextHelper.currencyFormatter;

public class TransaksiSayaDetailActivity extends BaseActivity {
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
    //    @BindView(R.id.transaksionclik_line_layout_trf)
//    View separatorTransferMethod;
    @BindView(R.id.transaksionclik_btn_selesai)
    Button btnEnd;
    @BindView(R.id.layout_detail_refund)
    RelativeLayout refundLayout;
    @BindView(R.id.detail_refund_arrow)
    ImageView refundToggleButton;
    @BindView(R.id.detailCicilanWrapper)
    RelativeLayout detailCicilanWrapper;
    @BindView(R.id.detail_refund_label)
    RelativeLayout detailRefundWrapper;

    //REFUND
    @BindView(R.id.tvRefundBank)
    TextView tvRefundBank;
    @BindView(R.id.tvRefundBranch)
    TextView tvRefundBranch;
    @BindView(R.id.tvRefundAccountName)
    TextView tvRefundAccountName;
    @BindView(R.id.tvRefundAccountNumber)
    TextView tvRefundAccountNumber;
    @BindView(R.id.tvPotonganVocer)
    TextView tvPotonganVoucher;
    @BindView(R.id.transaksionclik_icon_petunjuk_cicilan)
    ImageView icDetailCicilan;
    private Transaction transaction;
    private TransactionDetail transactionDetail;
    private ArrayList<Cicilan> detailCicilan = new ArrayList<>();
    private boolean isHistory = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaksi_saya_detail);
        ButterKnife.bind(this);
        title.setText(getString(R.string.transaksisaya));
        description.setText(getString(R.string.melihatdaftartransaksiygdilakukan));
        getArguments();
        fetchTransactionDetail();
        tvSalin.setOnClickListener(view -> {
            ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("simple text", "0353250229");
            clipboard.setPrimaryClip(clip);
            Toast.makeText(TransaksiSayaDetailActivity.this, "No rek telah di salin", Toast.LENGTH_LONG).show();

        });
        icDetailCicilan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent petunjukcil = new Intent(TransaksiSayaDetailActivity.this, DetailCicilanActivity.class);
                petunjukcil.putParcelableArrayListExtra("cicilan", detailCicilan);
                startActivity(petunjukcil);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        fetchTransactionDetail();
    }

    private void getArguments() {
        Intent intent = getIntent();
        transaction = (Transaction) intent.getSerializableExtra("transaction");
        isHistory = intent.getBooleanExtra("history", false);
        if(isHistory){
            title.setText("Riwayat Transaksi");
            description.setText("Riwayat transaksi yang telah dilakukan");
            tvPotonganVoucher.setText("Potongan harga");
        }
    }

    @SuppressLint("SetTextI18n")
    private void populateView(TransactionDetail transaction) {
        Log.d("imageurl", "image " + transaction.getProductDetail().getImageNew());
        Glide.with(this)
                .load(transaction.getProductDetail().getImageNew())
                .error(R.drawable.aca_insurance)
                .into(insuranceImg);
        Log.d("imageurl", "name : " + transaction.getProductDetail().getNama());

        insuranceName.setText(transaction.getProductDetail().getNama());
        insuranceDuration.setText(transaction.getDate_end());
        insurancePrice.setText("Rp." + currencyFormatter(transaction.getProductDetail().getPrice()));

        String status = "";
        if(transaction.getStatus().equals("done")){
            status = "Sukses";
        }else if(transaction.getStatus().equals("cancel transaction")){
            status = "Batal";
        }
        insuranceStatus.setText(status);
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
            Log.d("test", "cicilan");
            trfWrapper.setVisibility(View.GONE);
            //separatorTransferMethod.setVisibility(View.GONE);
            layoutPetunjukCicilan.setVisibility(View.VISIBLE);
            tvBiayaCicilan.setText("Rp." + transaction.getProductDetail().getPrice());
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
        }

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
        btnEnd.setOnClickListener(view->finish());
        setupViewBasedOnStatus(transaction.getStatus(),paymentChannel);
    }


    @SuppressLint("SetTextI18n")
    private void setupViewBasedOnStatus(String status, String paymentChannel) {
        if (status.toLowerCase().equals("refund")) {
            linearLayoutTime.setVisibility(View.INVISIBLE);
            trfWrapper.setVisibility(View.GONE);
            btnEnd.setVisibility(View.VISIBLE);
            insuranceStatus.setText(status);
            btnEnd.setText("Refund");
            if (transactionDetail.getRefundData() != null) {
                RefundDetail refundData = transactionDetail.getRefundData().getData();
                if (refundData.getId() != 0) {
                    refundLayout.setVisibility(View.VISIBLE);
                    btnEnd.setText("Menunggu Proses Refund");
                    btnEnd.setOnClickListener(view -> finish());
                    tvRefundBank.setText(refundData.getBank_name());
                    tvRefundBranch.setText(refundData.getBank_branch());
                    tvRefundAccountName.setText(refundData.getAccount_name());
                    tvRefundAccountNumber.setText(refundData.getAccount_no());
                } else {
                    refundLayout.setVisibility(View.GONE);
                    btnEnd.setVisibility(View.VISIBLE);
                    btnEnd.setOnClickListener(view -> {
                        Intent intent = new Intent(TransaksiSayaDetailActivity.this, RefundActivity.class);
                        intent.putExtra("order_code", transaction.getOrder_code());
                        startActivity(intent);
                    });
                }
            }
            refundToggleButton.setOnClickListener(view -> {
                if (detailRefundWrapper.getVisibility() == View.VISIBLE) {
                    detailRefundWrapper.setVisibility(View.GONE);
                } else {
                    detailRefundWrapper.setVisibility(View.VISIBLE);
                }
            });
        } else if (status.toLowerCase().equals("waiting document from user")
                || status.toLowerCase().equals("waiting payment")) {
            String s = "";
            if (status.toLowerCase().equals("waiting document from user")) {
                s = "Menunggu upload dokumen";
            } else {
                s = "Menunggu pembayaran";
            }
            insuranceStatus.setText(s);
            refundLayout.setVisibility(View.GONE);
            trfWrapper.setVisibility(View.VISIBLE);
            btnEnd.setVisibility(View.VISIBLE);
            if(paymentChannel.toLowerCase().contains("bca transfer")) {
                btnEnd.setText("Upload Bukti Pembayaran");
                btnEnd.setBackground(ContextCompat.getDrawable(this, R.drawable.border_yellow_2));
                btnEnd.setOnClickListener(view -> {
                    Intent intent = new Intent(TransaksiSayaDetailActivity.this, UploadTransferFileActivity.class);
                    intent.putExtra("order_code", transaction.getOrder_code());
                    startActivity(intent);
                });
            }else{
                btnEnd.setOnClickListener(view -> finish());
            }
        }else{
            btnEnd.setOnClickListener(view -> finish());
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
                            transactionDetail = resp.getData();
                            populateView(resp.data);
                            detailCicilan = resp.data.getDetailCicilan();

                        } else if(resp.getCode().equals(ErrorCode.ERROR_03)){
                            Intent intent = new Intent(TransaksiSayaDetailActivity.this, WelcomeActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }else {
                            showError(TransaksiSayaDetailActivity.this, resp.getMessage());
                        }
                    } catch (Exception e) {
                        Log.i(TAG, "asu: " + e.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    try {
                        showError(TransaksiSayaDetailActivity.this, "Time Out");
                    } catch (Exception e) {
                        e.getMessage();
                    }
                    Log.i(TAG, "ON FAILURE : " + t.getMessage());
                }
            });
        } else {
            showError(TransaksiSayaDetailActivity.this, getString(R.string.network_error));
        }
    }

    @OnClick(R.id.viewback_buttonback)
    public void transaktionBack() {
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}

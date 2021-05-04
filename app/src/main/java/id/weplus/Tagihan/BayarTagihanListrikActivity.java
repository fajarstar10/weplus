package id.weplus.Tagihan;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.weplus.BaseActivity;
import id.weplus.R;
//import id.weplus.belipolis.motor.KonfirmasiActivity;
import id.weplus.WelcomeActivity;
import id.weplus.belipolis.perjalanan.BeliPolisPerjalananActivity;
import id.weplus.model.Payment;
import id.weplus.model.request.BillTransactionRequest;
import id.weplus.model.request.PaymentMethodRequest;
import id.weplus.model.response.BillInquiryData;
import id.weplus.model.response.BillInquiryResponse;
import id.weplus.model.response.BillTransactionResponse;
import id.weplus.net.ErrorCode;
import id.weplus.net.NetworkManager;
import id.weplus.net.WeplusSharedPreference;
import id.weplus.pembayaran.OnPaymentClicked;
import id.weplus.pembayaran.PembayaranAdapter;
import id.weplus.utility.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static id.weplus.utility.TextHelper.currencyFormatter;


public class BayarTagihanListrikActivity extends BaseActivity implements OnPaymentClicked {
    @BindView(R.id.viewback_title) TextView title;
    @BindView(R.id.viewback_description) TextView description;
    @BindView(R.id.tvProductName) TextView tvProductName;
    @BindView(R.id.tvCustomerNumber) TextView tvCustomerNumber;
    @BindView(R.id.tvCustomerName) TextView tvCustomerName;
    @BindView(R.id.tvServiceType) TextView tvServiceType;
    @BindView(R.id.tvPaymentPeriodeVal) TextView tvPaymentPeriodeVal;
    @BindView(R.id.tvServiceFeeVal) TextView tvServiceFeeVal;
    @BindView(R.id.tvPriceVal) TextView tvPriceVal;
    @BindView(R.id.tvAdminFeeVal) TextView tvAdminFeeVal;
    @BindView(R.id.pembayaran_total) TextView tvTotalPrice;
    @BindView(R.id.viewback_buttonback) ImageView btnBack;
    @BindView(R.id.recycleview_pembayaran) RecyclerView recyclerViewPembayaran;
    @BindView(R.id.loading_progress) RelativeLayout loadingProgress;
    @BindView(R.id.productIcon) ImageView productIcon;

    private String TAG="BayarTagihanListrik";
    private String paymentChannel = "";
    private int billCategory=1;

    private BillInquiryData billInquiryData;
    private PaymentMethodRequest paymentMethodRequest;
    private PembayaranAdapter paymentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bayar_tagihan_listrik);
        ButterKnife.bind(this);
        getIntentData();
        setupToolbar();
        //fetchPaymentMethod();
    }

    @SuppressLint("SetTextI18n")
    private void getIntentData(){
        billInquiryData = getIntent().getParcelableExtra("bill_inquiry");
        billCategory = getIntent().getIntExtra("bill_category",1);
        if(billInquiryData !=null){
            setupPaymentMethodAdapter();
            prefillCustomerInformation();
        }
    }

    private void setupPaymentMethodAdapter(){
        recyclerViewPembayaran.setNestedScrollingEnabled(true);
        paymentAdapter = new PembayaranAdapter(this,this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewPembayaran.setLayoutManager(mLayoutManager);
        recyclerViewPembayaran.setAdapter(paymentAdapter);
        paymentAdapter.setPaymentMethod(billInquiryData.getPaymentList());
    }

    private void setupToolbar(){
        if(billCategory ==1) {
            title.setText(getString(R.string.bayartagihanlistrik));
        }else{
            title.setText(getString(R.string.bayartagihanbpjs));
        }
        description.setText(getString(R.string.lakukanberbagaipembayaranmelaluiweplus));
        btnBack.setOnClickListener(view -> finish());
    }

    private void sendBillTransactionRequest(){
        BillTransactionRequest request = new BillTransactionRequest();
        request.setBillNo(billInquiryData.getCustomer_id());
        request.setPaymentChannel(paymentChannel);
        request.setCode(billInquiryData.getItem());
        sendTransactionRequest(request);
    }

    private void sendTransactionRequest(BillTransactionRequest data){
        boolean isNetworkAvailable = Util.isNetworkAvailable(this);
        if (isNetworkAvailable){
            loadingProgress.setVisibility(View.VISIBLE);
            String token = WeplusSharedPreference.getToken(this);
            Call<String> call = NetworkManager
                    .getNetworkServiceWithHeader(this, token)
                    .sendBillTransaction(data);
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    Gson gson = new Gson();
                    BillTransactionResponse resp = gson.fromJson(response.body(), BillTransactionResponse.class);
                    try {
                        loadingProgress.setVisibility(View.GONE);
                        if (resp.getCode().equals(ErrorCode.ERROR_00)){
                            Intent intent = new Intent(BayarTagihanListrikActivity.this, KonfirmasiTagihanListrikActivity.class);
                            intent.putExtra("bill_category",billCategory);
                            intent.putExtra(
                                    "bill_transaction",
                                    resp.getData()
                            );
                            startActivity(intent);
                        } else if(resp.getCode().equals(ErrorCode.ERROR_03)){
                            Intent intent = new Intent(BayarTagihanListrikActivity.this, WelcomeActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }else {
                            new SweetAlertDialog(BayarTagihanListrikActivity.this, SweetAlertDialog.NORMAL_TYPE)
                                    .setTitleText("")
                                    .setContentText(resp.getMessage())
                                    .show();
                        }
                    }
                    catch (Exception e) {
                        loadingProgress.setVisibility(View.GONE);
                        Log.i(TAG,"asu: "+e.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    loadingProgress.setVisibility(View.GONE);
                    new SweetAlertDialog(BayarTagihanListrikActivity.this, SweetAlertDialog.NORMAL_TYPE)
                            .setTitleText("")
                            .setContentText("Time Out")
                            .show();

                    Log.i(TAG,"ON FAILURE : " + t.getMessage());
                }
            });
        } else {
            new SweetAlertDialog(this, SweetAlertDialog.NORMAL_TYPE)
                    .setTitleText(" ")
                    .setContentText(getString(R.string.network_error))
                    .show();
        }
    }

    @SuppressLint("SetTextI18n")
    private void prefillCustomerInformation() {
        if(billCategory==2){
            productIcon.setImageResource(R.drawable.ic_bpjs_3);
        }
        tvProductName.setText(billInquiryData.getItem_name());
        tvCustomerNumber.setText(billInquiryData.getCustomer_id());
        tvCustomerName.setText(billInquiryData.getCustomer_name());
        tvServiceType.setText(billInquiryData.getItem_name());
        tvPaymentPeriodeVal.setText(billInquiryData.getBill_period());
        tvServiceFeeVal.setText("Rp "+currencyFormatter(""+billInquiryData.getAdmin_price()));
        tvTotalPrice.setText("Rp "+currencyFormatter(""+billInquiryData.getTotal_price()));
        tvPriceVal.setText("Rp"+currencyFormatter(""+billInquiryData.getBilling_price()));
        tvAdminFeeVal.setText("Rp "+currencyFormatter("0"));
    }

    @OnClick(R.id.viewback_buttonback)
    public void actionBackPembayaran(){finish();}

    @OnClick(R.id.pembayaran_btn_beli_skrg)
    public void beliSekarang(){
        if(!paymentChannel.equals("")) {
//            Intent intent = new Intent(BayarTagihanListrikActivity.this, KonfirmasiTagihanListrikActivity.class);
//            startActivity(intent);
            sendBillTransactionRequest();
        }else{
            showError(this,"Pilih metode pembayaran");
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onPaymentClicked(Payment payment) {
        paymentChannel = payment.getParamValue();
        //dataTertanggungRequest.setPaymentChannel(payment.getParamValue());
        //insuranceProductPrice.setText("Rp."+currencyFormatter(payment.getNominal()));
        tvAdminFeeVal.setText("Rp "+currencyFormatter(payment.getAdminFee()));
        tvServiceFeeVal.setText("Rp "+currencyFormatter(payment.getPaymentFee()));
        tvPriceVal.setText("Rp "+currencyFormatter(payment.getNominal()));
        tvTotalPrice.setText("Rp "+currencyFormatter(payment.getTotal()));
    }
}


package id.weplus.pembayaran;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.deeplinkdispatch.DeepLink;
import com.airbnb.deeplinkdispatch.DeepLinkHandler;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.midtrans.sdk.corekit.callback.GetTransactionStatusCallback;
import com.midtrans.sdk.corekit.callback.TransactionCallback;
import com.midtrans.sdk.corekit.callback.TransactionFinishedCallback;
import com.midtrans.sdk.corekit.core.MidtransSDK;
import com.midtrans.sdk.corekit.core.TransactionRequest;
import com.midtrans.sdk.corekit.core.themes.CustomColorTheme;
import com.midtrans.sdk.corekit.models.TransactionResponse;
import com.midtrans.sdk.corekit.models.snap.Gopay;
import com.midtrans.sdk.corekit.models.snap.TransactionResult;
import com.midtrans.sdk.corekit.models.snap.TransactionStatusResponse;
import com.midtrans.sdk.uikit.SdkUIFlowBuilder;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.weplus.BaseActivity;
import id.weplus.R;
import id.weplus.Tagihan.BayarTagihanListrikActivity;
import id.weplus.WebViewActivity;
import id.weplus.WelcomeActivity;
import id.weplus.belipolis.PaymentInfoActivity;
import id.weplus.belipolis.mobil.FormDataMobilActivity;
import id.weplus.helper.OnSubmitNo;
import id.weplus.helper.OvoBottomSheet;
import id.weplus.helper.RoundedBottomSheet;
import id.weplus.model.Payment;
import id.weplus.model.Product;
import id.weplus.model.TransactionDetail;
import id.weplus.model.request.DataTertanggungRequest;
import id.weplus.model.request.OvoPaymentRequest;
import id.weplus.model.request.PaymentMethodRequest;
import id.weplus.model.response.PaymentMethodResponse;
import id.weplus.model.response.TransactionDetailResponse;
import id.weplus.net.ErrorCode;
import id.weplus.net.NetworkManager;
import id.weplus.net.WeplusSharedPreference;
import id.weplus.utility.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.midtrans.sdk.corekit.BuildConfig.BASE_URL;
import static id.weplus.utility.TextHelper.currencyFormatter;

public class PembayaranActivity extends BaseActivity implements OnPaymentClicked, OnSubmitNo, TransactionCallback {

    private Product product;
    private DataTertanggungRequest dataTertanggungRequest;
    private String TAG="PembayaranActivity";
    private PaymentMethodRequest paymentMethodRequest;
    private PembayaranAdapter paymentAdapter;
    private boolean isAgent =false;
    private String orderCode="";
    private String phone="";
    private String nik="";
    private int partnerWeplusId=0;
    private TransactionDetail transactionDetail;
    private String CLIENT_KEY="mid-client-Xy8UPb4tjlf3K3vw";

    @BindView(R.id.viewback_title) TextView title;
    @BindView(R.id.viewback_description) TextView description;
    @BindView(R.id.recycleview_pembayaran) RecyclerView recyclerViewPembayaran;
    @BindView(R.id.pembayaran_kode_voucher_tdk_ditemukan) TextView kodeVoucherTdkDitemukan;
    @BindView(R.id.pembayaran_icon) ImageView productIcon;
    @BindView(R.id.pay_insurance_name) TextView productName;
    @BindView(R.id.pay_insurance_amount) TextView productPrice;
    @BindView(R.id.tv_biayaprod_asuransi) TextView insuranceProductPrice;
    @BindView(R.id.tv_biaya_admin) TextView adminFee;
    @BindView(R.id.tv_biaya_asuransi) TextView insuranceFee;
    @BindView(R.id.tv_potongan_harga) TextView discount;
    @BindView(R.id.pembayaran_total) TextView totalFee;
    @BindView(R.id.pembayaran_btn_beli_skrg) Button btnBuy;
    @BindView(R.id.loading_progress) RelativeLayout loading_progress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pembayaran);
        ButterKnife.bind(this);
        title.setText(getString(R.string.pembayaran));
        description.setText(getString(R.string.silahkanpilihmetodepembayarananda));

        recyclerViewPembayaran.setNestedScrollingEnabled(true);
        paymentAdapter = new PembayaranAdapter(this,this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewPembayaran.setLayoutManager(mLayoutManager);
        recyclerViewPembayaran.setAdapter(paymentAdapter);
        getData();
        fetchPaymentMethod();
        setBuyButton();
    }

    private void setBuyButton(){
        btnBuy.setOnClickListener(view -> {
            if(dataTertanggungRequest.getPaymentChannel().toLowerCase().contains("ovo")){
                showOvoBottomSheet();
            }else {
                showThanks();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
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

    private void showThanks(){
        new SweetAlertDialog(PembayaranActivity.this, SweetAlertDialog.NORMAL_TYPE)
                .setTitleText("Terima Kasih")
                .setContentText("Terima kasih sudah memilih " +
                        "\nkami untuk proteksimu!" +
                        "\npolis kamu akan segera aktif" +
                        "\nsetelah kamu menyelesaikan" +
                        "\npembayaran dan mengupload" +
                        "\ndokumen tambahan jika" +
                        "\ndiperlukan")
                .setConfirmText("Ok")
                .setConfirmClickListener(sDialog -> {
                    sDialog.dismissWithAnimation();
                    insertTransaction();
                })
                .show();
    }



    private void showOvoBottomSheet(){
        OvoBottomSheet roundedBottomSheet = new OvoBottomSheet(this);
        Bundle bundle = new Bundle();
        roundedBottomSheet.setArguments(bundle);
        roundedBottomSheet.show(PembayaranActivity.this.getSupportFragmentManager(),"test");
    }

    @SuppressLint("SetTextI18n")
    private void getData(){
        product = getIntent().getParcelableExtra("product");
        isAgent = getIntent().getBooleanExtra("is_agent",false);
        partnerWeplusId = getIntent().getIntExtra("partner_weplus_id",0);
        nik = getIntent().getStringExtra("nik").toString();
        dataTertanggungRequest = getIntent().getParcelableExtra("data_tertanggung");
        dataTertanggungRequest.setProductId(""+product.getId());
        dataTertanggungRequest.setIsAgent(isAgent?"1":"0");
        paymentMethodRequest = new PaymentMethodRequest();
        paymentMethodRequest.setNominal(Integer.parseInt(product.getNominal()));
        paymentMethodRequest.setProductId(product.getId());
        paymentMethodRequest.setVoucherCode("");

        productName.setText(product.getName());
        if(product.getCategory_id().equals("7") ||product.getCategory_id().equals("2") || product.getCategory_id().equals("1") || product.getCategory_id().equals("5")) {
            productPrice.setText("Rp" + currencyFormatter(product.getNominal()));
            insuranceProductPrice.setText("Rp" + currencyFormatter(product.getNominal()));
        }else{
            productPrice.setText("Rp"+currencyFormatter(product.getPrice()));
            insuranceProductPrice.setText("Rp"+currencyFormatter(product.getPrice()));
            totalFee.setText("Rp"+currencyFormatter(product.getPrice()));

        }
        totalFee.setText("Rp"+currencyFormatter(product.getNominal()));
        Glide.with(this).load(product.getImage()).into(productIcon);
    }

    private void insertTransaction(){
        boolean isNetworkAvailable = Util.isNetworkAvailable(this);
        if (isNetworkAvailable){
            loading_progress.setVisibility(View.VISIBLE);
            String token = WeplusSharedPreference.getToken(this);
            Call<String> call = NetworkManager
                    .getNetworkServiceWithHeader(this, token)
                    .insertTransaction(dataTertanggungRequest);
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    loading_progress.setVisibility(View.GONE);
                    Gson gson = new Gson();
                    TransactionDetailResponse resp = gson.fromJson(response.body(), TransactionDetailResponse.class);
                    try {
                        if (resp.getCode().equals(ErrorCode.ERROR_00)){
                            transactionDetail = resp.data;
                            if(dataTertanggungRequest.getPaymentChannel().toLowerCase().contains("ovo")){
                                orderCode = resp.getData().getOrder_code();
                                sendOvoRequest(orderCode,phone);
                            }else if(dataTertanggungRequest.getPaymentChannel().toLowerCase().contains("gopay")){
                                Log.d("gopay","test gopay");
                                payUsingGopay(transactionDetail.getDetailPayment().getToken(),transactionDetail.getDetailPayment().getRedirectUrl());
//                                Log.d("gopay","test gopay url "+transactionDetail.getDetailPayment().getRedirectUrl());
//                                Intent intent = new Intent(PembayaranActivity.this, WebViewActivity.class);
//                                intent.putExtra("url", transactionDetail.getDetailPayment().getRedirectUrl());
//                                startActivity(intent);
                            }else{
                               goToPaymentInfo();
                            }
                            //Toast.makeText(PembayaranActivity.this,"Insert transaction success",Toast.LENGTH_LONG).show();
                        } else if(resp.getCode().equals(ErrorCode.ERROR_03)){
                            Intent intent = new Intent(PembayaranActivity.this, WelcomeActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }else {
                            Log.d(TAG,"error woi "+resp.getCode());
                            new SweetAlertDialog(PembayaranActivity.this, SweetAlertDialog.NORMAL_TYPE)
                                    .setTitleText("")
                                    .setContentText(resp.getMessage())
                                    .show();
                        }
                    }
                    catch (Exception e) {
                        Log.i(TAG,"asu: "+e.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    loading_progress.setVisibility(View.VISIBLE);
                    new SweetAlertDialog(PembayaranActivity.this, SweetAlertDialog.NORMAL_TYPE)
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

    private void goToPaymentInfo(){
        Intent intent = new Intent(PembayaranActivity.this, PaymentInfoActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("history", false);
        intent.putExtra("transaction_detail", transactionDetail);
        startActivity(intent);
        finish();
    }

    private void fetchPaymentMethod(){
        boolean isNetworkAvailable = Util.isNetworkAvailable(this);
        if (isNetworkAvailable){
            String token = WeplusSharedPreference.getToken(this);
            Call<String> call = NetworkManager
                    .getNetworkServiceWithHeader(this, token)
                    .getPaymentMethod(paymentMethodRequest);
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    Gson gson = new Gson();
                    PaymentMethodResponse resp = gson.fromJson(response.body(), PaymentMethodResponse.class);
                    try {
                        if (resp.getCode().equals(ErrorCode.ERROR_00)){
                            paymentAdapter.setPaymentMethod(resp.getData().getPaymentList());
                        } else {
                            new SweetAlertDialog(getApplicationContext(), SweetAlertDialog.NORMAL_TYPE)
                                    .setTitleText("")
                                    .setContentText(resp.getMessage())
                                    .show();
                        }
                    }
                    catch (Exception e) {
                        Log.i(TAG,"asu: "+e.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    new SweetAlertDialog(getApplicationContext(), SweetAlertDialog.NORMAL_TYPE)
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

    @OnClick(R.id.viewback_buttonback)
    public void actionBackPembayaran(){finish();}

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onPaymentClicked(Payment payment) {
        dataTertanggungRequest.setPaymentChannel(payment.getParamValue());
        insuranceFee.setText("Rp."+currencyFormatter(payment.getAdminFee()));
        adminFee.setText("Rp."+currencyFormatter(payment.getPaymentFee()));
        discount.setText("Rp."+currencyFormatter(payment.getDiscount()));
        totalFee.setText("Rp."+currencyFormatter(payment.getTotal()));
    }

    @Override
    public void onSubmitNo(@NotNull String phone) {
        this.phone=phone;
        showThanks();
    }

    private void sendOvoRequest(String orderCode,String phoneNumber) {
        boolean isNetworkAvailable = Util.isNetworkAvailable(this);
        if (isNetworkAvailable){
            OvoPaymentRequest request = new OvoPaymentRequest(orderCode,phoneNumber);
            loading_progress.setVisibility(View.VISIBLE);
            String token = WeplusSharedPreference.getToken(this);
            Call<String> call = NetworkManager
                    .getNetworkServiceWithHeader(this, token)
                    .sendOvoPayment(request);
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    loading_progress.setVisibility(View.GONE);
                    Gson gson = new Gson();
                    TransactionDetailResponse resp = gson.fromJson(response.body(), TransactionDetailResponse.class);
                    try {
                        if (resp.getCode().equals(ErrorCode.ERROR_00)){
                            new SweetAlertDialog(PembayaranActivity.this, SweetAlertDialog.NORMAL_TYPE)
                                    .setTitleText("")
                                    .setContentText("Jika tidak mendapatkan notifikasi" +
                                            "\npembayaran dari OVO silahkan" +
                                            "\nbuka aplikasi OVO anda dan cek" +
                                            "\nnotifikasi pembayaran")
                                    .setConfirmText("Ok")
                                    .setConfirmClickListener(sDialog -> {
                                        sDialog.dismissWithAnimation();
                                        goToPaymentInfo();
                                    })
                                    .show();
                        } else if(resp.getCode().equals(ErrorCode.ERROR_03)){
                            Intent intent = new Intent(PembayaranActivity.this, WelcomeActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }else {
                            Log.d(TAG,"error woi "+resp.getCode());
                            new SweetAlertDialog(PembayaranActivity.this, SweetAlertDialog.NORMAL_TYPE)
                                    .setTitleText("")
                                    .setContentText(resp.getMessage())
                                    .show();
                        }
                    }
                    catch (Exception e) {
                        Log.i(TAG,"asu: "+e.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    loading_progress.setVisibility(View.VISIBLE);
                    new SweetAlertDialog(PembayaranActivity.this, SweetAlertDialog.NORMAL_TYPE)
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

    private void
    payUsingGopay(String token,String redirectUrl){
        TransactionRequest transactionRequest = new TransactionRequest(transactionDetail.getOrder_code(), transactionDetail.getDetailPayment().getTotal_payment());
        //TransactionRequest transactionRequest = new TransactionRequest();
        transactionRequest.setGopay(new Gopay("com://weplus"));
        //transactionRequest.setGopay(new Gopay("demo://midtrans"));
        SdkUIFlowBuilder.init()
                .setClientKey(CLIENT_KEY) // client_key is mandatory
                .setContext(this) // context is mandatory
                .setTransactionFinishedCallback(result -> {
                    // Handle finished transaction here.
                }) // set transaction finish callback (sdk callback)
                .setMerchantBaseUrl(BASE_URL) //set merchant url (required)
                .enableLog(true) // enable sdk log (optional)
                .setColorTheme(new CustomColorTheme("#FFE51255", "#B61548", "#FFE51255")) // set theme. it will replace theme on snap theme on MAP ( optional)
                .buildSDK();
        Log.d("gopay","pay using gopay");
        MidtransSDK.getInstance().setTransactionRequest(transactionRequest);
        MidtransSDK.getInstance().getTransactionStatus(token, new GetTransactionStatusCallback() {
            @Override
            public void onSuccess(TransactionStatusResponse response) {
                // do action for response

                Log.d("test","testing sukses "+response.getStatusMessage());
            }

            @Override
            public void onFailure(TransactionStatusResponse response, String reason) {
                // do nothing
                Log.d("gopay","failed "+reason);
            }

            @Override
            public void onError(Throwable error) {
                // do action if error
                Log.d("gopay","failed "+error.getMessage());
            }
        });

        MidtransSDK.getInstance().paymentUsingGoPay(token, new TransactionCallback() {
                    @Override
                    public void onSuccess(TransactionResponse response) {
                        //action when transaction success
                        Log.d("test","testing sukses "+response.getStatusMessage());
                        String deeplinkUrl = response.getDeeplinkUrl();
                        if(deeplinkUrl.contains("callback_url")){
                            String[] test = deeplinkUrl.split("&callback_url");
                            Log.d("test","testing : "+test[0]);
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(test[0]+"&callback_url=com://weplus"));
                            startActivity(intent);
                        }else{
                            Log.d("test","testing deep: "+deeplinkUrl);
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(deeplinkUrl));
                            startActivity(intent);
                        }

//                        Log.d("gopay","sukses");
//                        String deeplinkUrl = response.getDeeplinkUrl();
//                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(deeplinkUrl));
//                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(TransactionResponse response, String reason) {
                        //action when transaction failure
                        Log.d("gopay","failed "+reason);
                    }

                    @Override
                    public void onError(Throwable error) {
                        //action when error
                        Log.d("gopay","error "+error.getMessage());
                    }
                }
        );


    }

    @Override
    public void onSuccess(TransactionResponse transactionResponse) {

    }

    @Override
    public void onFailure(TransactionResponse transactionResponse, String s) {

    }

    @Override
    public void onError(Throwable throwable) {

    }
}

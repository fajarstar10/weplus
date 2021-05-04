package id.weplus.Tagihan;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.weplus.R;
import id.weplus.WelcomeActivity;
import id.weplus.belipolis.perjalanan.BeliPolisPerjalananActivity;
import id.weplus.model.BillTransaction;
import id.weplus.model.request.BillTransactionRequest;
import id.weplus.model.response.BillTransactionData;
import id.weplus.model.response.BillTransactionResponse;
import id.weplus.net.ErrorCode;
import id.weplus.net.NetworkManager;
import id.weplus.net.WeplusSharedPreference;
import id.weplus.utility.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static id.weplus.utility.TextHelper.currencyFormatter;

public class DetailTagihanActivity extends AppCompatActivity {

    @BindView(R.id.productIcon) ImageView billIcon;
    @BindView(R.id.tvProductName) TextView billName;
    @BindView(R.id.tv_transaction_id) TextView transactionId;
    @BindView(R.id.tv_transaction_status) TextView transactionStatus;
    @BindView(R.id.tv_transaction_bill_number) TextView billNumber;
    @BindView(R.id.tvCustomerNumber) TextView customerNumber;
    @BindView(R.id.tvCustomerName) TextView customerName;
    @BindView(R.id.tv_transaction_date) TextView transactionDate;
    @BindView(R.id.tvPaymentPeriodeVal) TextView transactionPeriod;
    @BindView(R.id.tvPaymentType) TextView transactionType;
    @BindView(R.id.tvTransactionPrice) TextView transactionPrice;
    @BindView(R.id.tvAdminFeeVal) TextView adminFee;
    @BindView(R.id.tvServiceFeeVal) TextView serviceFee;
    @BindView(R.id.tv_transaction_total) TextView totalPrice;
    @BindView(R.id.tv_transaction_method_total) TextView paymentMethod;
    @BindView(R.id.btn_buy_again) Button buttonPayAgain;
    @BindView(R.id.loading_progress) RelativeLayout loadingProgress;
    @BindView(R.id.tv_transaction_bill_type_label) TextView billTypeLabel;
    @BindView(R.id.viewback_title) TextView title;
    @BindView(R.id.viewback_description) TextView description;
    @BindView(R.id.viewback_buttonback) ImageView buttonBack;

    private BillTransaction transaction;
    private BillTransactionData transactionData;
    private String TAG="DetailTagihanActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_tagihan);
        ButterKnife.bind(this);
        setupToolbar();
        getIntentData();
        fetchTransactionDetail();

    }

    private void setupToolbar(){
        title.setText("Detail Transaksi");
        description.setText("Melihat detail transaksi tagihan");
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void getIntentData(){
        transaction = getIntent().getParcelableExtra("transaction");
    }

    private void setupButtonPayAgain(){
        buttonPayAgain.setOnClickListener(view -> sendBillTransactionRequest());
    }
    private void fetchTransactionDetail(){
            boolean isNetworkAvailable = Util.isNetworkAvailable(this);
            if (isNetworkAvailable){
                loadingProgress.setVisibility(View.VISIBLE);
                String token = WeplusSharedPreference.getToken(this);
                Call<BillTransactionResponse> call = NetworkManager
                        .getNetworkServiceWithHeader(this, token)
                        .getBillDetail(""+transaction.getId());
                call.enqueue(new Callback<BillTransactionResponse>() {
                    @Override
                    public void onResponse(Call<BillTransactionResponse> call, Response<BillTransactionResponse> response) {
                        try {
                            loadingProgress.setVisibility(View.GONE);
                            if (response.body().getCode().equals(ErrorCode.ERROR_00)){
                                populateView(response.body().getData());
                                setupButtonPayAgain();
                            } else if(response.body().getCode().equals(ErrorCode.ERROR_03)){
                                Intent intent = new Intent(DetailTagihanActivity.this, WelcomeActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }else {
                                new SweetAlertDialog(DetailTagihanActivity.this, SweetAlertDialog.NORMAL_TYPE)
                                        .setTitleText("")
                                        .setContentText(response.message())
                                        .show();
                            }
                        }
                        catch (Exception e) {
                            loadingProgress.setVisibility(View.GONE);
                            Log.i(TAG,"asu: "+e.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(Call<BillTransactionResponse> call, Throwable t) {
                        loadingProgress.setVisibility(View.GONE);
                        new SweetAlertDialog(DetailTagihanActivity.this, SweetAlertDialog.NORMAL_TYPE)
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
    private void populateView(BillTransactionData data){
        transactionData = data;
        Glide.with(this).load(data.getImage()).into(billIcon);
        billNumber.setText(data.getBill_number());
        billName.setText(data.getProduct_name());
        transactionId.setText(data.getInquiry().getRq_uuid());

        String status = "";
        if(data.getStatus_payment().toLowerCase().equals("cancel transaction")){
            status="Batal";
        }else if(data.getStatus_payment().toLowerCase().equals("done")){
            status = "Berhasil";
        }else{
            status = data.getStatus_payment();
        }
        transactionStatus.setText(status);
        billTypeLabel.setText(data.getCategory_name());
        customerNumber.setText(data.getInquiry().getData().getCustomer_id());
        customerName.setText(data.getInquiry().getData().getCustomer_name());
        transactionDate.setText(data.getInquiry().getRs_datetime());
        transactionPeriod.setText(data.getInquiry().getData().getBill_period());
        transactionType.setText(data.getCategory_name());
        transactionPrice.setText("Rp "+currencyFormatter(""+data.getNominal()));
        adminFee.setText("Rp "+currencyFormatter(""+data.getProcessing_fee()));
        serviceFee.setText("Rp "+currencyFormatter(""+data.getAdmin_fee()));
        totalPrice.setText("Rp "+currencyFormatter(""+data.getTotal()));
        paymentMethod.setText(""+data.getPayment_name());
    }

    private void sendBillTransactionRequest(){
        BillTransactionRequest request = new BillTransactionRequest();
        request.setBillNo(transactionData.getBill_number());
        request.setPaymentChannel(transactionData.getPayment_channel());
        request.setCode(transactionData.getCode());
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
                            Intent intent = new Intent(DetailTagihanActivity.this, KonfirmasiTagihanListrikActivity.class);
                            intent.putExtra("bill_category",transactionData.getCategory_id());
                            intent.putExtra(
                                    "bill_transaction",
                                    resp.getData()
                            );
                            startActivity(intent);
                        } else if(resp.getCode().equals(ErrorCode.ERROR_03)){
                            Intent intent = new Intent(DetailTagihanActivity.this, WelcomeActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }else {
                            new SweetAlertDialog(DetailTagihanActivity.this, SweetAlertDialog.NORMAL_TYPE)
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
                    new SweetAlertDialog(DetailTagihanActivity.this, SweetAlertDialog.NORMAL_TYPE)
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
}
package id.weplus.transaksi;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.weplus.BaseActivity;
import id.weplus.R;
import id.weplus.Tagihan.BayarTagihanListrikActivity;
import id.weplus.WelcomeActivity;
import id.weplus.model.request.RefundRequest;
import id.weplus.model.response.RefundResponse;
import id.weplus.net.ErrorCode;
import id.weplus.net.NetworkManager;
import id.weplus.net.WeplusSharedPreference;
import id.weplus.pembayaran.PembayaranActivity;
import id.weplus.utility.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RefundActivity extends BaseActivity {
    @BindView(R.id.viewback_title) TextView title;
    @BindView(R.id.viewback_description) TextView description;
    @BindView(R.id.viewback_buttonback) ImageView btnBack;
    @BindView(R.id.bank_spinner) Spinner pilihanbank;
    @BindView(R.id.btn_refund) Button btnRefund;
    @BindView(R.id.kantor_cabang) EditText branchName;
    @BindView(R.id.nomor_rekening) EditText accountNumber;
    @BindView(R.id.nama_rekening) EditText accountName;
    @BindView(R.id.loadingProgress) RelativeLayout loadingProgress;

    private String TAG="RefundActivity";
    private String orderCode="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refund);
        ButterKnife.bind(this);
        getArgument();
        setupToolbar();
        setupBankOption();
        setupRefundButton();
    }

    private void getArgument(){
        orderCode = getIntent().getStringExtra("order_code");
    }
    @SuppressLint("SetTextI18n")
    private void setupToolbar(){
        title.setText(getResources().getString(R.string.refund));
        description.setText("Mengajukan proses pengembalian dana");
        btnBack.setOnClickListener(view -> finish());
    }

    private void setupBankOption(){
        List<String> listpilihanBank = new ArrayList<String>();
        listpilihanBank.add("Mandiri");
        listpilihanBank.add("BNI");
        listpilihanBank.add("BTPN");
        listpilihanBank.add("BRI");
        listpilihanBank.add("BCA");
        listpilihanBank.add("Danamon");
        listpilihanBank.add("DBS Bank");
        listpilihanBank.add("CIMB Niaga");
        ArrayAdapter<String> adapterPilihanBank = new ArrayAdapter<String>(this,R.layout.simple_spinner_item,listpilihanBank);
        adapterPilihanBank.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        pilihanbank.setAdapter(adapterPilihanBank);
        pilihanbank.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.grey_medium));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setupRefundButton(){
        btnRefund.setOnClickListener(view -> {
            if(validate()){
                sendRefundRequest();
            }else{
                    new SweetAlertDialog(this, SweetAlertDialog.NORMAL_TYPE)
                            .setTitleText("")
                            .setContentText("Semua field tidak boleh kosong")
                            .show();
            }
        });
    }

    private Boolean validate(){
       return !accountName.getText().toString().equals("") &&
                !accountName.getText().toString().equals("") &&
                !branchName.getText().toString().equals("");
    }
    
    private void sendRefundRequest(){
        Log.d("selected bank","bank "+pilihanbank.getSelectedItem().toString());
        Log.d("selected bank","order_code "+orderCode);

        boolean isNetworkAvailable = Util.isNetworkAvailable(this);
        if (isNetworkAvailable){
            loadingProgress.setVisibility(View.VISIBLE);
            String token = WeplusSharedPreference.getToken(this);
            RefundRequest request = new RefundRequest(
                    orderCode,
                    "transfer",
                    pilihanbank.getSelectedItem().toString(),
                    branchName.getText().toString(),
                    accountNumber.getText().toString(),
                    accountName.getText().toString()
            );
            Call<String> call = NetworkManager
                    .getNetworkServiceWithHeader(this, token)
                    .refund(request);
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {

                    loadingProgress.setVisibility(View.GONE);

                    Gson gson = new Gson();
                    RefundResponse resp = gson.fromJson(response.body(), RefundResponse.class);
                    try {
                        if (resp.getCode().equals(ErrorCode.ERROR_00)){
                            new SweetAlertDialog(RefundActivity.this, SweetAlertDialog.NORMAL_TYPE)
                                    .setTitleText("")
                                    .setContentText("Request refund telah dikirim dan " +
                                            "\nakan segera kami proses, mohon" +
                                            "\npolis kamu akan segera aktif" +
                                            "\nmenunggu. Terima Kasih")
                                    .setConfirmText("Ok")
                                    .setConfirmClickListener(sDialog -> {
                                        sDialog.dismissWithAnimation();
                                        finish();
                                    })
                                    .show();
                            //finish();
                        } else if(resp.getCode().equals(ErrorCode.ERROR_03)){
                            Intent intent = new Intent(RefundActivity.this, WelcomeActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }else {
                            showError(RefundActivity.this,resp.getMessage());
                        }
                    }
                    catch (Exception e) {
                        Log.i(TAG,"asu: "+e.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    loadingProgress.setVisibility(View.GONE);
                    finish();
                    try {
                        showError(RefundActivity.this,"Time Out");
                    }catch (Exception e){
                        e.getMessage();
                    }
                    Log.i(TAG,"ON FAILURE : " + t.getMessage());
                }
            });
        } else {
            showError(RefundActivity.this,getString(R.string.network_error));
        }
    }
}

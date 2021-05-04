package id.weplus.Tagihan;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.agrawalsuneet.dotsloader.loaders.AllianceLoader;
import com.google.gson.Gson;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.weplus.BaseActivity;
import id.weplus.R;
import id.weplus.WelcomeActivity;
import id.weplus.helper.FullScreenFilterActivity;
import id.weplus.model.request.BillInquiryRequest;
import id.weplus.model.request.BillPaymentProductRequest;
import id.weplus.model.response.BillInquiryResponse;
import id.weplus.model.response.BillPaymentCategoryProduct;
import id.weplus.model.response.BillPaymentCategoryProductResponse;
import id.weplus.net.ErrorCode;
import id.weplus.net.NetworkManager;
import id.weplus.net.WeplusSharedPreference;
import id.weplus.utility.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TagihanBpjsActivity extends BaseActivity {
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.viewback_title) TextView title;
    @BindView(R.id.viewback_description) TextView description;
    @BindView(R.id.jenis_bpjs_spiner) TextView jenisBpjs;
    @BindView(R.id.loader) AllianceLoader loader;
    @BindView(R.id.btn_bayar_bpjs) Button btnBayar;
    @BindView(R.id.nomor_va_keluarga_spiner) EditText etVaNumber;

    private String TAG="TagihanBPJSActivity";
    private String selectedJenisProduct="";
    private BillInquiryRequest billInquiryRequest;
    private HashMap mapProduct = new HashMap<String, String>();
    private BillPaymentCategoryProductResponse productResponse = new BillPaymentCategoryProductResponse();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tagihan_bpjs);

        ButterKnife.bind(this);
        title.setText(getString(R.string.bayarbpjs));
        description.setText(getString(R.string.lakukanberbagaipembayaranbpjsmelaluiweplus));
        fetchBillCategoryProduct();
    }

    @OnClick(R.id.viewback_buttonback)
    public void actionBackPembayaran(){finish();}

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @OnClick(R.id.btn_bayar_bpjs)
    public void beliSekarang(){
        billInquiryRequest = new BillInquiryRequest(selectedJenisProduct,etVaNumber.getText().toString());
        sendBillInquiry();

    }

    private void fetchBillCategoryProduct() {
        BillPaymentProductRequest request = new BillPaymentProductRequest();
        request.setCategoryProduct("2");
        boolean isNetworkAvailable = Util.isNetworkAvailable(this);
        if (isNetworkAvailable) {
            String token = WeplusSharedPreference.getToken(this);
            Call<String> call = NetworkManager
                    .getNetworkServiceWithHeader(this, token)
                    .getBillPaymentProduct(request);
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    Gson gson = new Gson();
                    productResponse = gson.fromJson(response.body(), BillPaymentCategoryProductResponse.class);
                    if (productResponse.getCode().equals("00")) {
                        setupProductSpinner(productResponse.getData().getCategoryProduct());
                    } else {
                        showError(TagihanBpjsActivity.this, productResponse.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    showError(TagihanBpjsActivity.this, getString(R.string.network_error));
                }
            });
        } else {
            showError(this, getString(R.string.network_error));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==23){
            String billType = data.getStringExtra("result");
            String code = (String) mapProduct.get(billType);
            selectedJenisProduct=code;
            jenisBpjs.setText(billType);
        }
    }

    private void setupProductSpinner(ArrayList<BillPaymentCategoryProduct> categoryProduct){

        mapProduct.clear();
        ArrayList<String> filter = new ArrayList<>();
        for (int i = 0; i < categoryProduct.size(); i++) {
            filter.add(categoryProduct.get(i).getName());
            mapProduct.put(categoryProduct.get(i).getName(),categoryProduct.get(i).getCode());
        }
        jenisBpjs.setOnClickListener(view -> {
            Intent intentBrand = new Intent(this, FullScreenFilterActivity.class);
            intentBrand.putStringArrayListExtra("searchList", filter);
            intentBrand.putExtra("resultCode", 23);
            startActivityForResult(intentBrand, 13);
        });
    }

    private void sendBillInquiry(){
        boolean isNetworkAvailable = Util.isNetworkAvailable(this);
        if (isNetworkAvailable){
            btnBayar.setVisibility(View.INVISIBLE);
            loader.setVisibility(View.VISIBLE);
            String token = WeplusSharedPreference.getToken(this);
            Call<String> call = NetworkManager
                    .getNetworkServiceWithHeader(this, token)
                    .sendBillInquiry(billInquiryRequest);
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    Gson gson = new Gson();
                    BillInquiryResponse resp = gson.fromJson(response.body(), BillInquiryResponse.class);
                    btnBayar.setVisibility(View.VISIBLE);
                    loader.setVisibility(View.INVISIBLE);
                    try {
                        if (resp.getCode().equals(ErrorCode.ERROR_00)){
                            Intent intent = new Intent(TagihanBpjsActivity.this, BayarTagihanListrikActivity.class);
                            intent.putExtra("bill_category",2);
                            intent.putExtra(
                                    "bill_inquiry",
                                    resp.getData()
                            );
                            startActivity(intent);
                        } else if(resp.getCode().equals(ErrorCode.ERROR_03)){
                            Intent intent = new Intent(TagihanBpjsActivity.this, WelcomeActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }else {
                            new SweetAlertDialog(TagihanBpjsActivity.this, SweetAlertDialog.NORMAL_TYPE)
                                    .setTitleText("")
                                    .setContentText(resp.getMessage())
                                    .show();
                        }
                    }
                    catch (Exception e) {
                        btnBayar.setVisibility(View.VISIBLE);
                        loader.setVisibility(View.INVISIBLE);
                        Log.i(TAG,"asu: "+e.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    btnBayar.setVisibility(View.VISIBLE);
                    loader.setVisibility(View.INVISIBLE);
                    new SweetAlertDialog(TagihanBpjsActivity.this, SweetAlertDialog.NORMAL_TYPE)
                            .setTitleText("")
                            .setContentText("Time Out")
                            .show();

                    Log.i(TAG,"ON FAILURE : " + t.getMessage());
                }
            });
        } else {
            btnBayar.setVisibility(View.VISIBLE);
            loader.setVisibility(View.INVISIBLE);
            new SweetAlertDialog(this, SweetAlertDialog.NORMAL_TYPE)
                    .setTitleText(" ")
                    .setContentText(getString(R.string.network_error))
                    .show();
        }
    }

}

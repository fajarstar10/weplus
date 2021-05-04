package id.weplus.Tagihan;

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

import androidx.annotation.Nullable;

import com.agrawalsuneet.dotsloader.loaders.AllianceLoader;
import com.google.gson.Gson;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import org.jetbrains.annotations.NotNull;

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
import id.weplus.helper.OnOptionsSelect;
import id.weplus.helper.RoundedBottomSheet;
import id.weplus.model.BaseFilter;
import id.weplus.model.BillCategory;
import id.weplus.model.request.BillInquiryRequest;
import id.weplus.model.request.BillPaymentProductRequest;
import id.weplus.model.response.BillInquiryResponse;
import id.weplus.model.response.BillPaymentCategoryProduct;
import id.weplus.model.response.BillPaymentCategoryProductResponse;
import id.weplus.model.response.BillPaymentCategoryResponse;
import id.weplus.model.response.BillPaymentProduct;
import id.weplus.net.ErrorCode;
import id.weplus.net.NetworkManager;
import id.weplus.net.WeplusSharedPreference;
import id.weplus.utility.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TagihanListrikActivity extends BaseActivity {
    @BindView(R.id.viewback_title) TextView title;
    @BindView(R.id.viewback_description) TextView description;
    @BindView(R.id.viewback_buttonback) ImageView backButton;
    @BindView(R.id.jenis_tagihan_listrik_spiner) TextView jenistagihanlistrik;
    @BindView(R.id.token_listrik_spiner) TextView jumlahnominaltokenlistrik;
    @BindView(R.id.jumlah_nominal_token_listrik) RelativeLayout jenisProductWrapper;
    @BindView(R.id.nomor_meter_spiner) EditText etNumberMeter;
    @BindView(R.id.tagihan_listrik_btn_bayar) Button btnBayar;
    @BindView(R.id.loader) AllianceLoader loader;

    private String TAG="BayarTagihanListrik";
    private ArrayList<BillPaymentCategoryProduct> productList = new ArrayList<>();
    private String pilihJenisTagihan = "Pilih Jenis Listrik PLN";
    private String pilihJenisProduk = "Pilih Jenis Produk";
    private String selectedJenisTagihan = "";
    private String selectedJenisProduct = "";
    private HashMap mapProduct = new HashMap<String, String>();
    private HashMap mapType = new HashMap<String, String>();
    private BillInquiryRequest billInquiryRequest;
    private BillPaymentCategoryProductResponse productResponse = new BillPaymentCategoryProductResponse();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tagihan_listrik);

        ButterKnife.bind(this);
        title.setText(getString(R.string.tagihan));
        description.setText(getString(R.string.lakukanberbagaipembayaranmelaluiweplus));
        backButton.setOnClickListener(view -> finish());

        fetchBillCategoryProduct();
    }

    private void fetchBillCategoryProduct() {
        BillPaymentProductRequest request = new BillPaymentProductRequest();
        request.setCategoryProduct("1");
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
                        setupPlnCategory(productResponse.getData());
                    } else {
                        showError(TagihanListrikActivity.this, productResponse.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    showError(TagihanListrikActivity.this, getString(R.string.network_error));
                }
            });
        } else {
            showError(this, getString(R.string.network_error));
        }
    }

    private void setupPlnCategory(BillCategory data) {
        setupPlnPaymentType(data.getCategoryProduct());
        setupJenisProduct(data.getCategoryProduct().get(1).getProduct());
    }

    private void setupPlnPaymentType(ArrayList<BillPaymentCategoryProduct> products) {
        this.productList = products;
        mapProduct.clear();
        ArrayList<String> filter = new ArrayList<>();
        for (int i = 0; i < productList.size(); i++) {
            filter.add(products.get(i).getName());
            mapProduct.put(products.get(i).getName(),products.get(i).getId());
        }
        jenistagihanlistrik.setOnClickListener(view -> {
            Intent intentBrand = new Intent(this, FullScreenFilterActivity.class);
            intentBrand.putStringArrayListExtra("searchList", filter);
            intentBrand.putExtra("resultCode", 23);
            startActivityForResult(intentBrand, 13);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==23){
            String billType = data.getStringExtra("result");
            String id = (String) mapProduct.get(billType);
            selectedJenisTagihan =""+id;
            jenistagihanlistrik.setText(billType);

            if (id.equals("2")) {
                jenisProductWrapper.setVisibility(View.VISIBLE);
                selectedJenisProduct = "";
            } else {
                jenisProductWrapper.setVisibility(View.GONE);
                selectedJenisProduct = "PLNPASCA";
            }
        }
        if(resultCode==24){
            String type = data.getStringExtra("result");
            String code = (String) mapType.get(type);
            selectedJenisProduct = code;
            jumlahnominaltokenlistrik.setText(type);
        }
    }

    private void setupJenisProduct(ArrayList<BillPaymentProduct> categoryProduct) {
        ArrayList<String> filters = new ArrayList<>();
        for (int i = 0; i < categoryProduct.size(); i++) {
            mapType.put(categoryProduct.get(i).getName(),categoryProduct.get(i).getCode());
            filters.add(categoryProduct.get(i).getName());
        }
        jumlahnominaltokenlistrik.setOnClickListener(view -> {
            Intent intentBrand = new Intent(this, FullScreenFilterActivity.class);
            intentBrand.putStringArrayListExtra("searchList", filters);
            intentBrand.putExtra("resultCode", 24);
            startActivityForResult(intentBrand, 14);
        });
    }


    @OnClick(R.id.viewback_buttonback)
    public void actionBackPembayaran() {
        finish();
    }

    @OnClick(R.id.tagihan_listrik_btn_bayar)
    public void beliSekarang() {
        Log.d("tagihan_listrik", "selected jenis product : " + selectedJenisProduct);
        billInquiryRequest = new BillInquiryRequest(selectedJenisProduct,etNumberMeter.getText().toString());
        sendBillInquiry();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
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
                            Intent intent = new Intent(TagihanListrikActivity.this, BayarTagihanListrikActivity.class);
                            intent.putExtra("bill_category",1);
                            intent.putExtra(
                                    "bill_inquiry",
                                    resp.getData()
                            );
                            startActivity(intent);
                        } else if(resp.getCode().equals(ErrorCode.ERROR_03)){
                            Intent intent = new Intent(TagihanListrikActivity.this, WelcomeActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }else {
                            new SweetAlertDialog(TagihanListrikActivity.this, SweetAlertDialog.NORMAL_TYPE)
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
                    new SweetAlertDialog(TagihanListrikActivity.this, SweetAlertDialog.NORMAL_TYPE)
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


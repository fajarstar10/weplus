package id.weplus.belipolis.criticalIll;


import android.content.Intent;
import android.os.Bundle;
import androidx.recyclerview.widget.DefaultItemAnimator;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.weplus.BaseActivity;
import id.weplus.R;
import id.weplus.Tagihan.BayarTagihanListrikActivity;
import id.weplus.WelcomeActivity;
import id.weplus.belipolis.productlist.OnPartnerClickListener;
import id.weplus.belipolis.productlist.PartnerListAdapter;
import id.weplus.belipolis.productlist.ProductListAdapter;
import id.weplus.helper.EndlessOnScrollListener;
import id.weplus.model.PartnerTravel;
import id.weplus.model.Product;
import id.weplus.model.request.CriticalllnessProductListRequest;
import id.weplus.model.request.ProductListRequest;
import id.weplus.model.response.ProductListResponse;
import id.weplus.net.ErrorCode;
import id.weplus.net.NetworkManager;
import id.weplus.net.WeplusSharedPreference;
import id.weplus.utility.Constant;
import id.weplus.utility.FirebaseAnalyticsHelper;
import id.weplus.utility.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CriticalProductListActivity extends BaseActivity implements OnPartnerClickListener {
    private String TAG="product_list";
    private int page=1;
    private int catId=0;
    private int partnerId=0;
    private boolean isFilterEnabled=true;
    private boolean isFromCompany=false;
    private CriticalProductListAdapter productsAdapter;
    private PartnerListAdapter partnersAdapter;
    private CriticalllnessProductListRequest requestBody;
    @BindView(R.id.rvProducts) RecyclerView rvProducts;
    @BindView(R.id.rvPartner) RecyclerView rvPartners;
    @BindView(R.id.viewback_title) TextView title;
    @BindView(R.id.viewback_description) TextView description;
    @BindView(R.id.viewback_buttonback) ImageView backButton;
    @BindView(R.id.tvSubtitle) TextView tvSubtitle;
    @BindView(R.id.loadingWrapper) RelativeLayout loadingWrapper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseAnalyticsHelper.logEvent(this, Constant.ANALYTICS_LIST_PRODUCT);

        setContentView(R.layout.activity_product_list);
        ButterKnife.bind(this);
        loadingWrapper.setVisibility(View.VISIBLE);
        getIntentVal();
        initToolbar();
        setupProductAdapter();
        setupPartnerAdapter();
        fetchProductList();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void initToolbar(){
        title.setText(getResources().getString(R.string.product_asuransi));
        description.setText(getResources().getString(R.string.product_asuransi_desc));
        backButton.setOnClickListener(view -> finish());
    }

    private void getIntentVal(){
        requestBody = getIntent().getParcelableExtra("requestBody");
        if(requestBody.getPartner_id()!=0){
            isFromCompany=true;
        }
        catId=getIntent().getIntExtra("cat_id",0);
    }

    private void setupProductAdapter(){
        productsAdapter = new CriticalProductListAdapter
                (CriticalProductListActivity.this, new ArrayList<Product>(),catId,requestBody);

        rvProducts.setNestedScrollingEnabled(true);
        RecyclerView.LayoutManager mLayoutManager
                = new LinearLayoutManager(CriticalProductListActivity.this);
        rvProducts.setLayoutManager(mLayoutManager);
        rvProducts.setAdapter(productsAdapter);
        rvProducts.setItemAnimator(new DefaultItemAnimator());
        rvProducts.addOnScrollListener(scrollData());
    }

    private void setupPartnerAdapter(){
        partnersAdapter = new PartnerListAdapter(this, new ArrayList<PartnerTravel>(), this);
        if(isFilterEnabled) {
            rvPartners.setVisibility(View.VISIBLE);
            LinearLayoutManager layoutManager
                    = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
            rvPartners.setLayoutManager(layoutManager);
            rvPartners.setAdapter(partnersAdapter);
            rvProducts.setItemAnimator(new DefaultItemAnimator());
        }else{
            tvSubtitle.setVisibility(View.GONE);
            rvPartners.setVisibility(View.GONE);
        }

    }

    private void fetchProductList(){
        boolean isNetworkAvailable = Util.isNetworkAvailable(this);
        if (isNetworkAvailable){
            String token = WeplusSharedPreference.getToken(this);
            Log.d("partner","request : "+catId+" - "+partnerId);
            Call<String> call = NetworkManager
                    .getNetworkServiceWithHeader(this, token)
                    .getCriticalProductList(page,requestBody);
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    loadingWrapper.setVisibility(View.GONE);
                    Gson gson = new Gson();
                    ProductListResponse resp = gson.fromJson(response.body(), ProductListResponse.class);
                    try {
                        if (resp.getCode().equals(ErrorCode.ERROR_00)){
                            productsAdapter.addItems((ArrayList<Product>) resp.getData().getProduct());
                            if(isFromCompany){
                                ArrayList<PartnerTravel> newPartnerList = new ArrayList();
                                for(int i=0;i<resp.getData().partner.size();i++){
                                    if(resp.getData().partner.get(i).getId()==requestBody.getPartner_id()){
                                        newPartnerList.add(resp.getData().partner.get(i));
                                    }
                                }
                                partnersAdapter.setPartners(newPartnerList);
                            }else {
                                partnersAdapter.setPartners((ArrayList<PartnerTravel>) resp.getData().partner);
                            }
                        } else if(resp.getCode().equals(ErrorCode.ERROR_03)){
                            Intent intent = new Intent(CriticalProductListActivity.this, WelcomeActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }else {
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
                    loadingWrapper.setVisibility(View.GONE);
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

    private void fetchProductByPartner(int partnerId){
        loadingWrapper.setVisibility(View.VISIBLE);
        requestBody.setPartner_id(partnerId);
        boolean isNetworkAvailable = Util.isNetworkAvailable(this);
        if (isNetworkAvailable){
            String token = WeplusSharedPreference.getToken(this);
            Log.d("partner","request : "+catId+" - "+partnerId);
            Call<String> call = NetworkManager
                    .getNetworkServiceWithHeader(this, token)
                    .getCriticalProductList(page,requestBody);
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    Gson gson = new Gson();
                    ProductListResponse resp = gson.fromJson(response.body(), ProductListResponse.class);
                    try {
                        if (resp.getCode().equals(ErrorCode.ERROR_00)){
                            productsAdapter.setItems((ArrayList<Product>) resp.getData().getProduct());
                            partnersAdapter.setPartners((ArrayList<PartnerTravel>) resp.getData().partner);
                        } else if(resp.getCode().equals(ErrorCode.ERROR_03)){
                            Intent intent = new Intent(CriticalProductListActivity.this, WelcomeActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }else {
                            new SweetAlertDialog(getApplicationContext(), SweetAlertDialog.NORMAL_TYPE)
                                    .setTitleText("")
                                    .setContentText(resp.getMessage())
                                    .show();
                        }
                    }
                    catch (Exception e) {
                        Log.i(TAG,"asu: "+e.getMessage());
                    }finally {
                        loadingWrapper.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onFailure(@NotNull Call<String> call, Throwable t) {
                    new SweetAlertDialog(getApplicationContext(), SweetAlertDialog.NORMAL_TYPE)
                            .setTitleText("")
                            .setContentText("Time Out")
                            .show();

                    Log.i(TAG,"ON FAILURE : " + t.getMessage());
                }
            });
        } else {
            loadingWrapper.setVisibility(View.GONE);
            new SweetAlertDialog(this, SweetAlertDialog.NORMAL_TYPE)
                    .setTitleText(" ")
                    .setContentText(getString(R.string.network_error))
                    .show();
        }
    }


    private EndlessOnScrollListener scrollData() {
        return new EndlessOnScrollListener() {
            @Override
            public void onLoadMore() {
                page++;
                fetchProductList();
            }
        };
    }

    @Override
    public void onPartnerClick(PartnerTravel partnerTravel) {
        page=1;
        fetchProductByPartner(partnerTravel.getId());
    }
}


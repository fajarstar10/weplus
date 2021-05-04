package id.weplus.belipolis.productlist;

import android.os.Bundle;
import androidx.recyclerview.widget.DefaultItemAnimator;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cooltechworks.views.shimmer.ShimmerRecyclerView;
import com.google.gson.Gson;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.weplus.BaseActivity;
import id.weplus.R;
import id.weplus.helper.EndlessOnScrollListener;
import id.weplus.model.PartnerTravel;
import id.weplus.model.Product;
import id.weplus.model.request.ProductListRequest;
import id.weplus.model.response.ProductListResponse;
import id.weplus.net.ErrorCode;
import id.weplus.net.NetworkManager;
import id.weplus.net.WeplusSharedPreference;
import id.weplus.utility.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductListActivity extends BaseActivity implements OnPartnerClickListener {
    private String TAG="product_list";
    private int page=1;
    private int catId=0;
    private int partnerId=0;
    private int partnerWeplusId=0;
    private boolean isAgent=false;
    private int isCompanyBase=0;
    private int nik=0;
    private boolean isFromCompany=false;
    private boolean isFilterEnabled=false;
    private ProductListAdapter productsAdapter;
    private PartnerListAdapter partnersAdapter;
    private ProductListRequest productListRequest;
    @BindView(R.id.rvProducts) ShimmerRecyclerView rvProducts;
    @BindView(R.id.rvPartner) RecyclerView rvPartners;
    @BindView(R.id.viewback_title) TextView title;
    @BindView(R.id.viewback_description) TextView description;
    @BindView(R.id.viewback_buttonback) ImageView buttonBack;
    @BindView(R.id.tvSubtitle) TextView tvSubtitle;
    @BindView(R.id.loadingWrapper) RelativeLayout loader;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
        ButterKnife.bind(this);
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
        buttonBack.setOnClickListener(view -> {
            finish();
        });
    }

    private void getIntentVal(){
        catId = getIntent().getIntExtra("cat_id",0);
        partnerId = getIntent().getIntExtra("partner_id",0);
        if(partnerId!=0){
            isFromCompany=true;
        }
        partnerWeplusId = getIntent().getIntExtra("partner_weplus_id",0);
        nik = getIntent().getIntExtra("nik",0);
        isFilterEnabled = getIntent().getBooleanExtra("filterEnabled",false);
        isCompanyBase = getIntent().getIntExtra("is_company_base",0);
        isAgent = getIntent().getBooleanExtra("is_agent",false);
        //if(catId>0){
            productListRequest = new ProductListRequest(catId,partnerId,partnerWeplusId,"2000-06-21","m",""+nik);
            productListRequest.setIs_company_base(isCompanyBase);
            productListRequest.setIsAgent(isAgent?1:0);
//        }else{
//            productListRequest= new ProductListRequest(catId,partnerId);
//        }
        Log.d("partner","partnerId: "+partnerId);
        Log.d("isAgent","productList: "+isAgent);
    }

    private void setupProductAdapter(){
        productsAdapter = new ProductListAdapter
                (ProductListActivity.this, new ArrayList<Product>(),catId);
        productsAdapter.setIsAgent(isAgent);
        rvProducts.setNestedScrollingEnabled(true);
        RecyclerView.LayoutManager mLayoutManager
                = new LinearLayoutManager(ProductListActivity.this);
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
        rvProducts.showShimmerAdapter();
        boolean isNetworkAvailable = Util.isNetworkAvailable(this);
        if (isNetworkAvailable){
            String token = WeplusSharedPreference.getToken(this);
            Log.d("partner","request : "+catId+" - "+partnerId);
            Call<String> call = NetworkManager
                    .getNetworkServiceWithHeader(this, token)
                    .getProductList(page,productListRequest);
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    rvProducts.hideShimmerAdapter();
                    Gson gson = new Gson();
                    ProductListResponse resp = gson.fromJson(response.body(), ProductListResponse.class);
                    try {
                        if (resp.getCode().equals(ErrorCode.ERROR_00)){
                          productsAdapter.addItems((ArrayList<Product>) resp.getData().getProduct());
                          partnersAdapter.setPartners((ArrayList<PartnerTravel>) resp.getData().partner);
                        } else {
                            new SweetAlertDialog(getApplicationContext(), SweetAlertDialog.NORMAL_TYPE)
                                    .setTitleText("")
                                    .setContentText(resp.getMessage())
                                    .show();
                        }
                    }
                    catch (Exception e) {
                        Log.i(TAG,"asu: "+e.getMessage());
                    }finally {
                        rvProducts.hideShimmerAdapter();
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    rvProducts.hideShimmerAdapter();
                    new SweetAlertDialog(getApplicationContext(), SweetAlertDialog.NORMAL_TYPE)
                            .setTitleText("")
                            .setContentText("Time Out")
                            .show();

                    Log.i(TAG,"ON FAILURE : " + t.getMessage());
                }
            });
        } else {
            rvProducts.hideShimmerAdapter();
            new SweetAlertDialog(this, SweetAlertDialog.NORMAL_TYPE)
                    .setTitleText(" ")
                    .setContentText(getString(R.string.network_error))
                    .show();
        }
    }

    private void fetchProductByPartner(int partnerId){
        loader.setVisibility(View.VISIBLE);
        productListRequest.setPartnerId(partnerId);
        boolean isNetworkAvailable = Util.isNetworkAvailable(this);
        if (isNetworkAvailable){
            String token = WeplusSharedPreference.getToken(this);
            Log.d("partner","request : "+catId+" - "+partnerId);
            Call<String> call = NetworkManager
                    .getNetworkServiceWithHeader(this, token)
                    .getProductList(page,productListRequest);
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    Gson gson = new Gson();
                    ProductListResponse resp = gson.fromJson(response.body(), ProductListResponse.class);
                    try {
                        if (resp.getCode().equals(ErrorCode.ERROR_00)){
                            if(page==1) productsAdapter.clearItems();
                            productsAdapter.setItems((ArrayList<Product>) resp.getData().getProduct());
                            partnersAdapter.setPartners((ArrayList<PartnerTravel>) resp.getData().partner);
                        } else {
                            new SweetAlertDialog(getApplicationContext(), SweetAlertDialog.NORMAL_TYPE)
                                    .setTitleText("")
                                    .setContentText(resp.getMessage())
                                    .show();
                        }
                    }
                    catch (Exception e) {
                        Log.i(TAG,"asu: "+e.getMessage());
                    }finally {
                        loader.setVisibility(View.GONE);
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
            loader.setVisibility(View.GONE);
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
        //Toast.makeText(this,"clicked "+partnerTravel.getName(),Toast.LENGTH_LONG).show();
        //productsAdapter.filterByPartner(""+partnerTravel.getId());
        //productsAdapter.sort(""+partnerTravel.getId());
        page=1;
        fetchProductByPartner(partnerTravel.getId());
    }
}

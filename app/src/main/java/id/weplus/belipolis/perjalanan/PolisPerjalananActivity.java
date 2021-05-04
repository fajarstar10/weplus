package id.weplus.belipolis.perjalanan;

import android.app.Activity;
import android.os.Bundle;
import androidx.recyclerview.widget.DefaultItemAnimator;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.weplus.BaseActivity;
import id.weplus.R;
import id.weplus.belipolis.productlist.OnPartnerClickListener;
import id.weplus.belipolis.productlist.PartnerListAdapter;
import id.weplus.belipolis.productlist.ProductListAdapter;
import id.weplus.helper.EndlessOnScrollListener;
import id.weplus.model.PartnerTravel;
import id.weplus.model.Product;
import id.weplus.model.request.TravelProductListRequest;
import id.weplus.model.response.ProductListResponse;
import id.weplus.net.ErrorCode;
import id.weplus.net.NetworkManager;
import id.weplus.net.WeplusSharedPreference;
import id.weplus.utility.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PolisPerjalananActivity extends BaseActivity implements OnPartnerClickListener {
    private static String TAG = "Polis Perjalanan ";
    private PartnerTravelAdapter.OnClickedKategoriSemua listenerSemua;
    private Activity activity;
    private int page=1;
    private int catId=0;
    private TravelListAdapter productsAdapter;
    private PartnerListAdapter partnersAdapter;
    private TravelProductListRequest requestBody;

    @BindView(R.id.viewback_title) TextView title;
    @BindView(R.id.viewback_description) TextView description;
    @BindView(R.id.perjalanan_partner_rec) RecyclerView rvPartners;
    @BindView(R.id.perjalanan_partner_rec_by_filter) RecyclerView rvProducts;
    @BindView(R.id.perjalanan_image_icon) ImageView icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_polis_perjalanan);
        ButterKnife.bind(this);
        getIntentVal();
        setupToolbar();
        setupProductAdapter();
        setupPartnerAdapter();
        fetchProductList();
        activity = this;
    }

    private void getIntentVal(){
        requestBody = getIntent().getParcelableExtra("requestBody");
        //isFilterEnabled = getIntent().getBooleanExtra("")
    }

    private void setupProductAdapter(){
        productsAdapter = new TravelListAdapter(
                PolisPerjalananActivity.this, new ArrayList<Product>(), catId,requestBody);

        rvProducts.setNestedScrollingEnabled(true);
        RecyclerView.LayoutManager mLayoutManager
                = new LinearLayoutManager(PolisPerjalananActivity.this);
        rvProducts.setLayoutManager(mLayoutManager);
        rvProducts.setAdapter(productsAdapter);
        rvProducts.setItemAnimator(new DefaultItemAnimator());
        rvProducts.addOnScrollListener(scrollData());
    }

    private void setupPartnerAdapter(){
        partnersAdapter = new PartnerListAdapter(this,new ArrayList<PartnerTravel>(),this);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvPartners.setLayoutManager(layoutManager);
        rvPartners.setAdapter(partnersAdapter);
        rvProducts.setItemAnimator(new DefaultItemAnimator());
    }

    private void setupToolbar(){
        title.setText(getResources().getString(R.string.asuransiperjalanan));
        description.setText(getResources().getString(R.string.pilihberbagaijenisasuransiygtersedia));
    }

    @OnClick(R.id.viewback_buttonback)
    public void actionBackPolisPerjalanan(){finish();}

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    private void fetchProductList(){
        boolean isNetworkAvailable = Util.isNetworkAvailable(this);
        if (isNetworkAvailable){
            String token = WeplusSharedPreference.getToken(this);
            Call<String> call = NetworkManager
                    .getNetworkServiceWithHeader(this, token)
                    .getTravelProductList(page,requestBody);
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    Gson gson = new Gson();
                    ProductListResponse resp = gson.fromJson(response.body(), ProductListResponse.class);
                    try {
                        if (resp.getCode().equals(ErrorCode.ERROR_00)){
                            productsAdapter.addItems((ArrayList<Product>) resp.getData().getProduct());
                            if(requestBody.getPartner_id()!=0){
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
                        } else {
                            new SweetAlertDialog(getApplicationContext(), SweetAlertDialog.NORMAL_TYPE)
                                    .setTitleText("")
                                    .setContentText(resp.getMessage())
                                    .show();
                        }
                    }
                    catch (Exception e) {
                        Log.i(TAG, e.getMessage());
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

    private void fetchProductByPartner(int id) {
        Log.d("testing", page+"filter by partner "+id);
        requestBody.setPartner_id(id);
        boolean isNetworkAvailable = Util.isNetworkAvailable(this);
        if (isNetworkAvailable){
            String token = WeplusSharedPreference.getToken(this);
            Call<String> call = NetworkManager
                    .getNetworkServiceWithHeader(this, token)
                    .getTravelProductList(page,requestBody);
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    Gson gson = new Gson();
                    ProductListResponse resp = gson.fromJson(response.body(), ProductListResponse.class);
                    try {
                        if (resp.getCode().equals(ErrorCode.ERROR_00)){
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
                        Log.i(TAG, e.getMessage());
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
        Log.d("testing", "clicked onPartnerClick");
        fetchProductByPartner(partnerTravel.getId());
    }


}

package id.weplus.Mitra;

import android.content.Intent;
import android.os.Bundle;import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.zxing.common.StringUtils;
import com.lsjwzh.widget.materialloadingprogressbar.CircleProgressBar;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.weplus.BaseActivity;
import id.weplus.R;
import id.weplus.WelcomeActivity;
import id.weplus.belipolis.KategoriAdapter;
import id.weplus.belipolis.gadget.GadgetFilterFormActivity;
import id.weplus.belipolis.mobil.FormDataMobilActivity;
import id.weplus.belipolis.motor.AsuransiMotorActivity;
import id.weplus.belipolis.perjalanan.BeliPolisPerjalananActivity;
import id.weplus.belipolis.productlist.ProductListActivity;
import id.weplus.model.BuyPolisModel;
import id.weplus.model.CategoryForPartner;
import id.weplus.model.response.CategoryForPartnerResponse;
import id.weplus.net.ErrorCode;
import id.weplus.net.NetworkManager;
import id.weplus.net.WeplusSharedPreference;
import id.weplus.utility.Constant;
import id.weplus.utility.FirebaseAnalyticsHelper;
import id.weplus.utility.GridSpacingItemDecoration;
import id.weplus.utility.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PartnerCategoryActivity extends BaseActivity implements OnPartnerCategoryClickListener {

    private int partnerId;
    private String nik;
    private String TAG="partnerCategory";
    private String titleText = "Karyawan ";
    private PartnerCategoryAdapter adapter;
    private MitraBannerAdapter adapterBanner;
    private KategoriAdapter.OnClickedKategoriSemua listenerSemua;
    @BindView(R.id.viewback_title) TextView title;
    @BindView(R.id.viewback_buttonback) ImageView backBtn;
    @BindView(R.id.viewback_description) TextView description;
    @BindView(R.id.progressBar) CircleProgressBar progressBar;
    @BindView(R.id.rvPartnerCategory) RecyclerView rvPartnerCategory;
    @BindView(R.id.rvPartnerBanner) RecyclerView rvPartnerBanner;
    @BindView(R.id.imgBanner) ImageView imgBanner;
    @BindView(R.id.bannerWrapper) LinearLayout bannerWrapper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partner_category);
        ButterKnife.bind(this);
        getData();
        setTitle();
        setupAdapter();
        fetchPartnerCategory();
    }

    private void setTitle(){
        title.setText(Util.upperCaseLetter(titleText));
        description.setText(getString(R.string.partneryangbekerjasamadenganweplus));
        backBtn.setOnClickListener(view -> finish());
    }
    private void getData(){
        partnerId = getIntent().getIntExtra("partner_id",0);
        nik = getIntent().getStringExtra("nik");
        titleText += getIntent().getStringExtra("employee");
        if(partnerId==1){
            imgBanner.setVisibility(View.VISIBLE);
            Glide.with(PartnerCategoryActivity.this)
                                    .load(R.drawable.banner_mitra_ranch_market_202011260001)
                                    .into(imgBanner);
        }else if(partnerId==3){
            imgBanner.setVisibility(View.VISIBLE);
            Glide.with(PartnerCategoryActivity.this)
                    .load(R.drawable.banner_mitra_mattel_202011260001)
                    .into(imgBanner);
        }else if(partnerId==2){
            imgBanner.setVisibility(View.VISIBLE);
            Glide.with(PartnerCategoryActivity.this)
                    .load(R.drawable.banner_mitra_alfamart_202011260001)
                    .into(imgBanner);
        }

    }

    private void setupAdapter() {
        adapter = new PartnerCategoryAdapter(this,this);
        adapterBanner = new MitraBannerAdapter(PartnerCategoryActivity.this);
        
        rvPartnerCategory.setNestedScrollingEnabled(true);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 3);
        rvPartnerCategory.setLayoutManager(mLayoutManager);
        rvPartnerCategory.setAdapter(adapter);
        rvPartnerCategory.addItemDecoration(new GridSpacingItemDecoration(3, Util.dpToPx(10, this), false));

        rvPartnerBanner.setNestedScrollingEnabled(true);
        LinearLayoutManager produkFavoritLM = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvPartnerBanner.setLayoutManager(produkFavoritLM);
        rvPartnerBanner.setAdapter(adapterBanner);
    }

    private void fetchPartnerCategory(){
        boolean isNetworkAvailable = Util.isNetworkAvailable(getApplication().getApplicationContext());
        if (isNetworkAvailable) {
            progressBar.setVisibility(View.VISIBLE);
            String token = WeplusSharedPreference.getToken(getApplication());
            Call<String> call = NetworkManager
                    .getNetworkServiceWithHeader(getApplication(),token)
                    .getCategoryForPartner(partnerId,nik);
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    progressBar.setVisibility(View.INVISIBLE);
                    Gson gson = new Gson();
                    CategoryForPartnerResponse resultResponse = gson.fromJson(response.body(), CategoryForPartnerResponse.class);
                    CategoryForPartner result = resultResponse.getData();

                    try {
                        JSONObject job = new JSONObject(response.body());
                        String code = job.getString("code");
                        String description = job.getString("message");
                        if (code.equals(ErrorCode.ERROR_00)) {
                            adapter.addItems(result.getCategory());
                            if(result.getBanner().size()>0){
                                bannerWrapper.setVisibility(View.VISIBLE);
                                adapterBanner.addItems(result.getBanner());
                            }

//                            Glide.with(PartnerCategoryActivity.this)
//                                    .load(result.getBanner().get(0).getImage())
//                                    .into(imgBanner);
                        }else if(code.equals(ErrorCode.ERROR_03)){
                            Intent intent = new Intent(PartnerCategoryActivity.this, WelcomeActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        } else {
                            showError(description);
                        }

                    } catch (Exception e) {
                        Log.i(TAG, e.getMessage());
                    }

                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    progressBar.setVisibility(View.INVISIBLE);
                    showError("Time Out");
                    Log.i(TAG, "On FAILUR : " + t.getMessage());

                }
            });
        } else {
            showError(getString(R.string.network_error));
        }
    }

    @Override
    public void onClickListener(BuyPolisModel.Categori categori) {
        switch (categori.getId()){
            case 1:
                Intent motor = new Intent(this, AsuransiMotorActivity.class);
                motor.putExtra("nik",nik);
                motor.putExtra("cat_id",categori.getId());
                motor.putExtra("partner_weplus_id",partnerId);
                startActivity(motor);
                break;
            case 5:
                Intent mobil = new Intent(this, FormDataMobilActivity.class);
                mobil.putExtra("nik",nik);
                mobil.putExtra("cat_id",categori.getId());
                mobil.putExtra("partner_weplus_id",partnerId);
                startActivity(mobil);
                break;
            case 15:
                Intent gadget = new Intent(this, GadgetFilterFormActivity.class);
                gadget.putExtra("nik",nik);
                gadget.putExtra("cat_id",categori.getId());
                gadget.putExtra("partner_weplus_id",partnerId);
                startActivity(gadget);
                break;
            default:goToProductList(categori,false);break;
        }
    }

    private void goToProductList(BuyPolisModel.Categori cat, boolean filterEnabled){
        FirebaseAnalyticsHelper.logEvent(this, Constant.ANALYTICS_LIST_PRODUCT);

        Intent productIntent = new Intent(this, ProductListActivity.class);
        productIntent.putExtra("cat_id",cat.getId());
        productIntent.putExtra("filterEnabled",filterEnabled);
        productIntent.putExtra("partner_weplus_id",partnerId);
        productIntent.putExtra("nik",nik);
        startActivity(productIntent);
    }
}

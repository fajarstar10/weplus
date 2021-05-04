package id.weplus.belipolis;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.weplus.BaseActivity;
import id.weplus.R;
import id.weplus.Tagihan.BayarTagihanListrikActivity;
import id.weplus.Tagihan.DetailTagihanActivity;
import id.weplus.Tagihan.KonfirmasiTagihanListrikActivity;
import id.weplus.WelcomeActivity;
import id.weplus.belipolis.criticalIll.AsuransiCriticalActivity;
import id.weplus.belipolis.gadget.GadgetFilterFormActivity;
import id.weplus.belipolis.kesehatan.KesehatanActivity;
import id.weplus.belipolis.life.AsuransiLifeActivity;
import id.weplus.belipolis.mobil.FormDataMobilActivity;
import id.weplus.belipolis.motor.AsuransiMotorActivity;
import id.weplus.belipolis.perjalanan.AsuransiPerjalananActivity;
import id.weplus.belipolis.productlist.ProductListActivity;
import id.weplus.model.BuyPolisModel;
import id.weplus.model.Partner;
import id.weplus.model.response.BillTransactionResponse;
import id.weplus.model.response.GetPartnerDetailResponse;
import id.weplus.model.response.PartnerDetail;
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

public class PartnerDetailActivity extends BaseActivity {
    @BindView(R.id.viewback_title) TextView title;
    @BindView(R.id.viewback_description) TextView description;
    @BindView(R.id.viewback_buttonback) ImageView buttonBack;
    @BindView(R.id.chevron_about) ImageView aboutArrow;
    @BindView(R.id.loading_progress) RelativeLayout loadingProgress;
    @BindView(R.id.img_partner) ImageView partnerImage;
    @BindView(R.id.tv_partner_name) TextView partnerName;
    @BindView(R.id.tv_partner_address) TextView partnerAddress;
    @BindView(R.id.tv_partner_desc) TextView partnerDesc;
    @BindView(R.id.rv_partner_category) RecyclerView rvPartnerCategory;
    @BindView(R.id.about_wrapper) RelativeLayout aboutWrapper;
    @BindView(R.id.tv_about_partner) TextView tvPartnerAbout;
    @BindView(R.id.tv_about_label) TextView tvPartnerAboutLabel;

    private String TAG="PartnerDetailAct";
    private int partnerId=-1;
    private KategoriAdapter kategoriAdapter;
    private KategoriAdapter.OnClickedKategoriSemua listenerSemua;
    private boolean isAboutVisible=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partner_detail);
        ButterKnife.bind(this);
        partnerId = getIntent().getIntExtra("partner_id",-1);
        setupToolbar();
        fetchPartnerInformation();
        setupAboutSection();
    }

    private void setupAboutSection() {
        aboutWrapper.setOnClickListener(view -> {
            if(!isAboutVisible){
                tvPartnerAbout.setVisibility(View.VISIBLE);
                aboutArrow.setImageResource(R.drawable.ic_baseline_keyboard_arrow_up_24);
            }else{
                tvPartnerAbout.setVisibility(View.GONE);
                aboutArrow.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down_24);
            }
            isAboutVisible=!isAboutVisible;
        });
    }

    @SuppressLint("SetTextI18n")
    private void setupToolbar(){
        title.setText("Beli Polis");
        description.setText("Pilih berbagai jenis asuransi yang tersedia");
        buttonBack.setOnClickListener(view -> finish());
    }

    private void fetchPartnerInformation() {
        boolean isNetworkAvailable = Util.isNetworkAvailable(this);
        if (isNetworkAvailable && partnerId!=-1){
            loadingProgress.setVisibility(View.VISIBLE);
            String token = WeplusSharedPreference.getToken(this);
            Call<GetPartnerDetailResponse> call = NetworkManager
                    .getNetworkServiceWithHeader(this, token)
                    .getPartnerInformation(""+partnerId);
            call.enqueue(new Callback<GetPartnerDetailResponse>() {
                @Override
                public void onResponse(Call<GetPartnerDetailResponse> call, Response<GetPartnerDetailResponse> response) {
                    try {
                        loadingProgress.setVisibility(View.GONE);
                        if (response.body().getCode().equals(ErrorCode.ERROR_00)){
                           populateView(response.body().getData());

                        } else if(response.body().getCode().equals(ErrorCode.ERROR_03)){
                            Intent intent = new Intent(PartnerDetailActivity.this, WelcomeActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }else {
                            showError(PartnerDetailActivity.this,response.message());
                        }
                    }
                    catch (Exception e) {
                        loadingProgress.setVisibility(View.GONE);
                        Log.i(TAG,"asu: "+e.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<GetPartnerDetailResponse> call, Throwable t) {
                    loadingProgress.setVisibility(View.GONE);
                    showError(PartnerDetailActivity.this,"Time Out");
                    Log.i(TAG,"ON FAILURE : " + t.getMessage());
                }
            });
        } else {
            showError(PartnerDetailActivity.this,getString(R.string.network_error));
        }
    }

    private void populateView(PartnerDetail detail){
        Glide.with(this)
                .load(detail.getImage())
                .apply(new RequestOptions().circleCrop())
                .into(partnerImage);
        partnerName.setText(detail.getName());
        partnerAddress.setText(detail.getAddress());
        partnerDesc.setText(Html.fromHtml(detail.getDesc()));
        tvPartnerAboutLabel.setText("Tentang "+detail.getName());
        tvPartnerAbout.setText(detail.getAbout());
        setupPartnerCategory(detail.getCategory());
    }

    private void setupPartnerCategory(ArrayList<BuyPolisModel.Categori> category) {
        setupListClickListener();
        kategoriAdapter = new KategoriAdapter (this, category);
        rvPartnerCategory.setNestedScrollingEnabled(true);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 3);
        rvPartnerCategory.setLayoutManager(mLayoutManager);
        rvPartnerCategory.setAdapter(kategoriAdapter);
        rvPartnerCategory.addItemDecoration(new GridSpacingItemDecoration(3, Util.dpToPx(10, this), false));
        rvPartnerCategory.setItemAnimator(new DefaultItemAnimator());
        kategoriAdapter.setListenerSemua(listenerSemua);
    }
    
    private void setupListClickListener(){
        listenerSemua = (pos, tag) -> {
            int catId = kategoriAdapter.getItem(pos).getId();

            switch (catId){
                case 1:
                    Intent motor = new Intent(this.getApplicationContext(), AsuransiMotorActivity.class);
                    motor.putExtra("cat_id",kategoriAdapter.getItem(pos).getId());
                    motor.putExtra("partner_id",partnerId);
                    startActivity(motor);
                    break;
                case 2:
                    Intent kesehatan = new Intent(this.getApplicationContext(), KesehatanActivity.class);
                    kesehatan.putExtra("cat_id",kategoriAdapter.getItem(pos).getId());
                    kesehatan.putExtra("partner_id",partnerId);
                    startActivity(kesehatan);
                    break;
                case 4:
                    Intent life = new Intent(this.getApplicationContext(), AsuransiLifeActivity.class);
                    life.putExtra("cat_id",kategoriAdapter.getItem(pos).getId());
                    life.putExtra("partner_id",partnerId);
                    startActivity(life);
                    break;
                case 5:
                    Intent mobil = new Intent(this.getApplicationContext(), FormDataMobilActivity.class);
                    mobil.putExtra("cat_id",kategoriAdapter.getItem(pos).getId());
                    mobil.putExtra("partner_id",partnerId);
                    startActivity(mobil);
                    break;
                case 7:
                    Intent travel = new Intent(this.getApplicationContext(), AsuransiPerjalananActivity.class);
                    travel.putExtra("cat_id",kategoriAdapter.getItem(pos).getId());
                    travel.putExtra("partner_id",partnerId);
                    startActivity(travel);
                    break;
                case 12:
                    Intent critical = new Intent(this.getApplicationContext(), AsuransiCriticalActivity.class);
                    critical.putExtra("cat_id",kategoriAdapter.getItem(pos).getId());
                    critical.putExtra("partner_id",partnerId);
                    startActivity(critical);
                    break;
                case 15:
                    Intent gadgetIntent = new Intent(this.getApplicationContext().getApplicationContext(), GadgetFilterFormActivity.class);
                    gadgetIntent.putExtra("cat_id",kategoriAdapter.getItem(pos).getId());
                    gadgetIntent.putExtra("partner_id",partnerId);
                    startActivity(gadgetIntent);
                    break;
                default:goToProductList(kategoriAdapter.getItem(pos),true);break;
            }

        };
    }

    private void goToProductList(BuyPolisModel.Categori cat, boolean filterEnabled){
        FirebaseAnalyticsHelper.logEvent(this, Constant.ANALYTICS_LIST_PRODUCT);

        Intent productIntent = new Intent(this, ProductListActivity.class);
        productIntent.putExtra("cat_id",cat.getId());
        productIntent.putExtra("is_company_base",1);
        productIntent.putExtra("partner_id",partnerId);
        productIntent.putExtra("filterEnabled",filterEnabled);
        startActivity(productIntent);
    }
}
package id.weplus.Mitra;

import android.content.Intent;
import android.os.Bundle;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lsjwzh.widget.materialloadingprogressbar.CircleProgressBar;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.weplus.BaseActivity;
import id.weplus.R;
import id.weplus.WelcomeActivity;
import id.weplus.belipolis.perjalanan.BeliPolisPerjalananActivity;
import id.weplus.model.Partner;
import id.weplus.model.PartnerBanner;
import id.weplus.model.response.PartnerListResponse;
import id.weplus.net.ErrorCode;
import id.weplus.net.NetworkManager;
import id.weplus.net.WeplusSharedPreference;
import id.weplus.utility.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MitraActivity extends BaseActivity{
    private static String TAG = "Mitra Activity";
    private MitraBannerAdapter bannerAdapter;
    private MitraAdapter mitraAdapter;

    @BindView(R.id.viewback_title) TextView title;
    @BindView(R.id.viewback_description) TextView description;
    @BindView(R.id.rec_mitra) RecyclerView recyclerViewMitra;
    @BindView(R.id.rec_mitra_banner) RecyclerView recyclerViewMitraBanner;
    @BindView(R.id.loader_bg) RelativeLayout progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mitra);
        ButterKnife.bind(this);
        title.setText("Partner");
        description.setText(getString(R.string.partneryangbekerjasamadenganweplus));
        setupBannerAdapter();
        setupPartnerAdapter();
        getMitra();
    }


    private void setupBannerAdapter(){
        bannerAdapter = new MitraBannerAdapter(this);
        recyclerViewMitraBanner.setNestedScrollingEnabled(true);
        LinearLayoutManager mitraBannerAdapterLM = new LinearLayoutManager(getApplication().getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewMitraBanner.setLayoutManager(mitraBannerAdapterLM);
        recyclerViewMitraBanner.setAdapter(bannerAdapter);
    }

    private void setupPartnerAdapter(){
        mitraAdapter = new MitraAdapter (this);
        recyclerViewMitra.setNestedScrollingEnabled(true);
        recyclerViewMitra.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerViewMitra.setAdapter(mitraAdapter);
    }

    @OnClick(R.id.viewback_buttonback)
    public void transaktionBack(){finish();}

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void getMitra() {
        boolean isNetworkAvailable = Util.isNetworkAvailable(getApplication().getApplicationContext());
        if (isNetworkAvailable) {
            progressBar.setVisibility(View.VISIBLE);
            String token = WeplusSharedPreference.getToken(getApplication());
            Call<String> call = NetworkManager.getNetworkServiceWithHeader(getApplication(),token).getPartnerList();
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {

                    Log.i(TAG, "Mitra Response : " + response.body());

                    Gson gson = new Gson();
                    PartnerListResponse mitraList = gson.fromJson(response.body(), PartnerListResponse.class);
                    PartnerListResponse.PartnerListData partnerListData = mitraList.getData();

                    try {
                        JSONObject job = new JSONObject(response.body());
                        String code = job.getString("code");
                        String description = job.getString("message");
                        if (code.equals(ErrorCode.ERROR_00)) {
                            mitraAdapter.addItems((ArrayList<Partner>) partnerListData.partner);
                            PartnerBanner banner = new PartnerBanner();
                            banner.setImage("http://uat.weplus.id/uat_cms/advertisement/picture/original/18");
                            partnerListData.banner.add(banner);
                            bannerAdapter.addItems((ArrayList<PartnerBanner>) partnerListData.banner);
                        } else if(code.equals(ErrorCode.ERROR_03)){
                            Intent intent = new Intent(MitraActivity.this, WelcomeActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }else {
                            showError(description);
                        }


                    } catch (Exception e) {
                        Log.i(TAG, e.getMessage());
                    }finally{
                        progressBar.setVisibility(View.GONE);
                    }

                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    progressBar.setVisibility(View.GONE);
                    showError("Time Out");
                    Log.i(TAG, "On FAILUR : " + t.getMessage());

                }
            });
        } else {
            new SweetAlertDialog(getApplicationContext(), SweetAlertDialog.NORMAL_TYPE)
                    .setTitleText("")
                    .setContentText(getString(R.string.network_error))
                    .show();
        }
    }
}


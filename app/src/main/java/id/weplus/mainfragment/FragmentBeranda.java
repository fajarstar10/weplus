package id.weplus.mainfragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.weplus.MainActivity;
import id.weplus.Mitra.MitraActivity;
import id.weplus.R;
import id.weplus.ResponseBeranda;
import id.weplus.Tagihan.TagihanActivity;
import id.weplus.VerifikasiOTP;
import id.weplus.WebViewActivity;
import id.weplus.WelcomeActivity;
import id.weplus.agen.dashboard.DashboardAgentActivity;
import id.weplus.agen.RegisterAgenActivity;
import id.weplus.belipolis.BeliPolisActivity;
import id.weplus.clinic.ClinicActivity;
import id.weplus.kodeproteksi.ProtectionCodeActivity;
import id.weplus.model.LoginData;
import id.weplus.model.OTPModel;
import id.weplus.model.request.Banner;
import id.weplus.net.ErrorCode;
import id.weplus.net.Facad;
import id.weplus.net.NetworkManager;
import id.weplus.net.VolleyCallback;
import id.weplus.net.VolleyErrorCallback;
import id.weplus.net.WeplusConfig;
import id.weplus.net.WeplusSharedPreference;
import id.weplus.polissaya.PolisSayaActivity;
import id.weplus.utility.Constant;
import id.weplus.utility.FirebaseAnalyticsHelper;
import id.weplus.utility.RecyclerItemClickListener;
import id.weplus.utility.Util;
import id.weplus.voucher.VoucherActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentBeranda extends Fragment implements RecyclerItemClickListener.OnItemClickListener {
    private static String TAG = "FragmentBeranda";

    @BindView(R.id.rec_produk_favorit) RecyclerView recyclerViewProdukFavorit;
    @BindView(R.id.rec_produk_baru) RecyclerView recyclerViewProdukBaru;
    @BindView(R.id.rec_banner) RecyclerView recyclerViewBanner;
    //@BindView(R.id.slider) SliderLayout mDemoSlider;
    @BindView(R.id.img_pofil_beranda) ImageView imgProfil;
    @BindView(R.id.imgAgen) ImageView imgAgen;
    @BindView(R.id.profil_name_beranda) TextView profilName;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.view_beranda, container, false);
        ButterKnife.bind(this, view);
        return view;

    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d("fcm","Device Token : "+ FirebaseMessaging.getInstance().getToken());
        String json_response = WeplusSharedPreference.getUser(getContext());
        Gson gson = new Gson();
        LoginData loginData = gson.fromJson(json_response, LoginData.class);
        /**
         * penyebab crash kembali dikarenakan loginModel pada line 88, mencoba untuk
         * mengubah empty string menjadi object. sehinga loginModel bernilai NULL
         *
         * kemudian mencoba mendapatkan getData() dari sebuah object yang bernilai null
         * sehingga menyebabkan error
         *
         * karena saat saya cari loginData tidak digunakan pada line ataupun class lain
         * sehingga loginData pada line 99 saya comment
         */
        if(loginData!=null) {
                profilName.setText("Halo, "+loginData.getName());
        }
        getHome();
        Log.d("logindata","avatar "+loginData.getPicture());
        if (loginData != null) {
            setupImageProfile(loginData.getPicture());
        }
    }

    private void setupAgenAction(String url){
        FirebaseAnalyticsHelper.logEvent(getActivity(), Constant.ANALYTICS_CLAIM);

        imgAgen.setOnClickListener(view -> {
            Intent intent = new Intent(getActivity(),WebViewActivity.class);
            intent.putExtra("url",url);
            getActivity().startActivity(intent);
        });
    }

    private void setupImageProfile(String imagePath) {
        Glide.with(this).load(imagePath).error(R.drawable.ic_baseline_account_circle_24).into(imgProfil);
    }

    @OnClick(R.id.menu_beli_polis)
    public void beliPolis() {
        FirebaseAnalyticsHelper.logEvent(Objects.requireNonNull(getActivity()), Constant.ANALYTICS_BUY_POLIS);
        Intent beliPolis = new Intent(getActivity(), BeliPolisActivity.class);
        startActivity(beliPolis);
    }

    @OnClick(R.id.menu_polis_saya)
    public void polisSaya() {
        FirebaseAnalyticsHelper.logEvent(getActivity(), Constant.ANALYTICS_LIST_POLIS);

        Intent polisSaya = new Intent(getActivity(), PolisSayaActivity.class);
        startActivity(polisSaya);
    }

    @OnClick(R.id.menu_kode_proteksi)
    public void menuKodeProteksi() {
        FirebaseAnalyticsHelper.logEvent(getActivity(), Constant.ANALYTICS_CODE_PROTECTION);

        Intent protectionCodeIntent = new Intent(getActivity(), ProtectionCodeActivity.class);
        startActivity(protectionCodeIntent);
    }

    @OnClick(R.id.menu_klinik)
    public void klinik() {
        FirebaseAnalyticsHelper.logEvent(getActivity(), Constant.ANALYTICS_CLINIC);

        Intent intent = new Intent(getActivity(), ClinicActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.menu_tagihan)
    public void pembayaran() {
        FirebaseAnalyticsHelper.logEvent(getActivity(), Constant.ANALYTICS_BILL_PAYMENT);

        Intent pem = new Intent(getActivity(), TagihanActivity.class);
        startActivity(pem);
    }

    @OnClick(R.id.menu_voucher)
    public void voucher() {
        Intent voucherActivity = new Intent(getActivity(), VoucherActivity.class);
        startActivity(voucherActivity);
    }

    @OnClick(R.id.menu_mitra)
    public void mitra() {
        FirebaseAnalyticsHelper.logEvent(getActivity(), Constant.ANALYTICS_PARTNER);

        Intent mitraActivity = new Intent(getActivity(), MitraActivity.class);
        startActivity(mitraActivity);
    }

    private void setupBanner(ArrayList<Banner> banner){
        BannerBerandaAdapter adapter = new BannerBerandaAdapter(getActivity(),banner,this);
        recyclerViewBanner.setNestedScrollingEnabled(true);
        LinearLayoutManager produkFavoritLM = new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewBanner.setLayoutManager(produkFavoritLM);
        recyclerViewBanner.setAdapter(adapter);
    }

    private void setupFavoritBanner(ArrayList<Banner> banner){

    }

    private void getHome() {
        boolean isNetworkAvailable = Util.isNetworkAvailable(getActivity().getApplicationContext());
        if (isNetworkAvailable) {
            String token = WeplusSharedPreference.getToken(getActivity());
            Call<String> call = NetworkManager.getNetworkServiceWithHeader(getActivity(), token).getHome();
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    Log.i(TAG, "Home Response : " + response.body());

                    // todo pastikan response apinya sama dengan di postman
                    Gson gson = new Gson();
                    ResponseBeranda responseBeranda = gson.fromJson(response.body(), ResponseBeranda.class);
                    if(responseBeranda!=null){
                        ResponseBeranda.BerandaData berandaData = responseBeranda.getData();


                        try {
                            JSONObject job = new JSONObject(response.body());
                            String code = job.getString("code");
                            String description = job.getString("message");
                            if (code.equals(ErrorCode.ERROR_00)) {
                                setupBanner((ArrayList<Banner>) berandaData.banner);
                                WeplusSharedPreference.setHomeBanner(getActivity(), berandaData.getBarangFavorite());
                                WeplusSharedPreference.setHomeBanner(getActivity(), berandaData.getProdukbaru());

                                ProdukBaru produkBaru = new ProdukBaru(getActivity(), berandaData.new_product_ads);
                                ProdukFavorit produkFavorit = new ProdukFavorit(getActivity(), berandaData.product_favorite);

                                recyclerViewProdukFavorit.setNestedScrollingEnabled(true);
                                LinearLayoutManager produkFavoritLM = new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
                                recyclerViewProdukFavorit.setLayoutManager(produkFavoritLM);
                                recyclerViewProdukFavorit.setAdapter(produkFavorit);
                                recyclerViewProdukFavorit.addOnItemTouchListener(new RecyclerItemClickListener(getActivity().getApplicationContext(),
                                        recyclerViewProdukFavorit, new RecyclerItemClickListener.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(View view, int position) {
                                        switch (position) {

                                        }
                                    }

                                    @Override
                                    public void onLongItemClick(View view, int position) {
                                    }
                                }
                                ));

                                recyclerViewProdukBaru.setNestedScrollingEnabled(true);
                                LinearLayoutManager produkBaruLM = new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
                                recyclerViewProdukBaru.setLayoutManager(produkBaruLM);
                                recyclerViewProdukBaru.setAdapter(produkBaru);
                                recyclerViewProdukBaru.addOnItemTouchListener(new RecyclerItemClickListener(getActivity().getApplicationContext(), recyclerViewProdukBaru, new RecyclerItemClickListener.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(View view, int position) {
                                        switch (position) {

                                        }
                                    }

                                    @Override
                                    public void onLongItemClick(View view, int position) {

                                    }
                                }));
                                setupAgenAction(berandaData.getClaimWebview());

                            } else if(code.equals(ErrorCode.ERROR_03)){
                                new SweetAlertDialog(getActivity(), SweetAlertDialog.NORMAL_TYPE)
                                        .setTitleText(" ")
                                        .setContentText("Please login first")
                                        .setConfirmText("Ok")
                                        .setCancelClickListener(sweetAlertDialog -> {
                                            Intent intent = new Intent(getActivity(), WelcomeActivity.class);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(intent);
                                        })
                                        .show();
                            }else if (code.equals(ErrorCode.ERROR_13)) {

                                Facad.getInstance(getContext()).getRequestWithToken(WeplusConfig.DOMAIN_URL + "otp/reset/", Request.Method.POST, token, new VolleyCallback() {
                                    @Override
                                    public void onSuccessResponse(String result) {
                                        Log.i("resetotp", result);
                                        Gson gson = new Gson();
                                        OTPModel otpModel = gson.fromJson(result, OTPModel.class);
                                        if (otpModel.getCode().equals(ErrorCode.ERROR_00)) {
                                            Intent intent = new Intent(getContext(), VerifikasiOTP.class);
                                            intent.putExtra("value_otp", otpModel.getData().getOtp());
                                            startActivity(intent);
                                        }
                                    }
                                }, error -> {

                                });

                            } else {

                                new SweetAlertDialog(getContext(), SweetAlertDialog.NORMAL_TYPE)
                                        .setTitleText("")
                                        .setContentText(description)
                                        .show();
                            }

                        } catch (Exception e) {
                            Log.i(TAG, e.getMessage());
                        }
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    new SweetAlertDialog(getContext(), SweetAlertDialog.NORMAL_TYPE)
                            .setTitleText("")
                            .setContentText("Time Out")
                            .show();

                    Log.i(TAG, "On FAILUR : " + t.getMessage());

                }
            });
        } else {
            new SweetAlertDialog(getContext(), SweetAlertDialog.NORMAL_TYPE)
                    .setTitleText("")
                    .setContentText(getString(R.string.network_error))
                    .show();
        }

    }

    @Override
    public void onItemClick(View view, int position) {

    }

    @Override
    public void onLongItemClick(View view, int position) {

    }
}

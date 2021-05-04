package id.weplus;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.gson.Gson;
import com.lsjwzh.widget.materialloadingprogressbar.CircleProgressBar;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.weplus.agen.RegisterAgen;
import id.weplus.agen.RegisterAgenActivity;
import id.weplus.agen.dashboard.DashboardAgentActivity;
import id.weplus.mainfragment.FragmentBeranda;
import id.weplus.mainfragment.FragmentProfil;
import id.weplus.mainfragment.FragmentTransaksi;
import id.weplus.net.ErrorCode;
import id.weplus.net.NetworkManager;
import id.weplus.net.WeplusSharedPreference;
import id.weplus.utility.Constant;
import id.weplus.utility.FirebaseAnalyticsHelper;
import id.weplus.utility.RegistrationFormActivity;
import id.weplus.utility.Util;
import id.weplus.voucher.VoucherActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    @BindView(R.id.beranda)
    LinearLayout beranda;
    @BindView(R.id.transaksi)
    LinearLayout transaksi;
    @BindView(R.id.affiliasi)
    LinearLayout affiliasi;
    @BindView(R.id.profil)
    LinearLayout profil;
    @BindView(R.id.content_layout)
    FrameLayout contentLayout;
    public static ImageView imgBeranda;
    public static ImageView imgTransaksi;
    public static ImageView imgAffiliasi;
    public static ImageView imgProfil;
    public static ImageView imgVoucher;
    public static TextView iconberanda;
    public static TextView icontransaksi;
    public static TextView iconafliasi;
    public static TextView iconprofil;
    public CircleProgressBar loading;
    private ArrayList<ImageView> imageViewBottomMenu = new ArrayList<>();
    private FragmentManager fragmentManager;
    private int isAgent=0;
    private String agentRegisterUrl="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        loading = findViewById(R.id.loadingView);
        imgBeranda = findViewById(R.id.img_beranda);
        imgTransaksi = findViewById(R.id.img_transaksi);
        imgAffiliasi = findViewById(R.id.img_affiliasi);
        imgProfil = findViewById(R.id.img_profil);
        imgVoucher = findViewById(R.id.img_voucher);
        imageViewBottomMenu.add(imgBeranda);
        imageViewBottomMenu.add(imgTransaksi);
        imageViewBottomMenu.add(imgAffiliasi);
        imageViewBottomMenu.add(imgVoucher);
        imageViewBottomMenu.add(imgProfil);
        toggleSelectedColor(0);
//        iconberanda = findViewById(R.id.brandaicon);
//        icontransaksi = findViewById(R.id.transaksiicon);
//        iconafliasi = findViewById(R.id.afliasiicon);
//        iconprofil = findViewById(R.id.profilicon);


        fragmentManager = getSupportFragmentManager();
        FragmentBeranda brd = new FragmentBeranda();
        setContent(brd);
        getHome();
    }

    public void setContent(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();

        for (int i = 0; i < fm.getBackStackEntryCount(); i++) {
            fm.popBackStack();
        }
        transaction.replace(R.id.content_layout, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        Log.d("onbackpressed","onbackpressed called on main Activiyt");
    }

    @OnClick(R.id.beranda)
    public void actionBeranda() {
        toggleSelectedColor(0);
        FragmentBeranda fragmentBeranda = new FragmentBeranda();
        setContent(fragmentBeranda);
    }

    @OnClick(R.id.transaksi)
    public void actionTransaksi() {
        FirebaseAnalyticsHelper.logEvent(this, Constant.ANALYTICS_TRANSACTION_LIST);

        toggleSelectedColor(1);
        FragmentTransaksi fragmentTransaksi = new FragmentTransaksi();
        fragmentManager.beginTransaction().replace(R.id.content_layout, fragmentTransaksi, "back").addToBackStack("back").commit();
    }

    @OnClick(R.id.voucher)
    public void actionVoucher() {
        FirebaseAnalyticsHelper.logEvent(this, Constant.ANALYTICS_LIST_VOUCHER);
        toggleSelectedColor(3);
        Intent voucherActivity = new Intent(this, VoucherActivity.class);
        startActivity(voucherActivity);
    }

    @OnClick(R.id.affiliasi)
    public void actionAffiliasi() {
        FirebaseAnalyticsHelper.logEvent(this, Constant.ANALYTICS_AGENT);

        toggleSelectedColor(2);

        Intent intent;
        if(isAgent==0){
            intent = new Intent(this, RegisterAgenActivity.class);
            intent.putExtra("url",agentRegisterUrl);
        }else{
            intent = new Intent(this, DashboardAgentActivity.class);
        }
        startActivity(intent);
        //FragmentAffiliasi fragment = new FragmentAffiliasi();y
//        FragmentClaim fragment = new FragmentClaim();
//        fragmentManager.beginTransaction().replace(R.id.content_layout, fragment, "back").addToBackStack("back").commit();
    }

    @OnClick(R.id.profil)
    public void actionProfil() {
        FirebaseAnalyticsHelper.logEvent(this, Constant.ANALYTICS_PROFILE);

        toggleSelectedColor(4);
        FragmentProfil fragmentProfil = new FragmentProfil();
        fragmentManager.beginTransaction().replace(R.id.content_layout, fragmentProfil, "back").addToBackStack("back").commit();
    }

    private void toggleSelectedColor(int index) {
        if (index == 0) {
            imageViewBottomMenu.get(0).setImageResource(R.drawable.ic_beranda);
            for (int i = 1; i < imageViewBottomMenu.size(); i++) {
                imageViewBottomMenu.get(i).clearColorFilter();
            }
        } else {
            imageViewBottomMenu.get(0).setImageResource(R.drawable.ic_beranda_disabled);

            for (int i = 1; i < imageViewBottomMenu.size(); i++) {
                if (i == index) {
                    imageViewBottomMenu.get(i).setColorFilter(ContextCompat.getColor(this, R.color.red));
                } else {
                    imageViewBottomMenu.get(i).clearColorFilter();
                }
            }
        }

    }

    private void getHome() {
        boolean isNetworkAvailable = Util.isNetworkAvailable(this.getApplicationContext());
        if (isNetworkAvailable) {
            String token = WeplusSharedPreference.getToken(this);
            Call<String> call = NetworkManager.getNetworkServiceWithHeader(this, token).getHome();
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    Log.i(TAG, "Home Response : " + response.body());

                    // todo pastikan response apinya sama dengan di postman
                    Gson gson = new Gson();
                    ResponseBeranda responseBeranda = gson.fromJson(response.body(), ResponseBeranda.class);
                    if (responseBeranda != null) {
                        ResponseBeranda.BerandaData berandaData = responseBeranda.getData();
                        try {
                            JSONObject job = new JSONObject(response.body());
                            String code = job.getString("code");
                            String description = job.getString("message");
                            if (code.equals(ErrorCode.ERROR_00)) {
                                setupAgenAction(berandaData.getIsAgent(), berandaData.getRegisterAgentUrl());
                            } else if (code.equals(ErrorCode.ERROR_03)) {
                                new SweetAlertDialog(MainActivity.this, SweetAlertDialog.NORMAL_TYPE)
                                        .setTitleText(" ")
                                        .setContentText("Please login first")
                                        .setConfirmText("Ok")
                                        .setCancelClickListener(sweetAlertDialog -> {
                                            Intent intent = new Intent(MainActivity.this, WelcomeActivity.class);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(intent);
                                        })
                                        .show();
                            } else {
                                new SweetAlertDialog(MainActivity.this, SweetAlertDialog.NORMAL_TYPE)
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
                    new SweetAlertDialog(MainActivity.this, SweetAlertDialog.NORMAL_TYPE)
                            .setTitleText("")
                            .setContentText("Time Out")
                            .show();

                    Log.i(TAG, "On FAILUR : " + t.getMessage());

                }
            });
        } else {
            new SweetAlertDialog(this, SweetAlertDialog.NORMAL_TYPE)
                    .setTitleText("")
                    .setContentText(getString(R.string.network_error))
                    .show();
        }

    }

    private void setupAgenAction(int isAgent, String registerAgentUrl) {
       this.isAgent = isAgent;
       this.agentRegisterUrl = registerAgentUrl;
    }
}

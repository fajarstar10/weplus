package id.weplus.Tagihan;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.weplus.BaseActivity;
import id.weplus.R;
import id.weplus.WelcomeActivity;
import id.weplus.model.BillCategory;
import id.weplus.model.response.BillPaymentCategoryResponse;
import id.weplus.net.ErrorCode;
import id.weplus.net.NetworkManager;
import id.weplus.net.WeplusSharedPreference;
import id.weplus.utility.Constant;
import id.weplus.utility.FirebaseAnalyticsHelper;
import id.weplus.utility.Util;
import retrofit2.Call;
import retrofit2.Callback;
import id.weplus.Tagihan.OnCategoryClicked;
import retrofit2.Response;

public class TagihanActivity extends BaseActivity implements OnCategoryClicked{
    private static String TAG = "Tagihan Activity";
    private Activity activity;
    private TagihanCategoryAdapter adapter;
    @BindView(R.id.viewback_title) TextView title;
    @BindView(R.id.viewback_description) TextView description;
    @BindView(R.id.rec_tagihan) RecyclerView recyclerViewTagihan;
    @BindView(R.id.loader_bg) RelativeLayout progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tagihan);
        ButterKnife.bind(this);
        title.setText(getString(R.string.tagihan));
        description.setText(getString(R.string.lakukanberbagaipembayaranmelaluiweplus));
        setupAdapter();
        getTagihan();
        activity = this;
    }

    private void setupAdapter(){
        adapter = new TagihanCategoryAdapter(this,this);
        recyclerViewTagihan.setNestedScrollingEnabled(true);
        recyclerViewTagihan.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerViewTagihan.setAdapter(adapter);
    }

    @OnClick(R.id.transaksi_riwayat_pembayaran)
    public void riwayatTagihan(){
        FirebaseAnalyticsHelper.logEvent(activity, Constant.ANALYTICS_TRANSACTION_HISTORY_BILLS);

        Intent riwayatTag = new Intent(TagihanActivity.this, RiwayatPembayaranActivity.class);
        startActivity(riwayatTag);
    }

    @OnClick(R.id.viewback_buttonback)
    public void actionBackPembayaran() {
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void getTagihan() {
        boolean isNetworkAvailable = Util.isNetworkAvailable(getApplication().getApplicationContext());
        if (isNetworkAvailable) {
            progressBar.setVisibility(View.VISIBLE);
            String token = WeplusSharedPreference.getToken(getApplication());
            Call<String> call = NetworkManager.getNetworkServiceWithHeader(getApplication(),token).getBillPaymentCategory();
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    progressBar.setVisibility(View.GONE);
                    Gson gson = new Gson();
                    BillPaymentCategoryResponse cat = gson.fromJson(response.body(), BillPaymentCategoryResponse.class);
                    try {
                        if (cat.getCode().equals(ErrorCode.ERROR_00)) {
                            adapter.addItems(cat.getData().getBillCategoryArrayList());
                        } else if(cat.getCode().equals(ErrorCode.ERROR_03)){
                            Intent intent = new Intent(TagihanActivity.this, WelcomeActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }else {
                            showError(cat.getMessage());
                        }
                    } catch (Exception e) {
                        Log.i(TAG, e.getMessage());
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
            showError(getString(R.string.network_error));
        }
    }

    @Override
    public void onCategoryClicked(BillCategory bill) {
        FirebaseAnalyticsHelper.logEvent(this, Constant.ANALYTICS_CATEGORY_BILL_PAYMENT);
        switch (bill.getId()){
            case 1:
                FirebaseAnalyticsHelper.logEvent(this, Constant.ANALYTICS_CATEGORY_BILL_PAYMENT_PLN);
                Intent intentListrik = new Intent(this,TagihanListrikActivity.class);
                startActivity(intentListrik);
                break;
            case 2:
                FirebaseAnalyticsHelper.logEvent(this, Constant.ANALYTICS_CATEGORY_BILL_PAYMENT_BPJS);
                Intent intentBpjs = new Intent(this,TagihanBpjsActivity.class);
                startActivity(intentBpjs);
                break;

        }
    }
}

package id.weplus.pemegangpolis;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.weplus.BaseActivity;
import id.weplus.MainActivity;
import id.weplus.R;
import id.weplus.ResponseBeranda;
import id.weplus.WelcomeActivity;
import id.weplus.model.response.insureduser.InsuredUserData;
import id.weplus.model.response.insureduser.InsuredUserResponse;
import id.weplus.net.ErrorCode;
import id.weplus.net.NetworkManager;
import id.weplus.net.WeplusSharedPreference;
import id.weplus.utility.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataPemegangPolisActivity extends BaseActivity {
    @BindView(R.id.viewback_title_no_desc)
    TextView title;
    @BindView(R.id.datapemegangpolis_rec)
    RecyclerView recyclerView;
    @BindView(R.id.loadingWrapper)
    RelativeLayout loader;
    private DataPemegangPolisAdapter adapter;
    private String TAG = "DataPemegangPolisActivity";
    private int page = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_pemegang_polis);
        ButterKnife.bind(this);
        title.setText("Data Tertanggung");
        setupAdapterList();
        getInsuredUsers();
    }

    private void setupAdapterList() {
        recyclerView.setNestedScrollingEnabled(true);
        adapter = new DataPemegangPolisAdapter(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(adapter!=null) getInsuredUsers();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @OnClick(R.id.viewback_buttonback_no_desc)
    public void actionback() {
        finish();
    }

    @OnClick(R.id.fab_add)
    public void add() {
        Intent pemegangPolisActivity = new Intent(DataPemegangPolisActivity.this, FormPemegangPolisActivity.class);
        startActivity(pemegangPolisActivity);

    }

    private void getInsuredUsers() {
        boolean isNetworkAvailable = Util.isNetworkAvailable(this.getApplicationContext());
        if (isNetworkAvailable) {
            loader.setVisibility(View.VISIBLE);
            String token = WeplusSharedPreference.getToken(this);
            Call<InsuredUserResponse> call = NetworkManager.getNetworkServiceWithHeader(this, token).getInsuredUsers(page);
            call.enqueue(new Callback<InsuredUserResponse>() {
                @Override
                public void onResponse(Call<InsuredUserResponse> call, Response<InsuredUserResponse> response) {
                    Log.i(TAG, "Home Response : " + response.body());

                    loader.setVisibility(View.GONE);
                    InsuredUserData data = response.body().getData();
                    String code = response.body().getCode();
                    try {
                        if (code.equals(ErrorCode.ERROR_00)) {
                            adapter.setInsuredList(data.getUser_insured());
                        } else {
                            showError(DataPemegangPolisActivity.this, response.body().getMessage());
                        }


                    } catch (Exception e) {
                        Log.i(TAG, e.getMessage());
                    }

                }

                @Override
                public void onFailure(Call<InsuredUserResponse> call, Throwable t) {

                    loader.setVisibility(View.GONE);
                    showError(DataPemegangPolisActivity.this, "Time Out");

                    Log.i(TAG, "On FAILUR : " + t.getMessage());

                }
            });
        } else {

            loader.setVisibility(View.GONE);
            showError(this, getString(R.string.network_error));
        }
    }
}

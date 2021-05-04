package id.weplus.mainfragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.DefaultItemAnimator;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.agrawalsuneet.dotsloader.loaders.AllianceLoader;
import com.google.gson.Gson;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.weplus.BaseActivity;
import id.weplus.LoginActivity;
import id.weplus.R;
import id.weplus.WelcomeActivity;
import id.weplus.belipolis.BeliPolisActivity;
import id.weplus.helper.EndlessOnScrollListener;
import id.weplus.model.Transaction;
import id.weplus.model.response.TransactionListResponse;
import id.weplus.net.ErrorCode;
import id.weplus.net.NetworkManager;
import id.weplus.net.WeplusSharedPreference;
import id.weplus.transaksi.TransaksiSayaDetailActivity;
import id.weplus.utility.Constant;
import id.weplus.utility.FirebaseAnalyticsHelper;
import id.weplus.utility.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RiwayatTransaksiActivity extends BaseActivity implements TransaksiSayaAdapter.TransaksiOnItemClicked{
    private static String TAG = RiwayatTransaksiActivity.class.getSimpleName();
    @BindView(R.id.viewback_title) TextView title;
    @BindView(R.id.viewback_description) TextView description;
    @BindView(R.id.polissemua_rec) RecyclerView recyclerViewTransaksi;
    @BindView(R.id.loader) AllianceLoader loader;
    @BindView(R.id.emptyLayout) RelativeLayout emptyLayout;
    @BindView(R.id.buyNow) TextView buyNow;


    private TransaksiSayaAdapter adapter;
    private String[] status = {"Berhasil", "Gagal"};
    private int history=1;
    private int page=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_riwayat_pembelian);
        ButterKnife.bind(this);
        title.setText(getResources().getString(R.string.riwayattransaksi));
        description.setText(getResources().getString(R.string.melihatdaftartransaksiygdilakukan));
        fetchTransactionList();
        setupAdapter();
        buyNow.setOnClickListener(view -> startActivity(new Intent(this, BeliPolisActivity.class)));
    }

    private void setupAdapter(){
        adapter = new TransaksiSayaAdapter(this,this);
        LinearLayoutManager transaksiLayoutmanager = new LinearLayoutManager(this.getApplicationContext(),LinearLayoutManager.VERTICAL,false);
        recyclerViewTransaksi.setLayoutManager(transaksiLayoutmanager);
        recyclerViewTransaksi.setAdapter(adapter);
        recyclerViewTransaksi.setItemAnimator(new DefaultItemAnimator());
        recyclerViewTransaksi.addOnScrollListener(scrollData());
    }


    private void fetchTransactionList(){
        boolean isNetworkAvailable = Util.isNetworkAvailable(this);
        if (isNetworkAvailable){
            String token = WeplusSharedPreference.getToken(this);
            //Log.d("partner","request : "+catId+" - "+partnerId);
            Log.d("token","request : "+token);
            Call<String> call = NetworkManager
                    .getNetworkServiceWithHeader(this, token)
                    .getTransactionList(history,page);
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    loader.setVisibility(View.GONE);
                    Gson gson = new Gson();
                    TransactionListResponse resp = gson.fromJson(response.body(), TransactionListResponse.class);
                    try {
                        if (resp.getCode().equals(ErrorCode.ERROR_00)){
                            if(resp.getData().transaction.size()>0) {
                                emptyLayout.setVisibility(View.GONE);
                                recyclerViewTransaksi.setVisibility(View.VISIBLE);
                                adapter.addItems((ArrayList<Transaction>) resp.getData().transaction);
                            } else{
                                if(adapter.getItemCount()==0) {
                                    emptyLayout.setVisibility(View.VISIBLE);
                                    recyclerViewTransaksi.setVisibility(View.GONE);
                                }else{

                                    emptyLayout.setVisibility(View.GONE);
                                }
                            }
                        } else if(resp.getCode().equals(ErrorCode.ERROR_03)){
                            Intent intent = new Intent(RiwayatTransaksiActivity.this, WelcomeActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        } else {
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
                    loader.setVisibility(View.GONE);
                    try {
                        new SweetAlertDialog(getApplicationContext(), SweetAlertDialog.NORMAL_TYPE)
                                .setTitleText("")
                                .setContentText("Time Out")
                                .show();
                    }catch (Exception e){
                        e.getMessage();
                    }
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

    @OnClick(R.id.viewback_buttonback)
    public void actionBackRiwayatTransaksi(){
        finish();
    }

    @Override
    public void onItem(Transaction transaction) {
        FirebaseAnalyticsHelper.logEvent(this, Constant.ANALYTICS_DETAIL_HISTORY_TRANSACTION);

        Intent intent = new Intent(this, TransaksiSayaDetailActivity.class);
        intent.putExtra("history",true);
        intent.putExtra("transaction",transaction);
        startActivity(intent);
    }

    private EndlessOnScrollListener scrollData() {
        return new EndlessOnScrollListener() {
            @Override
            public void onLoadMore() {
                page++;
                fetchTransactionList();
            }
        };
    }
}

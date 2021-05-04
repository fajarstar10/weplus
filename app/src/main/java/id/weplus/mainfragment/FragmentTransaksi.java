package id.weplus.mainfragment;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
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
import id.weplus.polissaya.FragmentPolisAktif;
import id.weplus.polissaya.FragmentPolisKadaluarsa;
import id.weplus.polissaya.FragmentPolisSemua;
import id.weplus.transaksi.TransaksiSayaDetailActivity;
import id.weplus.utility.Constant;
import id.weplus.utility.FirebaseAnalyticsHelper;
import id.weplus.utility.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentTransaksi extends Fragment implements TransaksiSayaAdapter.TransaksiOnItemClicked {
    private static String TAG = "FragmentTransaksi";
    @BindView(R.id.transaksi_recycleview) RecyclerView recyclerViewTransaksi;
    @BindView(R.id.viewback_title) TextView title;
    @BindView(R.id.viewback_description) TextView description;
    @BindView(R.id.emptyLayout) RelativeLayout emptyLayout;
    @BindView(R.id.buyNow) TextView buyNow;

    private FragmentPolisAktif fragmentPolisAktif;
    private FragmentPolisKadaluarsa fragmentPolisKadaluarsa;
    private FragmentPolisSemua fragmentSemua;
    private TransaksiSayaAdapter adapter;
    int page=1;
    int history=0;

    private String[] status = {"Menunggu Pembayaran", "Proses Refund"};

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getTransactionList(1,"");
    }

    @Override
    public void onViewCreated( View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        title.setText(getString(R.string.transaksisaya));
        description.setText(getString(R.string.melihatdaftartransaksiygdilakukan));
        setupAdapter();
        fetchTransactionList();
        buyNow.setOnClickListener(view1 -> {
            Intent beliPolis = new Intent(getActivity(), BeliPolisActivity.class);
            startActivity(beliPolis);
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.view_transaksi, container, false);
        ButterKnife.bind(this,view);
        return view;
    }

    private void setupAdapter(){
        adapter = new TransaksiSayaAdapter(getActivity(),this);
        LinearLayoutManager transaksiLayoutmanager = new LinearLayoutManager(getActivity().getApplicationContext(),LinearLayoutManager.VERTICAL,false);
        recyclerViewTransaksi.setLayoutManager(transaksiLayoutmanager);
        recyclerViewTransaksi.setAdapter(adapter);
        recyclerViewTransaksi.setItemAnimator(new DefaultItemAnimator());
        recyclerViewTransaksi.addOnScrollListener(scrollData());
    }

    private void fetchTransactionList(){
        boolean isNetworkAvailable = Util.isNetworkAvailable(getActivity());
        if (isNetworkAvailable){
            String token = WeplusSharedPreference.getToken(getActivity());
            //Log.d("partner","request : "+catId+" - "+partnerId);
            Log.d("token","request : "+token);
            Call<String> call = NetworkManager
                    .getNetworkServiceWithHeader(getActivity(), token)
                    .getTransactionList(history,page);
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    Gson gson = new Gson();
                    TransactionListResponse resp = gson.fromJson(response.body(), TransactionListResponse.class);
                    try {
                        if (resp.getCode().equals(ErrorCode.ERROR_00)){
                            ArrayList<Transaction> transactions = (ArrayList<Transaction>) resp.getData().transaction;
                            if(transactions!=null && transactions.size()>0) {
                                emptyLayout.setVisibility(View.GONE);
                                adapter.addItems(transactions);
                            }

                        }else if(resp.getCode().equals(ErrorCode.ERROR_03)){
                            Intent intent = new Intent(getActivity(), WelcomeActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }  else {
                            new SweetAlertDialog(getContext(), SweetAlertDialog.NORMAL_TYPE)
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
                    try {
                        new SweetAlertDialog(getContext(), SweetAlertDialog.NORMAL_TYPE)
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
            new SweetAlertDialog(getActivity(), SweetAlertDialog.NORMAL_TYPE)
                    .setTitleText(" ")
                    .setContentText(getString(R.string.network_error))
                    .show();
        }
    }

    @OnClick(R.id.transaksi_riwayat_pembelian)
    public void riwayatPembelian(){
        FirebaseAnalyticsHelper.logEvent(getActivity(), Constant.ANALYTICS_HISTORY_TRANSACTION);

        Intent intent = new Intent(getActivity().getApplicationContext(), RiwayatTransaksiActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.viewback_buttonback)
    public void backBtnFragmentTransaksi(){
        getFragmentManager().popBackStack();
    }

    @Override
    public void onItem(Transaction transaction) {
        FirebaseAnalyticsHelper.logEvent(getActivity(), Constant.ANALYTICS_DETAIL_TRANSACTION);

        Intent intent = new Intent(getContext(), TransaksiSayaDetailActivity.class);
        intent.putExtra("history",false);
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

    class ViewPagerAdapter extends FragmentPagerAdapter {
        //private List<Fragment> mFragmentList = new ArrayList<>();
        //private List<String> mFragmentTitleList = new ArrayList<>();
        FragmentManager manager;
        FragmentTransaction transaction;

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
            this.manager = manager;
        }

        public void clear(ViewGroup container) {
            transaction = manager.beginTransaction();

            for(int i = 0; i < getCount(); i++){

                final long itemId = getItemId(i);

                // Do we already have this fragment?
                String name = "android:switcher:" + container.getId() + ":" + itemId;
                Fragment fragment = manager.findFragmentByTag(name);

                if(fragment != null){
                    transaction.detach(fragment);
                }
            }
            transaction.commitAllowingStateLoss();
            transaction = null;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
//                case 0:{
//                }
                case 1:{
                    fragmentSemua = new FragmentPolisSemua();
                    return fragmentSemua;
//                    fragmentPolisKadaluarsa = new FragmentPolisKadaluarsa();
//                    return fragmentPolisKadaluarsa;
                }
                default: {
//                    fragmentSemua = new FragmentPolisSemua();
//                    return fragmentSemua;
                    fragmentPolisAktif = new FragmentPolisAktif();
                    return fragmentPolisAktif;
                }
            }
        }

        @Override
        public int getCount() {
            return 2;
            //return mFragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
//                case 0:{
//                    return getString(R.string.semua);
//                }
                case 0: {
                    return getString(R.string.aktif);
                }
                case 1: {
                    return getString(R.string.history);
                }
                default:
                    return getString(R.string.aktif);
            }
        }
    }
}



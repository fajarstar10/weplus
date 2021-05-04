package id.weplus.Tagihan;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import id.weplus.Tagihan.BillTransactionAdapter;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.lsjwzh.widget.materialloadingprogressbar.CircleProgressBar;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.weplus.R;
import id.weplus.WelcomeActivity;
import id.weplus.helper.EndlessOnScrollListener;
import id.weplus.model.BillTransaction;
import id.weplus.model.request.BillInquiryRequest;
import id.weplus.model.response.BillInquiryResponse;
import id.weplus.model.response.GetBillTransactionResponse;
import id.weplus.net.ErrorCode;
import id.weplus.net.NetworkManager;
import id.weplus.net.WeplusSharedPreference;
import id.weplus.utility.Constant;
import id.weplus.utility.FirebaseAnalyticsHelper;
import id.weplus.utility.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentTransaksi extends Fragment implements OnHistoryClick {
    @BindView(R.id.recycleviewtransaksi)
    RecyclerView recyclerView;
    @BindView(R.id.progressBar)
    CircleProgressBar progressBar;
    @BindView(R.id.emptyLayout)
    RelativeLayout emptyLayout;


    private BillInquiryRequest billInquiryRequest;
    private int page = 1;
    private int categoryId = 1;
    private String TAG = "FragmentTransaksi";
    private BillTransactionAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_transaksi, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseAnalyticsHelper.logEvent(getActivity(), Constant.ANALYTICS_TRANSACTION_BILLS);

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onViewCreated(@NotNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupAdapter();
        fetchTransactionList();
    }

    private void setupAdapter() {
        adapter = new BillTransactionAdapter(getActivity(), this);
        recyclerView.setNestedScrollingEnabled(true);
        LinearLayoutManager lm = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(lm);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addOnScrollListener(scrollData());
        recyclerView.setAdapter(adapter);
    }

    private void fetchTransactionList() {
        boolean isNetworkAvailable = Util.isNetworkAvailable(Objects.requireNonNull(getActivity()).getApplicationContext());
        if (isNetworkAvailable) {
            progressBar.setVisibility(View.VISIBLE);
            String token = WeplusSharedPreference.getToken(getActivity());
            Call<String> call = NetworkManager.getNetworkServiceWithHeader(getActivity(), token).getBillPaymentTransaction(0, page);
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    progressBar.setVisibility(View.INVISIBLE);
                    Gson gson = new Gson();
                    GetBillTransactionResponse cat = gson.fromJson(response.body(), GetBillTransactionResponse.class);
                    try {
                        if (cat.getCode().equals(ErrorCode.ERROR_00)) {
                            if (adapter.getItemCount() == 0 && cat.getData().getBillTransactionArrayList().size() == 0) {
                                emptyLayout.setVisibility(View.VISIBLE);
                            } else {
                                emptyLayout.setVisibility(View.GONE);
                            }
                            adapter.addItems(cat.getData().getBillTransactionArrayList());
                        } else if(cat.getCode().equals(ErrorCode.ERROR_03)){
                            Intent intent = new Intent(getActivity(), WelcomeActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }else {
                            new SweetAlertDialog(getContext(), SweetAlertDialog.NORMAL_TYPE)
                                    .setTitleText("")
                                    .setContentText(cat.getMessage())
                                    .show();
                        }
                    } catch (Exception e) {
                        Log.i(TAG, e.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    progressBar.setVisibility(View.INVISIBLE);
                    new SweetAlertDialog(getContext(), SweetAlertDialog.NORMAL_TYPE)
                            .setTitleText("")
                            .setContentText("Time out")
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

    private EndlessOnScrollListener scrollData() {
        return new EndlessOnScrollListener() {
            @Override
            public void onLoadMore() {
                page++;
                fetchTransactionList();
            }
        };
    }

    @Override
    public void setOnClickListener(BillTransaction transaction) {
        FirebaseAnalyticsHelper.logEvent(getActivity(), Constant.ANALYTICS_DETAIL_TRANSACTION_BILLS);

        Intent intent = new Intent(getActivity(),DetailTagihanActivity.class);
        intent.putExtra("transaction",transaction);
        getActivity().startActivity(intent);
    }

    private void sendBillInquiry() {
        boolean isNetworkAvailable = Util.isNetworkAvailable(getActivity());
        if (isNetworkAvailable) {
            String token = WeplusSharedPreference.getToken(getActivity());
            Call<String> call = NetworkManager
                    .getNetworkServiceWithHeader(getActivity(), token)
                    .sendBillInquiry(billInquiryRequest);
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    Gson gson = new Gson();
                    BillInquiryResponse resp = gson.fromJson(response.body(), BillInquiryResponse.class);

                    try {
                        if (resp.getCode().equals(ErrorCode.ERROR_00)) {
                            Intent intent = new Intent(getActivity(), BayarTagihanListrikActivity.class);
                            intent.putExtra("bill_category", categoryId);
                            intent.putExtra(
                                    "bill_inquiry",
                                    resp.getData()
                            );
                            startActivity(intent);
                        } else {
                            new SweetAlertDialog(getActivity(), SweetAlertDialog.NORMAL_TYPE)
                                    .setTitleText("")
                                    .setContentText(resp.getMessage())
                                    .show();
                        }
                    } catch (Exception e) {
                        Log.i(TAG, "asu: " + e.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    new SweetAlertDialog(getActivity(), SweetAlertDialog.NORMAL_TYPE)
                            .setTitleText("")
                            .setContentText("Time Out")
                            .show();

                    Log.i(TAG, "ON FAILURE : " + t.getMessage());
                }
            });
        } else {

            new SweetAlertDialog(getActivity(), SweetAlertDialog.NORMAL_TYPE)
                    .setTitleText(" ")
                    .setContentText(getString(R.string.network_error))
                    .show();
        }
    }

}

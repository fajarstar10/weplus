package id.weplus.voucher;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.lsjwzh.widget.materialloadingprogressbar.CircleProgressBar;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.weplus.R;
import id.weplus.helper.EndlessOnScrollListener;
import id.weplus.model.Voucher;
import id.weplus.model.VoucherListModel;
import id.weplus.net.ErrorCode;
import id.weplus.net.NetworkManager;
import id.weplus.net.WeplusSharedPreference;
import id.weplus.utility.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentVoucherDiscout extends Fragment {
    private static String TAG = "FragmentVoucherDiskon";
    private int page = 1;
    private VoucherDiscountAdapter adapter;
    @BindView(R.id.recycleview)
    RecyclerView voucherRecyclerView;
    @BindView(R.id.progressBar)
    CircleProgressBar progressBar;
    @BindView(R.id.emptyLayout)
    RelativeLayout emptyLayout;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.view_kontak_klaim, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupAdapter();
        progressBar.setShowArrow(true);
        getVoucherDiskon();
    }

    private void setupAdapter() {
        adapter = new VoucherDiscountAdapter(getActivity());
        voucherRecyclerView.setNestedScrollingEnabled(true);
        LinearLayoutManager produkBaruLM = new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        voucherRecyclerView.setLayoutManager(produkBaruLM);
        voucherRecyclerView.setAdapter(adapter);
        voucherRecyclerView.setItemAnimator(new DefaultItemAnimator());
        voucherRecyclerView.addOnScrollListener(scrollData());
    }

    private void getVoucherDiskon() {
        boolean isNetworkAvailable = Util.isNetworkAvailable(getActivity().getApplicationContext());
        if (isNetworkAvailable) {
            progressBar.setVisibility(View.VISIBLE);
            String token = WeplusSharedPreference.getToken(getActivity());
            Call<String> call = NetworkManager.getNetworkServiceWithHeader(getActivity(), token).getVoucherList(page);
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    progressBar.setVisibility(View.INVISIBLE);
                    Gson gson = new Gson();
                    VoucherListModel voucherListModel = gson.fromJson(response.body(), VoucherListModel.class);
                    try {
                        JSONObject job = new JSONObject(response.body());
                        String code = job.getString("code");
                        String description = job.getString("message");
                        if (code.equals(ErrorCode.ERROR_00)) {
                            List<Voucher> voucherListData = voucherListModel.getData();
                            if (voucherListData.size() > 0)
                                adapter.addItems(voucherListData);
                            else
                                emptyLayout.setVisibility(View.VISIBLE);
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

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    progressBar.setVisibility(View.INVISIBLE);
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

    private EndlessOnScrollListener scrollData() {
        return new EndlessOnScrollListener() {
            @Override
            public void onLoadMore() {
                page++;
                getVoucherDiskon();
            }
        };
    }


}


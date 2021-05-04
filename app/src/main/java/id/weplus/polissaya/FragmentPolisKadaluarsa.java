package id.weplus.polissaya;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.appcompat.widget.AppCompatButton;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.agrawalsuneet.dotsloader.loaders.AllianceLoader;
import com.google.gson.Gson;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.weplus.R;
import id.weplus.Tagihan.BayarTagihanListrikActivity;
import id.weplus.WelcomeActivity;
import id.weplus.belipolis.BeliPolisActivity;
import id.weplus.detailpolis.DetailPolisMobilActivity;
import id.weplus.model.Insured;
import id.weplus.model.InsuredListModel;
import id.weplus.net.ErrorCode;
import id.weplus.net.NetworkManager;
import id.weplus.net.WeplusSharedPreference;
import id.weplus.utility.Constant;
import id.weplus.utility.FirebaseAnalyticsHelper;
import id.weplus.utility.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentPolisKadaluarsa extends Fragment {
    private static String TAG = "FragmentPolisKadaluarsa";
    @BindView(R.id.polissemua_rec) RecyclerView recSemua;
    @BindView(R.id.btn_beli_skrg) AppCompatButton btnBeliPolisSkrg;
    @BindView(R.id.layout_no_polis) LinearLayout emptyLayout;
    @BindView(R.id.loader) AllianceLoader loader;
    private List<Insured> insuredList = new ArrayList<>();
    private PolisSayaAdapter adapter;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recSemua.setNestedScrollingEnabled(true);
        getPolisAktif();
        btnBeliPolisSkrg.setOnClickListener(view1 -> startActivity(new Intent(getActivity(), BeliPolisActivity.class)));

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.view_polis_semua, container,false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void getPolisAktif(){
        if(getActivity()!=null){
            loader.setVisibility(View.VISIBLE);
            boolean isNetworkAvailable = Util.isNetworkAvailable(getActivity());
            if (isNetworkAvailable){
                String token = WeplusSharedPreference.getToken(getActivity());
                Call<String> call = NetworkManager.getNetworkServiceWithHeader(getActivity(), token).getInsuredList(1);
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        Gson gson = new Gson();
                        InsuredListModel insuredListModel = gson.fromJson(response.body(), InsuredListModel.class);
                        try{
                            loader.setVisibility(View.GONE);
                            JSONObject job = new JSONObject(response.body());
                            Log.i(TAG, job.toString());
                            String code = job.getString("code");
                            String description = job.getString("message");
                            if (code.equals(ErrorCode.ERROR_00)) {
                                insuredList = insuredListModel.getData();
                                if(insuredList.size()>0) {
                                    emptyLayout.setVisibility(View.GONE);
                                    adapter = new PolisSayaAdapter(getActivity(), insuredList);
                                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                                    recSemua.setLayoutManager(linearLayoutManager);
                                    PolisSayaAdapter.PolisSayaOnItemClicked listener = new PolisSayaAdapter.PolisSayaOnItemClicked() {
                                        @Override
                                        public void onItem(int pos, String tag) {
                                            Intent intent = new Intent(getActivity().getApplicationContext(), DetailPolisMobilActivity.class);
                                            intent.putExtra("tipe", "Aktif");
                                            startActivity(intent);
                                        }
                                    };
                                    adapter.setListenerPolisSaya(listener);
                                    recSemua.setAdapter(adapter);
                                }else{
                                    emptyLayout.setVisibility(View.VISIBLE);
                                }
//                            adapter.addItems(insuredList);
                            } else if(code.equals(ErrorCode.ERROR_03)){
                                Intent intent = new Intent(getActivity(), WelcomeActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }else {
                                loader.setVisibility(View.GONE);
                                new SweetAlertDialog(getContext(), SweetAlertDialog.NORMAL_TYPE)
                                        .setTitleText("")
                                        .setContentText(description)
                                        .show();
                            }
                        }catch (Exception e){
                            loader.setVisibility(View.GONE);
                            Log.i(TAG, e.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        loader.setVisibility(View.GONE);
                    }
                });
            }else {
                loader.setVisibility(View.GONE);
                new SweetAlertDialog(getActivity(), SweetAlertDialog.NORMAL_TYPE)
                        .setTitleText(" ")
                        .setContentText(getString(R.string.network_error))
                        .show();
            }
        }

    }

}

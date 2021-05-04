package id.weplus.belipolis;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.weplus.R;
//import id.weplus.belipolis.motor.PolisMotorActivity;
import id.weplus.Tagihan.BayarTagihanListrikActivity;
import id.weplus.WelcomeActivity;
import id.weplus.belipolis.productlist.ProductListActivity;
import id.weplus.model.BuyPolisModel;
import id.weplus.net.ErrorCode;
import id.weplus.net.NetworkManager;
import id.weplus.net.WeplusSharedPreference;
import id.weplus.utility.Constant;
import id.weplus.utility.FirebaseAnalyticsHelper;
import id.weplus.utility.GridSpacingItemDecoration;
import id.weplus.utility.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentkategoriPerusahaan extends Fragment {
    private static String TAG = "FragmentKategoriPerusahaan";
    private KategoriPerusahaanAdapter kategoriperusahaanAdapter;
    private KategoriPerusahaanAdapter.OnClickedKategoriSemua listenerSemua;

    @BindView(R.id.kategoribelipolis_recycleview) RecyclerView categoryRecycleview;
//    @BindView(R.id.semuakategori_recycleview) RecyclerView categoryRecycleviewSemua;

//    private int[] imgIcon = {
//            R.drawable.ic_polis_axa,R.drawable.ic_adira,R.drawable.ic_autocilin,R.drawable.ic_axa,
//            R.drawable.ic_zurich,R.drawable.ic_simas,R.drawable.ic_malacca,R.drawable.ic_semua_white,};
//    private String[] labelIcon = {
//            "ACA", "Adira", "Autocilin", "AXA", "Zurich", "Sinarmas", "Mallaca Trust", "Semua"};
//
//    private int[] imgIconSemua = {
//            R.drawable.ic_polis_axa,R.drawable.ic_adira,R.drawable.ic_autocilin,R.drawable.ic_axa,
//            R.drawable.ic_zurich,R.drawable.ic_simas,R.drawable.ic_malacca,R.drawable.ic_bess,
//            R.drawable.ic_fpg,R.drawable.ic_jagadiri, R.drawable.ic_mag, R.drawable.ic_mega, R.drawable.ic_reliance};
//    private String[] labelIconSemua = {
//            "ACA", "Adira", "Autocilin", "AXA", "Zurich", "Sinarmas", "Mallaca Trust", "Bess Central", "FPG", "Jaga Diri","MAG", "Mega", "Reliance"};
//    private KategoriAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.view_kategori_beli_polis, container, false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onViewCreated(View view,Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getBuyPolis();
        listenerSemua = (pos, tag) -> goToProductList(kategoriperusahaanAdapter.getItem(pos).getId());
    }

    private void goToProductList(int partnerId){
        FirebaseAnalyticsHelper.logEvent(getActivity(), Constant.ANALYTICS_DETAIL_COMPANY_BUY_POLIS);

        Log.d("partner","partnerId : "+partnerId);
        Intent productIntent = new Intent(getActivity(), PartnerDetailActivity.class);
        productIntent.putExtra("partner_id",partnerId);
        productIntent.putExtra("filterEnabled",false);
        startActivity(productIntent);
    }

    private void getBuyPolis(){
        boolean isNetworkAvailable = Util.isNetworkAvailable(getActivity().getApplication());
        if (isNetworkAvailable){
            String token = WeplusSharedPreference.getToken(getActivity());
            Call<String> call = NetworkManager.getNetworkServiceWithHeader(getActivity(), token).getBuyPolis();
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    Log.i(TAG,"Beli Polis : " + response.body());
                    Gson gson = new Gson();
                    BuyPolisModel buyPolisModel = gson.fromJson(response.body(), BuyPolisModel.class);
                    BuyPolisModel.BuyPolisData buypolisData = buyPolisModel.getData();

                    try {
                        JSONObject belipolis = new JSONObject(response.body());
                        String code = belipolis.getString("code");
                        String description = belipolis.getString("message");
                        if (code.equals(ErrorCode.ERROR_00)){
                            WeplusSharedPreference.setBuyPolis(getActivity(),buypolisData.getPerusahaan());

                            kategoriperusahaanAdapter = new KategoriPerusahaanAdapter (getActivity(),buypolisData.partner);
                            kategoriperusahaanAdapter.setListenerSemua(listenerSemua);
                            categoryRecycleview.setNestedScrollingEnabled(true);
                            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 3);
                            categoryRecycleview.setLayoutManager(mLayoutManager);
                            categoryRecycleview.setAdapter(kategoriperusahaanAdapter);
                            categoryRecycleview.addItemDecoration(new GridSpacingItemDecoration(3, Util.dpToPx(10, getActivity()), false));
                            categoryRecycleview.setItemAnimator(new DefaultItemAnimator());

                        } else if(code.equals(ErrorCode.ERROR_03)){
                            new SweetAlertDialog(getContext(), SweetAlertDialog.NORMAL_TYPE)
                                    .setTitleText("")
                                    .setContentText("Please login first")
                                    .setConfirmText("Ok")
                                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                                            Intent intent = new Intent(getActivity(), WelcomeActivity.class);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(intent);
                                        }
                                    })
                                    .show();
                        }else {
                            new SweetAlertDialog(getContext(), SweetAlertDialog.NORMAL_TYPE)
                                    .setTitleText("")
                                    .setContentText(description)
                                    .show();
                        }
                    }
                    catch (Exception e) {
                        Log.i(TAG, e.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    new SweetAlertDialog(getContext(), SweetAlertDialog.NORMAL_TYPE)
                            .setTitleText("")
                            .setContentText("Time Out")
                            .show();

                    Log.i(TAG,"ON FAILURE : " + t.getMessage());
                }
            });
        } else {
            new SweetAlertDialog(getContext(), SweetAlertDialog.NORMAL_TYPE)
                    .setTitleText(" ")
                    .setContentText(getString(R.string.network_error))
                    .show();
        }
    }
}



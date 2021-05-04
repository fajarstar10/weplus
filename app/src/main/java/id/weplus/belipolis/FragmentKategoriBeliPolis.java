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

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.weplus.LoginActivity;
import id.weplus.R;
import id.weplus.WelcomeActivity;
import id.weplus.belipolis.criticalIll.AsuransiCriticalActivity;
import id.weplus.belipolis.gadget.GadgetFilterFormActivity;
import id.weplus.belipolis.kesehatan.KesehatanActivity;
import id.weplus.belipolis.life.AsuransiLifeActivity;
import id.weplus.belipolis.mobil.FormDataMobilActivity;
import id.weplus.belipolis.motor.AsuransiMotorActivity;
import id.weplus.belipolis.perjalanan.AsuransiPerjalananActivity;
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

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK;


public class FragmentKategoriBeliPolis extends Fragment {
    private static String TAG = "FragmentKategoriBeliPolis";
    private KategoriAdapter kategoriAdapter;
    private KategoriAdapter.OnClickedKategoriSemua listenerSemua;

    @BindView(R.id.kategoribelipolis_recycleview) RecyclerView categoryRecycleview;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.view_kategori_beli_polis, container, false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onViewCreated(@NotNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listenerSemua = (pos, tag) -> {
            FirebaseAnalyticsHelper.logEvent(getActivity(), Constant.ANALYTICS_DETAIL_CATEGORY_BUY_POLIS);
            int catId=kategoriAdapter.getItem(pos).getId();
            switch (catId){
                case 1:
                    Intent motor = new Intent(getActivity().getApplicationContext(), AsuransiMotorActivity.class);
                    motor.putExtra("cat_id",kategoriAdapter.getItem(pos).getId());
                    startActivity(motor);
                    break;
                case 5:
                    Intent mobil = new Intent(getActivity().getApplicationContext(), FormDataMobilActivity.class);
                    mobil.putExtra("cat_id",kategoriAdapter.getItem(pos).getId());
                    startActivity(mobil);
                    break;
                case 7:
                    Intent travel = new Intent(getActivity().getApplicationContext(), AsuransiPerjalananActivity.class);
                    travel.putExtra("cat_id",kategoriAdapter.getItem(pos).getId());
                    startActivity(travel);
                    break;
                case 2:
                    Intent kesehatan = new Intent(getActivity().getApplicationContext(), KesehatanActivity.class);
                    kesehatan.putExtra("cat_id",kategoriAdapter.getItem(pos).getId());
                    startActivity(kesehatan);
                    break;
                case 4:
                    Intent life = new Intent(getActivity().getApplicationContext(), AsuransiLifeActivity.class);
                    life.putExtra("cat_id",kategoriAdapter.getItem(pos).getId());
                    startActivity(life);
                    break;
                case 12:
                    Intent critical = new Intent(getActivity().getApplicationContext(), AsuransiCriticalActivity.class);
                    critical.putExtra("cat_id",kategoriAdapter.getItem(pos).getId());
                    startActivity(critical);
                    break;
                case 15:
                    Intent gadgetIntent = new Intent(getActivity().getApplicationContext(), GadgetFilterFormActivity.class);
                    gadgetIntent.putExtra("cat_id",kategoriAdapter.getItem(pos).getId());
                    startActivity(gadgetIntent);
                    break;
                default:goToProductList(kategoriAdapter.getItem(pos),true);break;
            }

        };
        getBuyPolis();

    }

    private void goToProductList(BuyPolisModel.Categori cat, boolean filterEnabled){
        FirebaseAnalyticsHelper.logEvent(getActivity(), Constant.ANALYTICS_LIST_PRODUCT);

        Intent productIntent = new Intent(getActivity(), ProductListActivity.class);
        productIntent.putExtra("cat_id",cat.getId());
        productIntent.putExtra("filterEnabled",filterEnabled);
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
                            WeplusSharedPreference.setBuyPolis(getActivity(),buypolisData.getPolis());
                            Log.d(TAG,"category : "+buypolisData.category.toString());
                            kategoriAdapter = new KategoriAdapter (getActivity(), buypolisData.category);

                            categoryRecycleview.setNestedScrollingEnabled(true);
                            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 3);
                            categoryRecycleview.setLayoutManager(mLayoutManager);
                            categoryRecycleview.setAdapter(kategoriAdapter);
                            categoryRecycleview.addItemDecoration(new GridSpacingItemDecoration(3, Util.dpToPx(10, getActivity()), false));
                            categoryRecycleview.setItemAnimator(new DefaultItemAnimator());
                            kategoriAdapter.setListenerSemua(listenerSemua);

                        } else if(code.equals(ErrorCode.ERROR_03)){
                            Intent intent = new Intent(getActivity(), WelcomeActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        } else {
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

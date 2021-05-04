package id.weplus.belipolis;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.appcompat.widget.AppCompatButton;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.weplus.BaseActivity;
import id.weplus.R;
import id.weplus.utility.Constant;
import id.weplus.utility.FirebaseAnalyticsHelper;

/**
 * menampilkan menu fitur beli polis by kategori dan perusahaan, serta daftar produk terbaru
 * kategori polis ada di fragmentkategoribelipolis
 * perusahaan ada di ...
 */

public class BeliPolisActivity extends BaseActivity {
    @BindView(R.id.viewback_title) TextView title;
    @BindView(R.id.viewback_description) TextView description;
//    @BindView(R.id.kategoribelipolis_list_produk_terbaru) RecyclerView produkTerbaruRecycleview;
    @BindView(R.id.btn_kategori) AppCompatButton btnKategori;
    @BindView(R.id.btn_perusahaan) AppCompatButton btnPerusahaan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beli_polis);
        ButterKnife.bind(this);
        title.setText(getResources().getString(R.string.belipolis));
        description.setText(getResources().getString(R.string.pilihberbagaijenisasuransiyangtersedia));
        FragmentKategoriBeliPolis fragmentKategoriBeliPolis = new FragmentKategoriBeliPolis();
        setContentBeliPolis(fragmentKategoriBeliPolis);
        FirebaseAnalyticsHelper.logEvent(this, Constant.ANALYTICS_BUY_POLIS_CATEGORY);

//        produkTerbaruRecycleview.setNestedScrollingEnabled(true);
//        produkTerbaruRecycleview.setHasFixedSize(false);
//        ProdukTerbaruAdapter produkTerbaruAdapter = new ProdukTerbaruAdapter(this);
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
//        produkTerbaruRecycleview.setLayoutManager(linearLayoutManager);
//        produkTerbaruRecycleview.setAdapter(produkTerbaruAdapter);
//        ViewCompat.setNestedScrollingEnabled(produkTerbaruRecycleview, false);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @OnClick(R.id.viewback_buttonback)
    public void actionback(){finish();}

    @OnClick(R.id.btn_kategori)
    public void actionKategori(){
        FirebaseAnalyticsHelper.logEvent(this, Constant.ANALYTICS_BUY_POLIS_CATEGORY);

        btnKategori.setBackgroundDrawable(getResources().getDrawable(R.drawable.border_fill_red_l));
        btnKategori.setTextColor(getResources().getColor(R.color.white));
        btnPerusahaan.setBackgroundDrawable(getResources().getDrawable(R.drawable.border_unfill_red));
        btnPerusahaan.setTextColor(getResources().getColor(R.color.red));
        FragmentKategoriBeliPolis fragmentKategoriBeliPolis = new FragmentKategoriBeliPolis();
        setContentBeliPolis(fragmentKategoriBeliPolis);
    }

    @OnClick(R.id.btn_perusahaan)
    public void actionPerusahaan(){
        FirebaseAnalyticsHelper.logEvent(this, Constant.ANALYTICS_BUY_POLIS_COMPANY);

        btnKategori.setBackgroundDrawable(getResources().getDrawable(R.drawable.border_unfill_l));
        btnKategori.setTextColor(getResources().getColor(R.color.red));
        btnPerusahaan.setBackgroundDrawable(getResources().getDrawable(R.drawable.border_fill_red_r));
        btnPerusahaan.setTextColor(getResources().getColor(R.color.white));
        FragmentkategoriPerusahaan fragmentkategoriPerusahaan = new FragmentkategoriPerusahaan();
        setContentBeliPolis(fragmentkategoriPerusahaan);
    }

    public void setContentBeliPolis(Fragment fragment){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();

        for (int i= 0; i< fm.getBackStackEntryCount();i++){
            fm.popBackStack();
        }
        transaction.replace(R.id.belipolis_content,fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}

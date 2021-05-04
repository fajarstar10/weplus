package id.weplus.Tagihan;


import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.weplus.BaseActivity;
import id.weplus.R;
import id.weplus.utility.Constant;
import id.weplus.utility.FirebaseAnalyticsHelper;
//import id.weplus.voucher.FragmentPromo;


public class RiwayatPembayaranActivity extends BaseActivity {

    @BindView(R.id.riwayatpembayarantagihan_viewpager) ViewPager viewPager;
    @BindView(R.id.viewback_title) TextView title;
    @BindView(R.id.viewback_description) TextView description;
    @BindView(R.id.riwayatpembayarantagihan_tab)
    TabLayout tabLayout;
    private FragmentTransaksi fragmentTransaksi;
    private FragmentRiwayat fragmentRiwayat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_riwayat_pembayaran);
        ButterKnife.bind(this);
        title.setText(getString(R.string.trasaksidanriwayat));
        description.setText(getString(R.string.lihatseluruhtransaksidanriwayatpembelian));
        RiwayatPembayaranActivity.ViewPagerAdapter adapter = new RiwayatPembayaranActivity.ViewPagerAdapter(getSupportFragmentManager());
        adapter.clear(viewPager);
        viewPager.setAdapter(adapter);
        //        viewPager.canScrollHorizontally(R.id.riwayatpembayarantagihan_tab);
        tabLayout.setupWithViewPager(viewPager);
    }

    @OnClick(R.id.viewback_buttonback)
    public void actionBackAffiliasi(){
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
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
            if (position == 1) {

                fragmentRiwayat = new FragmentRiwayat();
                return fragmentRiwayat;
            }
            fragmentTransaksi = new FragmentTransaksi();
            return fragmentTransaksi;
        }

        @Override
        public int getCount() {
            return 2;
        }


        @Override
        public CharSequence getPageTitle(int position) {
            if (position == 1) {
                return getString(R.string.tab_riwayat);
            }
            return getString(R.string.tab_transaksipembelian);
        }
    }
}


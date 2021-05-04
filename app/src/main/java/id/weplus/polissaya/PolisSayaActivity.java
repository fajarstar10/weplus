package id.weplus.polissaya;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.weplus.BaseActivity;
import id.weplus.R;

public class PolisSayaActivity extends BaseActivity {
    @BindView(R.id.viewback_title) TextView title;
    @BindView(R.id.viewback_description) TextView description;
    @BindView(R.id.polissaya_tab) TabLayout tabLayout;
    @BindView(R.id.polissaya_viewpager) ViewPager viewPager;

//    private FragmentPolisSemua fragmentSemua;
    private FragmentPolisAktif fragmentPolisAktif;
    private FragmentPolisKadaluarsa fragmentPolisKadaluarsa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_polis_saya);
        ButterKnife.bind(this);
        title.setText(getResources().getString(R.string.polissaya));
        description.setText(getResources().getString(R.string.daftarsemuapolisygandamiliki));
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.clear(viewPager);
        viewPager.setAdapter(adapter);
        viewPager.canScrollHorizontally(R.id.afiliasi_tab);
        tabLayout.setupWithViewPager(viewPager);
    }

    @OnClick(R.id.viewback_buttonback)
    public void backbtnPolisSaya(){
        finish();
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
//                    fragmentSemua = new FragmentPolisSemua();
//                    return fragmentSemua;
//                }
                case 0: {
                    fragmentPolisAktif = new FragmentPolisAktif();
                    return fragmentPolisAktif;
                }
                case 1:{
                    fragmentPolisKadaluarsa = new FragmentPolisKadaluarsa();
                    return fragmentPolisKadaluarsa;
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

        /*public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }*/

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
                    return getString(R.string.kadaluarsa);
                }
                default:
                    return getString(R.string.aktif);
            }
        }
    }
}

package id.weplus.voucher;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.weplus.BaseActivity;
import id.weplus.R;

public class VoucherActivity extends BaseActivity {
    @BindView(R.id.afiliasi_viewpager) ViewPager viewPager;
    @BindView(R.id.viewback_title) TextView title;
    @BindView(R.id.viewback_description) TextView description;
    private FragmentVoucherDiscout fragmentVoucherDiscout;
//    private FragmentPromo fragmentPromo;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_afiliasi);
        ButterKnife.bind(this);

        title.setText(getString(R.string.voucher));
        description.setText("Dapatkan voucher dan promo dari WE+");
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.clear(viewPager);
        viewPager.setAdapter(adapter);
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
//                case 1: {
//                    fragmentPromo = new FragmentPromo();
//                    return fragmentPromo;
//                }
                default: {
                    fragmentVoucherDiscout = new FragmentVoucherDiscout();
                    return fragmentVoucherDiscout;
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


    }
}

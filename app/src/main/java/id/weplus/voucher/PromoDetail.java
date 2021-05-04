package id.weplus.voucher;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.weplus.BaseActivity;
import id.weplus.R;

    public class PromoDetail extends BaseActivity {
        @BindView(R.id.viewback_title_no_desc)
        TextView title;
        @BindView(R.id.promodetail_tab)
        TabLayout tabLayout;
        @BindView(R.id.promodetail_viewpager)
        ViewPager viewPager;
        private FragmentKetentuan fragmentKetentuan;
        private FragmentCaraPakai fragmentCaraPakai;

        @SuppressLint("SetTextI18n")
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_promo_detail);
            ButterKnife.bind(this);
            title.setText("GRATIS COCO SUNDAE");

            ViewPagerPromoDetailAdapter adapter = new ViewPagerPromoDetailAdapter(getSupportFragmentManager());
            adapter.clear(viewPager);
            viewPager.setAdapter(adapter);
            viewPager.canScrollHorizontally(R.id.afiliasi_tab);
            tabLayout.setupWithViewPager(viewPager);

        }

        @SuppressLint("SetTextI18n")
        @OnClick(R.id.promo_detail_gunakan)
        public void btnGunakanPromo() {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            // set the custom layout
            final View customLayout = getLayoutInflater().inflate(R.layout.view_logout, null);
            builder.setView(customLayout);

            final AlertDialog dialog = builder.create();
            dialog.show();

            TextView dialogTitle = customLayout.findViewById(R.id.dialog_title);
            dialogTitle.setText(getString(R.string.kodevoucheranda) + "WE12345" + " voucher akan hilang setelah digunakan");

            TextView btnBatal = customLayout.findViewById(R.id.dialog_btn_batal);
            TextView btnYa = customLayout.findViewById(R.id.dialog_btn_ya);
            btnBatal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.cancel();
                }
            });
            btnYa.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }

        @OnClick(R.id.viewback_buttonback_no_desc)
        public void backPromoDetail() {
            finish();
        }

        public class ViewPagerPromoDetailAdapter extends FragmentPagerAdapter {
            FragmentManager manager;
            FragmentTransaction transaction;

            public ViewPagerPromoDetailAdapter(FragmentManager manager) {
                super(manager);
                this.manager = manager;
            }

            public void clear(ViewGroup container) {
                transaction = manager.beginTransaction();

                for (int i = 0; i < getCount(); i++) {

                    final long itemId = getItemId(i);

                    String name = "android:switcher:" + container.getId() + ":" + itemId;
                    Fragment fragment = manager.findFragmentByTag(name);

                    if (fragment != null) {
                        transaction.detach(fragment);
                    }
                }
                transaction.commitAllowingStateLoss();
                transaction = null;
            }

            @Override
            public Fragment getItem(int position) {
                switch (position) {
                    case 0: {
                        fragmentKetentuan = new FragmentKetentuan();
                        return fragmentKetentuan;
                    }
                    case 1: {
                        fragmentCaraPakai = new FragmentCaraPakai();
                        return fragmentCaraPakai;
                    }
                    default: {
                        fragmentKetentuan = new FragmentKetentuan();
                        return fragmentKetentuan;
                    }
                }
            }

            @Override
            public int getCount() {
                return 2;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                switch (position) {
                    case 0: {
                        return getString(R.string.tab_ketentuan);
                    }
                    case 1: {
                        return getString(R.string.tab_cara_pakai);
                    }
                    default:
                        return getString(R.string.tab_ketentuan);
                }
            }

        }

    }

package id.weplus.mainfragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.weplus.R;
import id.weplus.affiliasi.FragmentBengkelRekanan;
import id.weplus.kontakklaim.FragmentKontakKlaim;

public class FragmentAffiliasi extends Fragment {
    @BindView(R.id.viewback_title) TextView title;
    @BindView(R.id.viewback_description) TextView description;
    @BindView(R.id.afiliasi_viewpager)
    ViewPager viewPager;
    @BindView(R.id.afiliasi_tab)
    TabLayout tabLayout;
    private FragmentKontakKlaim fragmentKontakKlaim;
    private FragmentBengkelRekanan fragmentBengkelRekanan;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.view_afiliasi, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        title.setText(getResources().getString(R.string.affiliasi));
        description.setText(getResources().getString(R.string.pilihberbagaijenisasuransiyangtersedia));

        final ViewPagerAdapter adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());
        adapter.clear(viewPager);
        viewPager.setAdapter(adapter);
        viewPager.canScrollHorizontally(R.id.afiliasi_tab);
        tabLayout.setupWithViewPager(viewPager);
    }

    @OnClick(R.id.viewback_buttonback)
    public void backAffiliasi(){

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
                    fragmentKontakKlaim = new FragmentKontakKlaim();
                    return fragmentKontakKlaim;
                }
                case 1: {
                    fragmentBengkelRekanan = new FragmentBengkelRekanan();
                    return fragmentBengkelRekanan;
                }
                default: {
                    fragmentKontakKlaim = new FragmentKontakKlaim();
                    return fragmentKontakKlaim;
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
                    return getString(R.string.tab_kontak_klaim);
                }
                case 1: {
                    return getString(R.string.tab_bengkel_rekanan);
                }
                default: {
                    return getString(R.string.tab_kontak_klaim);
                }
            }
        }
    }

}

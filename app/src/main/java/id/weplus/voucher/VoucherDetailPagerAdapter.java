package id.weplus.voucher;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class VoucherDetailPagerAdapter extends FragmentStatePagerAdapter {

    private String caraPakai;
    private String ketentuan;
    public VoucherDetailPagerAdapter(FragmentManager fm, String ketentuan, String caraPakai) {
        super(fm);
        this.caraPakai=caraPakai;
        this.ketentuan=ketentuan;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return "Ketentuan";
        }
        return "Cara Pakai";
    }

    @Override
    public Fragment getItem(int i) {
        if(i==1){
            return FragmentCaraPakai.newInstance(caraPakai);
        }
        return FragmentCaraPakai.newInstance(ketentuan);
    }

}

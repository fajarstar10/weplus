package id.weplus.belipolis;

import android.os.Bundle;
import androidx.recyclerview.widget.DefaultItemAnimator;import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.weplus.BaseActivity;
import id.weplus.R;
import id.weplus.utility.GridSpacingItemDecoration;
import id.weplus.utility.Util;

public class SemuaKategoriActivity extends BaseActivity {
    @BindView(R.id.semuakategori_recycleview)
    RecyclerView categoryRecycleview;
    private int[] imgIcon =
            {R.drawable.ic_extreme_sport,R.drawable.ic_extreme_sport,R.drawable.ic_extreme_sport,R.drawable.ic_extreme_sport,
            R.drawable.ic_extreme_sport,R.drawable.ic_extreme_sport,R.drawable.ic_extreme_sport,R.drawable.ic_extreme_sport,R.drawable.ic_extreme_sport,};
    private String[] labelIcon =
            {"Perjalanan", "Kesehatan", "Motor", "Mobil", "Olahraga Ekstrim",
            "Kecelakaan Diri", "Properti", "Jiwa", "Olahraga Ekstrim"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_semua_kategori);
        ButterKnife.bind(this);

        categoryRecycleview.setNestedScrollingEnabled(true);
//        KategoriAdapter adapter = new KategoriAdapter(this, imgIcon, labelIcon);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 4);
        categoryRecycleview.setLayoutManager(mLayoutManager);
        categoryRecycleview.addItemDecoration(new GridSpacingItemDecoration(4, Util.dpToPx(10, this), true));
        categoryRecycleview.setItemAnimator(new DefaultItemAnimator());
//        categoryRecycleview.setAdapter(adapter);
    }

    @OnClick(R.id.semuakategori_btn_close)
    public void closeSemuaKategori(){
        finish();
    }
}

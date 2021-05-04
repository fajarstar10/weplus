package id.weplus.detailpolis;

import android.os.Bundle;

import butterknife.ButterKnife;
import id.weplus.BaseActivity;
import id.weplus.R;

public class DetailPolisTravelActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_polis_travel);
        ButterKnife.bind(this);
    }
}

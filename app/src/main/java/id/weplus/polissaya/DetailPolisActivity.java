package id.weplus.polissaya;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.weplus.BaseActivity;
import id.weplus.R;

public class DetailPolisActivity extends BaseActivity {
    @BindView(R.id.viewback_title) TextView title;
    @BindView(R.id.viewback_description) TextView description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_polis);
        ButterKnife.bind(this);
        title.setText(getResources().getString(R.string.detailpolis));
        description.setText(getResources().getString(R.string.melihatdaftartransaksiygdilakukan));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @OnClick(R.id.viewback_buttonback)
    public void actionBack(){finish();}

    @OnClick(R.id.detailpolis_btn_klaim_asuransi)
    public void actionKlaim(){finish();}

    @OnClick(R.id.detailpolis_benefit_asuransi)
    public void actionDetailBenefitAsurans(){
        Intent intent = new Intent(DetailPolisActivity.this, DetailBenefitAsuransiActivity.class);
        startActivity(intent);
    }
}

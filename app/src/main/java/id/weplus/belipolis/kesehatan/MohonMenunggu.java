package id.weplus.belipolis.kesehatan;

import android.os.Bundle;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.weplus.BaseActivity;
import id.weplus.R;

public class MohonMenunggu extends BaseActivity {
    @BindView(R.id.viewback_title) TextView title;
    @BindView(R.id.viewback_description) TextView description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mohon_menunggu);
        ButterKnife.bind(this);
        title.setText(getString(R.string.belipolis));
        description.setText(getString(R.string.pilihberbagaijenisasuransiyangtersedia));
    }
    @OnClick(R.id.viewback_buttonback)
    public void transaktionBack(){finish();}

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}

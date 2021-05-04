package id.weplus.belipolis;

import android.os.Bundle;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.weplus.BaseActivity;
import id.weplus.R;

public class FormAhliWarisActivity extends BaseActivity {
    @BindView(R.id.viewback_title)
    TextView title;
    @BindView(R.id.viewback_description) TextView description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_ahli_waris);
        ButterKnife.bind(this);
        title.setText(getResources().getString(R.string.isidataahliwaris));
        description.setText(getResources().getString(R.string.lakukanpengisiandatauntukmelanjutkan));
    }

    @OnClick(R.id.viewback_buttonback)
    public void backFormAhliWaris(){finish();}

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @OnClick(R.id.formahliwaris_btn_simpan)
    public void simpanAhliWaris(){
        finish();
    }
}

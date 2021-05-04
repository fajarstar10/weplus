//package id.weplus.belipolis.motor;
//
//import android.os.Bundle;
//import android.support.v7.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//import android.widget.TextView;
//
//import butterknife.BindView;
//import butterknife.ButterKnife;
//import butterknife.OnClick;
//import id.weplus.BaseActivity;
//import id.weplus.R;
//
//public class TambahanPerlindunganActivity extends BaseActivity {
//    @BindView(R.id.viewback_title) TextView title;
//    @BindView(R.id.viewback_description) TextView description;
//    @BindView(R.id.tambahanrecycleview) RecyclerView tambRecyclerView;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_tambahan_perlindungan);
//        ButterKnife.bind(this);
//        title.setText(getResources().getString(R.string.tambahanperlindungan));
//        description.setText(getResources().getString(R.string.tambahanperlindunganbisalebihdarisatu ));
//
//        tambRecyclerView.setNestedScrollingEnabled(true);
//        TambahanPerlindunganAdapter adapter = new TambahanPerlindunganAdapter(this);
//        LinearLayoutManager lm = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
//        tambRecyclerView.setLayoutManager(lm);
//        tambRecyclerView.setAdapter(adapter);
//    }
//
//    @OnClick(R.id.viewback_buttonback)
//    public void backBtn(){finish();}
//
//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//    }
//
//    @OnClick(R.id.tambahan_lanjutkan_btn)
//    public void tambahanPerlindunganBtnLanjut(){
//        finish();
//    }
//}

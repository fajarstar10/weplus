package id.weplus.belipolis.mobil;

import android.content.Intent;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.weplus.BaseActivity;
import id.weplus.R;
import id.weplus.model.BaseFilter;
import id.weplus.model.request.CarProductListRequest;

public class TambahanPerlindunganMobilActivity extends BaseActivity {
    @BindView(R.id.viewback_title) TextView title;
    @BindView(R.id.viewback_description) TextView description;
    @BindView(R.id.tambahanmobilrecycleview) RecyclerView tambRecyclerView;

    CarProductListRequest requestBody;
    ArrayList<BaseFilter> addProtections = new ArrayList<>();
    TambahanPerlindunganMobilAdapter adapter;
    private boolean isAgent=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambahan_perlindungan_mobil);
        ButterKnife.bind(this);
        title.setText(getResources().getString(R.string.tambahanperlindunganmobil));
        description.setText(getResources().getString(R.string.tambahanperlindunganbisalebihdarisatu ));

        getData();

        tambRecyclerView.setNestedScrollingEnabled(true);
        adapter=new TambahanPerlindunganMobilAdapter(this,addProtections);
        LinearLayoutManager lm = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        tambRecyclerView.setLayoutManager(lm);
        tambRecyclerView.setAdapter(adapter);
    }

    private void getData(){
        requestBody = getIntent().getParcelableExtra("requestBody");
        addProtections = getIntent().getParcelableArrayListExtra("addProtection");
        isAgent = getIntent().getBooleanExtra("is_agent",false);
        Log.d("isAgent","isagent tambahan perlindungan : "+isAgent);
    }


    @OnClick(R.id.viewback_buttonback)
    public void backBtn(){finish();}

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @OnClick(R.id.tambahan_lanjutkan_mobil_btn)
    public void tambahanPerlindunganMobilBtnLanjut(){
        requestBody.setAddition_protection(getSelectedProtection());
        Intent intent = new Intent(this, CarProductListActivity.class);
        intent.putExtra("requestBody",requestBody);
        intent.putExtra("is_agent",isAgent);
        Log.d("isAgent","isagent tambahan perlindungan before: "+isAgent);
        startActivity(intent);
    }

    private String getSelectedProtection(){
        StringBuilder selected= new StringBuilder();
        ArrayList<BaseFilter> test = adapter.getLists();
        for(int i=0;i<test.size();i++){
            BaseFilter filter = test.get(i);
            if(filter.isSelected()){
                selected.append("").append(filter.getId()).append(",");
            }
        }
        if (!selected.toString().equals("")) {
            selected = new StringBuilder(selected.substring(0, selected.length() - 1));
        }
        return selected.toString();
    }
}


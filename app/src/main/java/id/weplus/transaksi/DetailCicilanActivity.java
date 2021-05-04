package id.weplus.transaksi;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.weplus.BaseActivity;
import id.weplus.R;
import id.weplus.model.Cicilan;

public class DetailCicilanActivity extends BaseActivity {
    @BindView(R.id.viewback_title) TextView title;
    @BindView(R.id.viewback_description) TextView description;
    @BindView(R.id.rvCicilan) RecyclerView rvCicilan;

    private ArrayList<Cicilan> detailCicilan = new ArrayList<>();
    private CicilanAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_cicilan);
        ButterKnife.bind(this);
        title.setText(getResources().getString(R.string.detailcicilan));
        description.setText(getResources().getString(R.string.melihatdetailcicilansaatini));
        getArguments();
        setupAdapter();
    }

    private void setupAdapter(){
        detailCicilan = getIntent().getParcelableArrayListExtra("cicilan");
        adapter = new CicilanAdapter(this,detailCicilan);
        LinearLayoutManager transaksiLayoutmanager =
                new LinearLayoutManager(this.getApplicationContext(),LinearLayoutManager.VERTICAL,false);
        rvCicilan.setLayoutManager(transaksiLayoutmanager);
        rvCicilan.setAdapter(adapter);
    }

    private void getArguments(){
           detailCicilan= getIntent().getParcelableArrayListExtra("Cicilan");
    }

    @OnClick(R.id.viewback_buttonback)
    public void backDetailCicilan(){finish();}

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}

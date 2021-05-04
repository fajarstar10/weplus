package id.weplus.belipolis.motor;

import android.annotation.SuppressLint;
import android.content.Intent;
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
import id.weplus.model.BaseFilter;
import id.weplus.model.request.MotorProductListRequest;

public class TambahanPerlindunganAsuransiMotorActivity extends BaseActivity {

    @BindView(R.id.viewback_title) TextView title;
    @BindView(R.id.viewback_description) TextView description;
    @BindView(R.id.rvPerlindungan) RecyclerView rvPerlindungan;
    private ArrayList<BaseFilter> additonalProtection = new ArrayList<>();
    private MotorProductListRequest request;
    private  AdditionalProtectionAdapter adapter;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambahan_perlindungan_asuransi_motor);
        ButterKnife.bind(this);
        title.setText("Tambahan Perlindungan");
        description.setText("Tambahan Perlidungan (opsional)");
        getData();
        setupRecyclerView();
    }

    private void setupRecyclerView() {
        adapter = new AdditionalProtectionAdapter(this,additonalProtection);
        rvPerlindungan.setLayoutManager(new LinearLayoutManager(this));
        rvPerlindungan.setAdapter(adapter);
    }

    private void getData(){
        request = getIntent().getParcelableExtra("requestBody");
        additonalProtection.addAll(getIntent().getParcelableArrayListExtra("addProtection"));
//        driverIncidents.addAll(getIntent().getParcelableArrayListExtra("driver"));
//        passengerIncidents.addAll(getIntent().getParcelableArrayListExtra("passenger"));
    }

    @OnClick(R.id.viewback_buttonback)
    public void actionBackPembayaran(){finish();}

    @OnClick(R.id.btnLanjutkan)
    public void goToMotorProductList(){
        request.setAddition_protection(getSelectedProtection());
        request.setAddition_driver_incident(2);
        request.setAddition_passenger_incident(3);
        Intent intent = new Intent(this, MotorProductListActivity.class);
        intent.putExtra("requestBody",request);
        intent.putExtra("cat_id",1);
        startActivity(intent);
    }

    private String getSelectedProtection(){
        String selected="";
        for(int i=0;i<adapter.getItemCount();i++){
            if(adapter.getList().get(i).isSelected()){
                int id = adapter.getList().get(i).getId();
                selected+=""+id+",";
            }
        }
        if(!selected.equals("")){
            selected =  selected.substring(0, selected.length() - 1);
        }
        return selected;
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}

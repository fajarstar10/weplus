package id.weplus.belipolis.mobil;

import android.content.Intent;
import androidx.annotation.NonNull;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.xw.repo.BubbleSeekBar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.weplus.BaseActivity;
import id.weplus.R;
import id.weplus.model.CarAccessories;

import static id.weplus.utility.TextHelper.currencyFormatter;

public class AddCarAccesoriesActivity extends BaseActivity {

    @BindView(R.id.checkboxSelectAll) CheckBox cbSelectAll;
    @BindView(R.id.rvAksesoris) RecyclerView rvAksesoris;
    @BindView(R.id.viewback_title) TextView title;
    @BindView(R.id.viewback_description) TextView description;
    @BindView(R.id.viewback_buttonback) ImageView buttonBack;
    @BindView(R.id.btnSimpan) Button buttonSave;
    @BindView(R.id.seekBar) BubbleSeekBar seekBar;
    @BindView(R.id.tvRpValue) TextView rpValue;

    private CarAccesoriesAdapter adapter;
    private ArrayList<String> selectedAcc = new ArrayList<>();
    public static int accResultCode = 12;
    private int price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_car_accesories);
        ButterKnife.bind(this);
        setupTitle();
        getData();
        setupAdapter();
        setupCheckAll();
        setupButtonSaveAction();

    }

    private void getData(){
        price = getIntent().getIntExtra("acc_price",500000);
        selectedAcc=getIntent().getStringArrayListExtra("checked_labels");
        if(selectedAcc!=null) {
            Log.d("label test", "count label " + selectedAcc.size());
        }
        // customize section texts
        seekBar.setCustomSectionTextArray((sectionCount, array) -> {
            array.clear();
            array.put(1, "Rp.500 ribu");
            array.put(30, "Rp 15 Juta");
            return array;
        });

        seekBar.setOnProgressChangedListener(new BubbleSeekBar.OnProgressChangedListener() {
            @Override
            public void onProgressChanged(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat, boolean fromUser) {
                price=progress*500000;
                rpValue.setText(currencyFormatter(""+price));
            }

            @Override
            public void getProgressOnActionUp(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat) {

            }

            @Override
            public void getProgressOnFinally(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat, boolean fromUser) {

            }
        });
        int progress = price/500000;
        rpValue.setText(currencyFormatter(""+price));
        seekBar.setProgress(progress);
    }



    private void setupButtonSaveAction(){
        buttonSave.setOnClickListener(view -> {
            Intent returnIntent = new Intent();
            returnIntent.putStringArrayListExtra("checked_labels",adapter.getSelectedLabels());
            returnIntent.putExtra("checked",adapter.getCheckedList());
            returnIntent.putExtra("acc_price",price);
            setResult(accResultCode,returnIntent);
            finish();
        });
    }

    private void setupTitle(){
            title.setText("Tambah Aksesoris");
            description.setVisibility(View.GONE);
            buttonBack.setOnClickListener(view -> finish());
    }
    private void setupAdapter(){

        CarAccessories acc1 = new CarAccessories("Kaca Film",false);
        CarAccessories acc2 = new CarAccessories("Karpet Sintesis",false);
        CarAccessories acc3 = new CarAccessories("Sarung Jok Kulit",false);
        CarAccessories acc4 = new CarAccessories("Roda dan Band",false);
        CarAccessories acc5 = new CarAccessories("Body Kit",false);
        CarAccessories acc6 = new CarAccessories("Lain-lain",false);
        ArrayList<CarAccessories> carAccs = new ArrayList<>();
        carAccs.add(acc1);
        carAccs.add(acc2);
        carAccs.add(acc3);
        carAccs.add(acc4);
        carAccs.add(acc5);
        carAccs.add(acc6);
        if(selectedAcc!=null && selectedAcc.size()>0){
            for(String label:selectedAcc){
                for(CarAccessories acc:carAccs){
                    Log.d("label","test "+acc.getLabel()+" ~ "+label);
                    if(acc.getLabel().equals(label)){
                        acc.setChecked(true);
                    }
                }
            }
        }
        adapter=new CarAccesoriesAdapter(this,carAccs);
        LinearLayoutManager lm = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvAksesoris.setLayoutManager(lm);
        rvAksesoris.setAdapter(adapter);
    }

    private void setupCheckAll() {
        cbSelectAll.setOnCheckedChangeListener((compoundButton, b) -> {
            adapter.checkAll();
        });
    }
}

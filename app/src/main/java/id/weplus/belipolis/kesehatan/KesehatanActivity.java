package id.weplus.belipolis.kesehatan;

import android.content.Intent;
import android.os.Bundle;
import androidx.core.content.ContextCompat;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.weplus.BaseActivity;
import id.weplus.R;
import id.weplus.utility.RecyclerItemClickListener;

public class KesehatanActivity  extends BaseActivity implements View.OnClickListener {
    private static String TAG = "Kesehatan Activity";
    private int catId=0;
    private int partnerId=0;
    private int isFamily=-1;
    private int isSmoker=-1;
    @BindView(R.id.rec_kesehatan) RecyclerView recyclerViewKesehatan;
    @BindView(R.id.menu_keluarga) Button btnKeluarga;
    @BindView(R.id.menu_individual) Button btnIndividu;
    @BindView(R.id.menu_merokok) Button btnMerokok;
    @BindView(R.id.menu_tidak_merokok) Button btnTdkMeroko;
    @BindView(R.id.layoutSmokerWrapper) LinearLayout layoutSmokerWrapper;
    @BindView(R.id.tvSmoker) TextView tvSmoker;
    @BindView(R.id.buttonNext) TextView buttonNext;
    @BindView(R.id.viewback_description) TextView tvTitleDescription;
    @BindView(R.id.viewback_title) TextView tvTitle;
    @BindView(R.id.viewback_buttonback) ImageView backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kesehatan);
        ButterKnife.bind(this);

        catId=getIntent().getIntExtra("cat_id",0);
        partnerId = getIntent().getIntExtra("partner_id",0);
        btnIndividu.setOnClickListener(this);
        btnKeluarga.setOnClickListener(this);
        btnTdkMeroko.setOnClickListener(this);
        btnMerokok.setOnClickListener(this);
        setupToolbar();
        recyclerViewKesehatan.setNestedScrollingEnabled(true);
        KesehatanAdapter kesehatanAdapter = new KesehatanAdapter(this);
        LinearLayoutManager kesehatanAdapterLM = new LinearLayoutManager(getApplication().getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewKesehatan.setLayoutManager(kesehatanAdapterLM);
        recyclerViewKesehatan.setAdapter(kesehatanAdapter);
        recyclerViewKesehatan.addOnItemTouchListener(new RecyclerItemClickListener(getApplication().getApplicationContext(),
                recyclerViewKesehatan, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                switch (position) {
                }
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));

        buttonNext.setOnClickListener(view -> {
            goToHealthProduct();
        });
    }

    private void setupToolbar(){
        tvTitle.setText("Kesehatan");
        tvTitleDescription.setText("Pilih Tipe Asuransi");
        backButton.setOnClickListener(view -> finish());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.menu_individual:
                selectedButton(btnIndividu,btnKeluarga);
                isFamily=0;
                layoutSmokerWrapper.setVisibility(View.VISIBLE);
                tvSmoker.setText("Apakah anda seorang perokok?");
                break;
            case R.id.menu_keluarga:
                selectedButton(btnKeluarga,btnIndividu);
                isFamily=1;
                layoutSmokerWrapper.setVisibility(View.VISIBLE);
                tvSmoker.setText("Apakah ada anggota keluarga yang merokok?");
                break;
            case R.id.menu_merokok:
                selectedButton(btnMerokok,btnTdkMeroko);
                isSmoker=1;
                enableButtonNext();
                break;
            case R.id.menu_tidak_merokok:
                selectedButton(btnTdkMeroko,btnMerokok);
                isSmoker=0;
                enableButtonNext();
                break;
        }
    }

    public void enableButtonNext(){
        buttonNext.setBackground(ContextCompat.getDrawable(this,R.drawable.border_fill_red));
        buttonNext.setEnabled(true);
        buttonNext.setTextColor(ContextCompat.getColor(this,R.color.white));
    }

    private void selectedButton(Button selectedView,Button unselectedView){
        selectedView.setBackground(ContextCompat.getDrawable(KesehatanActivity.this,R.drawable.border_fill_red));
        selectedView.setTextColor(ContextCompat.getColor(this,R.color.white));
        unselectedView.setBackground(ContextCompat.getDrawable(KesehatanActivity.this,R.drawable.border_grey_stroke_greymedium));
        unselectedView.setTextColor(ContextCompat.getColor(this,R.color.black_7f7f7f));
    }

    private void goToHealthProduct(){
        Intent productIntent = new Intent(this, FormAsuransiHealth.class);
        productIntent.putExtra("isFamily",isFamily);
        productIntent.putExtra("isSmoker",isSmoker);
        productIntent.putExtra("partner_id",partnerId);
        startActivity(productIntent);
    }


}

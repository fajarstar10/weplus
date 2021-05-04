package id.weplus.belipolis;

import android.content.Intent;
import android.os.Bundle;

import butterknife.ButterKnife;
import id.weplus.BaseActivity;
import id.weplus.R;

public class KategoriOnclickedActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kategori_onclicked);
        ButterKnife.bind(this);
        Intent bundle = getIntent();
        String tagKategori = bundle.getStringExtra("kategori_tag");
        if (tagKategori.equals("semua")){

        Intent intent = new Intent(this, SemuaKategoriActivity.class);
        startActivity(intent);
        }
    }
}

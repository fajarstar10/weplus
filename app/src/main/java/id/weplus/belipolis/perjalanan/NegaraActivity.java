package id.weplus.belipolis.perjalanan;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.OnClick;
import id.weplus.R;

public class NegaraActivity extends AppCompatActivity {

    @BindView(R.id.viewback_title_negara) EditText destinasiPerjalanan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_negara);
    }

    @OnClick(R.id.viewback_title_negara)
    public void backBeliPolisPerjalanan() {
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


//    @OnClick(R.id.viewback_title)
//    public void negaraPerjalanan() {
//        Intent intent = new Intent(NegaraActivity.this, BeliPolisPerjalananActivity.class);
//        startActivity(intent);
//    }
}


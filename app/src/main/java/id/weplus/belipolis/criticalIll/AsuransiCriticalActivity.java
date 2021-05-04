package id.weplus.belipolis.criticalIll;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.weplus.BaseActivity;
import id.weplus.R;
import id.weplus.helper.DatePickerFragment;
import id.weplus.helper.OnDateSetListener;
import id.weplus.model.request.CriticalllnessProductListRequest;

public class AsuransiCriticalActivity extends BaseActivity implements OnDateSetListener {

    @BindView(R.id.etDob) TextView etDob;
    @BindView(R.id.spinnerGender) Spinner spinnerGender;
    @BindView(R.id.btnNext) Button btnNext;
    @BindView(R.id.viewback_buttonback) ImageView btnBack;
    @BindView(R.id.viewback_title) TextView title;
    @BindView(R.id.viewback_description) TextView description;

    @BindView(R.id.errorTextDob) TextView errorDob;
    @BindView(R.id.errorTextGender) TextView errorGender;

    private String gender="";
    private String dob="";
    private int partnerId=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asuransi_critical);
        ButterKnife.bind(this);
        setupToolbar();
        setupButtonNext();
        setupDatePicker();
        partnerId = getIntent().getIntExtra("partner_id",0);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void setupToolbar() {
        btnBack.setOnClickListener(view -> finish());
        title.setText("Asuransi Critical Illness");
        description.setText("Lengkapi data dibawah ini");
    }

    private void setupDatePicker() {
        Calendar calendar = Calendar.getInstance();
        int minAge = calendar.get(Calendar.YEAR) - 18;
        int maxAge = calendar.get(Calendar.YEAR) - 60;
        etDob.setOnClickListener(view -> {

            DialogFragment newFragment = DatePickerFragment.newInstance("dob", maxAge, minAge, this);
            //DialogFragment newFragment = DatePickerFragment.newInstance("dob_life",this);
            newFragment.show(getSupportFragmentManager(), "datePicker");
        });
    }

    private void setupButtonNext() {
        btnNext.setOnClickListener(view -> {
            if(validate()){
                gender=spinnerGender.getSelectedItemPosition()==1?"m":"f";
                Intent intent = new Intent(AsuransiCriticalActivity.this, CriticalProductListActivity.class);
                intent.putExtra("requestBody", new CriticalllnessProductListRequest(
                        0,"",12,partnerId,dob,gender
                ));
                intent.putExtra("cat_id",12);
                startActivity(intent);
            }
        });
    }

    private boolean validate(){
        boolean dobValidate = !etDob.getText().toString().equals("");
        boolean genderValidate = spinnerGender.getSelectedItemPosition()!=0;

        errorDob.setVisibility(dobValidate? View.GONE:View.VISIBLE);
        errorGender.setVisibility(genderValidate? View.GONE:View.VISIBLE);

        return (dobValidate && genderValidate);
    }


    @Override
    public void onDateSet(Calendar c, String cat) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat postFormater = new SimpleDateFormat(" dd MMMM yyyy");
        String newDateStr = postFormater.format(c.getTime());
        dob = c.get(Calendar.YEAR)+"-"+(c.get(Calendar.MONTH)+1)+"-"+c.get(Calendar.DAY_OF_MONTH);
        etDob.setText(newDateStr);
    }
}

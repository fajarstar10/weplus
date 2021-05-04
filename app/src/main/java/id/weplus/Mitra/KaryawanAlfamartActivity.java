package id.weplus.Mitra;

import android.content.Intent;
import android.os.Bundle;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.lsjwzh.widget.materialloadingprogressbar.CircleProgressBar;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.weplus.BaseActivity;
import id.weplus.R;
import id.weplus.WelcomeActivity;
import id.weplus.belipolis.perjalanan.BeliPolisPerjalananActivity;
import id.weplus.model.CheckResult;
import id.weplus.model.Partner;
import id.weplus.model.response.CheckEmployeeResponse;
import id.weplus.net.ErrorCode;
import id.weplus.net.NetworkManager;
import id.weplus.net.WeplusSharedPreference;
import id.weplus.utility.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KaryawanAlfamartActivity extends BaseActivity {
    private static String TAG = "KaryawanAlfamart";
    @BindView(R.id.viewback_title) TextView title;
    @BindView(R.id.viewback_description) TextView description;
    @BindView(R.id.viewback_buttonback) ImageView btnback;
    @BindView(R.id.nikkaryawan) EditText etNik;
    @BindView(R.id.btnNext) Button btnNext;
    @BindView(R.id.progressBar) CircleProgressBar progressBar;
    @BindView(R.id.logo_alfamart) ImageView mPartnerImage;
    private Partner partner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_karyawan_alfamart);

        ButterKnife.bind(this);

        partner = getIntent().getParcelableExtra("partner");
        btnNext.setOnClickListener(view -> {
            String text = etNik.getText().toString();
            if(text.isEmpty()){
                showError(this,"NIK harus di isi");
            }else{
                checkEmployeeId(text);
            }
        });
        title.setText(partner.getName());
        description.setText(getString(R.string.partneryangbekerjasamadenganweplus));
        btnback.setOnClickListener(view -> finish());
        Glide.with(this).load(partner.getImage()).into(mPartnerImage);
    }

    private void checkEmployeeId(String nik){
        boolean isNetworkAvailable = Util.isNetworkAvailable(getApplication().getApplicationContext());
        if (isNetworkAvailable) {
            progressBar.setVisibility(View.VISIBLE);
            String token = WeplusSharedPreference.getToken(getApplication());
            Call<String> call = NetworkManager
                            .getNetworkServiceWithHeader(getApplication(),token)
                            .getCheckEmployee(partner.getId(),nik);
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    progressBar.setVisibility(View.INVISIBLE);
                    Log.i(TAG, "Mitra Response : " + response.body());

                    Gson gson = new Gson();
                    CheckEmployeeResponse checkResultResponse = gson.fromJson(response.body(), CheckEmployeeResponse.class);
                    CheckResult result = checkResultResponse.getData();

                    try {
                        JSONObject job = new JSONObject(response.body());
                        String code = job.getString("code");
                        String description = job.getString("message");
                        if (code.equals(ErrorCode.ERROR_00)) {
                            Intent intent = new Intent(KaryawanAlfamartActivity.this,PartnerCategoryActivity.class);
                            intent.putExtra("partner_id",partner.getId());
                            intent.putExtra("nik",nik);
                            intent.putExtra("employee",result.getName().toLowerCase().replace("employee",""));
                            startActivity(intent);
                        } else if(code.equals(ErrorCode.ERROR_03)){
                            new SweetAlertDialog(KaryawanAlfamartActivity.this, SweetAlertDialog.NORMAL_TYPE)
                                    .setTitleText("")
                                    .setContentText("Please login first")
                                    .setConfirmText("Ok")
                                    .setConfirmClickListener(sweetAlertDialog -> {
                                        Intent intent = new Intent(KaryawanAlfamartActivity.this, WelcomeActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                    })
                                    .show();
                        }else {
                            showError(description);
                        }

                    } catch (Exception e) {
                        Log.i(TAG, e.getMessage());
                    }

                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    progressBar.setVisibility(View.INVISIBLE);
                    showError("Time Out");
                    Log.i(TAG, "On FAILUR : " + t.getMessage());

                }
            });
        } else {
            showError(getString(R.string.network_error));
        }
    }




    @OnClick(R.id.viewback_buttonback)
    public void transaktionBack(){finish();}

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}

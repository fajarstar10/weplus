package id.weplus.profil;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.weplus.BaseActivity;
import id.weplus.R;
import id.weplus.Tagihan.BayarTagihanListrikActivity;
import id.weplus.WelcomeActivity;
import id.weplus.model.LoginData;
import id.weplus.model.LoginModel;
import id.weplus.net.ErrorCode;
import id.weplus.net.NetworkManager;
import id.weplus.net.WeplusSharedPreference;
import id.weplus.utility.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UbahAlamatEmail extends BaseActivity {
    @BindView(R.id.ubahemaillengkap_email_lengkap) EditText emailLengkap;
    @BindView(R.id.viewback_title_no_desc) TextView title;
    @BindView(R.id.loading_progress) RelativeLayout loadingProgress;

    private LoginData user;
    private String TAG="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah_alamat_email);
        ButterKnife.bind(this);
        title.setText(getString(R.string.ubahalamatemail));

        getUserData();
        populateView();
    }

    private void getUserData(){
        String json_response = WeplusSharedPreference.getUser(this);
        Gson gson = new Gson();
        user = gson.fromJson(json_response, LoginData.class);
    }

    private void populateView(){
        emailLengkap.setText(user.getEmail());
    }

    @OnClick(R.id.ubahemaillengkap_btn_simpan)
    public void actionUbahEmail(){
        String email_v = emailLengkap.getText().toString();
         boolean isEmailValid = Util.isEmailValid(email_v);
         if (email_v.length()!=0) {// cek empty email
             if (!isEmailValid) {// cek valid email
                 showError(getString(R.string.erorformatemail));
             } else{ // do in this
                 user.setEmail(email_v);
                 sendUpdateEmailRequest();
             }
         }else {
            showError(getResources().getString(R.string.empty_field));
        }
    }

    @OnClick(R.id.viewback_buttonback_no_desc)
    public void actionBack(){finish();}

    private void sendUpdateEmailRequest(){
        boolean isNetworkAvailable = Util.isNetworkAvailable(this);
        if (isNetworkAvailable){
            loadingProgress.setVisibility(View.VISIBLE);
            String token = WeplusSharedPreference.getToken(this);
            Call<String> call = NetworkManager
                    .getNetworkServiceWithHeader(this, token)
                    .updateUser(user.toUpdateProfileRequest());
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    Gson gson = new Gson();
                    LoginModel resp = gson.fromJson(response.body(), LoginModel.class);
                    try {
                        loadingProgress.setVisibility(View.GONE);
                        if (resp.getCode().equals(ErrorCode.ERROR_00)){
                            new SweetAlertDialog(UbahAlamatEmail.this, SweetAlertDialog.SUCCESS_TYPE)
                                    .setTitleText("Sukses")
                                    .setContentText(resp.getMessage())
                                    .setConfirmText("Alamat Email telah berhasil diperbarui")
                                    .setConfirmClickListener(sweetAlertDialog ->{
                                                WeplusSharedPreference.saveUser(getApplicationContext(),gson.toJson(user));
                                                finish();
                                            }
                                    )
                                    .show();
                        } else if(resp.getCode().equals(ErrorCode.ERROR_03)){
                            Intent intent = new Intent(UbahAlamatEmail.this, WelcomeActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }else {
                            new SweetAlertDialog(UbahAlamatEmail.this, SweetAlertDialog.NORMAL_TYPE)
                                    .setTitleText("")
                                    .setContentText(resp.getMessage())
                                    .show();
                        }
                    }
                    catch (Exception e) {
                        loadingProgress.setVisibility(View.GONE);
                        Log.i(TAG,"asu: "+e.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    loadingProgress.setVisibility(View.GONE);
                    new SweetAlertDialog(UbahAlamatEmail.this, SweetAlertDialog.NORMAL_TYPE)
                            .setTitleText("")
                            .setContentText("Time Out")
                            .show();

                    Log.i(TAG,"ON FAILURE : " + t.getMessage());
                }
            });
        } else {
            new SweetAlertDialog(this, SweetAlertDialog.NORMAL_TYPE)
                    .setTitleText(" ")
                    .setContentText(getString(R.string.network_error))
                    .show();
        }
    }

}

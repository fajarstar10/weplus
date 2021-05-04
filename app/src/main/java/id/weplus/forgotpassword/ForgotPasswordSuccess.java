package id.weplus.forgotpassword;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.weplus.BaseActivity;
import id.weplus.Query.ResetPasswordQuery;
import id.weplus.R;
import id.weplus.net.NetworkManager;
import id.weplus.net.WeplusSharedPreference;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPasswordSuccess extends BaseActivity {
    @BindView(R.id.viewback_title) TextView title;
    @BindView(R.id.viewback_description) TextView description;
    @BindView(R.id.tv_katasandi_ygdikirim) TextView emailKatasandi;
    @BindView(R.id.tv_kirim_ulang) TextView suksesKirimUlang;

    private String email = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.success_forgot_password_activity);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        email = intent.getStringExtra("email");
        title.setText(R.string.lupakatasandi);
        description.setText(R.string.aturulangkatasandi);
        emailKatasandi.setText("Masukkan kata sandi yang kami kirimkan ke" + " " + email);
    }

    @OnClick(R.id.tdk_menerima_kata_sandi)
    public void kirimUlangnkatasandi(){
        // if sukses kirim ulang maka
        resetpassword(email);
    }

    private void resetpassword(final String email){
        showProgressBar();
        String token = WeplusSharedPreference.getToken(this);
        ResetPasswordQuery resetPasswordQuery = new ResetPasswordQuery();
        resetPasswordQuery.setEmail(email);
        resetPasswordQuery.setSource("android");
        Call<String> call = NetworkManager.getNetworkServiceWithHeader(this,token).resetPassword(resetPasswordQuery);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                hideProgressBar();
                Log.i("resetpass", response.body());
                if (response.isSuccessful()){
                    suksesKirimUlang.setVisibility(View.VISIBLE);
                } else {
                    showError("Unsuccess");
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                hideProgressBar();
                showError(t.getMessage());
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @OnClick(R.id.viewback_buttonback)
    public void actionBack(){finish();}

    @OnClick(R.id.btn_ke_hal_login)
    public void actionKeHalLogin(){ finish();}

}

package id.weplus.forgotpassword;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.weplus.BaseActivity;
import id.weplus.Query.ResetPasswordQuery;
import id.weplus.R;
import id.weplus.net.ErrorCode;
import id.weplus.net.NetworkManager;
import id.weplus.net.WeplusSharedPreference;
import id.weplus.utility.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPasswordActivity extends BaseActivity {
    private static String TAG = ForgotPasswordActivity.class.getSimpleName();
    @BindView(R.id.viewback_title) TextView title;
    @BindView(R.id.viewback_description) TextView description;
    @BindView(R.id.forgot_password_email) EditText forgotPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        ButterKnife.bind(this);
        title.setText(getString(R.string.lupakatasandi));
        description.setText(getString(R.string.aturulangkatasandianda));
    }

    @OnClick(R.id.forgotpassword_btn_lanjutkan)
    public void lanjutkanForgotPass(){
        boolean isNetworkAvailable = Util.isNetworkAvailable(this);
        String email = forgotPassword.getText().toString();
        boolean isEmailValid = Util.isEmailValid(email);
        if (isNetworkAvailable) {
            if (email.length() != 0 && isEmailValid) {
                resetpassword(email);

            } else {
                showError(getString(R.string.error_not_valid_email));
            }
        } else {
            showError(getString(R.string.network_error));
        }
    }

    private void resetpassword(final String email){
        showProgressBar();
        String token = WeplusSharedPreference.getToken(this);
        ResetPasswordQuery resetPasswordQuery = new ResetPasswordQuery();
        resetPasswordQuery.setEmail(email);
        resetPasswordQuery.setSource(getString(R.string.source));
        Call<String> call = NetworkManager.getNetworkServiceWithHeader(this,token).resetPassword(resetPasswordQuery);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                hideProgressBar();
                Log.i(TAG, response.body());
                if (response.isSuccessful()){
                    String data = response.body();
                    try {
                        JSONObject job = new JSONObject(data);
                        String code = job.getString("code");
                        String description = job.getString("message");
                        if (code.equals(ErrorCode.ERROR_00)){
                            Intent intent = new Intent(ForgotPasswordActivity.this, ForgotPasswordSuccess.class);
                            intent.putExtra("email", email);
                            startActivity(intent);
                            finish();
                        } else if (code.equals(ErrorCode.ERROR_01)){
                            showError(description);
                        }
                    } catch (Exception e){
                        Log.i(TAG, e.getMessage());
                    }

                } else {
                    showError("Unsuccess");
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                hideProgressBar();
                Log.i(TAG, t.getMessage());
            }
        });

    }

    @OnClick(R.id.viewback_buttonback)
    public void actionBackForgotPassword(){finish();}

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @OnClick(R.id.forgotpass_btn_to_login)
    public void backtoLogin(){
        finish();
    }
}

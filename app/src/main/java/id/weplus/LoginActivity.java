package id.weplus;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.weplus.Query.LoginQuery;
import id.weplus.forgotpassword.ForgotPasswordActivity;
import id.weplus.model.LoginData;
import id.weplus.model.LoginModel;
import id.weplus.model.request.UpdateFcmRequest;
import id.weplus.net.ErrorCode;
import id.weplus.net.NetworkManager;
import id.weplus.net.WeplusSharedPreference;
import id.weplus.utility.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends BaseActivity {
    private static String TAG = LoginActivity.class.getSimpleName();
    @BindView(R.id.viewback_title)
    TextView title;
    @BindView(R.id.viewback_description)
    TextView description;
    @BindView(R.id.email)
    EditText email;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.password_visible)
    ImageView invisible;
    private int passwordNotVisible = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        title.setText(getString(R.string.masuk));
        description.setText(getString(R.string.masukuntukmendapatkanproteksianda));
    }


    @OnClick(R.id.password_visible)
    public void setInvisiblePassword() {
        if (passwordNotVisible == 1) {
            password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            passwordNotVisible = 0;
            invisible.setImageResource(R.drawable.ic_baseline_visibility_24px);
        } else {
            password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            passwordNotVisible = 1;
            invisible.setImageResource(R.drawable.ic_baseline_visibility_off_24px);
        }
    }


    @OnClick(R.id.viewback_buttonback)
    public void actionLoginWelcome() {
        finish();
    }

    @OnClick(R.id.btn_login)
    public void actionLogin() {
        boolean isNetworkAvailable = Util.isNetworkAvailable(this);
        String email_v = email.getText().toString();
        String pass_v = password.getText().toString();
        boolean isEmailValid = Util.isEmailValid(email_v);
        if(email_v.isEmpty()){
            showError(this, "Masukkan email anda");
        }else{
            if(pass_v.isEmpty()){
                showError(this, "Masukkan password anda");
            }else{
                if (isNetworkAvailable) {
                    if (email_v.length() != 0 && pass_v.length() != 0) {
                        if (isEmailValid) {
                            login(email_v, pass_v);
                        } else {
                            showError(this, "Format email yang anda masukkan salah");
                        }
                    } else {
                        showError(this, getString(R.string.empty_field));
                    }
                } else {
                    showError(this, getString(R.string.network_error));
                }
            }
        }

    }


    private void login(String email, String pass) {
        showProgressBar();
        LoginQuery loginQuery = new LoginQuery();
        loginQuery.setEmail(email);
        loginQuery.setPassword(pass);

        Call<String> call = NetworkManager.getNetworkService(this).login(loginQuery);
        call.enqueue(new Callback<String>() {
            @SuppressLint("WrongViewCast")
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                hideProgressBar();
                if (response.isSuccessful()) {
                    Gson gson = new Gson();
                    LoginModel loginModel = gson.fromJson(response.body(), LoginModel.class);
                    LoginData loginData = loginModel.getData();
                    if (loginModel.getCode().equals(ErrorCode.ERROR_00)) {
                        WeplusSharedPreference.saveUser(getApplicationContext(), gson.toJson(loginData));
                        WeplusSharedPreference.saveRegister(getApplicationContext(), response.body());
                        WeplusSharedPreference.setLoggedIn(getApplicationContext(), true);

                        WeplusSharedPreference.setToken(LoginActivity.this, loginData.getApi_token());
                        if(loginData.getIs_confirm()) {
                            getFcmToken();
                        }else{
                            Intent newIntent =  new Intent(LoginActivity.this,
                                    VerifikasiOTP.class);
                            newIntent.putExtra("is_login",true);
                            startActivity(newIntent);
                            //finish();
                        }
                    } else if (loginModel.getCode().equals(ErrorCode.ERROR_09)) {
                        showError(LoginActivity.this, loginModel.getMessage());
                    } else {
                        showError(LoginActivity.this, loginModel.getMessage());
                    }
                } else {
                    showError(LoginActivity.this, getString(R.string.unsuccess));
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                hideProgressBar();
                Log.i(TAG, "ON FAILUR : " + t.getMessage());
            }
        });
    }

    private void getFcmToken() {
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                        return;
                    }

                    // Get new FCM registration token
                    String token = task.getResult();
                    sendFcmTokenToServer(token);
                    // Log and toast
                    //String msg = getString(R.string.msg_token_fmt, token);
                    Log.d(TAG, token);
                    //
                    // Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                });
    }

    private void sendFcmTokenToServer(String fcmToken) {
        String token = WeplusSharedPreference.getToken(this);
        Call<String> call =
                NetworkManager.getNetworkServiceWithHeader(this, token)
                        .updateFcmToken(new UpdateFcmRequest(fcmToken));
        call.enqueue(new Callback<String>() {
            @SuppressLint("WrongViewCast")
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                hideProgressBar();
                if (response.isSuccessful()) {
                    Gson gson = new Gson();
                    LoginModel loginModel = gson.fromJson(response.body(), LoginModel.class);
                    if (loginModel.getCode().equals(ErrorCode.ERROR_00)) {
                        Intent newIntent =  new Intent(LoginActivity.this,
                                MainActivity.class);
                        //newIntent.putExtra("is_login",true);
                        startActivity(newIntent);
                        finish();
                    } else if (loginModel.getCode().equals(ErrorCode.ERROR_09)) {
                        showError(LoginActivity.this, loginModel.getMessage());
                    } else {
                        showError(LoginActivity.this, loginModel.getMessage());
                    }
                } else {
                    showError(LoginActivity.this, getString(R.string.unsuccess));
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                hideProgressBar();
                Log.i(TAG, "ON FAILUR : " + t.getMessage());
            }
        });
    }


    @OnClick(R.id.btn_lupa_katasandi)
    public void actionLupaKataSandi() {
        Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}

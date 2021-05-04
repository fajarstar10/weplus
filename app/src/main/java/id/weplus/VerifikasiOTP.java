package id.weplus;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import androidx.appcompat.widget.AppCompatButton;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.Request;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.mukesh.OnOtpCompletionListener;
import com.mukesh.OtpView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.weplus.Query.OtpQuery;
import id.weplus.model.LoginModel;
import id.weplus.model.OTPModel;
import id.weplus.model.RegisterModel;
import id.weplus.model.request.UpdateFcmRequest;
import id.weplus.net.ErrorCode;
import id.weplus.net.Facad;
import id.weplus.net.NetworkManager;
import id.weplus.net.VolleyCallback;
import id.weplus.net.VolleyErrorCallback;
import id.weplus.net.WeplusConfig;
import id.weplus.net.WeplusSharedPreference;
import id.weplus.profil.UbahProfil;
import id.weplus.utility.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerifikasiOTP extends BaseActivity implements OnOtpCompletionListener {
    private static String TAG = VerifikasiOTP.class.getSimpleName();
    private OtpView inputOtp;
    @BindView(R.id.btn_konfirmasi) AppCompatButton btnKonfirmasi;
    @BindView(R.id.verifikasiotp_wrong_code) TextView kodeSalah;
    @BindView(R.id.timer) TextView timer;
    @BindView(R.id.viewback_title) TextView title;
    @BindView(R.id.viewback_description) TextView description;
    private String token = "";
    private LoginModel registerUser;
    private RegisterModel.RegisterData dataRegister;
    private String otp_v = "";
    private boolean isRegis=false;
    private boolean isLogin=false;

    private final TextWatcher otpTextWatcher = new TextWatcher() {
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            btnKonfirmasi.setBackground(getResources().getDrawable(R.drawable.border_grey_medium));
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        public void afterTextChanged(Editable s) {
            if (s.length() >= 4) {

                btnKonfirmasi.setBackground(getResources().getDrawable(R.drawable.border_red));
                btnKonfirmasi.setTextColor(getResources().getColor(R.color.white));
            } else{
                btnKonfirmasi.setBackground(getResources().getDrawable(R.drawable.border_grey_medium));
            }
        }

    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verifikasi_otp);
        ButterKnife.bind(this);

        token = WeplusSharedPreference.getToken(this);

        String user = WeplusSharedPreference.getUser(this);
        Gson gson = new Gson();
        Log.d("user","user json : "+user);
        /**
         *  ------ Fixed code----------
         */

        registerUser= gson.fromJson(user, LoginModel.class);
        /**
         * crash code below
         * penyebab crash adalah hasil dari gson.fromJson adalah null
         * dikarenakan user yang didapat melalui line 77 mengembalikan empty string ""
         * bukan json string{"token":"asdf"}.
         *
         * setelah ditelusuri penyebabnya adalah pada class SignUp.Java
         * response dari server yang dihandle pada line 114,
         * hanya terdapat code untuk menyimpan token saja,
         * mamun tidak ada code untuk menyimpang user dari registerModel.
         * oleh karena itu saya tambahkan line 117.
         *
         * code yang menyebabkan error saya comment dibawah ini
         */
//        RegisterModel registerModel = gson.fromJson(user, RegisterModel.class);
//        dataRegister = registerModel.getData();
//        registerUser = dataRegister.getUser();
        isRegis = getIntent().getBooleanExtra("is_regis",false);
        isLogin = getIntent().getBooleanExtra("is_login",false);
        title.setText(getString(R.string.verifikasi));
        description.setText(getString(R.string.menunggukodeotpygsedangdikirim));
        inputOtp = (OtpView) findViewById(R.id.otp_input_code_otp);
        inputOtp.setOtpCompletionListener(this);
        inputOtp.setText("" + otp_v);
        inputOtp.addTextChangedListener(otpTextWatcher);

        new CountDownTimer(300000, 1000) {

            public void onTick(long millisUntilFinished) {

                long secondsInMilli = 1000;
                long minutesInMilli = secondsInMilli * 60;

                long elapsedMinutes = millisUntilFinished / minutesInMilli;
                millisUntilFinished = millisUntilFinished % minutesInMilli;

                long elapsedSeconds = millisUntilFinished / secondsInMilli;
                String yy = String.format("%02d:%02d", elapsedMinutes, elapsedSeconds);

                timer.setText("" + yy);
            }

            public void onFinish() {
                timer.setText("00:00");
            }
        }.start();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @OnClick(R.id.viewback_buttonback)
    public void actionback(){finish();}

    @OnClick(R.id.btn_konfirmasi)
    public void actionKonfirmasi(){
        String otp_value = inputOtp.getText().toString();
        Log.i(TAG, "vale otp :  " + otp_value);
        cekOtp(otp_value);
    }

    @OnClick(R.id.verifikasiotp_tidak_menerima_kode)
    public void tidakMenerimaKode(){
        resetOtp();
    }

    @Override
    public void onOtpCompleted(String otp) {

    }

    private void cekOtp(String otp_value){
        boolean isNetworkAvailable = Util.isNetworkAvailable(this);
        if (isNetworkAvailable) {
            showProgressBar();
            OtpQuery otpQuery = new OtpQuery();
            otpQuery.setOtp(otp_value);
            if (otp_value.length() != 0) {

                Log.e("token otp", token);

                Call<String> call = NetworkManager.getNetworkServiceWithHeader(this, token).cekOtp(otpQuery);

                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        hideProgressBar();
                        Log.i(TAG, "response 123 : " + response.body());
                        Gson gson = new Gson();
                        ResponseGagal responseOTP = gson.fromJson(response.body(), ResponseGagal.class);
                        if (responseOTP.getCode().endsWith(ErrorCode.ERROR_00)){
                            //WeplusSharedPreference.saveUser(getApplicationContext(),response.body());
                            getFcmToken();
                        } else if (responseOTP.getCode().endsWith(ErrorCode.ERROR_01)){
                            showError(VerifikasiOTP.this,responseOTP.getMessage());
                        } else {
                            showError(VerifikasiOTP.this,responseOTP.getMessage());
                        }

                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        hideProgressBar();
                        Log.i(TAG, "ON FAILUR : " + t.getMessage());
                    }
                });
            } else {
                showError(getString(R.string.empty_field));
            }
        } else {
            showError(getString(R.string.network_error));
        }
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
                        if(isRegis) {
                            Intent intent = new Intent(VerifikasiOTP.this, MainActivity.class);
                            startActivity(intent);
                        }else if(isLogin){
                            Intent intent = new Intent(VerifikasiOTP.this, MainActivity.class);
                            startActivity(intent);
                        }else{
                            Intent intent = new Intent(VerifikasiOTP.this, UbahProfil.class);
                            startActivity(intent);
                        }
                        finish();
                    } else if (loginModel.getCode().equals(ErrorCode.ERROR_09)) {
                        showError(VerifikasiOTP.this, loginModel.getMessage());
                    } else {
                        showError(VerifikasiOTP.this, loginModel.getMessage());
                    }
                } else {
                    showError(VerifikasiOTP.this, getString(R.string.unsuccess));
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                hideProgressBar();
                Log.i(TAG, "ON FAILUR : " + t.getMessage());
            }
        });
    }

    private void resetOtp(){
        boolean isNetworkAvailable = Util.isNetworkAvailable(this);
        if (isNetworkAvailable) {
            showProgressBar();
            Facad.getInstance(this).getRequestWithToken(WeplusConfig.DOMAIN_URL + "otp/reset/", Request.Method.POST, token, new VolleyCallback() {
                @Override
                public void onSuccessResponse(String result) {
                    hideProgressBar();
                    Log.i("resetotp", result);
                    Gson gson = new Gson();
                    OTPModel otpModel = gson.fromJson(result, OTPModel.class);

                    if (otpModel.getCode().equals(ErrorCode.ERROR_00)) {
                        otp_v = otpModel.getData().getOtp();
                        inputOtp.setText("" + otp_v);


                    } else {
                        showError(otpModel.getMessage());
                    }
                }
            }, new VolleyErrorCallback() {
                @Override
                public void onFailedResponse(int error) {
                    hideProgressBar();

                }
            });
        } else {
            showError(getString(R.string.network_error));
        }

    }
}

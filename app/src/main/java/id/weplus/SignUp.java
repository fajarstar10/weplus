package id.weplus;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.weplus.Query.RegisterQuery;
import id.weplus.model.LoginModel;
import id.weplus.model.RegisterModel;
import id.weplus.net.ErrorCode;
import id.weplus.net.NetworkManager;
import id.weplus.net.WeplusSharedPreference;
import id.weplus.utility.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUp extends BaseActivity {
    private static String TAG = SignUp.class.getSimpleName();
    @BindView(R.id.viewback_title) TextView title;
    @BindView(R.id.viewback_description) TextView description;
    @BindView(R.id.signup_nama_lengkap) EditText namaLengkap;
    @BindView(R.id.signup_email) EditText email;
    @BindView(R.id.signup_password) EditText password;
    @BindView(R.id.signup_password_visible) ImageView invisible;
    @BindView(R.id.signup_nomor_telepon) EditText nomorTelepon;
    private int passwordNotVisible = 1;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);
        title.setText(getString(R.string.daftar));
        description.setText("Daftar akun anda dan mulai terproteksi");
    }

    @OnClick(R.id.signup_password_visible)
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
    public void actionBackDaftar() {
        finish();
    }

    @OnClick(R.id.signup_btn_daftar)
    public void actionDaftar() {
        String name_v = namaLengkap.getText().toString();
        String email_v = email.getText().toString();
        String pass_v = password.getText().toString();
        String noTelp_v = nomorTelepon.getText().toString();
        boolean isEmailValid = Util.isEmailValid(email_v);
        boolean isNetworkAvailable = Util.isNetworkAvailable(this);
        Log.d("sign-up","length : name : "+name_v.length());
        Log.d("sign-up","length : email : "+email_v.length());
        Log.d("sign-up","length : pass : "+pass_v.length());
        Log.d("sign-up","length : noTelp : "+noTelp_v.length());
        if (isNetworkAvailable) {
            if (name_v.length() != 0 && email_v.length() != 0 && pass_v.length() != 0 && noTelp_v.length() != 0) {
                if (isEmailValid) {
                    register(name_v, email_v, pass_v, noTelp_v);
                } else {
                    showError(this,getString(R.string.error_not_valid_email));
                }
            } else {
                showError(this,getString(R.string.empty_field));
            }
        } else {
            showError(this,getString(R.string.network_error));
        }

    }

    @OnClick(R.id.signup_syarat_ketentuan)
    public void syaratdanketentuan() {
        showError(this,"Coming Soon");
    }

    private void register(String name, String email, String pass, String notel) {
        showProgressBar();
        RegisterQuery registerQuery = new RegisterQuery();
        registerQuery.setEmail(email);
        registerQuery.setFullname(name);
        registerQuery.setPassword(pass);
        registerQuery.setPhone(notel);

        Log.i(TAG, "Query : " + registerQuery.toString());
        Call<String> call = NetworkManager.getNetworkService(this).register(registerQuery);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                hideProgressBar();
                Log.i(TAG, "response 123 : " + response);
                Gson gson = new Gson();
                RegisterModel registerModel = gson.fromJson(response.body(), RegisterModel.class);
                if (registerModel!=null && registerModel.getCode().equals(ErrorCode.ERROR_00)) {
                    WeplusSharedPreference.setToken(getApplicationContext(), registerModel.getData().getApi_token());
                    // added line 117 to fix app crash on otp verification
                    WeplusSharedPreference.saveUser(getApplicationContext(),gson.toJson(registerModel.getData()));
                    Intent intent = new Intent(SignUp.this, VerifikasiOTP.class);
                    intent.putExtra("is_regis",true);
                    startActivity(intent);
                    finish();
                } else if (registerModel!=null && registerModel.getCode().equals(ErrorCode.ERROR_01)) {
                    showError(SignUp.this,registerModel.getMessage());
                } else {
                    showError(SignUp.this,"Gagal terhubung ke server");
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                showError(SignUp.this,getString(R.string.timeout));
                Log.i(TAG, "ON FAILUR : " + t.getMessage());
            }
        });
    }

}

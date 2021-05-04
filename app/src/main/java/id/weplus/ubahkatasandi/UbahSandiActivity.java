package id.weplus.ubahkatasandi;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.weplus.BaseActivity;
import id.weplus.R;
import id.weplus.Tagihan.BayarTagihanListrikActivity;
import id.weplus.WelcomeActivity;
import id.weplus.belipolis.PaymentInfoActivity;
import id.weplus.model.request.ChangePasswordRequest;
import id.weplus.model.response.TransactionDetailResponse;
import id.weplus.net.ErrorCode;
import id.weplus.net.NetworkManager;
import id.weplus.net.WeplusSharedPreference;
import id.weplus.pembayaran.PembayaranActivity;
import id.weplus.utility.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UbahSandiActivity extends BaseActivity {
    @BindView(R.id.viewback_title_no_desc) TextView title;
    @BindView(R.id.ubahsandi_sandi_skrg) EditText sandiSekarang;
    @BindView(R.id.ubahsandi_sandi_baru) EditText sandiBaru;
    @BindView(R.id.ubahsandi_ulangi_sandi_baru) EditText ulangiSandiBaru;

    @BindView(R.id.ubahsandi_til_sandi_skrg) TextInputLayout wrapperSandiSkrg;
    @BindView(R.id.ubahsandi_til_sandi_baru) TextInputLayout wrapperSandiBaru;
    @BindView(R.id.ubahsandi_til_ulangi_sandi_baru) TextInputLayout wrapperUlangisandibaru;

    private String TAG="UbahSandi";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah_sandi);
        ButterKnife.bind(this);
        title.setText(getString(R.string.ubahkatasandi));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @OnClick(R.id.viewback_buttonback_no_desc)
    public void back(){finish();}

    @OnClick(R.id.ubahsandi_btn_simpan)
    public void Simpan(){
        String sandi_baru_v = sandiBaru.getText().toString();
        String ulangi_sandi_baru_v = ulangiSandiBaru.getText().toString();
        if (!sandi_baru_v.equals(ulangi_sandi_baru_v)){
            wrapperSandiBaru.setError(getString(R.string.erorkatasandiidaksesuai));
            wrapperUlangisandibaru.setError(getString(R.string.erorkatasandiidaksesuai));
        } else{
            wrapperSandiBaru.setErrorEnabled(false);
            wrapperUlangisandibaru.setErrorEnabled(false);
            changePassword(sandiSekarang.getText().toString(),sandi_baru_v,ulangi_sandi_baru_v);
        }
    }

    private void changePassword(String oldPassword,String newPassword,String confirmNewPassword){
        boolean isNetworkAvailable = Util.isNetworkAvailable(this);
        if (isNetworkAvailable){
            ChangePasswordRequest request = new ChangePasswordRequest(oldPassword,newPassword,confirmNewPassword);
            String token = WeplusSharedPreference.getToken(this);
            Call<String> call = NetworkManager
                    .getNetworkServiceWithHeader(this, token)
                    .changePassword(request);
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    Gson gson = new Gson();
                    TransactionDetailResponse resp = gson.fromJson(response.body(), TransactionDetailResponse.class);
                    try {
                        if (resp.getCode().equals(ErrorCode.ERROR_00)){
                            Toast.makeText(UbahSandiActivity.this,"Password berhasil diubah",Toast.LENGTH_LONG).show();
                            finish();
                        } else if(resp.getCode().equals(ErrorCode.ERROR_03)){
                            Intent intent = new Intent(UbahSandiActivity.this, WelcomeActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }else {
                            Log.d(TAG,"error woi "+resp.getCode());
                            new SweetAlertDialog(UbahSandiActivity.this, SweetAlertDialog.NORMAL_TYPE)
                                    .setTitleText("")
                                    .setContentText(resp.getMessage())
                                    .show();
                        }
                    }
                    catch (Exception e) {
                        Log.i(TAG,"woi: "+e.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    new SweetAlertDialog(UbahSandiActivity.this, SweetAlertDialog.NORMAL_TYPE)
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

    private void hideKeyboard() {
        View view = getCurrentFocus();
        if (view != null) {
            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).
                    hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public boolean validatePassword(String password) {
        return password.length() > 5;
    }
}

package id.weplus.profil;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.weplus.BaseActivity;
import id.weplus.R;
import id.weplus.SignUp;
import id.weplus.Tagihan.BayarTagihanListrikActivity;
import id.weplus.Tagihan.TagihanListrikActivity;
import id.weplus.VerifikasiOTP;
import id.weplus.WelcomeActivity;
import id.weplus.model.LoginData;
import id.weplus.model.RegisterModel;
import id.weplus.model.request.ChangePhoneRequest;
import id.weplus.model.response.BillInquiryResponse;
import id.weplus.model.response.ChangePhoneResponse;
import id.weplus.net.ErrorCode;
import id.weplus.net.NetworkManager;
import id.weplus.net.WeplusSharedPreference;
import id.weplus.pembayaran.PembayaranActivity;
import id.weplus.utility.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UbahNomorTelepon extends BaseActivity {
    @BindView(R.id.viewback_title_no_desc) TextView title;
    @BindView(R.id.ubahnomortelepon_code_negara) Spinner codeNegara;
    @BindView(R.id.ubahnomortelepon_nomor_telepon) EditText nomorTelepon;
    @BindView(R.id.ubahnomortelepon_btn_simpan) Button buttonSubmit;
    @BindView(R.id.loadingWrapper) RelativeLayout loaderWrapper;

    private LoginData user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah_nomor_telepon);
        ButterKnife.bind(this);
        title.setText(getString(R.string.ubahnomortelepon));
        getUser();
        String phone = user.getPhone().replaceFirst("^0+(?!$)", "");
        if(phone.contains("+")){
            phone = phone.substring(3);
        }
        nomorTelepon.setText(phone);
        List<String> list = new ArrayList<String>();
        list.add("+62");
        list.add("+70");
        list.add("+86");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        codeNegara.setAdapter(dataAdapter);
        codeNegara.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                ((TextView) parentView.getChildAt(0)).setTextColor(Color.GRAY);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    private void getUser(){
        String jsonResponse = WeplusSharedPreference.getUser(this);
        Gson gson = new Gson();
        user = gson.fromJson(jsonResponse, LoginData.class);
        Log.d("user","user phone : "+user.getPhone());
    }

    @OnClick(R.id.ubahnomortelepon_btn_simpan)
    public void actionSimpanNomorTelepon(){
        String code_negara = codeNegara.getSelectedItem().toString();
        String nomor_telp = nomorTelepon.getText().toString().replaceFirst("^0+(?!$)", "");
        if (code_negara.length() !=0 && nomor_telp.length() != 0){
            String phoneNumber = code_negara+nomor_telp;
            sendChangePhoneRequest(phoneNumber);
        }
        //finish();
    }

    @OnClick(R.id.viewback_buttonback_no_desc)
    public void backBtn(){finish();}

    private void sendChangePhoneRequest(String phoneNumber){

        boolean isNetworkAvailable = Util.isNetworkAvailable(this);
        if (isNetworkAvailable){
            buttonSubmit.setVisibility(View.INVISIBLE);
            loaderWrapper.setVisibility(View.VISIBLE);
            String token = WeplusSharedPreference.getToken(this);
            Call<String> call = NetworkManager
                    .getNetworkServiceWithHeader(this, token)
                    .changePhone(new ChangePhoneRequest(phoneNumber));
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    buttonSubmit.setVisibility(View.VISIBLE);
                    loaderWrapper.setVisibility(View.GONE);
                    Gson gson = new Gson();
                    ChangePhoneResponse resp = gson.fromJson(response.body(), ChangePhoneResponse.class);
                    WeplusSharedPreference.saveUser(getApplicationContext(),gson.toJson(resp.getData()));
                    try {
                        if (resp.getCode().equals(ErrorCode.ERROR_00)){
                            new SweetAlertDialog(UbahNomorTelepon.this, SweetAlertDialog.SUCCESS_TYPE)
                                    .setTitleText("No telp berhasil dirubah")
                                    .setContentText(resp.getMessage())
                                    .setConfirmText("Verifikasi no telp anda")
                                    .setConfirmClickListener(sweetAlertDialog -> {
                                        Intent intent = new Intent(UbahNomorTelepon.this, VerifikasiOTP.class);
                                        intent.putExtra("is_regis",false);
                                        startActivity(intent);
                                        finish();
                                    })
                                    .show();

                        } else if(resp.getCode().equals(ErrorCode.ERROR_03)){
                            Intent intent = new Intent(UbahNomorTelepon.this, WelcomeActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }else {
                            new SweetAlertDialog(UbahNomorTelepon.this, SweetAlertDialog.NORMAL_TYPE)
                                    .setTitleText("")
                                    .setContentText(resp.getMessage())
                                    .show();
                        }
                    }
                    catch (Exception e) {
                        buttonSubmit.setVisibility(View.VISIBLE);
                        loaderWrapper.setVisibility(View.GONE);
                        new SweetAlertDialog(UbahNomorTelepon.this, SweetAlertDialog.NORMAL_TYPE)
                                .setTitleText(e.getMessage())
                                .setContentText(resp.getMessage())
                                .show();
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    buttonSubmit.setVisibility(View.VISIBLE);
                    loaderWrapper.setVisibility(View.GONE);
                    new SweetAlertDialog(UbahNomorTelepon.this, SweetAlertDialog.NORMAL_TYPE)
                            .setTitleText("")
                            .setContentText("Time Out")
                            .show();
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

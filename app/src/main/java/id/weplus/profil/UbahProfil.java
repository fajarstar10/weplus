package id.weplus.profil;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.weplus.BaseActivity;
import id.weplus.R;
import id.weplus.Tagihan.BayarTagihanListrikActivity;
import id.weplus.WelcomeActivity;
import id.weplus.helper.GetRealPathUtil;
import id.weplus.model.LoginData;
import id.weplus.model.response.UploadImageResponse;
import id.weplus.net.ErrorCode;
import id.weplus.net.NetworkManager;
import id.weplus.net.WeplusSharedPreference;
import id.weplus.utility.Util;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static id.weplus.net.WeplusConfig.DOMAIN_URL;

public class UbahProfil extends BaseActivity {
    private int RESULT_LOAD_IMG = 1;
    @BindView(R.id.ubahprofil_img_profil) ImageView ic_profil;
    @BindView(R.id.ubahprofil_nama) TextView namaLengkap;
    @BindView(R.id.ubahprofil_alamat_email) TextView email;
    @BindView(R.id.ubahprofil_nomor_telepon) TextView nomorTelp;
    @BindView(R.id.progressBar) ProgressBar progressBar;
    private LoginData user;
    private Bitmap userPicture;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah_profil);
        ButterKnife.bind(this);
        getUserData();
        populateView();
    }

    private void getUserData(){
        String json_response = WeplusSharedPreference.getUser(this);
        Gson gson = new Gson();
        user = gson.fromJson(json_response, LoginData.class);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getUserData();
        if(namaLengkap!=null){
            populateView();
        }
    }

    private void populateView(){
        namaLengkap.setText(user.getName());
        email.setText(user.getEmail());
        nomorTelp.setText(user.getPhone());
        Glide.with(this).load(user.getPicture()).into(ic_profil);
    }

    @OnClick(R.id.ubahprofil_img_profil)
    public void ubahImgProfil(){
        takephoto();
    }

    @OnClick(R.id.ubahprofil_ic_plus)
    public void ubahIconProfil(){
        takephoto();
    }

    @OnClick(R.id.ubahprofil_btn_batal)
    public void actionBatal(){finish();}

    //Simpan profil
    @OnClick(R.id.ubahprofil_btn_simpan)
    public void actionSimpan(){
        String nama_lengkap = namaLengkap.getText().toString();
        String email_v = email.getText().toString();
        String nomor_telp = nomorTelp.getText().toString();
        finish();
    }

    @OnClick(R.id.ubahprofil_btn_ubah_nama_lengkap)
    public void actionUbahNamaLengkap(){
        Intent ubahNamaLengkap = new Intent(UbahProfil.this, UbahNamaLengkap.class);
        startActivity(ubahNamaLengkap);
    }

    @OnClick(R.id.ubahprofil_btn_ubah_alamat_email)
    public void actionUbahEmail(){
        Intent ubahAlamatEmail = new Intent(UbahProfil.this, UbahAlamatEmail.class);
        startActivity(ubahAlamatEmail);
    }

    @OnClick(R.id.ubahprofil_btn_ubah_nomor_telp)
    public void actionUbahNomorTelepon(){
        Intent ubahNomorTelepon = new Intent(UbahProfil.this, UbahNomorTelepon.class);
        startActivity(ubahNomorTelepon);
    }

    private void takephoto(){
        Dexter.withContext(this)
                .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override public void onPermissionGranted(PermissionGrantedResponse response) {/*
                             ... */
                        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                        photoPickerIntent.setType("image/*");
                        startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG);
                    }
                    @Override public void onPermissionDenied(PermissionDeniedResponse response) {/* ... */}
                    @Override public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {/* ... */}
                }).check();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                userPicture = BitmapFactory.decodeStream(imageStream);
                //ic_profil.setImageBitmap(selectedImage);
                onFilePicked(GetRealPathUtil.getRealPathFromURI(UbahProfil.this,imageUri));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
            }

        }else {
            Toast.makeText(this, "You haven't picked Image",Toast.LENGTH_LONG).show();
        }
    }

    private void onFilePicked(String filePath){
        File file = new File(filePath);
        long fileSizeInBytes = file.length();
        // Convert the bytes to Kilobytes (1 KB = 1024 Bytes)
        long fileSizeInKB = fileSizeInBytes / 1024;
        //  Convert the KB to MegaBytes (1 MB = 1024 KBytes)
        long fileSizeInMB = fileSizeInKB / 1024;

        if(fileSizeInMB>1){
            new SweetAlertDialog(UbahProfil.this, SweetAlertDialog.NORMAL_TYPE)
                    .setTitleText("")
                    .setContentText("Ukuran gambar lebih besar dari 1 MB")
                    .show();
        }else {
            HashMap<String, RequestBody> map = new HashMap<>();
            map.put("type", createPartFromString("profile"));

            RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
            MultipartBody.Part body = MultipartBody.Part.createFormData("image", file.getName(), reqFile);

            sendUploadImageRequest(body, map);
        }
    }

    private void sendUploadImageRequest(MultipartBody.Part image, Map<String,RequestBody> requestBody){
        boolean isNetworkAvailable = Util.isNetworkAvailable(this);
        if (isNetworkAvailable){
            progressBar.setVisibility(View.VISIBLE);
            String token = WeplusSharedPreference.getToken(this);
            Call<String> call = NetworkManager
                    .getNetworkServiceWithHeader(this, token)
                    .uploadImage(image,requestBody);
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    Gson gson = new Gson();
                    UploadImageResponse resp = gson.fromJson(response.body(), UploadImageResponse.class);
                    try {
                        progressBar.setVisibility(View.GONE);
                        if (resp.getCode().equals(ErrorCode.ERROR_00)){
                            user.setPicture(resp.getData().getUrl());
                            new SweetAlertDialog(UbahProfil.this, SweetAlertDialog.SUCCESS_TYPE)
                                    .setTitleText("Sukses")
                                    .setContentText(resp.getMessage())
                                    .setConfirmText("Profil telah berhasil diperbarui")
                                    .setConfirmClickListener(sweetAlertDialog ->{
                                                WeplusSharedPreference.saveUser(getApplicationContext(),gson.toJson(user));
                                                sweetAlertDialog.dismiss();
                                            }
                                    )
                                    .show();
                            if(userPicture!=null){
                                ic_profil.setImageBitmap(userPicture);
                            }
                        } else if(resp.getCode().equals(ErrorCode.ERROR_03)){
                            Intent intent = new Intent(UbahProfil.this, WelcomeActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }else {
                            new SweetAlertDialog(UbahProfil.this, SweetAlertDialog.NORMAL_TYPE)
                                    .setTitleText("")
                                    .setContentText(resp.getMessage())
                                    .show();
                        }
                    }
                    catch (Exception e) {
                        progressBar.setVisibility(View.GONE);
                        Log.i("test","asu: "+e.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                   progressBar.setVisibility(View.GONE);
                    new SweetAlertDialog(UbahProfil.this, SweetAlertDialog.NORMAL_TYPE)
                            .setTitleText("")
                            .setContentText("Time Out")
                            .show();

                    Log.i("test","ON FAILURE : " + t.getMessage());
                }
            });
        } else {
            new SweetAlertDialog(this, SweetAlertDialog.NORMAL_TYPE)
                    .setTitleText(" ")
                    .setContentText(getString(R.string.network_error))
                    .show();
        }
    }

    @NonNull
    private RequestBody createPartFromString(String descriptionString) {
        return RequestBody.create(
                okhttp3.MultipartBody.FORM, descriptionString);
    }




}

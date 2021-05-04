package id.weplus;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

public class BaseActivity extends AppCompatActivity {
    private ProgressDialog dialog;
    private SweetAlertDialog pDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void showLoading(){
        dialog = new ProgressDialog(this);
        dialog.setMessage("Please wait...");
        dialog.setCancelable(false);
        dialog.show();
    }
    public void hideLoading(){
        if (this.dialog != null && this.dialog.isShowing()) {
            this.dialog.dismiss();
        }
    }

    public void showProgressBar(){
        pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#ff37474f"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);
        //new Handler().postDelayed(() -> pDialog.show(), 100L);
        pDialog.show();
    }

    public void hideProgressBar(){
        pDialog.dismiss();
    }

    public void showError(String error){
        new SweetAlertDialog(this.getApplicationContext(), SweetAlertDialog.NORMAL_TYPE)
                .setTitleText("")
                .setContentText(error)
                .show();
    }

    public void showError(Activity act,String error){
        new SweetAlertDialog(act, SweetAlertDialog.NORMAL_TYPE)
                .setTitleText("")
                .setContentText(error)
                .show();
    }

    public void showSukses(String message){
        new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText("Transaksi Berhasil")
                .setContentText(message)
                .show();
    }

    public void showSukses(Activity act,String message){
        new SweetAlertDialog(act, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText("Transaksi Berhasil")
                .setContentText(message)
                .show();
    }


}

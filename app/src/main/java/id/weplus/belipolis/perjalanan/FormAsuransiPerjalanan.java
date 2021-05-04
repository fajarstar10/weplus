package id.weplus.belipolis.perjalanan;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.weplus.BaseActivity;
import id.weplus.R;
//import id.weplus.belipolis.motor.FormDataMotorActivity;
//import id.weplus.belipolis.motor.FormDataMotorGeneralActivity;
import id.weplus.pembayaran.PembayaranActivity;

public class FormAsuransiPerjalanan extends BaseActivity {
    @BindView(R.id.viewback_title)
    TextView title;
    @BindView(R.id.viewback_description)
    TextView description;

    //  asuransi perjalanan yg dibeli
    @BindView(R.id.perjalanan_icon_asuransi)
    ImageView iconAsuransiPerjalanan;
    @BindView(R.id.perjalanan_title_asuransi)
    TextView titleAsuransiPerjalanan;
    @BindView(R.id.perjalanan_price_asuransi)
    TextView priceAsuransiPerjalanan;

    // data pemesan
    @BindView(R.id.perjalanan_nama_pemesan)
    TextView namaPemesanPerjalanan;
    @BindView(R.id.perjalanan_email_pemesan)
    TextView emailPemesanPerjalanan;
    @BindView(R.id.perjalanan_telp_pemesan)
    TextView notelpPemesanPerjalanan;

    //data tertanggung
    @BindView(R.id.perjalanan_data_tertanggung)
    TextView dataTertanggung;
    @BindView(R.id.perjalanan_edit_data_tertanggung)
    ImageView dataTertanggungEditBtn;
    private boolean on = false;

    // data tertanggung 2
    @BindView(R.id.perjalanan_data_tertanggung_2)
    TextView datatertanggung2;
    @BindView(R.id.perjalanan_data_tertanggung_2_btn_edit)
    ImageView dataTertanggung2EditBtn;

    // data anak ke 1
    @BindView(R.id.perjalanan_data_anak_1)
    TextView anak1;
    @BindView(R.id.perjalanan_data_anak_1_btn_edit)
    ImageView anak1EditBtn;

    //data anak ke 2
    @BindView(R.id.perjalanan_data_anak_2)
    TextView anak2;
    @BindView(R.id.perjalanan_data_anak_2_btn_edit)
    ImageView anak2EditBtn;

    // data anak ke 3
    @BindView(R.id.perjalanan_data_anak_3)
    TextView anak3;
    @BindView(R.id.perjalanan_data_anak_3_btn_edit)
    ImageView anak3Editbtn;

    // data ahli waris
    @BindView(R.id.perjalanan_data_ahli_waris)
    TextView ahliWaris;
    @BindView(R.id.perjalanan_data_ahli_waris_btn_edit)
    ImageView ahliWarisEditBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_asuransi_perjalanan);
        ButterKnife.bind(this);

        title.setText(getString(R.string.beliasuransi));
        description.setText(getString(R.string.isidata2ygdiperlukan));
    }

    @OnClick(R.id.perjalanan_switch)// jika data sama dgn data pemesan
    public void actionSwitch() {
        //set data tertanggung sama dgn data pemesan
        if (on == true) {
            dataTertanggung.setText("Henry" + "\nhenry@weplus.id" + "\n08123456789");
            dataTertanggungEditBtn.setClickable(false);
            on = true;
        } else {
            dataTertanggung.setText("");
            dataTertanggungEditBtn.setClickable(true);
            on = false;
        }
    }

    @OnClick(R.id.btn_datatertanggung)
    public void editDataPemegangPolis(){
        Intent intent = new Intent(FormAsuransiPerjalanan.this, DataTertanggungActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.btn_datatertanggung2)
    public void editDatatertanggung(){
        Intent intent = new Intent(FormAsuransiPerjalanan.this, DataTertanggungActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.btn_dataanak1)
    public void editDataanak1(){
        Intent intent = new Intent(FormAsuransiPerjalanan.this, DataTertanggungActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.btn_dataanak2)
    public void editDataanak2(){
        Intent intent = new Intent(FormAsuransiPerjalanan.this, DataTertanggungActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.btn_dataanak3)
    public void editDataanak3(){
        Intent intent = new Intent(FormAsuransiPerjalanan.this, DataTertanggungActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.btn_ahliwaris)
    public void editDataahliwaris(){
        Intent intent = new Intent(FormAsuransiPerjalanan.this, DataTertanggungActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.viewback_buttonback)
    public void actionBack() {
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

//    @OnClick(R.id.perjalanan_btn_selanjutnya)
//    public void actionSelanjutnya() {
//        onCreateDialog(this);
//
//    }

    public Dialog onCreateDialog(Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // Get the layout inflater
        LayoutInflater inflater = this.getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(inflater.inflate(R.layout.view_dialog, null))
                // Add action buttons
                .setPositiveButton("lanjut", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                    }
                })
                .setNegativeButton("Periksa", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        return builder.create();
    }


    public void showAlertDialogButtonClicked(View view) {
        // create an alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // set the custom layout
        final View customLayout = getLayoutInflater().inflate(R.layout.view_dialog, null);
        builder.setView(customLayout);

        // add a button
//        builder.setPositiveButton("Lanjut", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                // send data from the AlertDialog to the Activity
//
//            }
//        });
//
//        builder.setNegativeButton("Periksa", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int id) {
//                dialog.cancel();
//            }
//        });
        // create and show the alert dialog
        final AlertDialog dialog = builder.create();
        dialog.show();

        TextView btnPeriksa = customLayout.findViewById(R.id.dialog_btn_periksa);
        btnPeriksa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    dialog.cancel();
            }
        });

        TextView btnLanjut = customLayout.findViewById(R.id.dialog_btn_lanjut);
        btnLanjut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent pembayaran = new Intent(FormAsuransiPerjalanan.this, PembayaranActivity.class);
                startActivity(pembayaran);
                dialog.dismiss();

            }
        });
    }
}

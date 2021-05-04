package id.weplus.belipolis;

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
import id.weplus.pembayaran.PembayaranActivity;
import id.weplus.pemegangpolis.FormPemegangPolisActivity;

public class FormDataNormalActivity extends BaseActivity {



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

    // data ahli waris
    @BindView(R.id.perjalanan_data_ahli_waris)
    TextView ahliWaris;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_data_normal);
        ButterKnife.bind(this);

        title.setText(getString(R.string.beliasuransi));
        description.setText(getString(R.string.isidata2ygdiperlukan));
    }

    @OnClick(R.id.perjalanan_switch)// jika data sama dgn data pemesan
    public void actionSwitch() {
        //set data tertanggung sama dgn data pemesan
        if (on == true) {
            dataTertanggung.setText("David" + "\ndavid@weplus.id" + "\n08123456789");
            dataTertanggungEditBtn.setClickable(false);
            on = true;
        } else {
            dataTertanggung.setText("");
            dataTertanggungEditBtn.setClickable(true);
            on = false;
        }
    }

    @OnClick(R.id.perjalanan_edit_data_tertanggung)
    public void actiondataTertanggung(){
        if (on == false){
            Intent intent = new Intent(FormDataNormalActivity.this, FormPemegangPolisActivity.class);
            startActivity(intent);
        }
    }

    @OnClick(R.id.perjalanan_data_ahli_waris_btn_edit)
    public void editDataAhliWaris(){
        Intent intent = new Intent(FormDataNormalActivity.this, FormAhliWarisActivity.class);
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


    public void showAlertDialogSelanjutnyaButtonClicked(View view) {
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

                Intent pembayaran = new Intent(FormDataNormalActivity.this, PembayaranActivity.class);
                startActivity(pembayaran);
                dialog.dismiss();

            }
        });
    }
}

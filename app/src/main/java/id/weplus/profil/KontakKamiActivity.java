package id.weplus.profil;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;

import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.weplus.BaseActivity;
import id.weplus.R;

public class KontakKamiActivity extends BaseActivity {
    @BindView(R.id.viewback_title_no_desc)
    TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kontak_kami);
        ButterKnife.bind(this);
        title.setText("Kontak Kami");
    }

    @OnClick(R.id.pemegangpolis_name)
    public void kirimEmail() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // set the custom layout
        final View customLayout = getLayoutInflater().inflate(R.layout.view_dialog_kirim_email, null);
        builder.setView(customLayout);

        final AlertDialog dialog = builder.create();
        dialog.show();

        TextView btnBatal = customLayout.findViewById(R.id.dialog_btn_batal);
        TextView btnYa = customLayout.findViewById(R.id.dialog_btn_ya);
        btnBatal.setOnClickListener(v -> dialog.cancel());
        btnYa.setOnClickListener(v -> {
            sendEmail();
            dialog.dismiss();
        });
    }

    private void sendEmail() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("plain/text");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"tami@weplus.id"});
        startActivity(Intent.createChooser(intent, ""));
    }

    @OnClick(R.id.relativ_mulai_cat)
    public void actionChat() {
        Intent chat = new Intent(KontakKamiActivity.this, ChatActivity.class);
        startActivity(chat);
    }

    @OnClick(R.id.pemegangpolis_hubungi)
    public void actionCallPolis() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // set the custom layout
        final View customLayout = getLayoutInflater().inflate(R.layout.view_dialog_call, null);
        builder.setView(customLayout);

        final AlertDialog dialog = builder.create();
        dialog.show();

        TextView btnBatal = customLayout.findViewById(R.id.dialog_btn_batal);
        TextView btnYa = customLayout.findViewById(R.id.dialog_btn_ya);
        btnBatal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        btnYa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:" + Uri.encode("02127899680".trim())));
                callIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(callIntent);
            }
        });
    }

    @OnClick(R.id.viewback_buttonback_no_desc)
    public void back() {
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}

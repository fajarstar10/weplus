package id.weplus.profil;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.weplus.BaseActivity;
import id.weplus.R;

public class ChatActivity extends BaseActivity {
    private int RESULT_LOAD_IMG = 1;
//    @BindView(R.id.ubahprofil_img_profil) ImageView ic_chat;
    @BindView(R.id.viewback_title_no_desc) TextView title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);
        title.setText("Kontak Kami");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @OnClick(R.id.viewback_buttonback_no_desc)
    public void backFromChatActivity(){finish();}

    @OnClick(R.id.img_chat)
    public void ubahIconProfil(){
        takephoto();
    }

    private void takephoto(){
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG);
    }

}

package id.weplus;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);
        Log.d("test","test");

    }

    @OnClick(R.id.btn_login_action)
    public void actionLoginWelcome(){
        Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.btn_sign_up_action)
    public void actionSignUp(){
        Intent signup = new Intent(WelcomeActivity.this, SignUp.class);
        startActivity(signup);
    }

}

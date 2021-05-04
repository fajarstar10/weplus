package id.weplus;

import android.content.ComponentCallbacks2;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

import id.weplus.net.WeplusSharedPreference;
import id.weplus.utility.SavePreference;

public class Splash extends AppCompatActivity implements ComponentCallbacks2 {

    private static int SPLASH_TIME_OUT = 5000;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        boolean isFirstTime = SavePreference.getFirstInstall(this);
        Timer(isFirstTime);
    }

    private void Timer(final boolean firstTimeInstallation){
        new Handler().postDelayed(() -> {

            if (!WeplusSharedPreference.getLoggedStatus(getApplicationContext())){
                startActivity(new Intent(getApplicationContext(), WelcomeActivity.class));
            }
            else {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }

            finish();
        }, SPLASH_TIME_OUT);
    }

    @Override
    public void onTrimMemory(int level) {
        switch (level){
            case ComponentCallbacks2.TRIM_MEMORY_UI_HIDDEN:
                break;
        }
    }
}

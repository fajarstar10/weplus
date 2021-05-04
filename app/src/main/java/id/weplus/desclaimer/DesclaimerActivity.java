package id.weplus.desclaimer;

import android.content.Intent;
import android.os.Bundle;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.weplus.R;
import id.weplus.WelcomeActivity;
import id.weplus.utility.SavePreference;
import me.relex.circleindicator.CircleIndicator;

public class DesclaimerActivity extends AppCompatActivity {
    @BindView(R.id.viewpager) ViewPager viewPager;
    @BindView(R.id.circle) CircleIndicator circleIndicator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_desclaimer);
        ButterKnife.bind(this);
        SavePreference.setFisrtInstall(this, true);
        viewPager.setAdapter(new CustomPagerAdapter(this));
        circleIndicator.setViewPager(viewPager);
    }

    public void cek(View view){
        Intent intent = new Intent(DesclaimerActivity.this, WelcomeActivity.class);
        startActivity(intent);
        finish();
    }

}

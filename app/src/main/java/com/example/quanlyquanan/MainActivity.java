package com.example.quanlyquanan;

// Import các thư viện cần thiết
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    // Thời gian hiển thị màn hình splash (tính bằng mili giây)
    private static final int SPLASH_TIMER = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView IMGLogo = (ImageView) findViewById(R.id.imgLogo);

        Animation sideAnim = AnimationUtils.loadAnimation(this, R.anim.side_anim);

        IMGLogo.setAnimation(sideAnim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), LuaChonActivity.class);

                startActivity(intent);

                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                finish();
            }
        }, SPLASH_TIMER);
    }
}

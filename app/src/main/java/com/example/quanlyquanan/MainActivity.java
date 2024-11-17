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
        // Gắn giao diện từ file activity_main.xml
        setContentView(R.layout.activity_main);

        // Tìm đối tượng ImageView trong layout (logo của ứng dụng)
        ImageView IMGLogo = (ImageView) findViewById(R.id.imgLogo);

        // Tải hiệu ứng hoạt hình từ tài nguyên anim/side_anim.xml
        Animation sideAnim = AnimationUtils.loadAnimation(this, R.anim.side_anim);

        // Áp dụng hiệu ứng hoạt hình cho ImageView
        IMGLogo.setAnimation(sideAnim);

        // Sử dụng Handler để trì hoãn chuyển Activity sau SPLASH_TIMER (3 giây)
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Tạo Intent để chuyển sang LuaChonActivity
                Intent intent = new Intent(getApplicationContext(), LuaChonActivity.class);

                // Bắt đầu Activity mới
                startActivity(intent);

                // Áp dụng hiệu ứng chuyển cảnh (mờ dần vào và mờ dần ra)
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                // Kết thúc MainActivity để không quay lại được bằng nút Back
                finish();
            }
        }, SPLASH_TIMER);
    }
}

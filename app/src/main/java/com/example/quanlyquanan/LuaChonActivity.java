package com.example.quanlyquanan;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;

public class LuaChonActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Gắn giao diện từ file activity_lua_chon.xml
        setContentView(R.layout.activity_lua_chon);
    }

    /**
     * Chuyển sang trang Đăng nhập
     * Được gọi khi người dùng nhấn vào nút Đăng nhập.
     */
    public void callLoginFromWel(View view) {
        // Tạo Intent để chuyển từ LuaChonActivity sang DangNhapActivity
        Intent intent = new Intent(getApplicationContext(), DangNhapActivity.class);

        // Tạo hiệu ứng chuyển cảnh (Shared Element Transition)
        Pair[] pairs = new Pair[1];
        pairs[0] = new Pair<View, String>(findViewById(R.id.btn_dangnhap), "transition_login");

        // Kiểm tra phiên bản Android
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            // Áp dụng hiệu ứng chuyển cảnh cho Activity
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(LuaChonActivity.this, pairs);
            startActivity(intent, options.toBundle());
        } else {
            // Nếu không hỗ trợ hiệu ứng, chuyển Activity bình thường
            startActivity(intent);
        }
    }

    /**
     * Chuyển sang trang Đăng ký
     * Được gọi khi người dùng nhấn vào nút Đăng ký.
     */
    public void callSignUpFromWel(View view) {
        // Tạo Intent để chuyển từ LuaChonActivity sang DangKy1Activity
        Intent intent = new Intent(getApplicationContext(), DangKy1Activity.class);

        // Tạo hiệu ứng chuyển cảnh (Shared Element Transition)
        Pair[] pairs = new Pair[1];
        pairs[0] = new Pair<View, String>(findViewById(R.id.btn_dangky), "transition_signup");

        // Kiểm tra phiên bản Android
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            // Áp dụng hiệu ứng chuyển cảnh cho Activity
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(LuaChonActivity.this, pairs);
            startActivity(intent, options.toBundle());
        } else {
            // Nếu không hỗ trợ hiệu ứng, chuyển Activity bình thường
            startActivity(intent);
        }
    }
}

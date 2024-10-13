package com.example.quanlyquanan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class QR extends AppCompatActivity {
    Button exit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        exit =findViewById(R.id.close);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Đóng màn hình QR và chuyển sang màn hình CTHD (Chi tiết hóa đơn)
                Intent intent = new Intent(QR.this, TrangChuActivity.class);
                startActivity(intent);
                finish(); // Kết thúc màn hình QR để không thể quay lại bằng nút Back
            }
        });
    }
}
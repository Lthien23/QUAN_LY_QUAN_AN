package com.example.lethuyhien;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.lethuyhien.Database.dbQLQA;

public class Dangnhap extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dangnhap);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Button btnDangnhap =findViewById(R.id.btnDangnhap);
        Button btnDangki = findViewById(R.id.btnDangki);
        EditText tendangnhap=findViewById(R.id.tendangnhap);
        EditText matkhau=findViewById(R.id.matkhau);

        btnDangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = tendangnhap.getText().toString();
                String password = matkhau.getText().toString();

                if (username.isEmpty()) {
                    Toast.makeText(Dangnhap.this, "Hãy nhập tài khoản!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (password.isEmpty()) {
                    Toast.makeText(Dangnhap.this, "Hãy nhập mật khẩu!", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Kiểm tra nếu là admin
                if (username.equals("admin1") && password.equals("admin1")) {
                    Toast.makeText(Dangnhap.this, "Đăng nhập thành công với tư cách admin!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Dangnhap.this, Trangchu.class);
                    startActivity(intent);
                    return;
                }

                // Kiểm tra tài khoản trong cơ sở dữ liệu
                dbQLQA db = new dbQLQA(Dangnhap.this);
                boolean accountExists = db.checkAccount(username, password);
                if (accountExists) {
                    Toast.makeText(Dangnhap.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Dangnhap.this, Trangchu.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(Dangnhap.this, "Tài khoản hoặc mật khẩu không đúng", Toast.LENGTH_SHORT).show();
                }
                // Trong Dangnhap.java, sau khi đăng nhập thành công
                Intent intent = new Intent(Dangnhap.this, Trangchu.class);
                intent.putExtra("username", username); // Gửi tên đăng nhập
                startActivity(intent);

            }
        });

        btnDangki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Dangnhap.this, Dangky.class);
                startActivity(intent);
            }
        });
    }
}
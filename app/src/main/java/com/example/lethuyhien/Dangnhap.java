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
                String ten = tendangnhap.getText().toString();
                String pass = matkhau.getText().toString();

                if (ten.equals("admin1")&&pass.equals("admin1"))
                {
                    Toast.makeText(Dangnhap.this,"Đăng nhập thành công!",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Dangnhap.this, Trangchu.class);
                    startActivity(intent);
                }
                if(ten.isEmpty())
                {Toast.makeText(Dangnhap.this, "Hãy nhập tài khoản!", Toast.LENGTH_SHORT).show();}
                if(pass.isEmpty())
                {Toast.makeText(Dangnhap.this, "Hãy nhập mật khẩu!", Toast.LENGTH_SHORT).show();}
                if (ten.isEmpty() || pass.isEmpty())
                {
                   Toast.makeText(Dangnhap.this, "Hãy nhập lại thông tin!", Toast.LENGTH_SHORT).show();
               }
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
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

public class Dangky extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dangky);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Button btnDangki = findViewById(R.id.btnDangki_dk);
        EditText hoten_dk=findViewById(R.id.hoten_dk);
        EditText ngaysinh_dk=findViewById(R.id.ngaysinh_dk);
        EditText sdt=findViewById(R.id.sdt_dk);
        EditText tendangnhap=findViewById(R.id.tendangnhap_dk);
        EditText matkhau=findViewById(R.id.matkhau_dk);
        EditText nhaplai=findViewById(R.id.nhaplai);
        btnDangki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String hoten = hoten_dk.getText().toString();
                String ngaysinh = ngaysinh_dk.getText().toString();
                String sodienthoai = sdt.getText().toString();
                String tendangnhap_dk = tendangnhap.getText().toString();
                String matkhau_dk = matkhau.getText().toString();
                String nhaplai_dk = nhaplai.getText().toString();
                if(hoten.isEmpty())
                {Toast.makeText(Dangky.this, "Hãy nhập ho ten!", Toast.LENGTH_SHORT).show();}
                if(ngaysinh.isEmpty())
                {Toast.makeText(Dangky.this, "Hãy nhập ngay sinh!", Toast.LENGTH_SHORT).show();}
                if(sodienthoai.isEmpty())
                {Toast.makeText(Dangky.this, "Hãy nhập so dien thoai!", Toast.LENGTH_SHORT).show();}
                if(tendangnhap_dk.isEmpty())
                {Toast.makeText(Dangky.this, "Hãy nhập ten dang nhap!", Toast.LENGTH_SHORT).show();}
                if(matkhau_dk.isEmpty())
                {Toast.makeText(Dangky.this, "Hãy nhập mat khau!", Toast.LENGTH_SHORT).show();}
                if(nhaplai_dk.isEmpty())
                {Toast.makeText(Dangky.this, "Hãy nhập lai mật khẩu!", Toast.LENGTH_SHORT).show();}
                if (hoten.isEmpty() || ngaysinh.isEmpty()|| sodienthoai.isEmpty()|| tendangnhap_dk.isEmpty()|| matkhau_dk.isEmpty()|| nhaplai_dk.isEmpty())
                {
                    Toast.makeText(Dangky.this, "Hãy nhập lại thông tin!", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(Dangky.this,"Đăng ký thành công!",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Dangky.this, Dangnhap.class);
                    startActivity(intent);
                }
            }
        });
    }
}
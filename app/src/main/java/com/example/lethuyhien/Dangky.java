package com.example.lethuyhien;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.lethuyhien.Database.dbQLQA;

public class Dangky extends AppCompatActivity {
    public EditText hoten_dk, ngaysinh_dk, sdthoai, tendangnhap, matkhau, nhaplai;
    public RadioGroup radioGroupGender;
    private Button btnDangki;

    // Khởi tạo cơ sở dữ liệu
    private dbQLQA database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dangky);

        // Thiết lập view
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        database = new dbQLQA(this);
        // Khởi tạo các EditText và Button
        hoten_dk = findViewById(R.id.hoten_dk);
        ngaysinh_dk = findViewById(R.id.ngaysinh_dk);
        sdthoai = findViewById(R.id.sdt_dk);
        tendangnhap = findViewById(R.id.tendangnhap_dk);
        matkhau = findViewById(R.id.matkhau_dk);
        nhaplai = findViewById(R.id.nhaplai);
        radioGroupGender = findViewById(R.id.radioGroupGender);
        btnDangki = findViewById(R.id.btnDangki_dk);

        btnDangki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Lấy thông tin từ các EditText
                String hoten = hoten_dk.getText().toString();
                String ngaysinh = ngaysinh_dk.getText().toString();
                String sdt = sdthoai.getText().toString();
                String username = tendangnhap.getText().toString();
                String password = matkhau.getText().toString();
                String nhaplai_dk = nhaplai.getText().toString();

                // Kiểm tra các trường nhập liệu
                if (hoten.isEmpty() || ngaysinh.isEmpty() || sdt.isEmpty() ||
                        username.isEmpty() || password.isEmpty() || nhaplai_dk.isEmpty()) {
                    Toast.makeText(Dangky.this, "Hãy nhập lại thông tin!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!password.equals(nhaplai_dk)) {
                    Toast.makeText(Dangky.this, "Mật khẩu không khớp!", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Lấy giới tính
                String gioitinh;
                int selectedId = radioGroupGender.getCheckedRadioButtonId();
                if (selectedId == R.id.radioMale) {
                    gioitinh = "Nam";
                } else if (selectedId == R.id.radioFemale) {
                    gioitinh = "Nữ";
                } else {
                    gioitinh = "Khác"; // Hoặc bạn có thể yêu cầu người dùng chọn giới tính
                }
                // Gọi hàm registerUser
                database.registerUser(hoten, ngaysinh, sdt, gioitinh, username, password);
                Toast.makeText(Dangky.this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Dangky.this, Dangnhap.class);
                startActivity(intent);
                finish(); // Kết thúc hoạt động đăng ký
            }
        });
    }
}

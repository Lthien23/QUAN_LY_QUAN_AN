package com.example.quanlyquanan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.example.quanlyquanan.DAO.BanAnDAO;
import com.example.quanlyquanan.R;

public class SuaBanActivity extends AppCompatActivity {

    TextInputLayout txtl_suabanActivity_tenban; // Khung nhập tên bàn (TextInputLayout)
    Button btn_suabanActivity_SuaBan; // Nút thực hiện cập nhật tên bàn
    BanAnDAO banAnDAO; // Đối tượng DAO để tương tác với cơ sở dữ liệu của bảng Bàn

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sua_ban); // Thiết lập layout cho activity

        // Ánh xạ view từ layout XML vào các thuộc tính
        txtl_suabanActivity_tenban = (TextInputLayout)findViewById(R.id.txtl_suabanActivity_tenban);
        btn_suabanActivity_SuaBan = (Button)findViewById(R.id.btn_suabanActivity_SuaBan);

        // Khởi tạo đối tượng DAO để mở kết nối đến cơ sở dữ liệu
        banAnDAO = new BanAnDAO(this);

        // Lấy mã bàn từ Intent (được truyền từ activity gọi đến)
        int maban = getIntent().getIntExtra("maban", 0);

        // Thiết lập sự kiện khi nhấn nút cập nhật bàn
        btn_suabanActivity_SuaBan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy tên bàn mới từ khung nhập
                String tenban = txtl_suabanActivity_tenban.getEditText().getText().toString();

                // Kiểm tra nếu tên bàn không null hoặc không rỗng
                if (tenban != null || !tenban.equals("")) {
                    // Cập nhật tên bàn trong cơ sở dữ liệu, ktra sẽ nhận kết quả thành công hoặc thất bại
                    boolean ktra = banAnDAO.CapNhatTenBan(maban, tenban);

                    // Tạo Intent để gửi kết quả cập nhật về activity trước đó
                    Intent intent = new Intent();
                    intent.putExtra("ketquasua", ktra); // Đưa kết quả cập nhật vào intent
                    setResult(RESULT_OK, intent); // Trả kết quả về cho activity trước đó
                    finish(); // Đóng activity hiện tại
                }
            }
        });
    }
}

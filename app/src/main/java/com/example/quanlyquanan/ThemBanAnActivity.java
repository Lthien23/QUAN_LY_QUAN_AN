package com.example.quanlyquanan;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputLayout;
import com.example.quanlyquanan.DAO.BanAnDAO;
import com.example.quanlyquanan.R;

public class ThemBanAnActivity extends AppCompatActivity {

    // Khai báo các thuộc tính cho giao diện
    TextInputLayout txtl_thembanActivity_tenban; // Khung nhập tên bàn (TextInputLayout)
    Button btn_thembanActivity_TaoBan; // Nút tạo bàn
    BanAnDAO banAnDAO; // Đối tượng DAO để thao tác với cơ sở dữ liệu bảng Bàn

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_ban_an); // Thiết lập layout cho activity

        // region Lấy các đối tượng trong view từ XML
        txtl_thembanActivity_tenban = (TextInputLayout)findViewById(R.id.txtl_thembanActivity_tenban);
        btn_thembanActivity_TaoBan = (Button)findViewById(R.id.btn_thembanActivity_TaoBan);

        // Khởi tạo DAO để mở kết nối với cơ sở dữ liệu
        banAnDAO = new BanAnDAO(this);

        // Thiết lập sự kiện khi nhấn nút tạo bàn
        btn_thembanActivity_TaoBan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy tên bàn từ khung nhập
                String sTenBanAn = txtl_thembanActivity_tenban.getEditText().getText().toString();

                // Kiểm tra nếu tên bàn không null hoặc không rỗng
                if (sTenBanAn != null || !sTenBanAn.equals("")) {
                    // Thực hiện thêm bàn vào cơ sở dữ liệu và lưu kết quả vào ktra
                    boolean ktra = banAnDAO.ThemBanAn(sTenBanAn);

                    // Trả kết quả thêm bàn về cho activity gọi đến
                    Intent intent = new Intent();
                    intent.putExtra("ketquathem", ktra); // Đưa kết quả vào Intent
                    setResult(RESULT_OK, intent); // Trả kết quả về cho activity trước đó
                    finish(); // Đóng activity hiện tại
                }
            }
        });
    }

    // Phương thức validateName để kiểm tra dữ liệu nhập tên bàn
    private boolean validateName() {
        // Lấy giá trị nhập từ khung nhập và loại bỏ khoảng trắng đầu cuối
        String val = txtl_thembanActivity_tenban.getEditText().getText().toString().trim();

        // Kiểm tra nếu giá trị trống thì báo lỗi, ngược lại xóa lỗi
        if (val.isEmpty()) {
            txtl_thembanActivity_tenban.setError(getResources().getString(R.string.not_empty)); // Báo lỗi không được để trống
            return false;
        } else {
            txtl_thembanActivity_tenban.setError(null); // Xóa thông báo lỗi
            txtl_thembanActivity_tenban.setErrorEnabled(false); // Tắt chế độ hiển thị lỗi
            return true;
        }
    }
}

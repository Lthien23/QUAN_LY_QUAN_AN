package com.example.quanlyquanan;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;  // Hỗ trợ hiệu ứng chuyển cảnh giữa các Activity
import android.content.Context;      // Cung cấp thông tin về ngữ cảnh của ứng dụng
import android.content.Intent;       // Dùng để chuyển đổi giữa các Activity
import android.content.SharedPreferences; // Lưu trữ dữ liệu dưới dạng key-value trong bộ nhớ thiết bị
import android.os.Bundle;
import android.util.Pair;            // Đại diện cho một cặp giá trị (key-value)
import android.view.View;
import android.widget.Button;
import android.widget.Toast;         // Hiển thị thông báo dạng popup

import com.example.quanlyquanan.DAO.NhanVienDAO;
import com.google.android.material.textfield.TextInputLayout; // Hỗ trợ nhập liệu có giao diện hiện đại

public class DangNhapActivity extends AppCompatActivity {

    // Khai báo các thành phần giao diện
    Button btn_dangnhap_dangnhap, btn_dangnhap_dangky; // Nút bấm "Đăng nhập" và "Đăng ký"
    TextInputLayout txtl_dangnhap_tendn, txtl_dangnhap_matkhau; // Các trường nhập liệu
    NhanVienDAO nhanVienDAO; // Đối tượng quản lý kết nối cơ sở dữ liệu (Data Access Object)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap); // Gắn giao diện từ tệp XML `activity_dang_nhap`

        // Liên kết các thành phần giao diện với ID trong layout
        txtl_dangnhap_tendn = findViewById(R.id.txtl_dangnhap_tendn); // Trường nhập tên đăng nhập
        txtl_dangnhap_matkhau = findViewById(R.id.txtl_dangnhap_matkhau); // Trường nhập mật khẩu
        btn_dangnhap_dangnhap = findViewById(R.id.btn_dangnhap_dangnhap); // Nút đăng nhập
        btn_dangnhap_dangky = findViewById(R.id.btn_dangnhap_dangky);     // Nút đăng ký

        // Khởi tạo đối tượng DAO để thao tác với cơ sở dữ liệu
        nhanVienDAO = new NhanVienDAO(this);

        // Xử lý sự kiện khi người dùng nhấn nút "Đăng nhập"
        btn_dangnhap_dangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Kiểm tra dữ liệu nhập vào (tên đăng nhập và mật khẩu)
                if (!validateUserName() | !validatePassWord()) {
                    return; // Nếu không hợp lệ, thoát khỏi sự kiện
                }

                // Lấy giá trị từ các trường nhập liệu
                String tenDN = txtl_dangnhap_tendn.getEditText().getText().toString(); // Lấy tên đăng nhập
                String matKhau = txtl_dangnhap_matkhau.getEditText().getText().toString(); // Lấy mật khẩu

                // Kiểm tra thông tin đăng nhập bằng hàm từ DAO
                int ktra = nhanVienDAO.KiemTraDN(tenDN, matKhau); // Trả về mã nhân viên nếu hợp lệ, 0 nếu không hợp lệ
                int maquyen = nhanVienDAO.LayQuyenNV(ktra); // Lấy mã quyền của nhân viên đăng nhập

                if (ktra != 0) { // Nếu mã nhân viên hợp lệ
                    // Lưu mã quyền vào SharedPreferences để sử dụng trong các màn hình khác
                    SharedPreferences sharedPreferences = getSharedPreferences("luuquyen", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt("maquyen", maquyen); // Lưu mã quyền
                    editor.commit(); // Lưu dữ liệu xuống thiết bị

                    // Tạo intent để chuyển đến màn hình Trang Chủ
                    Intent intent = new Intent(getApplicationContext(), TrangChuActivity.class);
                    intent.putExtra("tendn", tenDN); // Gửi tên đăng nhập sang Trang Chủ
                    intent.putExtra("manv", ktra);  // Gửi mã nhân viên sang Trang Chủ
                    startActivity(intent); // Bắt đầu màn hình Trang Chủ
                } else {
                    // Nếu mã nhân viên không hợp lệ, hiển thị thông báo lỗi
                    Toast.makeText(getApplicationContext(), "Đăng nhập thất bại!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * Hàm quay lại màn hình chính (LuaChonActivity).
     */
    public void backFromLogin(View view) {
        // Tạo intent chuyển sang LuaChonActivity
        Intent intent = new Intent(getApplicationContext(), LuaChonActivity.class);

        // Tạo hiệu ứng chuyển cảnh với Shared Element Transition
        Pair[] pairs = new Pair[1];
        pairs[0] = new Pair<View, String>(findViewById(R.id.layoutLogin), "transition_login"); // Liên kết View và tên chuyển cảnh

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            // Sử dụng hiệu ứng nếu thiết bị chạy Android 5.0 (Lollipop) trở lên
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(DangNhapActivity.this, pairs);
            startActivity(intent, options.toBundle());
        } else {
            // Không có hiệu ứng trên các phiên bản Android thấp hơn
            startActivity(intent);
        }
    }

    /**
     * Hàm chuyển qua màn hình Đăng ký (DangKy1Activity).
     */
    public void callRegisterFromLogin(View view) {
        // Tạo intent chuyển đến DangKy1Activity
        Intent intent = new Intent(getApplicationContext(), DangKy1Activity.class);
        startActivity(intent);

        // Thêm hiệu ứng chuyển cảnh fade in và fade out
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    /**
     * Kiểm tra tính hợp lệ của trường nhập tên đăng nhập.
     * @return true nếu hợp lệ, false nếu không hợp lệ.
     */
    private boolean validateUserName() {
        String val = txtl_dangnhap_tendn.getEditText().getText().toString().trim(); // Lấy giá trị từ trường nhập

        if (val.isEmpty()) { // Kiểm tra nếu để trống
            txtl_dangnhap_tendn.setError(getResources().getString(R.string.not_empty)); // Hiển thị lỗi
            return false;
        } else {
            // Xóa lỗi nếu hợp lệ
            txtl_dangnhap_tendn.setError(null);
            txtl_dangnhap_tendn.setErrorEnabled(false);
            return true;
        }
    }

    /**
     * Kiểm tra tính hợp lệ của trường nhập mật khẩu.
     * @return true nếu hợp lệ, false nếu không hợp lệ.
     */
    private boolean validatePassWord() {
        String val = txtl_dangnhap_matkhau.getEditText().getText().toString().trim(); // Lấy giá trị từ trường nhập

        if (val.isEmpty()) { // Kiểm tra nếu để trống
            txtl_dangnhap_matkhau.setError(getResources().getString(R.string.not_empty)); // Hiển thị lỗi
            return false;
        } else {
            // Xóa lỗi nếu hợp lệ
            txtl_dangnhap_matkhau.setError(null);
            txtl_dangnhap_matkhau.setErrorEnabled(false);
            return true;
        }
    }
}

package com.example.quanlyquanan;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions; // Hỗ trợ hiệu ứng chuyển cảnh giữa các Activity
import android.content.Intent; // Dùng để chuyển đổi giữa các Activity
import android.os.Bundle;
import android.util.Pair; // Cung cấp khả năng tạo cặp key-value (dùng trong chuyển cảnh)
import android.view.View;
import android.widget.Button; // Thực thể của Button trên giao diện
import android.widget.ImageView; // Thực thể của ImageView trên giao diện
import android.widget.TextView; // Thực thể của TextView trên giao diện
import com.google.android.material.textfield.TextInputLayout; // Hỗ trợ các trường nhập liệu có giao diện hiện đại

import java.util.regex.Pattern; // Dùng để kiểm tra chuỗi qua biểu thức chính quy

import static com.example.quanlyquanan.R.*;

public class DangKy1Activity extends AppCompatActivity {

    // Khai báo các thành phần giao diện
    ImageView img_dangky1_back; // Nút quay lại của màn hình đăng ký
    Button btn_dangki1_tieptheo; // Nút tiếp theo để chuyển sang màn hình đăng ký thứ 2
    TextView txt_dangky1_Tieude; // Tiêu đề màn hình đăng ký
    TextInputLayout txtl_dangki1_hoten, txtl_dangky1_tendn, txtl_dangki1_Email, txtl_dangki1_sdt, txtl_dangki1_matkhau; // Các trường nhập liệu cho thông tin cá nhân, tên đăng nhập, email, số điện thoại, mật khẩu

    public static final String BUNDLE = "BUNDLE"; // Tên khóa để truyền dữ liệu qua Intent
    // Biểu thức chính quy để kiểm tra mật khẩu, yêu cầu ít nhất 6 ký tự và không có khoảng trắng
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=\\S+$)" +            // Không được có khoảng trắng
                    ".{6,}" +                // Mật khẩu phải có ít nhất 6 ký tự
                    "$");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_dang_ky1); // Gắn layout đăng ký vào Activity

        // Liên kết các thành phần giao diện với ID từ layout
        img_dangky1_back = (ImageView)findViewById(id.img_dangky1_back);
        btn_dangki1_tieptheo = (Button)findViewById(id.btn_dangki1_tieptheo);
        txt_dangky1_Tieude = (TextView)findViewById(id.txt_dangky1_Tieude);
        txtl_dangki1_hoten = (TextInputLayout)findViewById(id.txtl_dangki1_hoten);
        txtl_dangky1_tendn = (TextInputLayout)findViewById(id.txtl_dangky1_tendn);
        txtl_dangki1_Email = (TextInputLayout)findViewById(id.txtl_dangki1_Email);
        txtl_dangki1_sdt = (TextInputLayout)findViewById(id.txtl_dangki1_sdt);
        txtl_dangki1_matkhau = (TextInputLayout)findViewById(id.txtl_dangki1_matkhau);

        // Xử lý sự kiện khi nhấn nút "Tiếp theo"
        btn_dangki1_tieptheo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Kiểm tra dữ liệu người dùng nhập vào có hợp lệ không
                if (!validateFullName() | !validateUserName() | !validateEmail() | !validatePhone() | !validatePassWord()) {
                    return; // Nếu có bất kỳ trường nào không hợp lệ, ngừng lại và không chuyển màn hình
                }
                // Lấy dữ liệu từ các trường nhập liệu
                String hoTen = txtl_dangki1_hoten.getEditText().getText().toString();
                String tenDN = txtl_dangky1_tendn.getEditText().getText().toString();
                String eMail = txtl_dangki1_Email.getEditText().getText().toString();
                String sDT = txtl_dangki1_sdt.getEditText().getText().toString();
                String matKhau = txtl_dangki1_matkhau.getEditText().getText().toString();

                // Gửi dữ liệu đến màn hình đăng ký thứ 2
                byBundleNextSignupScreen(hoTen, tenDN, eMail, sDT, matKhau);
            }
        });

    }

    // Hàm quay lại màn hình trước (LuaChonActivity)
    public void backFromRegister(View view) {
        Intent intent = new Intent(getApplicationContext(), LuaChonActivity.class);

        // Tạo hiệu ứng chuyển cảnh khi quay lại màn hình trước
        Pair[] pairs = new Pair[1];
        pairs[0] = new Pair<View, String>(findViewById(id.layoutRegister), "transition_signup");

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            // Nếu thiết bị hỗ trợ, sử dụng hiệu ứng chuyển cảnh
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(DangKy1Activity.this, pairs);
            startActivity(intent, options.toBundle());
        } else {
            startActivity(intent); // Nếu không hỗ trợ, chuyển màn hình mà không có hiệu ứng
        }
    }

    // Hàm chuyển đến màn hình đăng ký thứ 2 và truyền dữ liệu qua Intent với Bundle
    public void byBundleNextSignupScreen(String hoTen, String tenDN, String eMail, String sDT, String matKhau) {
        Intent intent = new Intent(getApplicationContext(), DangKy2Activity.class);
        Bundle bundle = new Bundle();
        // Đưa các thông tin người dùng vào Bundle
        bundle.putString("hoten", hoTen);
        bundle.putString("tendn", tenDN);
        bundle.putString("email", eMail);
        bundle.putString("sdt", sDT);
        bundle.putString("matkhau", matKhau);
        // Gửi Bundle kèm theo Intent
        intent.putExtra(BUNDLE, bundle);

        // Chuyển sang màn hình đăng ký thứ 2 với hiệu ứng chuyển động
        startActivity(intent);
        overridePendingTransition(anim.slide_in_right, anim.slide_out_left); // Thêm hiệu ứng trượt từ phải sang trái
    }

    // region Validate field: Kiểm tra tính hợp lệ của các trường nhập liệu

    // Kiểm tra trường họ tên
    private boolean validateFullName() {
        String val = txtl_dangki1_hoten.getEditText().getText().toString().trim(); // Lấy giá trị từ trường họ tên

        if (val.isEmpty()) { // Nếu trường họ tên trống
            txtl_dangki1_hoten.setError(getResources().getString(string.not_empty)); // Hiển thị lỗi
            return false;
        } else {
            txtl_dangki1_hoten.setError(null); // Xóa lỗi nếu hợp lệ
            txtl_dangki1_hoten.setErrorEnabled(false); // Tắt lỗi
            return true;
        }
    }

    // Kiểm tra trường tên đăng nhập
    private boolean validateUserName() {
        String val = txtl_dangky1_tendn.getEditText().getText().toString().trim();
        String checkspaces = "\\A\\w{1,50}\\z"; // Kiểm tra tên đăng nhập không có khoảng trắng và không dài quá 50 ký tự

        if (val.isEmpty()) { // Nếu trường tên đăng nhập trống
            txtl_dangky1_tendn.setError(getResources().getString(string.not_empty));
            return false;
        } else if (val.length() > 50) { // Nếu tên đăng nhập quá dài
            txtl_dangky1_tendn.setError("Phải nhỏ hơn 50 ký tự");
            return false;
        } else if (!val.matches(checkspaces)) { // Nếu tên đăng nhập chứa khoảng trắng
            txtl_dangky1_tendn.setError("Không được cách chữ!");
            return false;
        } else {
            txtl_dangky1_tendn.setError(null); // Xóa lỗi nếu hợp lệ
            txtl_dangky1_tendn.setErrorEnabled(false); // Tắt lỗi
            return true;
        }
    }

    // Kiểm tra trường email
    private boolean validateEmail() {
        String val = txtl_dangki1_Email.getEditText().getText().toString().trim();
        String checkspaces = "[a-zA-Z0-9._-]+@[a-z]+.+[a-z]+"; // Biểu thức chính quy kiểm tra định dạng email

        if (val.isEmpty()) { // Nếu email trống
            txtl_dangki1_Email.setError(getResources().getString(string.not_empty));
            return false;
        } else if (!val.matches(checkspaces)) { // Nếu email không hợp lệ
            txtl_dangki1_Email.setError("Email không hợp lệ!");
            return false;
        } else {
            txtl_dangki1_Email.setError(null); // Xóa lỗi nếu hợp lệ
            txtl_dangki1_Email.setErrorEnabled(false); // Tắt lỗi
            return true;
        }
    }

    // Kiểm tra trường số điện thoại
    private boolean validatePhone() {
        String val = txtl_dangki1_sdt.getEditText().getText().toString().trim();

        if (val.isEmpty()) { // Nếu số điện thoại trống
            txtl_dangki1_sdt.setError(getResources().getString(string.not_empty));
            return false;
        } else if (val.length() != 10) { // Nếu số điện thoại không đủ 10 chữ số
            txtl_dangki1_sdt.setError("Số điện thoại không hợp lệ!");
            return false;
        } else {
            txtl_dangki1_sdt.setError(null); // Xóa lỗi nếu hợp lệ
            txtl_dangki1_sdt.setErrorEnabled(false); // Tắt lỗi
            return true;
        }
    }

    // Kiểm tra trường mật khẩu
    private boolean validatePassWord() {
        String val = txtl_dangki1_matkhau.getEditText().getText().toString().trim();

        if (val.isEmpty()) { // Nếu mật khẩu trống
            txtl_dangki1_matkhau.setError(getResources().getString(string.not_empty));
            return false;
        } else if (!PASSWORD_PATTERN.matcher(val).matches()) { // Nếu mật khẩu không hợp lệ
            txtl_dangki1_matkhau.setError("Mật khẩu ít nhất 6 ký tự!");
            return false;
        } else {
            txtl_dangki1_matkhau.setError(null); // Xóa lỗi nếu hợp lệ
            txtl_dangki1_matkhau.setErrorEnabled(false); // Tắt lỗi
            return true;
        }
    }
}

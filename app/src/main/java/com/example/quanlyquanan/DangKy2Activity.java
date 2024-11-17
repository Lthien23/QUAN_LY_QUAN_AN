package com.example.quanlyquanan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent; // Để thực hiện chuyển màn hình giữa các Activity
import android.os.Bundle; // Để xử lý dữ liệu khi Activity được tạo
import android.util.Log; // Dùng để log thông tin trong quá trình phát triển
import android.view.View; // Để xử lý sự kiện khi người dùng nhấn vào các thành phần giao diện
import android.widget.Button; // Khai báo nút bấm
import android.widget.DatePicker; // Khai báo DatePicker để chọn ngày tháng
import android.widget.RadioGroup; // Khai báo RadioGroup để chọn giới tính
import android.widget.Toast; // Để hiển thị thông báo ngắn tới người dùng

import com.example.quanlyquanan.DAO.NhanVienDAO; // Dùng để thao tác với cơ sở dữ liệu nhân viên
import com.example.quanlyquanan.DAO.QuyenDAO; // Dùng để thao tác với cơ sở dữ liệu quyền
import com.example.quanlyquanan.Model.NhanVien; // Dùng để đại diện cho đối tượng nhân viên
import com.example.quanlyquanan.R; // Để truy cập các tài nguyên như layout, string, id...

import java.util.Calendar; // Dùng để xử lý thời gian, ngày tháng

public class DangKy2Activity extends AppCompatActivity {

    // Khai báo các thành phần giao diện
    RadioGroup rg_dangki2_GioiTinh; // Dùng để chọn giới tính
    DatePicker dt_dangky2_ngaysinh; // Dùng để chọn ngày sinh
    Button btn_dangky2_tieptheo; // Dùng để nhấn tiếp tục đăng ký
    String hoTen, tenDN, eMail, sDT, matKhau, gioiTinh; // Các biến lưu thông tin người dùng
    NhanVienDAO nhanVienDAO; // Đối tượng DAO để thao tác với cơ sở dữ liệu nhân viên
    QuyenDAO quyenDAO; // Đối tượng DAO để thao tác với cơ sở dữ liệu quyền

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky2); // Gán layout cho Activity

        // Liên kết các thành phần giao diện với các ID tương ứng trong layout
        rg_dangki2_GioiTinh = (RadioGroup)findViewById(R.id.rg_dangki2_GioiTinh);
        dt_dangky2_ngaysinh = (DatePicker)findViewById(R.id.dt_dangky2_ngaysinh);
        btn_dangky2_tieptheo = (Button)findViewById(R.id.btn_dangky2_tieptheo);

        // Lấy dữ liệu từ Bundle của Activity đăng ký 1
        Bundle bundle = getIntent().getBundleExtra(DangKy1Activity.BUNDLE);
        if(bundle != null) {
            hoTen = bundle.getString("hoten");
            tenDN = bundle.getString("tendn");
            eMail = bundle.getString("email");
            sDT = bundle.getString("sdt");
            matKhau = bundle.getString("matkhau");
        }

        // Khởi tạo các đối tượng DAO để thao tác với cơ sở dữ liệu
        nhanVienDAO = new NhanVienDAO(this);
        quyenDAO = new QuyenDAO(this);

        // Xử lý sự kiện khi nhấn nút "Tiếp theo"
        btn_dangky2_tieptheo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Kiểm tra giới tính và tuổi hợp lệ trước khi tiến hành đăng ký
                if(!validateAge() | !validateGender()){
                    return; // Nếu có lỗi, không tiếp tục thực hiện
                }

                // Lấy thông tin giới tính từ RadioGroup
                switch (rg_dangki2_GioiTinh.getCheckedRadioButtonId()){
                    case R.id.rd_dangki2_Nam:
                        gioiTinh = "Nam"; break;
                    case R.id.rd_dangky2_Nu:
                        gioiTinh = "Nữ"; break;
                    case R.id.rd_dangky2_Khac:
                        gioiTinh = "Khác"; break;
                }

                // Lấy thông tin ngày sinh từ DatePicker
                String ngaySinh = dt_dangky2_ngaysinh.getDayOfMonth() + "/" + (dt_dangky2_ngaysinh.getMonth() + 1)
                        +"/"+dt_dangky2_ngaysinh.getYear();

                // Tạo đối tượng nhân viên và set thông tin từ các trường
                NhanVien nhanVien = new NhanVien();
                nhanVien.setHOTENNV(hoTen);
                nhanVien.setTENDN(tenDN);
                nhanVien.setEMAIL(eMail);
                nhanVien.setSDT(sDT);
                nhanVien.setMATKHAU(matKhau);
                nhanVien.setGIOITINH(gioiTinh);
                nhanVien.setNGAYSINH(ngaySinh);

                // Kiểm tra nếu đây là nhân viên đầu tiên thì cấp quyền "Quản lý"
                if(!nhanVienDAO.KtraTonTaiNV()){
                    quyenDAO.ThemQuyen("Quản lý");
                    quyenDAO.ThemQuyen("Nhân viên");
                    nhanVien.setMAQUYEN(1); // Quản lý
                }else {
                    nhanVien.setMAQUYEN(2); // Nhân viên
                }

                // Thêm nhân viên vào cơ sở dữ liệu
                long ktra = nhanVienDAO.ThemNhanVien(nhanVien);
                if(ktra != 0){
                    Toast.makeText(DangKy2Activity.this,getResources().getString(R.string.add_sucessful),Toast.LENGTH_SHORT).show();
                    callLoginFromRegister(); // Nếu thành công, gọi màn hình đăng nhập
                }else{
                    Toast.makeText(DangKy2Activity.this,getResources().getString(R.string.add_failed),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Hàm quay lại màn hình đăng ký 1
    public void backFromRegister2nd(View view){
        Intent intent = new Intent(getApplicationContext(), DangKy1Activity.class);
        startActivity(intent); // Chuyển đến Activity đăng ký 1
        finish(); // Kết thúc Activity hiện tại
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right); // Thêm hiệu ứng chuyển cảnh
    }

    // Hàm chuyển màn hình sang LuaChonActivity khi đăng ký thành công
    public void callLoginFromRegister(){
        Intent intent = new Intent(getApplicationContext(), LuaChonActivity.class);
        startActivity(intent); // Chuyển đến màn hình LuaChonActivity
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out); // Thêm hiệu ứng chuyển cảnh
    }

    // Kiểm tra giới tính có được chọn không
    private boolean validateGender(){
        if(rg_dangki2_GioiTinh.getCheckedRadioButtonId() == -1){
            Toast.makeText(this,"Hãy chọn giới tính",Toast.LENGTH_SHORT).show(); // Hiển thị thông báo nếu chưa chọn giới tính
            return false;
        }else {
            return true;
        }
    }

    // Kiểm tra độ tuổi của người dùng (>= 10 tuổi)
    private boolean validateAge(){
        int currentYear = Calendar.getInstance().get(Calendar.YEAR); // Lấy năm hiện tại
        int userAge = dt_dangky2_ngaysinh.getYear(); // Lấy năm sinh của người dùng
        int isAgeValid = currentYear - userAge; // Tính tuổi của người dùng

        if(isAgeValid < 10){ // Nếu tuổi nhỏ hơn 10
            Toast.makeText(this,"Bạn không đủ tuổi đăng ký!",Toast.LENGTH_SHORT).show(); // Hiển thị thông báo lỗi
            return false;
        }else {
            return true; // Nếu hợp lệ, cho phép tiếp tục
        }
    }
}

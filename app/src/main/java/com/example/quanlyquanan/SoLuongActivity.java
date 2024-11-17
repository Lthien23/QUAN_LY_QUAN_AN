package com.example.quanlyquanan;

// Import các thư viện và lớp cần thiết
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.example.quanlyquanan.DAO.ChiTietDonDatDAO;
import com.example.quanlyquanan.DAO.DonDatDAO;
import com.example.quanlyquanan.Model.ChiTietDonDat;
import com.example.quanlyquanan.R;

public class SoLuongActivity extends AppCompatActivity {

    // Khai báo các thành phần giao diện và biến
    TextInputLayout txtl_soluongActivity_SoLuong; // Trường nhập liệu số lượng
    Button btn_soluongActivity_DongY; // Nút xác nhận
    int maban, mamon; // Mã bàn và mã món được chọn
    DonDatDAO donDatDAO; // Đối tượng thao tác với đơn đặt
    ChiTietDonDatDAO chiTietDonDatDAO; // Đối tượng thao tác với chi tiết đơn đặt

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_so_luong); // Gắn layout cho Activity

        // Lấy đối tượng từ giao diện
        txtl_soluongActivity_SoLuong = (TextInputLayout) findViewById(R.id.txtl_soluongActivity_SoLuong);
        btn_soluongActivity_DongY = (Button) findViewById(R.id.btn_soluongActivity_DongY);

        // Khởi tạo kết nối cơ sở dữ liệu
        donDatDAO = new DonDatDAO(this);
        chiTietDonDatDAO = new ChiTietDonDatDAO(this);

        // Lấy thông tin mã bàn và mã món từ Intent (truyền từ Activity trước)
        Intent intent = getIntent();
        maban = intent.getIntExtra("maban", 0); // Mã bàn
        mamon = intent.getIntExtra("mamon", 0); // Mã món

        // Xử lý sự kiện khi bấm nút "Đồng ý"
        btn_soluongActivity_DongY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Kiểm tra số lượng có hợp lệ hay không
                if (!validateAmount()) {
                    return; // Nếu không hợp lệ, dừng xử lý
                }

                // Lấy mã đơn đặt theo mã bàn và trạng thái chưa thanh toán ("false")
                int madondat = (int) donDatDAO.LayMaDonTheoMaBan(maban, "false");

                // Kiểm tra món ăn đã tồn tại trong chi tiết đơn đặt hay chưa
                boolean ktra = chiTietDonDatDAO.KiemTraMonTonTai(madondat, mamon);
                if (ktra) {
                    // Nếu món đã tồn tại, cập nhật số lượng
                    int sluongcu = chiTietDonDatDAO.LaySLMonTheoMaDon(madondat, mamon); // Lấy số lượng cũ
                    int sluongmoi = Integer.parseInt(txtl_soluongActivity_SoLuong.getEditText().getText().toString()); // Lấy số lượng mới từ input
                    int tongsl = sluongcu + sluongmoi; // Tổng số lượng mới

                    // Tạo đối tượng ChiTietDonDat để cập nhật
                    ChiTietDonDat chiTietDonDat = new ChiTietDonDat();
                    chiTietDonDat.setMaMon(mamon); // Gán mã món
                    chiTietDonDat.setMaDonDat(madondat); // Gán mã đơn đặt
                    chiTietDonDat.setSoLuong(tongsl); // Gán số lượng mới

                    // Thực hiện cập nhật số lượng trong cơ sở dữ liệu
                    boolean ktracapnhat = chiTietDonDatDAO.CapNhatSL(chiTietDonDat);
                    if (ktracapnhat) {
                        // Nếu cập nhật thành công
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.add_sucessful), Toast.LENGTH_SHORT).show();
                    } else {
                        // Nếu cập nhật thất bại
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.add_failed), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Nếu món chưa tồn tại, thêm mới món vào chi tiết đơn đặt
                    int sluong = Integer.parseInt(txtl_soluongActivity_SoLuong.getEditText().getText().toString()); // Lấy số lượng
                    ChiTietDonDat chiTietDonDat = new ChiTietDonDat();
                    chiTietDonDat.setMaMon(mamon); // Gán mã món
                    chiTietDonDat.setMaDonDat(madondat); // Gán mã đơn đặt
                    chiTietDonDat.setSoLuong(sluong); // Gán số lượng

                    // Thực hiện thêm mới chi tiết đơn đặt vào cơ sở dữ liệu
                    boolean ktracapnhat = chiTietDonDatDAO.ThemChiTietDonDat(chiTietDonDat);
                    if (ktracapnhat) {
                        // Nếu thêm thành công
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.add_sucessful), Toast.LENGTH_SHORT).show();
                    } else {
                        // Nếu thêm thất bại
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.add_failed), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    // Phương thức kiểm tra tính hợp lệ của số lượng
    private boolean validateAmount() {
        String val = txtl_soluongActivity_SoLuong.getEditText().getText().toString().trim(); // Lấy giá trị từ input và loại bỏ khoảng trắng
        if (val.isEmpty()) {
            // Nếu giá trị rỗng, hiển thị lỗi
            txtl_soluongActivity_SoLuong.setError(getResources().getString(R.string.not_empty));
            return false;
        } else if (!val.matches("\\d+(?:\\.\\d+)?")) {
            // Nếu giá trị không phải số hợp lệ, hiển thị lỗi
            txtl_soluongActivity_SoLuong.setError("Số lượng không hợp lệ");
            return false;
        } else {
            // Nếu hợp lệ, xóa lỗi
            txtl_soluongActivity_SoLuong.setError(null);
            txtl_soluongActivity_SoLuong.setErrorEnabled(false);
            return true;
        }
    }
}

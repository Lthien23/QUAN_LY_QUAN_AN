package com.example.quanlyquanan;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;

import com.example.quanlyquanan.Adapter.AdapterThanhToan;
import com.example.quanlyquanan.DAO.BanAnDAO;
import com.example.quanlyquanan.DAO.DonDatDAO;
import com.example.quanlyquanan.DAO.ThanhToanDAO;
import com.example.quanlyquanan.Model.ThanhToan;
import com.example.quanlyquanan.R;

import java.util.List;

public class ThanhToanActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView IMG_payment_backbtn;
    TextView TXT_payment_TenBan, TXT_payment_NgayDat, TXT_payment_TongTien;
    Button BTN_payment_ThanhToan;
    GridView gvDisplayPayment;
    DonDatDAO donDatDAO;
    BanAnDAO banAnDAO;
    ThanhToanDAO thanhToanDAO;
    List<ThanhToan> thanhToanS;
    AdapterThanhToan adapterThanhToan;
    long tongtien = 0;
    int maban, madondat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanh_toan);

        //region thuộc tính view
        gvDisplayPayment = findViewById(R.id.gvDisplayPayment);
        IMG_payment_backbtn = findViewById(R.id.img_payment_backbtn);
        TXT_payment_TenBan = findViewById(R.id.txt_payment_TenBan);
        TXT_payment_NgayDat = findViewById(R.id.txt_payment_NgayDat);
        TXT_payment_TongTien = findViewById(R.id.txt_payment_TongTien);
        BTN_payment_ThanhToan = findViewById(R.id.btn_payment_ThanhToan);
        //endregion

        //khởi tạo kết nối csdl
        donDatDAO = new DonDatDAO(this);
        thanhToanDAO = new ThanhToanDAO(this);
        banAnDAO = new BanAnDAO(this);

        //lấy data từ mã bàn được chọn
        Intent intent = getIntent();
        maban = intent.getIntExtra("maban", 0);
        String tenban = intent.getStringExtra("tenban");
        String ngaydat = intent.getStringExtra("ngaydat");

        TXT_payment_TenBan.setText(tenban);
        TXT_payment_NgayDat.setText(ngaydat);

        //kiểm tra mã bàn tồn tại thì hiển thị
        if (maban != 0) {
            HienThiThanhToan();

            for (int i = 0; i < thanhToanS.size(); i++) {
                int soluong = thanhToanS.get(i).getSoLuong();
                int giatien = thanhToanS.get(i).getGiaTien();
                tongtien += (soluong * giatien);
            }
            TXT_payment_TongTien.setText(String.valueOf(tongtien) + " VNĐ");
        }

        BTN_payment_ThanhToan.setOnClickListener(this);
        IMG_payment_backbtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btn_payment_ThanhToan:
                showPaymentMethodDialog(); // Hiển thị hộp thoại chọn phương thức thanh toán
                break;

            case R.id.img_payment_backbtn:
                finish();
                break;
        }
    }

    // Phương thức hiển thị hộp thoại lựa chọn phương thức thanh toán
    private void showPaymentMethodDialog() {
        // Tạo một AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Chọn phương thức thanh toán");

        // Danh sách các tùy chọn thanh toán
        String[] paymentMethods = {"Thanh toán tiền mặt", "Chuyển khoản ngân hàng"};

        // Thiết lập các tùy chọn và xử lý sự kiện khi người dùng chọn
        builder.setItems(paymentMethods, (dialog, which) -> {
            switch (which) {
                case 0: // Thanh toán tiền mặt
                    boolean ktraban = banAnDAO.CapNhatTinhTrangBan(maban, "false");
                    boolean ktradondat = donDatDAO.UpdateTThaiDonTheoMaBan(maban, "true");
                    boolean ktratongtien = donDatDAO.UpdateTongTienDonDat(madondat, String.valueOf(tongtien));
                    if (ktraban && ktradondat && ktratongtien) {
                        HienThiThanhToan();
                        TXT_payment_TongTien.setText("0 VNĐ");
                        Toast.makeText(getApplicationContext(), "Thanh toán thành công!", Toast.LENGTH_SHORT).show();

                        // Chuyển về TrangChuActivity
                        Intent intent = new Intent(ThanhToanActivity.this, TrangChuActivity.class);
                        startActivity(intent);
                        finish(); // Đóng activity hiện tại
                    } else {
                        Toast.makeText(getApplicationContext(), "Lỗi thanh toán!", Toast.LENGTH_SHORT).show();
                    }
                    break;

                case 1: // Chuyển khoản ngân hàng (mở màn hình QR)
                    boolean ktraban1 = banAnDAO.CapNhatTinhTrangBan(maban, "false");
                    boolean ktradondat1 = donDatDAO.UpdateTThaiDonTheoMaBan(maban, "true");
                    boolean ktratongtien1 = donDatDAO.UpdateTongTienDonDat(madondat, String.valueOf(tongtien));
                    if (ktraban1 && ktradondat1 && ktratongtien1) {
                        HienThiThanhToan();
                        TXT_payment_TongTien.setText("0 VNĐ");
                        Toast.makeText(getApplicationContext(), "Thanh toán thành công!", Toast.LENGTH_SHORT).show();

                        // Chuyển về TrangChuActivity
                        Intent intent = new Intent(ThanhToanActivity.this, QR.class);
                        startActivity(intent);
                        finish(); // Đóng activity hiện tại
                    } else {
                        Toast.makeText(getApplicationContext(), "Lỗi thanh toán!", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        });

        // Hiển thị hộp thoại
        builder.show();
    }

    //hiển thị data lên gridview
    private void HienThiThanhToan() {
        madondat = (int) donDatDAO.LayMaDonTheoMaBan(maban, "false");
        thanhToanS = thanhToanDAO.LayDSMonTheoMaDon(madondat);
        adapterThanhToan = new AdapterThanhToan(this, R.layout.thanhtoan, thanhToanS);
        gvDisplayPayment.setAdapter(adapterThanhToan);
        adapterThanhToan.notifyDataSetChanged();
    }
}

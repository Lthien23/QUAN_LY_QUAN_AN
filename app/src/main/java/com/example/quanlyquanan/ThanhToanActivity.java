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

    ImageView img_thanhtoanActivity_backbtn;
    TextView txt_thanhtoanActivity_TenBan, txt_thanhtoanActivity_NgayDat, txt_thanhtoanActivity_TongTien;
    Button btn_thanhtoanActivity_ThanhToan;
    GridView gvthanhtoanActivity;
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

        gvthanhtoanActivity = findViewById(R.id.gvthanhtoanActivity);
        img_thanhtoanActivity_backbtn = findViewById(R.id.img_thanhtoanActivity_backbtn);
        txt_thanhtoanActivity_TenBan = findViewById(R.id.txt_thanhtoanActivity_TenBan);
        txt_thanhtoanActivity_NgayDat = findViewById(R.id.txt_thanhtoanActivity_NgayDat);
        txt_thanhtoanActivity_TongTien = findViewById(R.id.txt_thanhtoanActivity_TongTien);
        btn_thanhtoanActivity_ThanhToan = findViewById(R.id.btn_thanhtoanActivity_ThanhToan);



        donDatDAO = new DonDatDAO(this);
        thanhToanDAO = new ThanhToanDAO(this);
        banAnDAO = new BanAnDAO(this);


        Intent intent = getIntent();
        maban = intent.getIntExtra("maban", 0);
        String tenban = intent.getStringExtra("tenban");
        String ngaydat = intent.getStringExtra("ngaydat");

        txt_thanhtoanActivity_TenBan.setText(tenban);
        txt_thanhtoanActivity_NgayDat.setText(ngaydat);


        if (maban != 0) {
            HienThiThanhToan();

            for (int i = 0; i < thanhToanS.size(); i++) {
                int soluong = thanhToanS.get(i).getSoLuong();
                int giatien = thanhToanS.get(i).getGiaTien();
                tongtien += (soluong * giatien);
            }
            txt_thanhtoanActivity_TongTien.setText(String.valueOf(tongtien) + " VNĐ");
        }

        btn_thanhtoanActivity_ThanhToan.setOnClickListener(this);
        img_thanhtoanActivity_backbtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btn_thanhtoanActivity_ThanhToan:
                showPaymentMethodDialog();
                break;

            case R.id.img_thanhtoanActivity_backbtn:
                finish();
                break;
        }
    }

    private void showPaymentMethodDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Chọn phương thức thanh toán");

        String[] paymentMethods = {"Thanh toán tiền mặt", "Chuyển khoản ngân hàng"};

        builder.setItems(paymentMethods, (dialog, which) -> {
            switch (which) {
                case 0:
                    boolean ktraban = banAnDAO.CapNhatTinhTrangBan(maban, "false");
                    boolean ktradondat = donDatDAO.UpdateTThaiDonTheoMaBan(maban, "true");
                    boolean ktratongtien = donDatDAO.UpdateTongTienDonDat(madondat, String.valueOf(tongtien));
                    if (ktraban && ktradondat && ktratongtien) {
                        HienThiThanhToan();
                        txt_thanhtoanActivity_TongTien.setText("0 VNĐ");
                        Toast.makeText(getApplicationContext(), "Thanh toán thành công!", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(ThanhToanActivity.this, TrangChuActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "Lỗi thanh toán!", Toast.LENGTH_SHORT).show();
                    }
                    break;

                case 1:
                    boolean ktraban1 = banAnDAO.CapNhatTinhTrangBan(maban, "false");
                    boolean ktradondat1 = donDatDAO.UpdateTThaiDonTheoMaBan(maban, "true");
                    boolean ktratongtien1 = donDatDAO.UpdateTongTienDonDat(madondat, String.valueOf(tongtien));
                    if (ktraban1 && ktradondat1 && ktratongtien1) {
                        HienThiThanhToan();
                        txt_thanhtoanActivity_TongTien.setText("0 VNĐ");
                        Toast.makeText(getApplicationContext(), "Thanh toán thành công!", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(ThanhToanActivity.this, QR.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "Lỗi thanh toán!", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        });

        builder.show();
    }

    private void HienThiThanhToan() {
        madondat = (int) donDatDAO.LayMaDonTheoMaBan(maban, "false");
        thanhToanS = thanhToanDAO.LayDSMonTheoMaDon(madondat);
        adapterThanhToan = new AdapterThanhToan(this, R.layout.thanhtoan, thanhToanS);
        gvthanhtoanActivity.setAdapter(adapterThanhToan);
        adapterThanhToan.notifyDataSetChanged();
    }
}

package com.example.quanlyquanan;

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

    TextInputLayout txtl_soluongActivity_SoLuong;
    Button btn_soluongActivity_DongY;
    int maban, mamon;
    DonDatDAO donDatDAO;
    ChiTietDonDatDAO chiTietDonDatDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_so_luong);

        //Lấy đối tượng view
        txtl_soluongActivity_SoLuong = (TextInputLayout)findViewById(R.id.txtl_soluongActivity_SoLuong);
        btn_soluongActivity_DongY = (Button)findViewById(R.id.btn_soluongActivity_DongY);

        //khởi tạo kết nối csdl
        donDatDAO = new DonDatDAO(this);
        chiTietDonDatDAO = new ChiTietDonDatDAO(this);

        //Lấy thông tin từ bàn được chọn
        Intent intent = getIntent();
        maban = intent.getIntExtra("maban",0);
        mamon = intent.getIntExtra("mamon",0);

        btn_soluongActivity_DongY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!validateAmount()){
                    return;
                }

                int madondat = (int) donDatDAO.LayMaDonTheoMaBan(maban,"false");
                boolean ktra = chiTietDonDatDAO.KiemTraMonTonTai(madondat,mamon);
                if(ktra){
                    //update số lượng món đã chọn
                    int sluongcu = chiTietDonDatDAO.LaySLMonTheoMaDon(madondat,mamon);
                    int sluongmoi = Integer.parseInt(txtl_soluongActivity_SoLuong.getEditText().getText().toString());
                    int tongsl = sluongcu + sluongmoi;

                    ChiTietDonDat chiTietDonDat = new ChiTietDonDat();
                    chiTietDonDat.setMaMon(mamon);
                    chiTietDonDat.setMaDonDat(madondat);
                    chiTietDonDat.setSoLuong(tongsl);

                    boolean ktracapnhat = chiTietDonDatDAO.CapNhatSL(chiTietDonDat);
                    if(ktracapnhat){
                        Toast.makeText(getApplicationContext(),getResources().getString(R.string.add_sucessful),Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(getApplicationContext(),getResources().getString(R.string.add_failed),Toast.LENGTH_SHORT).show();
                    }
                }else {
                    //thêm số lượng món nếu chưa chọn món này
                    int sluong = Integer.parseInt(txtl_soluongActivity_SoLuong.getEditText().getText().toString());
                    ChiTietDonDat chiTietDonDat = new ChiTietDonDat();
                    chiTietDonDat.setMaMon(mamon);
                    chiTietDonDat.setMaDonDat(madondat);
                    chiTietDonDat.setSoLuong(sluong);

                    boolean ktracapnhat = chiTietDonDatDAO.ThemChiTietDonDat(chiTietDonDat);
                    if(ktracapnhat){
                        Toast.makeText(getApplicationContext(),getResources().getString(R.string.add_sucessful),Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(getApplicationContext(),getResources().getString(R.string.add_failed),Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    //validate số lượng
    private boolean validateAmount(){
        String val = txtl_soluongActivity_SoLuong.getEditText().getText().toString().trim();
        if(val.isEmpty()){
            txtl_soluongActivity_SoLuong.setError(getResources().getString(R.string.not_empty));
            return false;
        }else if(!val.matches(("\\d+(?:\\.\\d+)?"))){
            txtl_soluongActivity_SoLuong.setError("Số lượng không hợp lệ");
            return false;
        }else {
            txtl_soluongActivity_SoLuong.setError(null);
            txtl_soluongActivity_SoLuong.setErrorEnabled(false);
            return true;
        }
    }
}
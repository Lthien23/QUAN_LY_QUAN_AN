package com.example.quanlyquanan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.quanlyquanan.Adapter.AdapterThanhToan;
import com.example.quanlyquanan.DAO.BanAnDAO;
import com.example.quanlyquanan.DAO.NhanVienDAO;
import com.example.quanlyquanan.DAO.ThanhToanDAO;
import com.example.quanlyquanan.Model.NhanVien;
import com.example.quanlyquanan.Model.ThanhToan;
import com.example.quanlyquanan.R;

import java.util.List;

public class ThongKeActivity extends AppCompatActivity {

    ImageView img_detailstatistic_backbtn;
    TextView txt_detailstatistic_MaDon,txt_detailstatistic_NgayDat,txt_detailstatistic_TenBan
            ,txt_detailstatistic_TenNV,txt_detailstatistic_TongTien;
    GridView gvDetailStatistic;
    int madon, manv, maban;
    String ngaydat, tongtien;
    NhanVienDAO nhanVienDAO;
    BanAnDAO banAnDAO;
    List<ThanhToan> thanhToanList;
    ThanhToanDAO thanhToanDAO;
    AdapterThanhToan adapterThanhToan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_ke);

        //Lấy thông tin từ display statistic
        Intent intent = getIntent();
        madon = intent.getIntExtra("madon",0);
        manv = intent.getIntExtra("manv",0);
        maban = intent.getIntExtra("maban",0);
        ngaydat = intent.getStringExtra("ngaydat");
        tongtien = intent.getStringExtra("tongtien");

        //region Thuộc tính bên view
        img_detailstatistic_backbtn = (ImageView)findViewById(R.id.img_detailstatistic_backbtn);
        txt_detailstatistic_MaDon = (TextView)findViewById(R.id.txt_detailstatistic_MaDon);
        txt_detailstatistic_NgayDat = (TextView)findViewById(R.id.txt_detailstatistic_NgayDat);
        txt_detailstatistic_TenBan = (TextView)findViewById(R.id.txt_detailstatistic_TenBan);
        txt_detailstatistic_TenNV = (TextView)findViewById(R.id.txt_detailstatistic_TenNV);
        txt_detailstatistic_TongTien = (TextView)findViewById(R.id.txt_detailstatistic_TongTien);
        gvDetailStatistic = (GridView)findViewById(R.id.gvDetailStatistic);
        //endregion

        //khởi tạo lớp dao mở kết nối csdl
        nhanVienDAO = new NhanVienDAO(this);
        banAnDAO = new BanAnDAO(this);
        thanhToanDAO = new ThanhToanDAO(this);

        //chỉ hiển thị nếu lấy đc mã đơn đc chọn
        if (madon !=0){
            txt_detailstatistic_MaDon.setText("Mã đơn: "+madon);
            txt_detailstatistic_NgayDat.setText(ngaydat);
            txt_detailstatistic_TongTien.setText(tongtien+" VNĐ");

            NhanVien nhanVien = nhanVienDAO.LayNVTheoMa(manv);
            txt_detailstatistic_TenNV.setText(nhanVien.getHOTENNV());
            txt_detailstatistic_TenBan.setText(banAnDAO.LayTenBanTheoMa(maban));

            HienThiDSCTDD();
        }

        img_detailstatistic_backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
            }
        });
    }

    private void HienThiDSCTDD(){
        thanhToanList = thanhToanDAO.LayDSMonTheoMaDon(madon);
        adapterThanhToan = new AdapterThanhToan(this,R.layout.thanhtoan,thanhToanList);
        gvDetailStatistic.setAdapter(adapterThanhToan);
        adapterThanhToan.notifyDataSetChanged();
    }
}
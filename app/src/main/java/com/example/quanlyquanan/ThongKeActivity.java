package com.example.quanlyquanan;

import static android.app.PendingIntent.getActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quanlyquanan.Adapter.AdapterThanhToan;
import com.example.quanlyquanan.DAO.BanAnDAO;
import com.example.quanlyquanan.DAO.NhanVienDAO;
import com.example.quanlyquanan.DAO.ThanhToanDAO;
import com.example.quanlyquanan.Model.NhanVien;
import com.example.quanlyquanan.Model.ThanhToan;
import com.example.quanlyquanan.R;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class ThongKeActivity extends AppCompatActivity {

    ImageView img_thongkeActivity_backbtn;
    TextView txt_thongkeActivity_MaDon, txt_thongkeActivity_NgayDat, txt_thongkeActivity_TenBan,
            txt_thongkeActivity_TenNV, txt_thongkeActivity_TongTien;
    GridView gvthongkeActivity;
    int madon, manv, maban;
    String ngaydat, tongtien;
    NhanVienDAO nhanVienDAO;
    BanAnDAO banAnDAO;
    List<ThanhToan> thanhToanList;
    ThanhToanDAO thanhToanDAO;
    AdapterThanhToan adapterThanhToan;
    Button btnInHoaDonWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_ke);

        Intent intent = getIntent();
        madon = intent.getIntExtra("madon", 0);
        manv = intent.getIntExtra("manv", 0);
        maban = intent.getIntExtra("maban", 0);
        ngaydat = intent.getStringExtra("ngaydat");
        tongtien = intent.getStringExtra("tongtien");

        img_thongkeActivity_backbtn = findViewById(R.id.img_thongkeActivity_backbtn);
        txt_thongkeActivity_MaDon = findViewById(R.id.txt_thongkeActivity_MaDon);
        txt_thongkeActivity_NgayDat = findViewById(R.id.txt_thongkeActivity_NgayDat);
        txt_thongkeActivity_TenBan = findViewById(R.id.txt_thongkeActivity_TenBan);
        txt_thongkeActivity_TenNV = findViewById(R.id.txt_thongkeActivity_TenNV);
        txt_thongkeActivity_TongTien = findViewById(R.id.txt_thongkeActivity_TongTien);
        gvthongkeActivity = findViewById(R.id.gvthongkeActivity);
        btnInHoaDonWord = findViewById(R.id.btnInHoaDonWord);

        nhanVienDAO = new NhanVienDAO(this);
        banAnDAO = new BanAnDAO(this);
        thanhToanDAO = new ThanhToanDAO(this);

        if (madon != 0) {
            txt_thongkeActivity_MaDon.setText("Mã đơn: " + madon);
            txt_thongkeActivity_NgayDat.setText(ngaydat);
            txt_thongkeActivity_TongTien.setText(tongtien + " VNĐ");
            NhanVien nhanVien = nhanVienDAO.LayNVTheoMa(manv);
            txt_thongkeActivity_TenNV.setText(nhanVien.getHOTENNV());
            txt_thongkeActivity_TenBan.setText(banAnDAO.LayTenBanTheoMa(maban));
            HienThiDSCTDD();
        }

        img_thongkeActivity_backbtn.setOnClickListener(v -> {
            finish();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        });

        btnInHoaDonWord.setOnClickListener(v -> {
            try {
                exportToWord();
            } catch (IOException e) {
                Toast.makeText(this, "Lỗi xuất Word: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void HienThiDSCTDD() {
        thanhToanList = thanhToanDAO.LayDSMonTheoMaDon(madon);
        adapterThanhToan = new AdapterThanhToan(this, R.layout.thanhtoan, thanhToanList);
        gvthongkeActivity.setAdapter(adapterThanhToan);
        adapterThanhToan.notifyDataSetChanged();
    }

    private void exportToWord() throws IOException {
        XWPFDocument document = new XWPFDocument();

        // Tiêu đề
        XWPFParagraph title = document.createParagraph();
        XWPFRun titleRun = title.createRun();
        titleRun.setText("Hóa đơn");
        titleRun.setBold(true);
        titleRun.setFontSize(16);

        // Bảng 1: Thông tin hóa đơn
        XWPFTable tableInfo = document.createTable();
        XWPFTableRow headerRowInfo = tableInfo.getRow(0);
        headerRowInfo.getCell(0).setText("Mã Đơn");
        headerRowInfo.addNewTableCell().setText("Ngày Đặt");
        headerRowInfo.addNewTableCell().setText("Tên Bàn");
        headerRowInfo.addNewTableCell().setText("Tên NV");
        headerRowInfo.addNewTableCell().setText("Tổng Tiền");

        XWPFTableRow dataRowInfo = tableInfo.createRow();
        dataRowInfo.getCell(0).setText(String.valueOf(madon));
        dataRowInfo.getCell(1).setText(ngaydat);
        dataRowInfo.getCell(2).setText(banAnDAO.LayTenBanTheoMa(maban));
        dataRowInfo.getCell(3).setText(nhanVienDAO.LayNVTheoMa(manv).getHOTENNV());
        dataRowInfo.getCell(4).setText(tongtien);

        // Thêm dòng trống giữa hai bảng
        XWPFParagraph space = document.createParagraph();
        space.createRun().addBreak();

        // Bảng 2: Chi tiết món ăn
        XWPFTable tableItems = document.createTable();
        XWPFTableRow headerRowItems = tableItems.getRow(0);
        headerRowItems.getCell(0).setText("Tên Món");
        headerRowItems.addNewTableCell().setText("Số Lượng");
        headerRowItems.addNewTableCell().setText("Đơn Giá");

        // Thêm các món ăn vào bảng
        for (ThanhToan thanhToan : thanhToanList) {
            XWPFTableRow itemRow = tableItems.createRow();
            itemRow.getCell(0).setText(thanhToan.getTenMon());
            itemRow.getCell(1).setText(String.valueOf(thanhToan.getSoLuong()));
            itemRow.getCell(2).setText(String.valueOf(thanhToan.getGiaTien()));
        }

        // Lưu file Word
        File path = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);
        if (!path.exists()) {
            path.mkdirs();
        }
        File file = new File(path, "HoaDon_" + madon + ".docx");
        try (FileOutputStream outputStream = new FileOutputStream(file)) {
            document.write(outputStream);
        }

        Toast.makeText(this, "Hóa đơn đã được xuất thành công dưới dạng Word!", Toast.LENGTH_LONG).show();
    }

}

package com.example.quanlyquanan.DAO;

// Import các thư viện cần thiết
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.core.util.Pair;

import com.example.quanlyquanan.Model.ChiTietDonDat;
import com.example.quanlyquanan.Model.DonDat;
import com.example.quanlyquanan.Model.LoaiMon;
import com.example.quanlyquanan.Database.CreateDatabase;

import java.util.ArrayList;
import java.util.List;

// Lớp DonDatDAO dùng để thao tác với bảng đơn đặt hàng trong cơ sở dữ liệu
public class DonDatDAO {

    SQLiteDatabase database; // Biến đại diện cho cơ sở dữ liệu SQLite

    // Constructor: Mở kết nối đến cơ sở dữ liệu
    public DonDatDAO(Context context) {
        CreateDatabase createDatabase = new CreateDatabase(context); // Tạo đối tượng CreateDatabase
        database = createDatabase.open(); // Mở kết nối đến cơ sở dữ liệu
    }

    // Thêm một đơn đặt hàng mới vào cơ sở dữ liệu
    public long ThemDonDat(DonDat donDat) {
        ContentValues contentValues = new ContentValues(); // Tạo đối tượng ContentValues để chứa dữ liệu
        contentValues.put(CreateDatabase.TBL_DONDAT_MABAN, donDat.getMaBan()); // Gán mã bàn
        contentValues.put(CreateDatabase.TBL_DONDAT_MANV, donDat.getMaNV()); // Gán mã nhân viên
        contentValues.put(CreateDatabase.TBL_DONDAT_NGAYDAT, donDat.getNgayDat()); // Gán ngày đặt
        contentValues.put(CreateDatabase.TBL_DONDAT_TINHTRANG, donDat.getTinhTrang()); // Gán tình trạng
        contentValues.put(CreateDatabase.TBL_DONDAT_TONGTIEN, donDat.getTongTien()); // Gán tổng tiền

        // Chèn dữ liệu vào bảng đơn đặt và lấy mã đơn đặt vừa tạo
        long madondat = database.insert(CreateDatabase.TBL_DONDAT, null, contentValues);
        return madondat; // Trả về mã đơn đặt
    }

    // Lấy danh sách tất cả các đơn đặt hàng
    public List<DonDat> LayDSDonDat() {
        List<DonDat> donDatDTOS = new ArrayList<>(); // Tạo danh sách để chứa các đơn đặt
        String query = "SELECT * FROM " + CreateDatabase.TBL_DONDAT; // Câu truy vấn lấy toàn bộ dữ liệu
        Cursor cursor = database.rawQuery(query, null); // Thực thi câu truy vấn
        cursor.moveToFirst(); // Di chuyển con trỏ về bản ghi đầu tiên

        while (!cursor.isAfterLast()) { // Lặp qua từng bản ghi
            DonDat donDat = new DonDat(); // Tạo đối tượng DonDat
            donDat.setMaDonDat(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TBL_DONDAT_MADONDAT))); // Gán mã đơn đặt
            donDat.setMaBan(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TBL_DONDAT_MABAN))); // Gán mã bàn
            donDat.setTongTien(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_DONDAT_TONGTIEN))); // Gán tổng tiền
            donDat.setTinhTrang(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_DONDAT_TINHTRANG))); // Gán tình trạng
            donDat.setNgayDat(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_DONDAT_NGAYDAT))); // Gán ngày đặt
            donDat.setMaNV(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TBL_DONDAT_MANV))); // Gán mã nhân viên

            donDatDTOS.add(donDat); // Thêm đối tượng DonDat vào danh sách
            cursor.moveToNext(); // Di chuyển con trỏ đến bản ghi tiếp theo
        }

        cursor.close(); // Đóng con trỏ sau khi sử dụng
        return donDatDTOS; // Trả về danh sách các đơn đặt
    }

    // Lấy danh sách đơn đặt hàng theo ngày
    public List<DonDat> LayDSDonDatNgay(String ngaythang) {
        List<DonDat> donDatS = new ArrayList<>();
        String query = "SELECT * FROM " + CreateDatabase.TBL_DONDAT + " WHERE " +
                CreateDatabase.TBL_DONDAT_NGAYDAT + " LIKE '" + ngaythang + "'";
        Cursor cursor = database.rawQuery(query, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            DonDat donDat = new DonDat();
            donDat.setMaDonDat(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TBL_DONDAT_MADONDAT)));
            donDat.setMaBan(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TBL_DONDAT_MABAN)));
            donDat.setTongTien(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_DONDAT_TONGTIEN)));
            donDat.setTinhTrang(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_DONDAT_TINHTRANG)));
            donDat.setNgayDat(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_DONDAT_NGAYDAT)));
            donDat.setMaNV(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TBL_DONDAT_MANV)));

            donDatS.add(donDat);
            cursor.moveToNext();
        }

        cursor.close();
        return donDatS;
    }

    // Lấy mã đơn đặt hàng theo mã bàn và tình trạng
    public long LayMaDonTheoMaBan(int maban, String tinhtrang) {
        String query = "SELECT * FROM " + CreateDatabase.TBL_DONDAT + " WHERE " +
                CreateDatabase.TBL_DONDAT_MABAN + " = '" + maban + "' AND " +
                CreateDatabase.TBL_DONDAT_TINHTRANG + " = '" + tinhtrang + "'";
        long magoimon = 0;
        Cursor cursor = database.rawQuery(query, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            magoimon = cursor.getInt(cursor.getColumnIndex(CreateDatabase.TBL_DONDAT_MADONDAT));
            cursor.moveToNext();
        }

        cursor.close();
        return magoimon;
    }

    // Cập nhật tổng tiền của đơn đặt hàng
    public boolean UpdateTongTienDonDat(int madondat, String tongtien) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TBL_DONDAT_TONGTIEN, tongtien);

        long ktra = database.update(CreateDatabase.TBL_DONDAT, contentValues,
                CreateDatabase.TBL_DONDAT_MADONDAT + " = " + madondat, null);
        return ktra != 0; // Trả về true nếu cập nhật thành công
    }

    // Cập nhật tình trạng đơn đặt hàng theo mã bàn
    public boolean UpdateTThaiDonTheoMaBan(int maban, String tinhtrang) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TBL_DONDAT_TINHTRANG, tinhtrang);

        long ktra = database.update(CreateDatabase.TBL_DONDAT, contentValues,
                CreateDatabase.TBL_DONDAT_MABAN + " = '" + maban + "'", null);
        return ktra != 0; // Trả về true nếu cập nhật thành công
    }

    // Lấy tổng doanh thu theo ngày
    public List<Pair<String, Float>> LayTongDoanhThuTheoNgay() {
        List<Pair<String, Float>> doanhThuTheoNgay = new ArrayList<>();
        String query = "SELECT " + CreateDatabase.TBL_DONDAT_NGAYDAT + ", SUM(" +
                CreateDatabase.TBL_DONDAT_TONGTIEN + ") AS TongTien " +
                "FROM " + CreateDatabase.TBL_DONDAT +
                " GROUP BY " + CreateDatabase.TBL_DONDAT_NGAYDAT;

        Cursor cursor = database.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                String ngayDat = cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_DONDAT_NGAYDAT));
                float tongTien = cursor.getFloat(cursor.getColumnIndex("TongTien"));
                doanhThuTheoNgay.add(new Pair<>(ngayDat, tongTien)); // Thêm cặp giá trị ngày - doanh thu vào danh sách
            } while (cursor.moveToNext());
        }

        cursor.close();
        return doanhThuTheoNgay;
    }
}

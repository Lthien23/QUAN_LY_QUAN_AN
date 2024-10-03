package com.example.lethuyhien.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class dbQLQA extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "QLQA.db";
    private static final int DATABASE_VERSION = 1;

    // Account
    private static final String TABLE_TaiKhoan = "Account";
    private static final String COLUMN_USERNAME_ACCOUNT = "username";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_idNhanVien_ACCOUNT = "idNhanVien";

    // NhanVien
    private static final String TABLE_NhanVien = "NhanVien";
    private static final String COLUMN_idNhanVien = "idNhanVien";
    private static final String COLUMN_Hoten = "Hoten";
    private static final String COLUMN_Ngaysinh = "Ngaysinh";
    private static final String COLUMN_Sdt = "Sdt";
    private static final String COLUMN_Gioitinh = "Gioitinh";

    // MonAn
    private static final String TABLE_MonAn = "MonAn";
    private static final String COLUMN_idMonAn = "idMonAn";
    private static final String COLUMN_TenMonAn = "TenMonAn";
    private static final String COLUMN_Image = "image";
    private static final String COLUMN_DonGia = "DonGia";
    private static final String COLUMN_MoTa = "MoTa";
    private static final String COLUMN_LoaiMon = "LoaiMon";

    // Ban
    private static final String TABLE_Ban = "Ban";
    private static final String COLUMN_idBan = "idBan";
    private static final String COLUMN_TrangThaiBan = "TrangThai";
    private static final String COLUMN_SoChoNgoi = "SoChoNgoi";

    // HoaDon
    private static final String TABLE_HoaDon = "HoaDon";
    private static final String COLUMN_MaHoaDon = "MaHoaDon";
    private static final String COLUMN_MaBan = "MaBan";
    private static final String COLUMN_MaMon = "MaMon";
    private static final String COLUMN_ThoiGian = "ThoiGian";
    private static final String COLUMN_TrangThaiHD = "TrangThai";
    private static final String COLUMN_GhiChu = "GhiChu";
    private static final String COLUMN_TenMon = "TenMon";
    private static final String COLUMN_SoLuongBan = "SoLuongBan";
    private static final String COLUMN_TongTien = "TongTien";

    public dbQLQA(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Bảng Account
        String CREATE_TABLE_ACCOUNT = "CREATE TABLE " + TABLE_TaiKhoan + "("
                + COLUMN_USERNAME_ACCOUNT + " TEXT PRIMARY KEY,"
                + COLUMN_PASSWORD + " TEXT,"
                + COLUMN_idNhanVien_ACCOUNT + " INTEGER" + ");";
        db.execSQL(CREATE_TABLE_ACCOUNT);

        // Bảng NhanVien
        String CREATE_TABLE_NHANVIEN = "CREATE TABLE " + TABLE_NhanVien + "("
                + COLUMN_idNhanVien + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_Hoten + " TEXT,"
                + COLUMN_Ngaysinh + " DATE,"
                + COLUMN_Sdt + " TEXT,"
                + COLUMN_Gioitinh + " TEXT" + ");";
        db.execSQL(CREATE_TABLE_NHANVIEN);

        // Bảng MonAn
        String CREATE_TABLE_MONAN = "CREATE TABLE " + TABLE_MonAn + "("
                + COLUMN_idMonAn + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_TenMonAn + " TEXT,"
                + COLUMN_Image + " INTEGER,"
                + COLUMN_DonGia + " DOUBLE,"
                + COLUMN_MoTa + " TEXT,"
                + COLUMN_LoaiMon + " TEXT" + ");";
        db.execSQL(CREATE_TABLE_MONAN);

        // Bảng Ban
        String CREATE_TABLE_BAN = "CREATE TABLE " + TABLE_Ban + "("
                + COLUMN_idBan + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_TrangThaiBan + " TEXT,"
                + COLUMN_SoChoNgoi + " INTEGER" + ");";
        db.execSQL(CREATE_TABLE_BAN);

        // Bảng HoaDon
        String CREATE_TABLE_HOADON = "CREATE TABLE " + TABLE_HoaDon + "("
                + COLUMN_MaHoaDon + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_MaBan + " INTEGER,"
                + COLUMN_MaMon + " INTEGER,"
                + COLUMN_ThoiGian + " DATE,"
                + COLUMN_TrangThaiHD + " TEXT,"
                + COLUMN_GhiChu + " TEXT,"
                + COLUMN_TenMon + " TEXT,"
                + COLUMN_SoLuongBan + " INTEGER,"
                + COLUMN_TongTien + " DOUBLE" + ");";
        db.execSQL(CREATE_TABLE_HOADON);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TaiKhoan);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NhanVien);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MonAn);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Ban);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HoaDon);
        onCreate(db);
    }

    // Phương thức thêm tài khoản
    public void addAccount(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME_ACCOUNT, username);
        values.put(COLUMN_PASSWORD, password);
        db.insert(TABLE_TaiKhoan, null, values);
        db.close();
    }

    // Phương thức kiểm tra tài khoản
//    public boolean checkAccount(String username, String password) {
//        SQLiteDatabase db = this.getReadableDatabase();
//        String query = "SELECT * FROM " + TABLE_TaiKhoan + " WHERE " + COLUMN_USERNAME_ACCOUNT + " = ? AND " + COLUMN_PASSWORD + " = ?";
//        Cursor cursor = db.rawQuery(query, new String[]{username, password});
//        boolean exists = cursor.moveToFirst();
//        cursor.close();
//        db.close();
//        return exists;
//   }
}

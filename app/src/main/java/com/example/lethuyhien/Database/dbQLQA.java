package com.example.lethuyhien.Database;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class dbQLQA extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "QLQA.db";
    public static final int DATABASE_VERSION = 1;

    // Account
    public static final String TABLE_TaiKhoan = "Account";
    public static final String COLUMN_USERNAME_ACCOUNT = "username";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_idNhanVien_ACCOUNT = "idNhanVien";

    // NhanVien
    public static final String TABLE_NhanVien = "NhanVien";
    public static final String COLUMN_idNhanVien = "idNhanVien";
    public static final String COLUMN_Hoten = "Hoten";
    public static final String COLUMN_Ngaysinh = "Ngaysinh";
    public static final String COLUMN_Sdt = "Sdt";
    public static final String COLUMN_Gioitinh = "Gioitinh";

    // MonAn
    public static final String TABLE_MonAn = "MonAn";
    public static final String COLUMN_idMonAn = "idMonAn";
    public static final String COLUMN_TenMonAn = "TenMonAn";
    public static final String COLUMN_Image = "image";
    public static final String COLUMN_DonGia = "DonGia";
    public static final String COLUMN_MoTa = "MoTa";
    public static final String COLUMN_LoaiMon = "LoaiMon";

    // Ban
    public static final String TABLE_Ban = "Ban";
    public static final String COLUMN_idBan = "idBan";
    public static final String COLUMN_TrangThaiBan = "TrangThai";
    public static final String COLUMN_SoChoNgoi = "SoChoNgoi";

    // HoaDon
    public static final String TABLE_HoaDon = "HoaDon";
    public static final String COLUMN_MaHoaDon = "MaHoaDon";
    public static final String COLUMN_MaBan = "MaBan";
    public static final String COLUMN_MaMon = "MaMon";
    public static final String COLUMN_ThoiGian = "ThoiGian";
    public static final String COLUMN_TrangThaiHD = "TrangThai";
    public static final String COLUMN_GhiChu = "GhiChu";
    public static final String COLUMN_TenMon = "TenMon";
    public static final String COLUMN_SoLuongBan = "SoLuongBan";
    public static final String COLUMN_TongTien = "TongTien";

    public dbQLQA(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Bảng Account
        // Bảng Account
        String CREATE_TABLE_ACCOUNT = "CREATE TABLE " + TABLE_TaiKhoan + "("
                + COLUMN_USERNAME_ACCOUNT + " TEXT PRIMARY KEY,"
                + COLUMN_PASSWORD + " TEXT,"
                + COLUMN_idNhanVien_ACCOUNT + " INTEGER,"
                + "FOREIGN KEY (" + COLUMN_idNhanVien_ACCOUNT + ") REFERENCES " + TABLE_NhanVien + "(" + COLUMN_idNhanVien + ")"
                + ");";
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
                + COLUMN_TongTien + " DOUBLE,"
                // Khóa ngoại liên kết với bảng Ban
                + " FOREIGN KEY (" + COLUMN_MaBan + ") REFERENCES " + TABLE_Ban + "(" + COLUMN_idBan + "),"
                // Khóa ngoại liên kết với bảng MonAn
                + " FOREIGN KEY (" + COLUMN_MaMon + ") REFERENCES " + TABLE_MonAn + "(" + COLUMN_idMonAn + ")"
                + ");";
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

    public void addHoaDon(int maBan, int maMon, String thoiGian, String trangThai, String ghiChu, String tenMon, int soLuong, double tongTien) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_MaBan, maBan);
        values.put(COLUMN_MaMon, maMon);
        values.put(COLUMN_ThoiGian, thoiGian);
        values.put(COLUMN_TrangThaiHD, trangThai);
        values.put(COLUMN_GhiChu, ghiChu);
        values.put(COLUMN_TenMon, tenMon);
        values.put(COLUMN_SoLuongBan, soLuong);
        values.put(COLUMN_TongTien, tongTien);
        db.insert(TABLE_HoaDon, null, values);
        db.close();
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

    public void registerUser(String hoten, String ngaysinh, String sdt, String gioitinh, String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        try {
            // Thêm nhân viên vào bảng NhanVien
            ContentValues nhanVienValues = new ContentValues();
            nhanVienValues.put(COLUMN_Hoten, hoten);
            nhanVienValues.put(COLUMN_Ngaysinh, ngaysinh);
            nhanVienValues.put(COLUMN_Sdt, sdt);
            nhanVienValues.put(COLUMN_Gioitinh, gioitinh);
            long idNhanVien = db.insert(TABLE_NhanVien, null, nhanVienValues);
            // Thêm tài khoản vào bảng Account, liên kết với idNhanVien
            ContentValues accountValues = new ContentValues();
            accountValues.put(COLUMN_USERNAME_ACCOUNT, username);
            accountValues.put(COLUMN_PASSWORD, password);
            accountValues.put(COLUMN_idNhanVien_ACCOUNT, idNhanVien);
            db.insert(TABLE_TaiKhoan, null, accountValues);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
    }

}
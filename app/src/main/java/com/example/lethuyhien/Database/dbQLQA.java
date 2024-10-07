package com.example.lethuyhien.Database;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.lethuyhien.Model.NhanVien;
import com.example.lethuyhien.Model.Trang_chu;
import com.example.lethuyhien.R;

import java.util.ArrayList;
import java.util.List;

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
    public static final String COLUMN_SoChoNgoi = "SoChoNgoi";
    public static final String COLUMN_TrangThaiBan = "TrangThai";
    public static final String COLUMN_Anh = "image";

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
        String CREATE_TABLE_ACCOUNT = "CREATE TABLE " + TABLE_TaiKhoan + "("
                + COLUMN_USERNAME_ACCOUNT + " TEXT PRIMARY KEY,"
                + COLUMN_PASSWORD + " TEXT,"
                + COLUMN_idNhanVien_ACCOUNT + " INTEGER,"
                + "FOREIGN KEY (" + COLUMN_idNhanVien_ACCOUNT + ") REFERENCES " + TABLE_NhanVien + "(" + COLUMN_idNhanVien + ")"
                + ");";
        db.execSQL(CREATE_TABLE_ACCOUNT);
        // Bảng NhanVien
        String CREATE_TABLE_NHANVIEN = "CREATE TABLE " + TABLE_NhanVien + "("
                + COLUMN_idNhanVien + " INTEGER PRIMARY KEY ,"
                + COLUMN_Hoten + " TEXT,"
                + COLUMN_Ngaysinh + " DATE,"
                + COLUMN_Sdt + " TEXT,"
                + COLUMN_Gioitinh + " TEXT" + ");";
        db.execSQL(CREATE_TABLE_NHANVIEN);

        // Bảng MonAn
        String CREATE_TABLE_MONAN = "CREATE TABLE " + TABLE_MonAn + "("
                + COLUMN_idMonAn + " INTEGER PRIMARY KEY ,"
                + COLUMN_TenMonAn + " TEXT,"
                + COLUMN_Image + " INTEGER,"
                + COLUMN_DonGia + " DOUBLE,"
                + COLUMN_MoTa + " TEXT,"
                + COLUMN_LoaiMon + " TEXT" + ");";
        db.execSQL(CREATE_TABLE_MONAN);
        // Bảng Ban
        String CREATE_TABLE_BAN = "CREATE TABLE " + TABLE_Ban + "("
                + COLUMN_idBan + " INTEGER PRIMARY KEY ,"
                + COLUMN_SoChoNgoi + " TEXT,"
                + COLUMN_Anh + " BLOB,"
                + COLUMN_TrangThaiBan + " TEXT " + ");";
        db.execSQL(CREATE_TABLE_BAN);
        // Bảng HoaDon
        String CREATE_TABLE_HOADON = "CREATE TABLE " + TABLE_HoaDon + "("
                + COLUMN_MaHoaDon + " INTEGER PRIMARY KEY ,"
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
    public boolean checkAccount(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_TaiKhoan + " WHERE " + COLUMN_USERNAME_ACCOUNT + " = ? AND " + COLUMN_PASSWORD + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{username, password});
        boolean exists = cursor.moveToFirst();
        cursor.close();
        db.close();
        return exists;
    }
    // Phương thức trong dbQLQA để lấy thông tin nhân viên dựa trên tên đăng nhập
    public NhanVien getThongTinNhanVien(String username) {
        NhanVien nhanVien = null;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null; // Khai báo con trỏ bên ngoài để đảm bảo nó có thể được đóng bên ngoài khối try

        try {
            // Truy vấn thông tin nhân viên dựa trên tên đăng nhập
            cursor = db.rawQuery("SELECT " + COLUMN_Hoten + ", " + COLUMN_Sdt + " FROM " + TABLE_NhanVien
                    + " INNER JOIN " + TABLE_TaiKhoan
                    + " ON " + TABLE_NhanVien + "." + COLUMN_idNhanVien + " = " + TABLE_TaiKhoan + "." + COLUMN_idNhanVien_ACCOUNT
                    + " WHERE " + COLUMN_USERNAME_ACCOUNT + " = ?", new String[]{username});

            if (cursor != null && cursor.moveToFirst()) {
                int hotenIndex = cursor.getColumnIndex(COLUMN_Hoten);
                int sdtIndex = cursor.getColumnIndex(COLUMN_Sdt);

                // Kiểm tra xem các chỉ số cột có hợp lệ không
                if (hotenIndex != -1 && sdtIndex != -1) {
                    String hoten = cursor.getString(hotenIndex);
                    String sdt = cursor.getString(sdtIndex);
                    nhanVien = new NhanVien(hoten, sdt);
                } else {
                    // Xử lý khi tên cột không tồn tại
                    Log.e("DBError", "Column names are not valid");
                }
            }
        } catch (Exception e) {
            e.printStackTrace(); // Xử lý lỗi nếu có
        } finally {
            if (cursor != null) {
                cursor.close(); // Đảm bảo con trỏ được đóng
            }
            db.close(); // Đóng cơ sở dữ liệu
        }
        return nhanVien; // Trả về thông tin nhân viên
    }

    public boolean kiemTraTrangThaiBan(int idBan) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + COLUMN_TrangThaiBan + " FROM " + TABLE_Ban + " WHERE " + COLUMN_idBan + " = ?", new String[]{String.valueOf(idBan)});
        if (cursor != null && cursor.moveToFirst()) {
            String trangthai = cursor.getString(0);
            cursor.close();
            return trangthai.equals("Có khách");
        }
        return false;
    }

    // Adding a contact
    public void addContact(Trang_chu contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_SoChoNgoi, contact.getSocho());
        values.put(COLUMN_Anh, contact.getAnh());
        values.put(COLUMN_TrangThaiBan, contact.getTrangthai());
        db.insert(TABLE_Ban, null, values);
        db.close();
    }

    // Getting a single contact
    public Trang_chu getContact(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_Ban, new String[] { COLUMN_idBan,
                        COLUMN_SoChoNgoi, COLUMN_Anh, COLUMN_TrangThaiBan },
                COLUMN_idBan + "=?", new String[] { String.valueOf(id) },
                null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Trang_chu contact = new Trang_chu(
                cursor.getInt(0),    // idBan
                cursor.getBlob(2),   // image
                cursor.getString(1), // SoChoNgoi
                cursor.getString(3)  // TrangThaiBan
        );
        cursor.close();
        return contact;
    }

    // Getting all contacts
    public List<Trang_chu> getAllContacts() {
        List<Trang_chu> contactList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_Ban;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Trang_chu contact = new Trang_chu();
                contact.setId_ban(cursor.getInt(0));
                contact.setSocho(cursor.getString(1));
                contact.setAnh(cursor.getBlob(2));
                contact.setTrangthai(cursor.getString(3));
                contactList.add(contact);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return contactList;
    }

    // Updating a contact
    public int updateContact(Trang_chu contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_SoChoNgoi, contact.getSocho());
        values.put(COLUMN_Anh, contact.getAnh());
        values.put(COLUMN_TrangThaiBan, contact.getTrangthai());
        return db.update(TABLE_Ban, values, COLUMN_idBan + " = ?",
                new String[] { String.valueOf(contact.getId_ban()) });
    }

    // Deleting a contact
    public void deleteContact(Trang_chu contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_Ban, COLUMN_idBan + " = ?",
                new String[] { String.valueOf(contact.getId_ban()) });
        db.close();
    }

    // Getting contacts count
    public int getContactsCount() {
        String countQuery = "SELECT * FROM " + TABLE_Ban;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }


    public SQLiteDatabase open() {
        return this.getWritableDatabase();
    }
}
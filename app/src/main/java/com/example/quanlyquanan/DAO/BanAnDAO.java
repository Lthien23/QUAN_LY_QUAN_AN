package com.example.quanlyquanan.DAO;

// Import các thư viện cần thiết
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.quanlyquanan.Model.BanAn;
import com.example.quanlyquanan.Database.CreateDatabase;

import java.util.ArrayList;
import java.util.List;

public class BanAnDAO {
    SQLiteDatabase database; // Đối tượng thao tác với cơ sở dữ liệu SQLite

    // Constructor: Khởi tạo và mở kết nối đến cơ sở dữ liệu
    public BanAnDAO(Context context) {
        CreateDatabase createDatabase = new CreateDatabase(context); // Tạo đối tượng giúp tạo và quản lý cơ sở dữ liệu
        database = createDatabase.open(); // Mở kết nối cơ sở dữ liệu
    }

    // Hàm thêm bàn ăn mới
    public boolean ThemBanAn(String tenban) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TBL_BAN_TENBAN, tenban); // Gán tên bàn vào ContentValues
        contentValues.put(CreateDatabase.TBL_BAN_TINHTRANG, "false"); // Mặc định trạng thái bàn là "false" (chưa sử dụng)

        // Thực hiện thêm dữ liệu vào bảng TBL_BAN
        long ktra = database.insert(CreateDatabase.TBL_BAN, null, contentValues);
        return ktra != 0; // Nếu kết quả trả về khác 0, thêm thành công
    }

    // Hàm xóa bàn ăn theo mã bàn
    public boolean XoaBanTheoMa(int maban) {
        // Thực hiện xóa bàn dựa vào mã bàn
        long ktra = database.delete(CreateDatabase.TBL_BAN, CreateDatabase.TBL_BAN_MABAN + " = " + maban, null);
        return ktra != 0; // Nếu kết quả khác 0, xóa thành công
    }

    // Hàm cập nhật tên bàn
    public boolean CapNhatTenBan(int maban, String tenban) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TBL_BAN_TENBAN, tenban); // Gán tên bàn mới vào ContentValues

        // Thực hiện cập nhật tên bàn dựa vào mã bàn
        long ktra = database.update(CreateDatabase.TBL_BAN, contentValues, CreateDatabase.TBL_BAN_MABAN + " = '" + maban + "' ", null);
        return ktra != 0; // Nếu kết quả khác 0, cập nhật thành công
    }

    // Hàm lấy danh sách tất cả các bàn ăn (đổ vào giao diện GridView)
    public List<BanAn> LayTatCaBanAn() {
        List<BanAn> banAnList = new ArrayList<>(); // Tạo danh sách chứa các bàn ăn
        String query = "SELECT * FROM " + CreateDatabase.TBL_BAN; // Truy vấn lấy toàn bộ dữ liệu từ bảng TBL_BAN
        Cursor cursor = database.rawQuery(query, null); // Thực thi truy vấn và nhận kết quả trong con trỏ

        cursor.moveToFirst(); // Di chuyển con trỏ đến dòng đầu tiên
        while (!cursor.isAfterLast()) { // Lặp qua tất cả các dòng
            BanAn banAn = new BanAn();
            banAn.setMaBan(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TBL_BAN_MABAN))); // Lấy mã bàn
            banAn.setTenBan(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_BAN_TENBAN))); // Lấy tên bàn

            banAnList.add(banAn); // Thêm bàn ăn vào danh sách
            cursor.moveToNext(); // Di chuyển con trỏ đến dòng tiếp theo
        }
        return banAnList; // Trả về danh sách các bàn ăn
    }

    // Hàm lấy trạng thái của bàn ăn theo mã bàn
    public String LayTinhTrangBanTheoMa(int maban) {
        String tinhtrang = ""; // Biến chứa trạng thái
        String query = "SELECT * FROM " + CreateDatabase.TBL_BAN + " WHERE " + CreateDatabase.TBL_BAN_MABAN + " = '" + maban + "' ";
        Cursor cursor = database.rawQuery(query, null); // Thực thi truy vấn và nhận kết quả

        cursor.moveToFirst(); // Di chuyển con trỏ đến dòng đầu tiên
        while (!cursor.isAfterLast()) { // Lặp qua các dòng
            tinhtrang = cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_MON_TINHTRANG)); // Lấy trạng thái bàn
            cursor.moveToNext(); // Di chuyển con trỏ
        }

        return tinhtrang; // Trả về trạng thái
    }

    // Hàm cập nhật trạng thái của bàn ăn
    public boolean CapNhatTinhTrangBan(int maban, String tinhtrang) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TBL_BAN_TINHTRANG, tinhtrang); // Gán trạng thái mới vào ContentValues

        // Thực hiện cập nhật trạng thái bàn dựa vào mã bàn
        long ktra = database.update(CreateDatabase.TBL_BAN, contentValues, CreateDatabase.TBL_BAN_MABAN + " = '" + maban + "' ", null);
        return ktra != 0; // Nếu kết quả khác 0, cập nhật thành công
    }

    // Hàm lấy tên bàn ăn theo mã bàn
    public String LayTenBanTheoMa(int maban) {
        String tenban = ""; // Biến chứa tên bàn
        String query = "SELECT * FROM " + CreateDatabase.TBL_BAN + " WHERE " + CreateDatabase.TBL_BAN_MABAN + " = '" + maban + "' ";
        Cursor cursor = database.rawQuery(query, null); // Thực thi truy vấn và nhận kết quả

        cursor.moveToFirst(); // Di chuyển con trỏ đến dòng đầu tiên
        while (!cursor.isAfterLast()) { // Lặp qua các dòng
            tenban = cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_BAN_TENBAN)); // Lấy tên bàn
            cursor.moveToNext(); // Di chuyển con trỏ
        }

        return tenban; // Trả về tên bàn
    }
}

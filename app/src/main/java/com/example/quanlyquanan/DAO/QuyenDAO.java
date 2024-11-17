package com.example.quanlyquanan.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.quanlyquanan.Model.Quyen;
import com.example.quanlyquanan.Database.CreateDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Lớp QuyenDAO quản lý các thao tác liên quan đến bảng "TBL_QUYEN" trong cơ sở dữ liệu.
 * Bao gồm các chức năng thêm quyền mới, và lấy tên quyền theo mã quyền.
 */
public class QuyenDAO {

    SQLiteDatabase database; // Đối tượng SQLiteDatabase để thao tác với cơ sở dữ liệu.

    /**
     * Constructor QuyenDAO
     * Khởi tạo kết nối với cơ sở dữ liệu thông qua lớp CreateDatabase.
     * @param context - Ngữ cảnh hiện tại, thường là Activity.
     */
    public QuyenDAO(Context context) {
        // Khởi tạo đối tượng CreateDatabase để quản lý cơ sở dữ liệu
        CreateDatabase createDatabase = new CreateDatabase(context);
        // Mở kết nối cơ sở dữ liệu và gán cho biến database
        database = createDatabase.open();
    }

    /**
     * Phương thức ThemQuyen
     * Chèn một quyền mới vào bảng "TBL_QUYEN".
     * @param tenquyen - Tên của quyền cần thêm.
     */
    public void ThemQuyen(String tenquyen) {
        // Tạo đối tượng ContentValues để lưu dữ liệu dưới dạng key-value
        ContentValues contentValues = new ContentValues();
        // Gán giá trị tên quyền (tenquyen) vào cột TBL_QUYEN_TENQUYEN
        contentValues.put(CreateDatabase.TBL_QUYEN_TENQUYEN, tenquyen);
        // Chèn bản ghi vào bảng TBL_QUYEN
        database.insert(CreateDatabase.TBL_QUYEN, null, contentValues);
    }

    /**
     * Phương thức LayTenQuyenTheoMa
     * Lấy tên quyền từ bảng "TBL_QUYEN" dựa vào mã quyền.
     * @param maquyen - Mã quyền cần tìm.
     * @return tenquyen - Tên của quyền tương ứng với mã quyền.
     */
    public String LayTenQuyenTheoMa(int maquyen) {
        String tenquyen = ""; // Khởi tạo biến lưu trữ tên quyền
        // Câu lệnh SQL truy vấn bảng TBL_QUYEN để lấy tên quyền dựa vào mã quyền
        String query = "SELECT * FROM " + CreateDatabase.TBL_QUYEN +
                " WHERE " + CreateDatabase.TBL_QUYEN_MAQUYEN + " = " + maquyen;
        // Thực thi truy vấn và nhận con trỏ Cursor
        Cursor cursor = database.rawQuery(query, null);

        // Di chuyển con trỏ đến bản ghi đầu tiên
        cursor.moveToFirst();
        // Lặp qua các kết quả trả về từ truy vấn
        while (!cursor.isAfterLast()) {
            // Lấy giá trị từ cột TBL_QUYEN_TENQUYEN và gán vào biến tenquyen
            tenquyen = cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_QUYEN_TENQUYEN));
            cursor.moveToNext(); // Di chuyển con trỏ đến bản ghi tiếp theo
        }

        // Đóng con trỏ sau khi sử dụng
        cursor.close();

        // Trả về tên quyền tìm được
        return tenquyen;
    }
}

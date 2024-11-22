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
    SQLiteDatabase database;

    public BanAnDAO(Context context) {
        CreateDatabase createDatabase = new CreateDatabase(context);
        database = createDatabase.open();
    }

    // Hàm thêm bàn ăn mới
    public boolean ThemBanAn(String tenban) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TBL_BAN_TENBAN, tenban);
        contentValues.put(CreateDatabase.TBL_BAN_TINHTRANG, "false");

        long ktra = database.insert(CreateDatabase.TBL_BAN, null, contentValues);
        return ktra != 0;
    }

    public boolean XoaBanTheoMa(int maban) {
        long ktra = database.delete(CreateDatabase.TBL_BAN, CreateDatabase.TBL_BAN_MABAN + " = " + maban, null);
        return ktra != 0;
    }

    public boolean CapNhatTenBan(int maban, String tenban) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TBL_BAN_TENBAN, tenban);

        long ktra = database.update(CreateDatabase.TBL_BAN, contentValues, CreateDatabase.TBL_BAN_MABAN + " = '" + maban + "' ", null);
        return ktra != 0;
    }

    public List<BanAn> LayTatCaBanAn() {
        List<BanAn> banAnList = new ArrayList<>();
        String query = "SELECT * FROM " + CreateDatabase.TBL_BAN;
        Cursor cursor = database.rawQuery(query, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            BanAn banAn = new BanAn();
            banAn.setMaBan(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TBL_BAN_MABAN)));
            banAn.setTenBan(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_BAN_TENBAN)));

            banAnList.add(banAn);
            cursor.moveToNext();
        }
        return banAnList;
    }

    public String LayTinhTrangBanTheoMa(int maban) {
        String tinhtrang = "";
        String query = "SELECT * FROM " + CreateDatabase.TBL_BAN + " WHERE " + CreateDatabase.TBL_BAN_MABAN + " = '" + maban + "' ";
        Cursor cursor = database.rawQuery(query, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            tinhtrang = cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_MON_TINHTRANG));
            cursor.moveToNext();
        }

        return tinhtrang;
    }

    public boolean CapNhatTinhTrangBan(int maban, String tinhtrang) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TBL_BAN_TINHTRANG, tinhtrang);

        long ktra = database.update(CreateDatabase.TBL_BAN, contentValues, CreateDatabase.TBL_BAN_MABAN + " = '" + maban + "' ", null);
        return ktra != 0;
    }

    public String LayTenBanTheoMa(int maban) {
        String tenban = ""; // Biến chứa tên bàn
        String query = "SELECT * FROM " + CreateDatabase.TBL_BAN + " WHERE " + CreateDatabase.TBL_BAN_MABAN + " = '" + maban + "' ";
        Cursor cursor = database.rawQuery(query, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            tenban = cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_BAN_TENBAN));
            cursor.moveToNext();
        }

        return tenban;
    }
}

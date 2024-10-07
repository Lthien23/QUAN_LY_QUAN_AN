package com.example.lethuyhien.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.lethuyhien.Model.Trang_chu;
import com.example.lethuyhien.Database.dbQLQA;

import java.util.ArrayList;
import java.util.List;

public class BanAnDao {
    SQLiteDatabase database;

    public BanAnDao(Context context) {
        dbQLQA createDatabase = new dbQLQA(context);
        database = createDatabase.open();
    }

    // Add a new table (Bàn Ăn)
    public boolean ThemBanAn(String socho, byte[] anh, String trangthai) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(dbQLQA.COLUMN_SoChoNgoi, socho);  // Number of seats
        contentValues.put(dbQLQA.COLUMN_Anh, anh);  // Image
        contentValues.put(dbQLQA.COLUMN_TrangThaiBan, trangthai);  // Table status
        long result = database.insert(dbQLQA.TABLE_Ban, null, contentValues);
        return result != -1;  // Return true if the insertion was successful
    }

    public List<Trang_chu> LayTatCaBanAn() {
        List<Trang_chu> banAnDTOList = new ArrayList<>();
        String query = "SELECT * FROM " + dbQLQA.TABLE_Ban;
        Cursor cursor = database.rawQuery(query, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            Trang_chu banAnDTO = new Trang_chu();
            banAnDTO.setId_ban(cursor.getInt(cursor.getColumnIndex(dbQLQA.COLUMN_idBan)));
            banAnDTO.setSocho(cursor.getString(cursor.getColumnIndex(dbQLQA.COLUMN_SoChoNgoi)));
            banAnDTO.setAnh(cursor.getBlob(cursor.getColumnIndex(dbQLQA.COLUMN_Anh)));  // BLOB for image
            banAnDTO.setTrangthai(cursor.getString(cursor.getColumnIndex(dbQLQA.COLUMN_TrangThaiBan)));

            banAnDTOList.add(banAnDTO);
            cursor.moveToNext();
        }
        cursor.close();  // Always close the cursor after using it
        return banAnDTOList;
    }
}

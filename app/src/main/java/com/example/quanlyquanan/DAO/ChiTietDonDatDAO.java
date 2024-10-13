package com.example.quanlyquanan.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.quanlyquanan.Model.ChiTietDonDat;
import com.example.quanlyquanan.Database.CreateDatabase;

public class ChiTietDonDatDAO {

    SQLiteDatabase database;
    public ChiTietDonDatDAO(Context context){
        CreateDatabase createDatabase = new CreateDatabase(context);
        database = createDatabase.open();
    }

    public boolean KiemTraMonTonTai(int madondat, int mamon){
        String query = "SELECT * FROM " +CreateDatabase.TBL_CHITIETDONDAT+ " WHERE " +CreateDatabase.TBL_CHITIETDONDAT_MAMON+
                " = " +mamon+ " AND " +CreateDatabase.TBL_CHITIETDONDAT_MADONDAT+ " = "+madondat;
        Cursor cursor = database.rawQuery(query,null);
        if(cursor.getCount() != 0){
            return true;
        }else {
            return false;
        }
    }

    public int LaySLMonTheoMaDon(int madondat, int mamon){
        int soluong = 0;
        String query = "SELECT * FROM " +CreateDatabase.TBL_CHITIETDONDAT+ " WHERE " +CreateDatabase.TBL_CHITIETDONDAT_MAMON+
                " = " +mamon+ " AND " +CreateDatabase.TBL_CHITIETDONDAT_MADONDAT+ " = "+madondat;
        Cursor cursor = database.rawQuery(query,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            soluong = cursor.getInt(cursor.getColumnIndex(CreateDatabase.TBL_CHITIETDONDAT_SOLUONG));
            cursor.moveToNext();
        }
        return soluong;
    }

    public boolean CapNhatSL(ChiTietDonDat chiTietDonDat){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TBL_CHITIETDONDAT_SOLUONG, chiTietDonDat.getSoLuong());

        long ktra = database.update(CreateDatabase.TBL_CHITIETDONDAT,contentValues,CreateDatabase.TBL_CHITIETDONDAT_MADONDAT+ " = "
                +chiTietDonDat.getMaDonDat()+ " AND " +CreateDatabase.TBL_CHITIETDONDAT_MAMON+ " = "
                +chiTietDonDat.getMaMon(),null);
        if(ktra !=0){
            return true;
        }else {
            return false;
        }
    }

    public boolean ThemChiTietDonDat(ChiTietDonDat chiTietDonDat){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TBL_CHITIETDONDAT_SOLUONG,chiTietDonDat.getSoLuong());
        contentValues.put(CreateDatabase.TBL_CHITIETDONDAT_MADONDAT,chiTietDonDat.getMaDonDat());
        contentValues.put(CreateDatabase.TBL_CHITIETDONDAT_MAMON,chiTietDonDat.getMaMon());

        long ktra = database.insert(CreateDatabase.TBL_CHITIETDONDAT,null,contentValues);
        if(ktra !=0){
            return true;
        }else {
            return false;
        }
    }

}

package com.example.quanlyquanan.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.quanlyquanan.Model.Mon;
import com.example.quanlyquanan.Database.CreateDatabase;

import java.util.ArrayList;
import java.util.List;

public class MonDAO {
    SQLiteDatabase database;
    public MonDAO(Context context){
        CreateDatabase createDatabase = new CreateDatabase(context);
        database = createDatabase.open();
    }

    public boolean ThemMon(Mon mon){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TBL_MON_MALOAI,mon.getMaLoai());
        contentValues.put(CreateDatabase.TBL_MON_TENMON,mon.getTenMon());
        contentValues.put(CreateDatabase.TBL_MON_GIATIEN,mon.getGiaTien());
        contentValues.put(CreateDatabase.TBL_MON_HINHANH,mon.getHinhAnh());
        contentValues.put(CreateDatabase.TBL_MON_TINHTRANG,"true");

        long ktra = database.insert(CreateDatabase.TBL_MON,null,contentValues);

        if(ktra !=0){
            return true;
        }else {
            return false;
        }
    }

    public boolean XoaMon(int mamon){
        long ktra = database.delete(CreateDatabase.TBL_MON,CreateDatabase.TBL_MON_MAMON+ " = " +mamon
                ,null);
        if(ktra !=0 ){
            return true;
        }else {
            return false;
        }
    }

    public boolean SuaMon(Mon mon,int mamon){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TBL_MON_MALOAI,mon.getMaLoai());
        contentValues.put(CreateDatabase.TBL_MON_TENMON,mon.getTenMon());
        contentValues.put(CreateDatabase.TBL_MON_GIATIEN,mon.getGiaTien());
        contentValues.put(CreateDatabase.TBL_MON_HINHANH,mon.getHinhAnh());
        contentValues.put(CreateDatabase.TBL_MON_TINHTRANG,mon.getTinhTrang());

        long ktra = database.update(CreateDatabase.TBL_MON,contentValues,
                CreateDatabase.TBL_MON_MAMON+" = "+mamon,null);
        if(ktra !=0){
            return true;
        }else {
            return false;
        }
    }

    public List<Mon> LayDSMonTheoLoai(int maloai){
        List<Mon> monList = new ArrayList<Mon>();
        String query = "SELECT * FROM " +CreateDatabase.TBL_MON+ " WHERE " +CreateDatabase.TBL_MON_MALOAI+ " = '" +maloai+ "' ";
        Cursor cursor = database.rawQuery(query,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            Mon mon = new Mon();
            mon.setHinhAnh(cursor.getBlob(cursor.getColumnIndex(CreateDatabase.TBL_MON_HINHANH)));
            mon.setTenMon(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_MON_TENMON)));
            mon.setMaLoai(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TBL_MON_MALOAI)));
            mon.setMaMon(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TBL_MON_MAMON)));
            mon.setGiaTien(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_MON_GIATIEN)));
            mon.setTinhTrang(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_MON_TINHTRANG)));
            monList.add(mon);

            cursor.moveToNext();
        }
        return monList;
    }

    public Mon LayMonTheoMa(int mamon){
        Mon mon = new Mon();
        String query = "SELECT * FROM "+CreateDatabase.TBL_MON+" WHERE "+CreateDatabase.TBL_MON_MAMON+" = "+mamon;
        Cursor cursor = database.rawQuery(query,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            mon.setHinhAnh(cursor.getBlob(cursor.getColumnIndex(CreateDatabase.TBL_MON_HINHANH)));
            mon.setTenMon(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_MON_TENMON)));
            mon.setMaLoai(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TBL_MON_MALOAI)));
            mon.setMaMon(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TBL_MON_MAMON)));
            mon.setGiaTien(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_MON_GIATIEN)));
            mon.setTinhTrang(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_MON_TINHTRANG)));

            cursor.moveToNext();
        }
        return mon;
    }

}

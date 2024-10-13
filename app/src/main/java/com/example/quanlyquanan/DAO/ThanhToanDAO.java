package com.example.quanlyquanan.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.quanlyquanan.Model.ThanhToan;
import com.example.quanlyquanan.Database.CreateDatabase;

import java.util.ArrayList;
import java.util.List;

public class ThanhToanDAO {

    SQLiteDatabase database;
    public ThanhToanDAO(Context context){
        CreateDatabase createDatabase = new CreateDatabase(context);
        database = createDatabase.open();
    }

    public List<ThanhToan> LayDSMonTheoMaDon(int madondat){
        List<ThanhToan> thanhToanS = new ArrayList<ThanhToan>();
        String query = "SELECT * FROM "+CreateDatabase.TBL_CHITIETDONDAT+" ctdd,"+CreateDatabase.TBL_MON+" mon WHERE "
                +"ctdd."+CreateDatabase.TBL_CHITIETDONDAT_MAMON+" = mon."+CreateDatabase.TBL_MON_MAMON+" AND "
                +CreateDatabase.TBL_CHITIETDONDAT_MADONDAT+" = '"+madondat+"'";

        Cursor cursor = database.rawQuery(query,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            ThanhToan thanhToan = new ThanhToan();
            thanhToan.setSoLuong(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TBL_CHITIETDONDAT_SOLUONG)));
            thanhToan.setGiaTien(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TBL_MON_GIATIEN)));
            thanhToan.setTenMon(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_MON_TENMON)));
            thanhToan.setHinhAnh(cursor.getBlob(cursor.getColumnIndex(CreateDatabase.TBL_MON_HINHANH)));
            thanhToanS.add(thanhToan);

            cursor.moveToNext();
        }

        return thanhToanS;
    }
}

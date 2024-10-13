package com.example.quanlyquanan.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Pair;

import com.example.quanlyquanan.Model.ChiTietDonDat;
import com.example.quanlyquanan.Model.DonDat;
import com.example.quanlyquanan.Model.LoaiMon;
import com.example.quanlyquanan.Database.CreateDatabase;

import java.util.ArrayList;
import java.util.List;

public class DonDatDAO {

    SQLiteDatabase database;
    public DonDatDAO(Context context){
        CreateDatabase createDatabase = new CreateDatabase(context);
        database = createDatabase.open();
    }

    public long ThemDonDat(DonDat donDat){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TBL_DONDAT_MABAN,donDat.getMaBan());
        contentValues.put(CreateDatabase.TBL_DONDAT_MANV,donDat.getMaNV());
        contentValues.put(CreateDatabase.TBL_DONDAT_NGAYDAT,donDat.getNgayDat());
        contentValues.put(CreateDatabase.TBL_DONDAT_TINHTRANG,donDat.getTinhTrang());
        contentValues.put(CreateDatabase.TBL_DONDAT_TONGTIEN,donDat.getTongTien());

        long madondat = database.insert(CreateDatabase.TBL_DONDAT,null,contentValues);

        return madondat;
    }

    public List<DonDat> LayDSDonDat(){
        List<DonDat> donDatDTOS = new ArrayList<DonDat>();
        String query = "SELECT * FROM "+CreateDatabase.TBL_DONDAT;
        Cursor cursor = database.rawQuery(query,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            DonDat donDat = new DonDat();
            donDat.setMaDonDat(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TBL_DONDAT_MADONDAT)));
            donDat.setMaBan(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TBL_DONDAT_MABAN)));
            donDat.setTongTien(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_DONDAT_TONGTIEN)));
            donDat.setTinhTrang(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_DONDAT_TINHTRANG)));
            donDat.setNgayDat(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_DONDAT_NGAYDAT)));
            donDat.setMaNV(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TBL_DONDAT_MANV)));
            donDatDTOS.add(donDat);

            cursor.moveToNext();
        }
        return donDatDTOS;
    }

    public List<DonDat> LayDSDonDatNgay(String ngaythang){
        List<DonDat> donDatS = new ArrayList<DonDat>();
        String query = "SELECT * FROM "+CreateDatabase.TBL_DONDAT+" WHERE "+CreateDatabase.TBL_DONDAT_NGAYDAT+" like '"+ngaythang+"'";
        Cursor cursor = database.rawQuery(query,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            DonDat donDat = new DonDat();
            donDat.setMaDonDat(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TBL_DONDAT_MADONDAT)));
            donDat.setMaBan(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TBL_DONDAT_MABAN)));
            donDat.setTongTien(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_DONDAT_TONGTIEN)));
            donDat.setTinhTrang(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_DONDAT_TINHTRANG)));
            donDat.setNgayDat(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_DONDAT_NGAYDAT)));
            donDat.setMaNV(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TBL_DONDAT_MANV)));
            donDatS.add(donDat);

            cursor.moveToNext();
        }
        return donDatS;
    }

    public long LayMaDonTheoMaBan(int maban, String tinhtrang){
        String query = "SELECT * FROM " +CreateDatabase.TBL_DONDAT+ " WHERE " +CreateDatabase.TBL_DONDAT_MABAN+ " = '" +maban+ "' AND "
                +CreateDatabase.TBL_DONDAT_TINHTRANG+ " = '" +tinhtrang+ "' ";
        long magoimon = 0;
        Cursor cursor = database.rawQuery(query,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            magoimon = cursor.getInt(cursor.getColumnIndex(CreateDatabase.TBL_DONDAT_MADONDAT));

            cursor.moveToNext();
        }
        return magoimon;
    }

    public boolean UpdateTongTienDonDat(int madondat,String tongtien){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TBL_DONDAT_TONGTIEN,tongtien);
        long ktra  = database.update(CreateDatabase.TBL_DONDAT,contentValues,
                CreateDatabase.TBL_DONDAT_MADONDAT+" = "+madondat,null);
        if(ktra != 0){
            return true;
        }else{
            return false;
        }
    }

    public boolean UpdateTThaiDonTheoMaBan(int maban,String tinhtrang){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TBL_DONDAT_TINHTRANG,tinhtrang);
        long ktra = database.update(CreateDatabase.TBL_DONDAT,contentValues,CreateDatabase.TBL_DONDAT_MABAN+
                " = '"+maban+"'",null);
        if(ktra !=0){
            return true;
        }else {
            return false;
        }
    }

}

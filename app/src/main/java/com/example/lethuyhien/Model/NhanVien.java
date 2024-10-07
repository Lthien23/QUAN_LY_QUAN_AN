package com.example.lethuyhien.Model;

import java.util.Date;

import kotlin.text.UStringsKt;

public class NhanVien {
    private int idnhanvien;
    private String hoten;
    private Date Ngaysinh;
    private String Sdt;
    private String Gioitinh;

    public NhanVien() {
    }

    public NhanVien(String hoten, String sdt) {
        this.hoten = hoten;
        Sdt = sdt;
    }

    public NhanVien(int idnhanvien, String hoten, Date ngaysinh, String sdt, String gioitinh) {
        this.idnhanvien = idnhanvien;
        this.hoten = hoten;
        Ngaysinh = ngaysinh;
        Sdt = sdt;
        Gioitinh = gioitinh;
    }

    public int getIdnhanvien() {
        return idnhanvien;
    }

    public void setIdnhanvien(int idnhanvien) {
        this.idnhanvien = idnhanvien;
    }

    public String getHoten() {
        return hoten;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }

    public Date getNgaysinh() {
        return Ngaysinh;
    }

    public void setNgaysinh(Date ngaysinh) {
        Ngaysinh = ngaysinh;
    }

    public String getSdt() {
        return Sdt;
    }

    public void setSdt(String sdt) {
        Sdt = sdt;
    }

    public String getGioitinh() {
        return Gioitinh;
    }

    public void setGioitinh(String gioitinh) {
        Gioitinh = gioitinh;
    }
}

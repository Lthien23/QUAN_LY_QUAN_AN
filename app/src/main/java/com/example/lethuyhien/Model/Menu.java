package com.example.lethuyhien.Model;

public class Menu {
    private int anhmenu;
    private String tenmonan;
    private double gia;
    private String loai;
    private int soluong;

    public Menu() {
    }

    public Menu(int anhmenu, String tenmonan, double gia, String loai,int soluong) {
        this.anhmenu = anhmenu;
        this.tenmonan = tenmonan;
        this.gia = gia;
        this.loai=loai;
        this.soluong=0;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

    public String getLoai() {
        return loai;
    }

    public void setLoai(String loai) {
        this.loai = loai;
    }

    public int getAnhmenu() {
        return anhmenu;
    }

    public void setAnhmenu(int anhmenu) {
        this.anhmenu = anhmenu;
    }

    public String getTenmonan() {
        return tenmonan;
    }

    public void setTenmonan(String tenmonan) {
        this.tenmonan = tenmonan;
    }

    public double getGia() {
        return gia;
    }

    public void setGia(double gia) {
        this.gia = gia;
    }
}

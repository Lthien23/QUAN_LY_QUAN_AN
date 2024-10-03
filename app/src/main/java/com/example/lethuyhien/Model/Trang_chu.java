package com.example.lethuyhien.Model;

public class Trang_chu {
    private  int anh;
    private  int id_ban;
    private String socho;
    private  String trangthai;

    public Trang_chu() {
    }

    public Trang_chu(int anh,int id_ban, String socho, String trangthai) {
        this.anh=anh;
        this.id_ban = id_ban;
        this.socho = socho;
        this.trangthai = trangthai;
    }

    public int getAnh() {
        return anh;
    }

    public void setAnh(int anh) {
        this.anh = anh;
    }

    public int getId_ban() {
        return id_ban;
    }

    public void setId_ban(int id_ban) {
        this.id_ban = id_ban;
    }

    public String getSocho() {
        return socho;
    }

    public void setSocho(String socho) {
        this.socho = socho;
    }

    public String getTrangthai() {
        return trangthai;
    }

    public void setTrangthai(String trangthai) {
        this.trangthai = trangthai;
    }
}

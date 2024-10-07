package com.example.lethuyhien.Model;


public class Trang_chu {
    private int id_ban;
    private byte[] anh;
    private String socho;
    private String trangthai;

    public Trang_chu() {}

    public Trang_chu(int id_ban, byte[] anh, String socho, String trangthai) {
        this.id_ban = id_ban;
        this.anh = anh;
        this.socho = socho;
        this.trangthai = trangthai;
    }

    public int getId_ban() {
        return id_ban;
    }

    public void setId_ban(int id_ban) {
        this.id_ban = id_ban;
    }

    public byte[] getAnh() {
        return anh;
    }

    public void setAnh(byte[] anh) {
        this.anh = anh;
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

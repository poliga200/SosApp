package com.example.sosapplication.DAO;

public class NguoiDung {
    private String ma_nd;
    private String sdt;
    private String hoTen;

    public NguoiDung() {
    }

    public NguoiDung(String ma_nd, String sdt, String hoTen){
        this.ma_nd = ma_nd;
        this.sdt = sdt;
        this.hoTen = hoTen;
    }

    public String getHoTen() {
        return hoTen;
    }

    public String getMa_nd() {
        return ma_nd;
    }

    public String getSdt() {
        return sdt;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public void setMa_nd(String ma_nd) {
        this.ma_nd = ma_nd;
    }
}

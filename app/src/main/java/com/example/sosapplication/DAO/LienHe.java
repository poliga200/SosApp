package com.example.sosapplication.DAO;

public class LienHe {
    String ma_lh, loai;
    int dut;

    public LienHe(){
    }
    public LienHe(String ma_lh, String loai, int dut){
        this.ma_lh = ma_lh;
        this.loai = loai;
        this.dut = dut;
    }

    public String getLoai() {
        return loai;
    }

    public int getDut() {
        return dut;
    }

    public String getMa_lh() {
        return ma_lh;
    }

    public void setDut(int dut) {
        this.dut = dut;
    }

    public void setLoai(String loai) {
        this.loai = loai;
    }

    public void setMa_lh(String ma_lh) {
        this.ma_lh = ma_lh;
    }
}

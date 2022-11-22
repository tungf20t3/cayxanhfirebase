package com.zantung.sinhvienfirebase.model;

public class CayXanh {
    private int id;
    private String ten_khoa_hoc, ten_thuong_goi, dac_tinh, mau_la;
    private String hinh_anh;

    public CayXanh(int id, String ten_khoa_hoc, String ten_thuong_goi, String dac_tinh, String mau_la, String hinh_anh) {
        this.id = id;
        this.ten_khoa_hoc = ten_khoa_hoc;
        this.ten_thuong_goi = ten_thuong_goi;
        this.dac_tinh = dac_tinh;
        this.mau_la = mau_la;
        this.hinh_anh = hinh_anh;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTen_khoa_hoc() {
        return ten_khoa_hoc;
    }

    public void setTen_khoa_hoc(String ten_khoa_hoc) {
        this.ten_khoa_hoc = ten_khoa_hoc;
    }

    public String getTen_thuong_goi() {
        return ten_thuong_goi;
    }

    public void setTen_thuong_goi(String ten_thuong_goi) {
        this.ten_thuong_goi = ten_thuong_goi;
    }

    public String getDac_tinh() {
        return dac_tinh;
    }

    public void setDac_tinh(String dac_tinh) {
        this.dac_tinh = dac_tinh;
    }

    public String getMau_la() {
        return mau_la;
    }

    public void setMau_la(String mau_la) {
        this.mau_la = mau_la;
    }

    public String getHinh_anh() {
        return hinh_anh;
    }

    public void setHinh_anh(String hinh_anh) {
        this.hinh_anh = hinh_anh;
    }
}

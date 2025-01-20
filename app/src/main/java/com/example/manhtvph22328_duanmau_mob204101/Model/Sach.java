package com.example.manhtvph22328_duanmau_mob204101.Model;

public class Sach {
    private int maSach;
    private String tenSach;
    private int giaThue;
    private int loaiSach;
    private int soTrang;

    public Sach() {
    }

    public Sach(int maSach, String tenSach, int giaThue, int loaiSach) {
        this.maSach = maSach;
        this.tenSach = tenSach;
        this.giaThue = giaThue;
        this.loaiSach = loaiSach;
    }

    public Sach(int maSach, String tenSach, int giaThue, int loaiSach, int soTrang) {
        this.maSach = maSach;
        this.tenSach = tenSach;
        this.giaThue = giaThue;
        this.loaiSach = loaiSach;
        this.soTrang = soTrang;
    }

    public int getMaSach() {
        return maSach;
    }

    public void setMaSach(int maSach) {
        this.maSach = maSach;
    }

    public String getTenSach() {
        return tenSach;
    }

    public void setTenSach(String tenSach) {
        this.tenSach = tenSach;
    }

    public int getGiaThue() {
        return giaThue;
    }

    public void setGiaThue(int giaThue) {
        this.giaThue = giaThue;
    }

    public int getLoaiSach() {
        return loaiSach;
    }

    public void setLoaiSach(int loaiSach) {
        this.loaiSach = loaiSach;
    }

    public int getSoTrang() {
        return soTrang;
    }

    public void setSoTrang(int soTrang) {
        this.soTrang = soTrang;
    }
}

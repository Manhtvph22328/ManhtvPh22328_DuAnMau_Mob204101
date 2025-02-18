package com.example.manhtvph22328_duanmau_mob204101.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Data extends SQLiteOpenHelper {
    public static final String SQL_PhieuMuon = "create table PHIEUMUON (" +
            "maPM INTEGER primary key AUTOINCREMENT,"+
            "maTT INTEGER REFERENCES THUTHU(maTT),"+
            "maTV INTEGER REFERENCES THANHVIEN(maTV),"+
            "maSach INTEGER REFERENCES SACH(maSach),"+
            "tienThue INTEGER not null,"+
            "ngay DATE not null,"+
            "traSach INTEGER not null);";

    public static final String SQL_LoaiSach = "create table LOAISACH("+
            "maLoai INTEGER primary key AUTOINCREMENT,"+
            "tenLoai TEXT not null);";
    public static final String SQL_ThuThu = "create table THUTHU("+
            "maTT TEXT primary key,"+
            "hoTen TEXT not null,"+
            "matKhau TEXT not null);";
    public static final String SQL_Sach = "create table SACH("+
            "maSach INTEGER primary key AUTOINCREMENT,"+
            "tenSach TEXT not null,"+
            "giaThue INTEGER not null,"+
            "maLoai INTEGER REFERENCES LOAISACH(maLoai));";
    public static final String SQL_ThanhVien = "create table THANHVIEN("+
            "maTV INTEGER primary key AUTOINCREMENT,"+
            "hoTen TEXT not null,"+
            "namSinh TEXT not null,"+
            "cccd INTEGER not null);";
    public static final String Insert_ThuThu = "INSERT INTO THUTHU(maTT,hoTen,matKhau)VALUES"+
            "('admin','admin','123')";
    public Data(@Nullable Context context) {
        super(context, "QLTV.db", null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_PhieuMuon);
        db.execSQL(SQL_LoaiSach);
        db.execSQL(SQL_ThuThu);
        db.execSQL(SQL_Sach);
        db.execSQL(SQL_ThanhVien);
        db.execSQL(Insert_ThuThu);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS PHIEUMUON");
        db.execSQL("DROP TABLE IF EXISTS LOAISACH");
        db.execSQL("DROP TABLE IF EXISTS THUTHU");
        db.execSQL("DROP TABLE IF EXISTS SACH");
        db.execSQL("DROP TABLE IF EXISTS THANHVIEN");
        onCreate(db);
    }
}

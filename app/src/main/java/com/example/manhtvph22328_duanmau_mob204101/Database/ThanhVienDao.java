package com.example.manhtvph22328_duanmau_mob204101.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.manhtvph22328_duanmau_mob204101.Model.ThanhVien;

import java.util.ArrayList;

public class ThanhVienDao {
    private Context contextx;
    private SQLiteDatabase sqLiteDatabase;
    private Data database;

    public ThanhVienDao(Context contextx){
        this.contextx = contextx;
        database = new Data(contextx);
        sqLiteDatabase = database.getWritableDatabase();
    }

    public ArrayList<ThanhVien> getAllThanhVien(){
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT *FROM THANHVIEN",null);
        ArrayList<ThanhVien> list = new ArrayList<>();
        if (cursor.getCount()!=0){
            cursor.moveToFirst();
            do {
                list.add(new ThanhVien(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getInt(3)));
            }while (cursor.moveToNext());
        }
        return list;
    }

    public int Insert(ThanhVien thanhVien){
        ContentValues values = new ContentValues();
        values.put("hoTen", thanhVien.getTenTV());
        values.put("namSinh", thanhVien.getNamSinh());
        values.put("cccd", thanhVien.getCccd());
        long kq = sqLiteDatabase.insert("THANHVIEN", null,values);
        if (kq <=0){
            return -1;
        }
        return 1;
    }
    public int delete(ThanhVien thanhVien){
        int kq = sqLiteDatabase.delete("THANHVIEN","maTV=?",new String[]{String.valueOf(thanhVien.getMaTV())});
        if (kq<=0){
            return -1;
        }
        return 1;
    }
    public int update(ThanhVien thanhVien){
        ContentValues values = new ContentValues();
        values.put("hoTen", thanhVien.getTenTV());
        values.put("namSinh", thanhVien.getNamSinh());
        values.put("cccd", thanhVien.getCccd());
        int kq = sqLiteDatabase.update("THANHVIEN",values,"maTV=?", new String[]{String.valueOf(thanhVien.getMaTV())});
        if (kq<=0){
            return -1;
        }
        return 1;
    }

    public ThanhVien getID(int id){
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT *FROM THANHVIEN WHERE maTV=?",new String[]{String.valueOf(id)});
        ArrayList<ThanhVien> list = new ArrayList<>();
        if (cursor.getCount()!=0){
            cursor.moveToFirst();
            list.add(new ThanhVien(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getInt(3)));
        }
        if (!list.isEmpty()){
            return list.get(0);
        }else {
            return null;
        }
    }
}

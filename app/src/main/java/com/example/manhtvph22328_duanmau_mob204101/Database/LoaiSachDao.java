package com.example.manhtvph22328_duanmau_mob204101.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.manhtvph22328_duanmau_mob204101.Model.LoaiSach;

import java.util.ArrayList;

public class LoaiSachDao {
    private Context context;
    private SQLiteDatabase sqLiteDatabase;
    private Data database;

    public LoaiSachDao(Context context) {
        this.context = context;
        database = new Data(context);
        sqLiteDatabase = database.getWritableDatabase();
    }

    public ArrayList<LoaiSach> getAllLoaiSach(){
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT *FROM LOAISACH",null);
        ArrayList<LoaiSach> list = new ArrayList<>();
        if (cursor.getCount()!=0){
            cursor.moveToFirst();
            do {
                list.add(new LoaiSach(cursor.getInt(0),cursor.getString(1)));
            }while (cursor.moveToNext());
        }
        return list;
    }

    public int Insert(LoaiSach loaiSach){
        ContentValues values = new ContentValues();
        values.put("tenLoai", loaiSach.getTenLoai());
        long kq = sqLiteDatabase.insert("LOAISACH",null,values);
        if (kq<=0){
            return -1;
        }
        return 1;
    }
    public int delete(LoaiSach loaiSach){
        int kq = sqLiteDatabase.delete("LOAISACH","maLoai=?",new String[]{String.valueOf(loaiSach.getMaLoai())});
        if (kq<=0){
            return -1;
        }
        return 1;
    }
    public int update(LoaiSach loaiSach){
        ContentValues values = new ContentValues();
        values.put("maLoai",loaiSach.getMaLoai());
        values.put("tenLoai", loaiSach.getTenLoai());
        int kq = sqLiteDatabase.update("LOAISACH",values,"maLoai=?",new String[]{String.valueOf(loaiSach.getMaLoai())});
        if (kq<=0){
            return -1;
        }
        return 1;
    }

    public LoaiSach getID(int id){
        Cursor cursor= sqLiteDatabase.rawQuery("SELECT *FROM LOAISACH WHERE maLoai= ?",new String[]{String.valueOf(id)});
        ArrayList<LoaiSach> list = new ArrayList<>();
        cursor.moveToFirst();
        list.add(new LoaiSach(cursor.getInt(0),cursor.getString(1)));
        return list.get(0);
    }
}

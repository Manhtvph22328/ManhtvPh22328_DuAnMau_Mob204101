package com.example.manhtvph22328_duanmau_mob204101.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.manhtvph22328_duanmau_mob204101.Model.Sach;

import java.util.ArrayList;

public class SachDao {
    private Context contextt;
    private SQLiteDatabase sqLiteDatabase;
    private Data database;

    public SachDao(Context context){
        this.contextt = context;
        database = new Data(contextt);
        sqLiteDatabase = database.getWritableDatabase();
    }

    public ArrayList<Sach> getAllSach(){
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT *FROM SACH",null);
        ArrayList<Sach> list = new ArrayList<>();
        if (cursor.getCount()!=0){
            cursor.moveToFirst();
            do {
                list.add(new Sach(cursor.getInt(0),cursor.getString(1),cursor.getInt(2),cursor.getInt(3)));
            }while (cursor.moveToNext());
        }
        return list;
    }

    public int Insert(Sach sach){
        ContentValues values = new ContentValues();
        values.put("tenSach",sach.getTenSach());
        values.put("giaThue",sach.getGiaThue());
        values.put("maLoai",sach.getLoaiSach());
        long kq = sqLiteDatabase.insert("SACH",null, values);
        if (kq<=0){
            return -1;
        }
        return 1;
    }
    public int delete(Sach sach){
        int kq = sqLiteDatabase.delete("SACH","maSach=?",new String[]{String.valueOf(sach.getMaSach())});
        if (kq <= 0){
            return -1;
        }
        return 1;
    }
    public int update(Sach sach){
        ContentValues values = new ContentValues();
        values.put("tenSach",sach.getTenSach());
        values.put("giaThue", sach.getGiaThue());
        values.put("maLoai", sach.getLoaiSach());
        int kq = sqLiteDatabase.update("SACH",values,"maSach=?",new String[]{String.valueOf(sach.getMaSach())});
        if (kq<=0){
            return -1;
        }
        return 1;
    }

    public Sach getID(int id){
        Cursor cursor= sqLiteDatabase.rawQuery("SELECT *FROM SACH WHERE maSach= ?",new String[]{String.valueOf(id)});
        ArrayList<Sach> list = new ArrayList<>();
        cursor.moveToFirst();
        list.add(new Sach(cursor.getInt(0),cursor.getString(1),cursor.getInt(2),cursor.getInt(3)));
        Sach sach = list.get(0);
        return sach;
    }
}

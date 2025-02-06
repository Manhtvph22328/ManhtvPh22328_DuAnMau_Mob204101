package com.example.manhtvph22328_duanmau_mob204101.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.manhtvph22328_duanmau_mob204101.Model.ThuThu;

import java.util.ArrayList;

public class ThuThuDao {
    private Context context;
    private SQLiteDatabase sqLiteDatabase;
    private Data database;

    public ThuThuDao(Context context){
        this.context = context;
        database = new Data(context);
        sqLiteDatabase = database.getWritableDatabase();
    }

    public int Insert(ThuThu thuThu){
        ContentValues values = new ContentValues();
        values.put("maTT",thuThu.getMaTT());
        values.put("hoTen",thuThu.getHoTen());
        values.put("matKhau",thuThu.getMatKhau());
        long kq = sqLiteDatabase.insert("THUTHU",null,values);
        if (kq<=0){
            return -1;
        }
        return 1;
    }

    public ArrayList<ThuThu> getAllThuThu(){
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT *FROM THUTHU",null);
        ArrayList<ThuThu> list = new ArrayList<>();
        if (cursor.getCount()!=0){
            cursor.moveToFirst();
            do {
                list.add(new ThuThu(cursor.getString(0),cursor.getString(1),cursor.getString(2)));
            }while (cursor.moveToNext());
        }
        return list;
    }
    public int delete(ThuThu thuThu){
        int kq = sqLiteDatabase.delete("THUTHU", "maTT=?", new String[]{String.valueOf(thuThu.getMaTT())});
        if (kq<=0){
            return -1;
        }
        return 1;
    }
    public int update(ThuThu thuThu){
        ContentValues values = new ContentValues();
        values.put("maTT", thuThu.getMaTT());
        values.put("hoTen", thuThu.getHoTen());
        values.put("matKhau", thuThu.getMatKhau());
        int kq = sqLiteDatabase.update("THUTHU", values, "maTT=?", new String[]{String.valueOf(thuThu.getMaTT())});
        if (kq <=0){
            return -1;
        }
        return 1;
    }

    public ThuThu getId(String id){
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM THUTHU WHERE maTT=?", new String[]{id});
        ArrayList<ThuThu> list = new ArrayList<>();
        if (cursor.getCount()!=0){
            cursor.moveToFirst();
            list.add(new ThuThu(cursor.getString(0),cursor.getString(1),cursor.getString(2)));
        }
        if (!list.isEmpty()){
            return list.get(0);
        }else {
            return null;
        }

    }
}

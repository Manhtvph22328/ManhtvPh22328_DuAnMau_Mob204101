package com.example.manhtvph22328_duanmau_mob204101.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.manhtvph22328_duanmau_mob204101.Model.ThuThu;

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

}

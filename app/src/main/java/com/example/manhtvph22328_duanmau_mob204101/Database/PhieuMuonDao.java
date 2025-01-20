package com.example.manhtvph22328_duanmau_mob204101.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.manhtvph22328_duanmau_mob204101.Model.PhieuMuon;
import com.example.manhtvph22328_duanmau_mob204101.Model.Sach;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class PhieuMuonDao {
    private Context context;
    private SQLiteDatabase sqLiteDatabase;
    private Data database;
    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

    public PhieuMuonDao(Context context) {
        this.context = context;
        database = new Data(context);
        sqLiteDatabase = database.getWritableDatabase();
    }

    public ArrayList<PhieuMuon> getAllPhieuMuon(){
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT *FROM PHIEUMUON",null);
        ArrayList<PhieuMuon> list = new ArrayList<>();
        if (cursor.getCount()!=0){
            cursor.moveToFirst();
            do {
                try {
                    list.add(new PhieuMuon(cursor.getInt(0),
                            cursor.getInt(1),
                            cursor.getInt(2),
                            cursor.getInt(3),
                            cursor.getInt(4),
                            format.parse(cursor.getString(5)),
                            cursor.getInt(6)));
                }catch (ParseException e){
                    e.printStackTrace();
                }
            }while (cursor.moveToNext());
        }
        return list;
    }

    public int Insert(PhieuMuon phieuMuon){
        ContentValues values = new ContentValues();
        values.put("maTT",phieuMuon.getMaTT());
        values.put("maTV",phieuMuon.getMaTV());
        values.put("maSach",phieuMuon.getMaSach());
        values.put("tienThue",phieuMuon.getTienThue());
        values.put("ngay", format.format(phieuMuon.getNgayMuon()));
        values.put("traSach",phieuMuon.getTraSach());

        long kq = sqLiteDatabase.insert("PHIEUMUON", null,values);
        if (kq<=0){
            return -1;
        }
        return 1;
    }
    public int delete(PhieuMuon phieuMuon){
        int kq = sqLiteDatabase.delete("PHIEUMUON", "maPM=?", new String[]{String.valueOf(phieuMuon.getMaPhieu())});
        if (kq<=0){
            return -1;
        }
        return 1;
    }
    public int update(PhieuMuon phieuMuon){
        ContentValues values = new ContentValues();
        values.put("maTT", phieuMuon.getMaTT());
        values.put("maTV", phieuMuon.getMaTV());
        values.put("maSach", phieuMuon.getMaSach());
        values.put("tienThue", phieuMuon.getTienThue());
        values.put("ngay", format.format(phieuMuon.getNgayMuon()));
        values.put("traSach", phieuMuon.getTraSach());

        int kq = sqLiteDatabase.update("PHIEUMUON",values,"maPM=?",new String[]{String.valueOf(phieuMuon.getMaPhieu())});
        if (kq<=0){
            return  -1;
        }
        return 1;
    }

    public ArrayList<Sach> top10(){
        Cursor cursor= sqLiteDatabase.rawQuery("SELECT SACH.maSACH,SACH.tenSach,SACH.giathue, COUNT(SACH.maSach) FROM SACH JOIN PHIEUMUON ON SACH.maSach GROUP BY PHIEUMUON.maSach ORDER BY COUNT(SACH.maSach)DESC LIMIT 10",null);
        ArrayList<Sach> list = new ArrayList<>();
        if (cursor.getCount() !=0){
            cursor.moveToFirst();
            do {
                list.add(new Sach(cursor.getInt(0),cursor.getString(1),cursor.getInt(2),cursor.getInt(3)));
            }while (cursor.moveToNext());
        }
        return list;
    }

    public int DoanhThu(String date1, String date2){
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT SUM(tienThue) FROM PHIEUMUON WHERE ngay BETWEEN ? AND ?",new String[]{date1, date2});
        int dt = 0;
        if (cursor.getCount()!=0){
            cursor.moveToFirst();
            dt = cursor.getInt(0);
        }
        return dt;
    }

    public ArrayList<PhieuMuon> getAllByMaPM(int ma){
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT *FROM PHIEUMUON WHERE maPM=?",new String[]{String.valueOf(ma)});
        ArrayList<PhieuMuon> list = new ArrayList<>();
        if (cursor.getCount()!=0){
            cursor.moveToFirst();
            do {
                try {
                    list.add(new PhieuMuon(cursor.getInt(0),
                            cursor.getInt(1),
                            cursor.getInt(2),
                            cursor.getInt(3),
                            cursor.getInt(4),
                            format.parse(cursor.getString(5)),
                            cursor.getInt(6)));
                }catch (ParseException e){
                    e.printStackTrace();
                }
            }while (cursor.moveToNext());
        }
        return list;
    }
}

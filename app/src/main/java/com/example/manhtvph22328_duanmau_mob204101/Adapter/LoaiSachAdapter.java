package com.example.manhtvph22328_duanmau_mob204101.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.manhtvph22328_duanmau_mob204101.Database.LoaiSachDao;
import com.example.manhtvph22328_duanmau_mob204101.Model.LoaiSach;
import com.example.manhtvph22328_duanmau_mob204101.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class LoaiSachAdapter extends RecyclerView.Adapter<LoaiSachAdapter.userViewHilder>{
    private Context context;
    private ArrayList<LoaiSach> arrayList;
    private LoaiSachDao loaiSachDao;

    public LoaiSachAdapter(Context context) {
        this.context = context;
    }
    public void setData(ArrayList<LoaiSach> arrayList1){
        this.arrayList = arrayList1;
        loaiSachDao = new LoaiSachDao(context);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public userViewHilder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loaisach,parent,false);
        return new userViewHilder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull userViewHilder holder, int position) {
        LoaiSach loaiSach = arrayList.get(position);
        if (loaiSach == null){
            return;
        }
        holder.tv_maLoai.setText("Mã loại: "+loaiSach.getMaLoai());
        holder.tv_tenLoai.setText("Tên loại: "+loaiSach.getTenLoai());

        holder.img_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogDelete(loaiSach);
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogUpdate(loaiSach);
            }
        });

    }

    @Override
    public int getItemCount() {
        if (arrayList != null){
            return arrayList.size();
        }
        return 0;
    }


    public class userViewHilder extends RecyclerView.ViewHolder{
        private TextView tv_tenLoai, tv_maLoai;
        private ImageView img_delete;
        public userViewHilder(@NonNull View itemView) {
            super(itemView);
            tv_maLoai = itemView.findViewById(R.id.tv_loaiSach_maLoai);
            tv_tenLoai = itemView.findViewById(R.id.tv_loaiSach_tenLoai);
            img_delete = itemView.findViewById(R.id.img_loaiSach_delete);
        }
    }
    private void dialogDelete(LoaiSach loaiSach){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Thông báo");
        builder.setTitle("Bạn có muốn xóa không");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                loaiSachDao.delete(loaiSach);
                arrayList = loaiSachDao.getAllLoaiSach();
                setData(arrayList);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }
    private void dialogUpdate(LoaiSach loaiSach){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_loaisach,null);
        TextInputEditText ed_maLoai = v.findViewById(R.id.ed_diaLs_maLs);
        TextInputEditText ed_tenLoai = v.findViewById(R.id.ed_diaLs_tenLs);

        ed_maLoai.setText(String.valueOf(loaiSach.getMaLoai()));
        ed_tenLoai.setText(String.valueOf(loaiSach.getTenLoai()));

        builder.setView(v);
        AlertDialog alertDialog = builder.create();
        v.findViewById(R.id.btn_diaLs_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loaiSachDao = new LoaiSachDao(context);
                loaiSach.setTenLoai(ed_tenLoai.getText().toString());
                int kq = loaiSachDao.update(loaiSach);
                if (kq == -1){
                    Toast.makeText(context,"Cập nhật thất bại",Toast.LENGTH_SHORT).show();
                }
                if (kq == 1){
                    Toast.makeText(context, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                    arrayList = loaiSachDao.getAllLoaiSach();
                    setData(arrayList);
                }
                alertDialog.cancel();
            }
        });
        v.findViewById(R.id.btn_diaLs_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.cancel();
            }
        });
        alertDialog.show();
    }
}

package com.example.manhtvph22328_duanmau_mob204101.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.manhtvph22328_duanmau_mob204101.Database.ThanhVienDao;
import com.example.manhtvph22328_duanmau_mob204101.Model.ThanhVien;
import com.example.manhtvph22328_duanmau_mob204101.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class ThanhVienAdapter extends RecyclerView.Adapter<ThanhVienAdapter.userViewHolder>{
    private Context context;
    private ArrayList<ThanhVien> arrayList;
    private ThanhVienDao thanhVienDao;

    public ThanhVienAdapter(Context context) {
        this.context = context;
    }
    public void setData(ArrayList<ThanhVien> arrayList){
        this.arrayList = arrayList;
        thanhVienDao = new ThanhVienDao(context);
    }

    @NonNull
    @Override
    public ThanhVienAdapter.userViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_thanhvien,parent,false);
        return new userViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ThanhVienAdapter.userViewHolder holder, int position) {
        ThanhVien thanhVien = arrayList.get(position);
        if (thanhVien == null){
            return;
        }
        holder.tvmaTv.setText("Mã: "+thanhVien.getMaTV());
        holder.tvtenTv.setText("Họ Tên: "+thanhVien.getTenTV());
        holder.tvngaySinh.setText("Năm sinh: "+thanhVien.getNamSinh());
        holder.tvcccd.setText("Cccd: "+thanhVien.getCccd());

        holder.imgdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogDelete(thanhVien);
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogUpdate(thanhVien);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(arrayList != null){
            return arrayList.size();
        }
        return 0;
    }

    public class userViewHolder extends RecyclerView.ViewHolder{
        private TextView tvmaTv, tvtenTv, tvngaySinh, tvcccd;
        private ImageView imgdelete;
        public userViewHolder(@NonNull View itemView){
            super(itemView);

            tvmaTv = itemView.findViewById(R.id.tv_thanhvien_ma);
            tvtenTv = itemView.findViewById(R.id.tv_thanhvien_ten);
            tvngaySinh = itemView.findViewById(R.id.tv_thanhvien_ngaySinh);
            tvcccd = itemView.findViewById(R.id.tv_thanhvien_cccd);
            imgdelete = itemView.findViewById(R.id.img_tv_delete);
        }
    }
    private void dialogDelete(ThanhVien thanhVien){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Thông báo");
        builder.setMessage("Bạn có muốn xóa không");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                thanhVienDao.delete(thanhVien);
                arrayList = thanhVienDao.getAllThanhVien();
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
    private void dialogUpdate(ThanhVien thanhVien){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_thanhvien, null);
        TextInputEditText edmaTv = v.findViewById(R.id.ed_diaTv_maTv);
        TextInputEditText edtenTv = v.findViewById(R.id.ed_diaTv_tenTv);
        TextInputEditText edngaySinh = v.findViewById(R.id.ed_diaTv_ngaySinh);
        TextInputEditText edcccd = v.findViewById(R.id.ed_diaTv_cccdTv);

        Button btnluu, btnhuy;
        btnluu = v.findViewById(R.id.btn_diaTv_ok);
        btnhuy = v.findViewById(R.id.btn_diaTv_delete);

        edmaTv.setText(String.valueOf(thanhVien.getMaTV()));
        edtenTv.setText(thanhVien.getTenTV());
        edngaySinh.setText(thanhVien.getNamSinh());
        edcccd.setText(String.valueOf(thanhVien.getCccd()));

        builder.setView(v);
        AlertDialog alertDialog = builder.create();
        btnluu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                thanhVienDao = new ThanhVienDao(context);
                thanhVien.setTenTV(edtenTv.getText().toString());
                thanhVien.setNamSinh(edngaySinh.getText().toString());
                thanhVien.setCccd(Long.parseLong(edcccd.getText().toString()));

                int kq = thanhVienDao.update(thanhVien);
                if (kq==-1){
                    Toast.makeText(context, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
                }
                if (kq == 1){
                    arrayList = thanhVienDao.getAllThanhVien();
                    setData(arrayList);
                    Toast.makeText(context, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                }
                alertDialog.cancel();
            }
        });

        btnhuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.cancel();
            }
        });
        alertDialog.show();
    }
}

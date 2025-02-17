package com.example.manhtvph22328_duanmau_mob204101.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.manhtvph22328_duanmau_mob204101.Model.Sach;
import com.example.manhtvph22328_duanmau_mob204101.R;

import java.util.ArrayList;

public class Top10Adapter extends RecyclerView.Adapter<Top10Adapter.userViewHolder> {
    private Context context;
    private ArrayList<Sach> arrayList;

    public Top10Adapter(Context context) {
        this.context = context;
    }
    public void setData(ArrayList<Sach> arrayList){
        this.arrayList = arrayList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public userViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_top10, parent,false);
        return new userViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull userViewHolder holder, int position) {
        Sach sach = arrayList.get(position);
        if (sach == null){
            return;
        }
        holder.tvmaSach.setText("Mã sách: "+sach.getMaSach());
        holder.tvtenSach.setText("Tên sách: "+sach.getTenSach());
        holder.tvgiaSach.setText("Giá thuê: "+String.valueOf(sach.getGiaThue()));
        holder.tvSl.setText("Số lượng: "+sach.getLoaiSach());
    }

    @Override
    public int getItemCount() {
        if (arrayList != null) {
            return arrayList.size();
        }
        return 0;
    }


    public class userViewHolder extends RecyclerView.ViewHolder{

        TextView tvmaSach, tvtenSach, tvgiaSach, tvSl;
        public userViewHolder(@NonNull View itemView){
            super(itemView);
            tvmaSach = itemView.findViewById(R.id.tv_top1_maSach);
            tvtenSach = itemView.findViewById(R.id.tv_top1_tenSach);
            tvgiaSach = itemView.findViewById(R.id.tv_top1_gia);
            tvSl = itemView.findViewById(R.id.tv_top1_soLuong);
        }
    }
}

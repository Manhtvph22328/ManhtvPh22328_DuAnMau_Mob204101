package com.example.manhtvph22328_duanmau_mob204101.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.manhtvph22328_duanmau_mob204101.Database.LoaiSachDao;
import com.example.manhtvph22328_duanmau_mob204101.Database.SachDao;
import com.example.manhtvph22328_duanmau_mob204101.Model.LoaiSach;
import com.example.manhtvph22328_duanmau_mob204101.Model.Sach;
import com.example.manhtvph22328_duanmau_mob204101.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class SachAdapter extends RecyclerView.Adapter<SachAdapter.userViewHolder> implements Filterable {
    private Context context;
    private ArrayList<Sach> listSach;
    private ArrayList<Sach> arrayList;
    private LoaiSachDao loaiSachDao;
    private SachDao sachDao;
    public SachAdapter(Context context) {
        this.context = context;
    }
    public void setData(ArrayList<Sach> arrayList){
        this.arrayList = arrayList;
        this.listSach = arrayList;
        notifyDataSetChanged();
        sachDao = new SachDao(context);
        loaiSachDao = new LoaiSachDao(context);
    }

    @NonNull
    @Override
    public userViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sach,parent,false);
        return new userViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull userViewHolder holder, int position) {
        Sach sach = arrayList.get(position);
        if (sach == null){
            return;
        }
        holder.tv_maSach.setText("Mã sách: "+sach.getMaSach());
        holder.tv_tenSach.setText("Tên sách: "+sach.getTenSach());
        holder.tv_giaTien.setText("Giá tiền: "+sach.getGiaThue());
        LoaiSach loaiSach = loaiSachDao.getID(sach.getLoaiSach());
        holder.tv_loaiSach.setText("Loại sách: "+(loaiSach!= null? loaiSach.getTenLoai() : "Loại sách không tồn tại"));
        holder.img_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogDelete(sach);
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (loaiSach == null){
                    Toast.makeText(context, "Cần phải thêm dữ liệu cho loại sách", Toast.LENGTH_SHORT).show();
                }else{
                    dialogUpdate(sach);
                }
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

    public class  userViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_maSach,tv_tenSach, tv_giaTien, tv_loaiSach;
        private ImageView img_delete;
        public userViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_maSach = itemView.findViewById(R.id.tv_sach_maSach);
            tv_loaiSach = itemView.findViewById(R.id.tv_sach_loaiSach);
            tv_tenSach = itemView.findViewById(R.id.tv_sach_tenSach);
            tv_giaTien = itemView.findViewById(R.id.tv_sach_gia);
            img_delete = itemView.findViewById(R.id.img_sach_delete);
        }
    }
    public void dialogDelete(Sach sach){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Thông báo");
        builder.setMessage("Bạn có muốn xóa không");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                sachDao.delete(sach);
                arrayList = sachDao.getAllSach();
                setData(arrayList);
            }
        });
        builder.setNegativeButton("Cacel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }
    public void dialogUpdate(Sach sach){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_sach,null);
        TextInputEditText edmaSach, edtenSach, edgiaSach;
        Spinner spnloaiSach;
        Button btn_luu, btn_huy;

        edmaSach = v.findViewById(R.id.ed_diaSach_maSach);
        edtenSach = v.findViewById(R.id.ed_diaSach_tenSach);
        edgiaSach = v.findViewById(R.id.ed_diaSach_giaSach);
        spnloaiSach = v.findViewById(R.id.spn_diaSach_Ls);
        btn_luu = v.findViewById(R.id.btn_diaSach_ok);
        btn_huy = v.findViewById(R.id.btn_diaSach_delete);
        loaiSachDao = new LoaiSachDao(context);
        List<String> loaiSach = new ArrayList<>();

        for (LoaiSach listLoaiSach: loaiSachDao.getAllLoaiSach()){
            loaiSach.add(listLoaiSach.getMaLoai()+"."+listLoaiSach.getTenLoai());
        }
        ArrayAdapter<String> adapterLoaiSach = new ArrayAdapter<>(context, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,loaiSach);
        spnloaiSach.setAdapter(adapterLoaiSach);

        edmaSach.setText(String.valueOf(sach.getMaSach()));
        edtenSach.setText(sach.getTenSach());
        edgiaSach.setText(String.valueOf(sach.getGiaThue()));
        for (int i=0; i<loaiSach.size(); i++){
            String chuoi = loaiSach.get(i);
            String[] chuoi2 = chuoi.split("\\.");
            if (sach.getLoaiSach()==Integer.parseInt(chuoi2[0])){
                spnloaiSach.setSelection(i);
            }
        }
        builder.setView(v);
        AlertDialog alertDialog = builder.create();
        btn_luu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String LoaiSach = (String) spnloaiSach.getSelectedItem();
                String[] maLoai = LoaiSach.split("\\.");
                sach.setTenSach(edtenSach.getText().toString());
                sach.setGiaThue(Integer.parseInt(edgiaSach.getText().toString()));
                sach.setLoaiSach(Integer.parseInt(maLoai[0]));
                int kq = sachDao.update(sach);
                if (kq==-1){
                    Toast.makeText(context, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
                }
                if (kq ==1){
                    arrayList =sachDao.getAllSach();
                    setData(arrayList);
                    Toast.makeText(context, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                }
                alertDialog.cancel();
            }
        });

        btn_huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.cancel();
            }
        });
        alertDialog.show();
    }

    @Override
    public Filter getFilter(){
        return new Filter() {

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String tenSach = constraint.toString();
                ArrayList<Sach> list = new ArrayList<>();
                if (tenSach.isEmpty()){
                    arrayList = listSach;
                }
                for (Sach list2: listSach){
                    if (list2.getTenSach().toLowerCase().contains(tenSach.toLowerCase())){
                        list.add(list2);
                    }
                }
                arrayList = list;
                FilterResults filterResults = new FilterResults();
                filterResults.values = arrayList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                arrayList = (ArrayList<Sach>) results.values;
                notifyDataSetChanged();
            }
        };
    }

}

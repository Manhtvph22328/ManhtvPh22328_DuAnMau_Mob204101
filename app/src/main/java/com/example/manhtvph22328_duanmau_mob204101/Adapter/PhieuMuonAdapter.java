package com.example.manhtvph22328_duanmau_mob204101.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.manhtvph22328_duanmau_mob204101.Database.PhieuMuonDao;
import com.example.manhtvph22328_duanmau_mob204101.Database.SachDao;
import com.example.manhtvph22328_duanmau_mob204101.Database.ThanhVienDao;
import com.example.manhtvph22328_duanmau_mob204101.Model.PhieuMuon;
import com.example.manhtvph22328_duanmau_mob204101.Model.Sach;
import com.example.manhtvph22328_duanmau_mob204101.Model.ThanhVien;
import com.example.manhtvph22328_duanmau_mob204101.R;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PhieuMuonAdapter extends RecyclerView.Adapter<PhieuMuonAdapter.PmViewHolder> {

    private ArrayList<PhieuMuon> arrayList;
    private Context context;
    private PhieuMuonDao phieuMuonDao;
    private ThanhVienDao thanhVienDao;
    private SachDao sachDao;
    private Spinner spn_TenTV, spn_TenSach;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("hh:m:ss");

    public PhieuMuonAdapter(Context context){
        this.context = context;
    }

    public void  setData(ArrayList<PhieuMuon> arrayList1){
        this.arrayList = arrayList1;
        notifyDataSetChanged();
        phieuMuonDao = new PhieuMuonDao(context);
        thanhVienDao = new ThanhVienDao(context);
        sachDao = new SachDao(context);
    }

    @NonNull
    @Override
    public PmViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_phieumuon,parent,false);
        return new PmViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PmViewHolder holder, int position) {
        PhieuMuon phieuMuon = arrayList.get(position);
        if (phieuMuon == null){
            return;
        }
        holder.tvmaPM.setText(String.valueOf(phieuMuon.getMaPhieu()));
        if (phieuMuon.getTienThue()>5000){
            holder.tvmaPM.setTextColor(Color.parseColor("#FFDA1D0F"));
        }else {
            holder.tvmaPM.setTextColor(Color.parseColor("#2638D7"));
        }
        ThanhVien thanhVien = thanhVienDao.getID(phieuMuon.getMaTV());
        holder.tvtenTV.setText(thanhVien.getTenTV());
        Sach sach = sachDao.getID(phieuMuon.getMaSach());
        holder.tvtenSach.setText(String.valueOf(phieuMuon.getTienThue()));
        holder.tvngay.setText(new SimpleDateFormat("dd/MM/yyyy").format(phieuMuon.getNgayMuon()));
        holder.img_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogDelete(phieuMuon);
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogDelete(phieuMuon);
            }
        });
        if (phieuMuon.getTraSach()==1){
            holder.tvtraSach.setText("Đã trả sách");
            holder.tvtraSach.setTextColor(Color.parseColor("#2638D7"));
        }
        else {
            holder.tvtraSach.setText("Chưa trả sách");
            holder.tvtraSach.setTextColor(Color.parseColor("#FFDA1D0F"));
        }
    }

    @Override
    public int getItemCount() {
        if (arrayList != null){
            return arrayList.size();
        }
        return 0;
    }

    public class PmViewHolder extends RecyclerView.ViewHolder {
        private TextView tvmaPM, tvtenTV, tvtenSach, tvTien, tvngay, tvtraSach;
        private ImageView img_delete;
        public PmViewHolder(@NonNull View itemView) {
            super(itemView);
            tvmaPM = itemView.findViewById(R.id.tv_phieuMuon_ma);
            tvtenTV = itemView.findViewById(R.id.tv_phieuMuon_tenTv);
            tvtenSach = itemView.findViewById(R.id.tv_phieuMuon_tenSach);
            tvTien = itemView.findViewById(R.id.tv_phieuMuon_tien);
            tvngay = itemView.findViewById(R.id.tv_phieuMuon_ngay);
            tvtraSach = itemView.findViewById(R.id.tv_phieuMuon_tinhTrang);
            img_delete = itemView.findViewById(R.id.img_phieuMuon_delete);

        }
    }
    private void dialogDelete(PhieuMuon phieuMuon){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Thông báo");
        builder.setMessage("Bạn có muốn xóa không");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                phieuMuonDao.delete(phieuMuon);
                arrayList = phieuMuonDao.getAllPhieuMuon();
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
    private void dialogUpdate(PhieuMuon phieuMuon){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_phieumuon,null);

        TextView tvngayThue, tvtienThue;
        TextInputEditText edmaPm;
        CheckBox chk_traSach;

        edmaPm = v.findViewById(R.id.ed_diaPm_maPhieu);
        tvngayThue = v.findViewById(R.id.tv_diaPm_ngay);
        tvtienThue = v.findViewById(R.id.tv_diaPm_tien);
        chk_traSach = v.findViewById(R.id.chk_traSach);
        spn_TenTV = v.findViewById(R.id.spn_diaPm_tenTV);
        spn_TenSach = v.findViewById(R.id.spn_diaPm_tenSach);

        thanhVienDao = new ThanhVienDao(context);
        sachDao = new SachDao(context);

        edmaPm.setText(String.valueOf(phieuMuon.getMaPhieu()));
        List<String> tenTv = new ArrayList<>();
        for (ThanhVien list: thanhVienDao.getAllThanhVien()){
            tenTv.add(list.getMaTV()+"."+list.getTenTV());
        }
        Spn_Adapter(spn_TenTV, tenTv);
        List<String> sach = new ArrayList<>();
        for (Sach listSach : sachDao.getAllSach()){
            sach.add(listSach.getMaSach()+"."+listSach.getTenSach());
        }
        Spn_Adapter(spn_TenSach, sach);
        for (int i =0; i<tenTv.size(); i++){
            String chuoi = tenTv.get(i);
            String[] chuoi2 = chuoi.split("\\.");
            if (phieuMuon.getMaTV() == Integer.parseInt(chuoi2[0])){
                spn_TenTV.setSelection(i);
            }
        }
        for (int i =0; i<tenTv.size(); i++){
            String chuoi = sach.get(i);
            String[] chuoi2 = chuoi.split("\\.");
            if (phieuMuon.getMaSach() == Integer.parseInt(chuoi2[0])){
                spn_TenSach.setSelection(i);
            }
        }
        tvngayThue.setText("Ngày thuê: "+ new SimpleDateFormat("dd/MM/yyyy").format(phieuMuon.getNgayMuon()));
        spn_TenSach.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Sach sach1 = sachDao.getID(split(spn_TenSach));
                tvtienThue.setText((String.valueOf(sach1.getGiaThue())));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        builder.setView(v);
        AlertDialog alertDialog = builder.create();

        v.findViewById(R.id.btn_diaPm_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phieuMuonDao = new PhieuMuonDao(context);
                phieuMuon.setMaTV(split(spn_TenTV));
                phieuMuon.setMaSach(split(spn_TenSach));
                phieuMuon.setTienThue(Integer.parseInt(tvtienThue.getText().toString()));
                phieuMuon.setNgayMuon(new Date());

                if (chk_traSach.isChecked()){
                    phieuMuon.setTraSach(1);
                }else {
                    phieuMuon.setTraSach(0);
                }
                int kq = phieuMuonDao.update(phieuMuon);
                if (kq ==-1){
                    Toast.makeText(context, "Cập nhật nhất bại", Toast.LENGTH_SHORT).show();
                }
                if (kq ==1){
                    Toast.makeText(context, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                }
                arrayList = phieuMuonDao.getAllPhieuMuon();
                setData(arrayList);
                alertDialog.cancel();
            }
        });

        v.findViewById(R.id.btn_diaPm_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.cancel();
            }
        });
        alertDialog.show();
    }

    public void Spn_Adapter(Spinner spn, List<String> list){
        if (list!= null){
            ArrayAdapter<String> adapterSach = new ArrayAdapter<>(context, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, list);
            spn.setAdapter(adapterSach);
        }else {
            Toast.makeText(context, "Hiện tại đang không có dữ liệu, Vui lòng thêm dữ liệu để xuất hóa đơn", Toast.LENGTH_SHORT).show();
        }
    }
    public int split(Spinner spn){
        if (spn.getSelectedItem()!= null){
            String chuoi = (String) spn.getSelectedItem();
            String[] chuoi2 = chuoi.split("\\.");
            return Integer.parseInt(chuoi2[0]);
        }else {
            Toast.makeText(context, "Thành viên hoặc sách hiện đang khong có , bạn cần thêm dữ liệu", Toast.LENGTH_SHORT).show();
            return -1;
        }
    }
}

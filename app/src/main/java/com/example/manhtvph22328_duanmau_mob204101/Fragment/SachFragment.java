package com.example.manhtvph22328_duanmau_mob204101.Fragment;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.manhtvph22328_duanmau_mob204101.Adapter.SachAdapter;
import com.example.manhtvph22328_duanmau_mob204101.Database.LoaiSachDao;
import com.example.manhtvph22328_duanmau_mob204101.Database.SachDao;
import com.example.manhtvph22328_duanmau_mob204101.Model.LoaiSach;
import com.example.manhtvph22328_duanmau_mob204101.Model.Sach;
import com.example.manhtvph22328_duanmau_mob204101.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;


public class SachFragment extends Fragment {

    private RecyclerView recyclerView;
    private FloatingActionButton actionButton;
    private ArrayList<Sach> list = new ArrayList<>();
    private SachDao sachDao;
    private SachAdapter adapter;
    private LoaiSachDao loaiSachDao;
    EditText ed_timKiem;
    Button btn_TimKiem;

    public SachFragment() {
        // Required empty public constructor
    }

    public static SachFragment newInstance() {
        SachFragment fragment = new SachFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sachDao = new SachDao(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sach, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle saveInstanceState){
        super.onViewCreated(view, saveInstanceState);
        recyclerView = view.findViewById(R.id.recy_S);
        actionButton = view.findViewById(R.id.floating_S);

        ed_timKiem = view.findViewById(R.id.ed_timkiemSach);
        btn_TimKiem = view.findViewById(R.id.btntimkiemSach);
        btn_TimKiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ed_timKiem.getText().toString().isEmpty()){
                    Toast.makeText(getActivity(), "Bạn phải nhập thông tin tên sách để tìm kiếm thông tin", Toast.LENGTH_SHORT).show();
                }
                adapter.getFilter().filter(ed_timKiem.getText().toString().trim());
            }
        });
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_Sach();
            }
        });
    }

    public void dialog_Sach(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_sach, null);
        TextInputEditText edmaSach, edtenSach, edgiaSach;
        Spinner spn_loaiSach;
        Button btnLuu, btnHuy;
        edmaSach = v.findViewById(R.id.ed_diaSach_maSach);
        edtenSach = v.findViewById(R.id.ed_diaSach_tenSach);
        edgiaSach = v.findViewById(R.id.ed_diaSach_giaSach);
        spn_loaiSach = v.findViewById(R.id.spn_diaSach_Ls);
        btnLuu = v.findViewById(R.id.btn_diaSach_ok);
        btnHuy = v.findViewById(R.id.btn_diaSach_delete);

        loaiSachDao = new LoaiSachDao(getActivity());
        List<String> loaiSach = new ArrayList<>();
        for (LoaiSach listLoaiSach: loaiSachDao.getAllLoaiSach()){
            loaiSach.add(listLoaiSach.getMaLoai()+"."+listLoaiSach.getTenLoai());
        }
        ArrayAdapter<String> adapterLoaiSach = new ArrayAdapter<>(getActivity(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,loaiSach);
        spn_loaiSach.setAdapter(adapterLoaiSach);
        builder.setView(v);
        AlertDialog alertDialog = builder.create();
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtenSach.length()==0 || edgiaSach.length()==0){
                    Toast.makeText(getActivity(), "Không để trống", Toast.LENGTH_SHORT).show();
                }else {
                    Sach sach = new Sach();
                    String Loaisach = (String) spn_loaiSach.getSelectedItem();
                    if (Loaisach != null){
                        String[] maLoai = Loaisach.split("\\.");
                        sach.setTenSach(edtenSach.getText().toString());
                        sach.setGiaThue(Integer.parseInt(edgiaSach.getText().toString()));
                        sach.setLoaiSach(Integer.parseInt(maLoai[0]));

                        int kq = sachDao.Insert(sach);
                        if (kq == -1){
                            Toast.makeText(getActivity(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
                        }
                        if (kq == 1){
                            Toast.makeText(getActivity(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                        }
                        onResume();
                        alertDialog.cancel();
                    }else {
                        Toast.makeText(getContext(), "Vui lòng thêm loại sách trước rồi mới thêm sách", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.cancel();
            }
        });
        alertDialog.show();
    }

    @Override
    public void onResume(){
        super.onResume();
        sachDao = new SachDao(getActivity());
        list.clear();
        list = sachDao.getAllSach();
        adapter = new SachAdapter(getActivity());
        adapter.setData(list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }
}
package com.example.manhtvph22328_duanmau_mob204101.Fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.manhtvph22328_duanmau_mob204101.Adapter.LoaiSachAdapter;
import com.example.manhtvph22328_duanmau_mob204101.Database.LoaiSachDao;
import com.example.manhtvph22328_duanmau_mob204101.Model.LoaiSach;
import com.example.manhtvph22328_duanmau_mob204101.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class LoaiSachFragment extends Fragment {

    private RecyclerView recyclerView;
    private LoaiSachDao loaiSachDao;
    private LoaiSachAdapter adapter;
    private ArrayList<LoaiSach> list = new ArrayList<>();
    private Context context;

    public LoaiSachFragment() {
        // Required empty public constructor
    }

    public static LoaiSachFragment newInstance(String param1, String param2) {
        LoaiSachFragment fragment = new LoaiSachFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_loai_sach, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle saveInstanceState){
        super.onViewCreated(view, saveInstanceState);
        recyclerView = view.findViewById(R.id.recy_Ls);
        FloatingActionButton actionButton = view.findViewById(R.id.floating_Ls);
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_Ls();
            }
        });
    }

    public void dialog_Ls(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_loaisach, null);
        TextInputEditText edmaLoai = v.findViewById(R.id.ed_diaLs_maLs);
        TextInputEditText edtenLoai = v.findViewById(R.id.ed_diaLs_tenLs);
        builder.setView(v);
        AlertDialog alertDialog = builder.create();
        v.findViewById(R.id.btn_diaLs_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loaiSachDao = new LoaiSachDao(getActivity());
                LoaiSach loaiSach = new LoaiSach();
                loaiSach.setTenLoai(edtenLoai.getText().toString());
                int kq = loaiSachDao.Insert(loaiSach);
                if (kq == -1){
                    Toast.makeText(getActivity(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
                }
                if (kq == 1){
                    Toast.makeText(getActivity(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                }
                onResume();
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

    @Override
    public void onResume(){
        super.onResume();
        loaiSachDao = new LoaiSachDao(getActivity());
        list.clear();
        list = loaiSachDao.getAllLoaiSach();
        adapter = new LoaiSachAdapter(getActivity());
        adapter.setData(list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }
}
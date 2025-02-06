package com.example.manhtvph22328_duanmau_mob204101.Fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import com.example.manhtvph22328_duanmau_mob204101.Adapter.ThanhVienAdapter;
import com.example.manhtvph22328_duanmau_mob204101.Database.ThanhVienDao;
import com.example.manhtvph22328_duanmau_mob204101.Model.ThanhVien;
import com.example.manhtvph22328_duanmau_mob204101.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class ThanhVienFragment extends Fragment {

    private RecyclerView recyclerView;
    private FloatingActionButton actionButton;
    private ArrayList<ThanhVien> list = new ArrayList<>();
    private ThanhVienAdapter adapter;
    private ThanhVienDao thanhVienDao;
    private int day, month, year;

    public ThanhVienFragment() {
        // Required empty public constructor
    }


    public static ThanhVienFragment newInstance(String param1, String param2) {
        ThanhVienFragment fragment = new ThanhVienFragment();
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
        return inflater.inflate(R.layout.fragment_thanh_vien, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recy_Tv);
        actionButton = view.findViewById(R.id.floating_Tv);
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_Tv();
            }
        });
    }
    @SuppressLint("MissingInflatedId")
    public void dialog_Tv(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_thanhvien, null);
        TextInputEditText ed_tenTv = v.findViewById(R.id.ed_diaTv_tenTv);
        TextInputEditText ed_ngaySinh = v.findViewById(R.id.ed_diaTv_ngaySinh);

        Button btn_luu,btn_huy;
        btn_luu = v.findViewById(R.id.btn_diaTv_ok);
        btn_huy = v.findViewById(R.id.btn_diaTv_delete);
        builder.setView(v);
        AlertDialog alertDialog = builder.create();
        Calendar calendar = Calendar.getInstance();
        day = calendar.get(Calendar.DAY_OF_MONTH);
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        ed_ngaySinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        calendar.set(year,month, dayOfMonth);
                        ed_ngaySinh.setText(simpleDateFormat.format(calendar.getTime()));
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });

        btn_luu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ed_ngaySinh.length() == 0 || ed_tenTv.length() == 0){
                    Toast.makeText(getActivity(), "Không để trống", Toast.LENGTH_SHORT).show();
                }else {
                    thanhVienDao = new ThanhVienDao(getActivity());
                    ThanhVien thanhVien = new ThanhVien();
                    thanhVien.setTenTV(ed_tenTv.getText().toString());
                    thanhVien.setNamSinh(ed_ngaySinh.getText().toString());

                    int kq = thanhVienDao.Insert(thanhVien);
                    if (kq == -1){
                        Toast.makeText(getActivity(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
                    }
                    if (kq == 1){
                        Toast.makeText(getActivity(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                    }
                    onResume();
                    alertDialog.cancel();
                }
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
    public void onResume(){
        super.onResume();
        thanhVienDao = new ThanhVienDao(getActivity());
        list.clear();
        list = thanhVienDao.getAllThanhVien();
        adapter = new ThanhVienAdapter(getActivity());
        adapter.setData(list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }
}
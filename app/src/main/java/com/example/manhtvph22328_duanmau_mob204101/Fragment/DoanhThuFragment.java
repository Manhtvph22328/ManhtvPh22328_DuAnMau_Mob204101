package com.example.manhtvph22328_duanmau_mob204101.Fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import com.example.manhtvph22328_duanmau_mob204101.Database.PhieuMuonDao;
import com.example.manhtvph22328_duanmau_mob204101.R;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class DoanhThuFragment extends Fragment {

    private TextInputEditText edtuNgay, eddenNgay;
    private TextView tv_doanhThu;
    private int day, month, year;
    private PhieuMuonDao phieuMuonDao;

    public DoanhThuFragment() {
        // Required empty public constructor
    }


    public static DoanhThuFragment newInstance(String param1, String param2) {
        DoanhThuFragment fragment = new DoanhThuFragment();
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
        return inflater.inflate(R.layout.fragment_doanh_thu, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle saveInstanceState){
        super.onViewCreated(view, saveInstanceState);
        edtuNgay = view.findViewById(R.id.ed_DT_tuNgay);
        eddenNgay = view.findViewById(R.id.ed_DT_denNgay);
        tv_doanhThu = view.findViewById(R.id.tvdoanhthu);

        Calendar calendar = Calendar.getInstance();
        day = calendar.get(Calendar.DAY_OF_MONTH);
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        edtuNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        calendar.set(year, month, dayOfMonth);
                        edtuNgay.setText(simpleDateFormat.format(calendar.getTime()));
                    }
                },year, month, day);
                datePickerDialog.show();
            }
        });

        eddenNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        calendar.set(year, month, dayOfMonth);
                        eddenNgay.setText(simpleDateFormat.format(calendar.getTime()));
                    }
                },year, month, day);
                datePickerDialog.show();
            }
        });

        view.findViewById(R.id.btndoanhthu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phieuMuonDao = new PhieuMuonDao(getActivity());
                tv_doanhThu.setText(String.valueOf(phieuMuonDao.DoanhThu(edtuNgay.getText().toString(), eddenNgay.getText().toString())));
            }
        });
    }
}
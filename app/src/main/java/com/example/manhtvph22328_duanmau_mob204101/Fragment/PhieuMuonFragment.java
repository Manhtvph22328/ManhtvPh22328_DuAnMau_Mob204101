package com.example.manhtvph22328_duanmau_mob204101.Fragment;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.manhtvph22328_duanmau_mob204101.Adapter.PhieuMuonAdapter;
import com.example.manhtvph22328_duanmau_mob204101.Database.PhieuMuonDao;
import com.example.manhtvph22328_duanmau_mob204101.Database.SachDao;
import com.example.manhtvph22328_duanmau_mob204101.Database.ThanhVienDao;
import com.example.manhtvph22328_duanmau_mob204101.Model.PhieuMuon;
import com.example.manhtvph22328_duanmau_mob204101.Model.Sach;
import com.example.manhtvph22328_duanmau_mob204101.Model.ThanhVien;
import com.example.manhtvph22328_duanmau_mob204101.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PhieuMuonFragment extends Fragment {

    private RecyclerView recyclerView;
    private FloatingActionButton actionButton;
    private PhieuMuonAdapter adapter;
    private ArrayList<PhieuMuon> list = new ArrayList<>();
    private ThanhVienDao thanhVienDao;
    private PhieuMuonDao phieuMuonDao;
    private SachDao sachDao;
    private Spinner spntenTv, spntenSach;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm");


    public PhieuMuonFragment() {
        // Required empty public constructor
    }


    public static PhieuMuonFragment newInstance(String param1, String param2) {
        PhieuMuonFragment fragment = new PhieuMuonFragment();
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
        return inflater.inflate(R.layout.fragment_phieu_muon, container, false);
    }

    @Override
    public void onViewCreated(@Nullable View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recy_Pm);
        actionButton = view.findViewById(R.id.floating_Pm);
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_Pm();

            }
        });

        EditText ed_timKiem = view.findViewById(R.id.edtimkiem);
        Button btn_timKiem = view.findViewById(R.id.btntimkiem);
        btn_timKiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    phieuMuonDao = new PhieuMuonDao(getActivity());
                    list = phieuMuonDao.getAllByMaPM(Integer.parseInt(ed_timKiem.getText().toString()));
                    adapter = new PhieuMuonAdapter(getActivity());
                    adapter.setData(list);

                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                    recyclerView.setLayoutManager(linearLayoutManager);
                    recyclerView.setAdapter(adapter);
                } catch (NumberFormatException e) {
                    Toast.makeText(getActivity(), "Giá trị nhập vào là số không phải kí tự", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void dialog_Pm() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_phieumuon, null);
        TextView tv_ngayThue, tv_tienThue;
        CheckBox chk_traSach;
        tv_ngayThue = v.findViewById(R.id.tv_diaPm_ngay);
        tv_tienThue = v.findViewById(R.id.tv_diaPm_tien);
        chk_traSach = v.findViewById(R.id.chk_traSach);
        spntenTv = v.findViewById(R.id.spn_diaPm_tenTV);
        spntenSach = v.findViewById(R.id.spn_diaPm_tenSach);

        thanhVienDao = new ThanhVienDao(getActivity());
        sachDao = new SachDao(getActivity());

        List<String> tenTv = new ArrayList<>();
        for (ThanhVien list : thanhVienDao.getAllThanhVien()) {
            tenTv.add(list.getMaTV() + "." + list.getTenTV());
        }

        Spn_Adapter(spntenTv, tenTv);
        List<String> sach = new ArrayList<>();
        for (Sach listSach : sachDao.getAllSach()) {
            sach.add(listSach.getMaSach() + "." + listSach.getTenSach());
        }
        Spn_Adapter(spntenSach, sach);

        tv_ngayThue.setText("Ngày Thuê: " + new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
        spntenSach.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Sach sach1 = sachDao.getID(split(spntenSach));
                tv_tienThue.setText(String.valueOf(sach1.getGiaThue()));
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
                phieuMuonDao = new PhieuMuonDao(getActivity());
                PhieuMuon phieuMuon = new PhieuMuon();
                phieuMuon.setMaTV(split(spntenTv));
                phieuMuon.setMaSach(split(spntenSach));

                phieuMuon.setTienThue(Integer.parseInt(tv_tienThue.getText().toString()));
                phieuMuon.setNgayMuon(new Date());

                if (chk_traSach.isChecked()) {
                    phieuMuon.setTraSach(1);
                } else {
                    phieuMuon.setTraSach(0);
                }
                int kq = phieuMuonDao.Insert(phieuMuon);
                if (kq == -1) {
                    Toast.makeText(getActivity(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
                }
                if (kq == 1) {
                    Toast.makeText(getActivity(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                }
                onResume();
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

    @Override
    public void onResume() {
        super.onResume();
        phieuMuonDao = new PhieuMuonDao(getActivity());
        list.clear();
        list = phieuMuonDao.getAllPhieuMuon();
        adapter = new PhieuMuonAdapter(getActivity());
        adapter.setData(list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    public void Spn_Adapter(Spinner spn, List<String> list) {
        if (list != null) {
            ArrayAdapter<String> adapterSach = new ArrayAdapter<>(getActivity(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, list);
            spn.setAdapter(adapterSach);
        } else {
            Toast.makeText(getActivity(), "Hiện tại đang không có dữ liêu, Vui lòng thêm dữ liệu để xuất hóa đơn", Toast.LENGTH_SHORT).show();
        }
    }

    public int split(Spinner spn) {
        if (spn.getSelectedItem() != null) {
            String chuoi = (String) spn.getSelectedItem();
            String[] chuoi2 = chuoi.split("\\.");
            return Integer.parseInt(chuoi2[0]);
        } else {
            Toast.makeText(getActivity(), "Thành viên hoặc sách hiện đang không có , bạn cần thêm dữ liệu", Toast.LENGTH_SHORT).show();
            return -1;
        }
    }
}
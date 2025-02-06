package com.example.manhtvph22328_duanmau_mob204101.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.manhtvph22328_duanmau_mob204101.Database.ThuThuDao;
import com.example.manhtvph22328_duanmau_mob204101.Model.ThuThu;
import com.example.manhtvph22328_duanmau_mob204101.R;
import com.google.android.material.textfield.TextInputEditText;

public class ThemNguoiDungFragment extends Fragment {

    private TextInputEditText edmaTT, edhoTen, edMatkhau, ednhapLai;
    private Button btnthem, btnhuy;
    private ThuThuDao thuThuDao;

    public ThemNguoiDungFragment() {
        // Required empty public constructor
    }

    public static ThemNguoiDungFragment newInstance(String param1, String param2) {
        ThemNguoiDungFragment fragment = new ThemNguoiDungFragment();
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
        return inflater.inflate(R.layout.fragment_them_nguoi_dung, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle saveInstanceState){
        super.onViewCreated(view, saveInstanceState);
        edmaTT = view.findViewById(R.id.ed_themND_tenDN);
        edhoTen = view.findViewById(R.id.ed_themND_hoTen);
        edMatkhau = view.findViewById(R.id.ed_themND_Mk);
        ednhapLai = view.findViewById(R.id.ed_themND_MkLai);
        btnthem = view.findViewById(R.id.btn_themND_Them);
        btnhuy = view.findViewById(R.id.btn_themND_Huy);
        thuThuDao = new ThuThuDao(getActivity());
        btnthem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edmaTT.length()==0||edhoTen.length()==0||edMatkhau.length()==0||ednhapLai.length()==0){
                    Toast.makeText(getActivity(), "Không để trống", Toast.LENGTH_SHORT).show();
                } else if (edhoTen.length()<5&edhoTen.length()>15) {
                    Toast.makeText(getActivity(), "Tên đăng nhập phải ít nhất 5 kí tự và nhiều nhất 15 kí tự", Toast.LENGTH_SHORT).show();
                } else if (edhoTen.getText().toString().charAt(0)>100) {
                    Toast.makeText(getActivity(), "Bạn phải viết hoa chữ cái đầu", Toast.LENGTH_SHORT).show();
                }else {
                    if (edMatkhau.getText().toString().equals(ednhapLai.getText().toString())){
                        ThuThu thuThu = new ThuThu();
                        thuThu.setMaTT(edmaTT.getText().toString());
                        thuThu.setHoTen(edhoTen.getText().toString());
                        thuThu.setMatKhau(edMatkhau.getText().toString());
                        int kq = thuThuDao.Insert(thuThu);
                        if (kq == -1) {
                            Toast.makeText(getActivity(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
                        }
                        if (kq == 1) {
                            Toast.makeText(getActivity(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
        btnhuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
    }
}
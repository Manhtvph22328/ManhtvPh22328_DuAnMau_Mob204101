package com.example.manhtvph22328_duanmau_mob204101.Fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.manhtvph22328_duanmau_mob204101.Database.ThuThuDao;
import com.example.manhtvph22328_duanmau_mob204101.Model.ThuThu;
import com.example.manhtvph22328_duanmau_mob204101.R;
import com.google.android.material.textfield.TextInputEditText;

public class DoiMkFragment extends Fragment {

    private TextInputEditText ed_Mkcu, ed_Mkmoi, ed_nhapLaimk;
    private ThuThuDao thuThuDao;
    private Button ok,del;
    private TextView hhh;
    private Context context;
    private LinearLayout l ;
    public DoiMkFragment() {
        // Required empty public constructor
    }

    public static DoiMkFragment newInstance(String param1, String param2) {
        DoiMkFragment fragment = new DoiMkFragment();
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
        return inflater.inflate(R.layout.fragment_doi_mk, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle saveInstanceState) {
        super.onViewCreated(view, saveInstanceState);
        ed_Mkcu = view.findViewById(R.id.eddoiMk_mkcu);
        ed_Mkmoi = view.findViewById(R.id.eddoiMk_mkmoi);
        ed_nhapLaimk = view.findViewById(R.id.eddoiMk_nhaclaimk);
        del= view.findViewById(R.id.btn_doimk_delete);
        ok = view.findViewById(R.id.btn_doimk_ok);
        thuThuDao = new ThuThuDao(getActivity());


        Bundle args = getArguments();

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (args != null) {
                    String maTT = args.getString("Tk");
                    ThuThu thuThu = thuThuDao.getId(maTT);
                    if (ed_Mkcu.getText().toString().equals(thuThu.getMatKhau()) && ed_Mkmoi.getText().toString().equals(ed_nhapLaimk.getText().toString())) {
                        thuThu.setMatKhau(ed_Mkmoi.getText().toString());
                        int kq = thuThuDao.update(thuThu);
                        if (kq == -1) {
                            Toast.makeText(getActivity(), "Đổi thất bại", Toast.LENGTH_SHORT).show();
                        }
                        if (kq == 1) {
                            Toast.makeText(getActivity(), "Đổi thành công", Toast.LENGTH_SHORT).show();
                        }
                    } else if (ed_Mkcu.length() == 0 || ed_Mkmoi.length() == 0 || ed_nhapLaimk.length() == 0) {
                        Toast.makeText(getActivity(), "Không để trống", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), "Sai thông tin", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        view.findViewById(R.id.btn_doimk_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onVisibleBehindCanceled();
            }
        });


    }
}
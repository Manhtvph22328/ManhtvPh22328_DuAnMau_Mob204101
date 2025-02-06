package com.example.manhtvph22328_duanmau_mob204101.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.manhtvph22328_duanmau_mob204101.Adapter.Top10Adapter;
import com.example.manhtvph22328_duanmau_mob204101.Database.PhieuMuonDao;
import com.example.manhtvph22328_duanmau_mob204101.Model.Sach;
import com.example.manhtvph22328_duanmau_mob204101.R;

import java.util.ArrayList;

public class Top10Fragment extends Fragment {

    private ArrayList<Sach> list = new ArrayList<>();
    private PhieuMuonDao phieuMuonDao;
    private Top10Adapter adapter;
    private RecyclerView recyclerView;

    public Top10Fragment() {
        // Required empty public constructor
    }

    public static Top10Fragment newInstance(String param1, String param2) {
        Top10Fragment fragment = new Top10Fragment();
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
        return inflater.inflate(R.layout.fragment_top10, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recy_Top);
        phieuMuonDao = new PhieuMuonDao(getActivity());
        list.clear();
        list = phieuMuonDao.top10();
        adapter = new Top10Adapter(getActivity());
        adapter.setData(list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }
}
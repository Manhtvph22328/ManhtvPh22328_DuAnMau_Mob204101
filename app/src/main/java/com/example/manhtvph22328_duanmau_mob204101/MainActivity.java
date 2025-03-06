package com.example.manhtvph22328_duanmau_mob204101;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.manhtvph22328_duanmau_mob204101.Database.ThuThuDao;
import com.example.manhtvph22328_duanmau_mob204101.Fragment.DoanhThuFragment;
import com.example.manhtvph22328_duanmau_mob204101.Fragment.DoiMkFragment;
import com.example.manhtvph22328_duanmau_mob204101.Fragment.LoaiSachFragment;
import com.example.manhtvph22328_duanmau_mob204101.Fragment.PhieuMuonFragment;
import com.example.manhtvph22328_duanmau_mob204101.Fragment.SachFragment;
import com.example.manhtvph22328_duanmau_mob204101.Fragment.ThanhVienFragment;
import com.example.manhtvph22328_duanmau_mob204101.Fragment.ThemNguoiDungFragment;
import com.example.manhtvph22328_duanmau_mob204101.Fragment.Top10Fragment;
import com.example.manhtvph22328_duanmau_mob204101.Model.ThuThu;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private FrameLayout frameLayout;
    private ThuThuDao thuThuDao;
    private TextView tv_header;

    private DoiMkFragment doiMkFragment = new DoiMkFragment();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawerLayout);
        frameLayout = findViewById(R.id.frameLayout);
        toolbar = findViewById(R.id.toolBar);
        navigationView = findViewById(R.id.navigationView);

        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,0,0);
        toggle.syncState();

        Intent intent = getIntent();
        String maTT = intent.getStringExtra("TK");
        Bundle bundle = new Bundle();
        bundle.putString("TK", maTT);
        doiMkFragment.setArguments(bundle);

        thuThuDao = new ThuThuDao(this);
        ThuThu thuThu = thuThuDao.getId(maTT);

        MenuItem item = navigationView.getMenu().findItem(R.id.nd_themNd);
        if (thuThu.getHoTen().equals("Admin User")){
            item.setEnabled(true);
        }else {
            item.setEnabled(false);
        }
        View header = navigationView.getHeaderView(0);
        tv_header = header.findViewById(R.id.tv_head);
        tv_header.setText(thuThu.getHoTen());
        replaceFragment(new PhieuMuonFragment());
        navigationView.setNavigationItemSelectedListener((NavigationView.OnNavigationItemSelectedListener) this);
    }
    public  boolean onNavigationItemSelected(@NonNull MenuItem item){
        int id = item.getItemId();
        if (id == R.id.ql_phieumuon){
            toolbar.setTitle("Quản lý phiếu mượn");
            replaceFragment(new PhieuMuonFragment());
        }else if (id == R.id.ql_loaisach){
            toolbar.setTitle("Quản lý loại sách");
            replaceFragment(new LoaiSachFragment());
        }else if (id == R.id.ql_Sach){
            toolbar.setTitle("Quản lý sách");
            replaceFragment(new SachFragment());
        }else if (id == R.id.ql_thanhvien){
            toolbar.setTitle("Quản lý thành viên");
            replaceFragment(new ThanhVienFragment());
        }else if (id == R.id.tk_top){
            toolbar.setTitle("Top 10 cuốn mượn nhiều nhất");
            replaceFragment(new Top10Fragment());
        }else if (id == R.id.tk_doanhthu){
            toolbar.setTitle("Doanh thu");
            replaceFragment(new DoanhThuFragment());
        }else if (id == R.id.nd_themNd){
            toolbar.setTitle("Thêm người dùng");
            replaceFragment(new ThemNguoiDungFragment());
        }else if (id == R.id.nd_doiMk){
            toolbar.setTitle("Đổi mật khẩu");
            replaceFragment(new DoiMkFragment());
        }else if (id == R.id.nd_dangxuat){
            finish();
        }
        drawerLayout.closeDrawer(navigationView);
        return true;
    }
    @Override
    public void onBackPressed(){
        if (drawerLayout.isDrawerOpen(navigationView)){
            drawerLayout.closeDrawer(navigationView);
        }else {
            super.onBackPressed();
        }
    }

    public void replaceFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameLayout, fragment);
        transaction.commit();
    }
}
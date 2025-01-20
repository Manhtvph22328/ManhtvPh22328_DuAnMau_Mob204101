package com.example.manhtvph22328_duanmau_mob204101;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.manhtvph22328_duanmau_mob204101.Database.ThuThuDao;
import com.example.manhtvph22328_duanmau_mob204101.Fragment.DoiMkFragment;
import com.example.manhtvph22328_duanmau_mob204101.Model.ThuThu;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class Login extends AppCompatActivity {

    private ThuThuDao thuThuDao;
    private TextInputEditText edUser, edPass;
    private Button btnlogin, btncacel;
    private CheckBox chkLuu;
    private List<ThuThu> arraylist;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        edUser = findViewById(R.id.ed_login_Tk);
        edPass = findViewById(R.id.ed_login_Mk);
        chkLuu = findViewById(R.id.chk_luumk);
        btnlogin = findViewById(R.id.btn_login_dangNhap);
        btncacel = findViewById(R.id.btn_login_Huy);

        thuThuDao = new ThuThuDao(Login.this);
        DoiMkFragment doiMkFragment = new DoiMkFragment();
        List<String> list = new ArrayList<>();
        list = readPreference();

        if (list.size()>0){
            edUser.setText(list.get(0));
            edPass.setText(list.get(1));
            chkLuu.setChecked(Boolean.parseBoolean(list.get(2)));
        }

        btncacel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                System.exit(0);
            }
        });
        arraylist = thuThuDao.getAllThuThu();
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tk = edUser.getText().toString();
                String mk = edPass.getText().toString();
                boolean status = chkLuu.isChecked();
                for (int i=0; i<thuThuDao.getAllThuThu().size();i++){
                    ThuThu thuThu = arraylist.get(i);
                    Log.e("User", thuThu.getMaTT()+""+thuThu.getMatKhau());
                    if (tk.equals(thuThu.getMaTT())&&mk.equals(thuThu.getMatKhau())){
                        savePreference(tk, mk, status);
                        Toast.makeText(Login.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Login.this, MainActivity.class);
                        intent.putExtra("TK",tk);
                        startActivity(intent);
                        return;
                    }
                }
                if (tk.length()==0||mk.length()==0){
                    Toast.makeText(Login.this, "Không để trống", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(Login.this, "Sai thông tin", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void savePreference(String tk, String mk, Boolean status){
        SharedPreferences sharedPreferences = getSharedPreferences("My_FILE", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (!status){
            editor.clear();
        }else {
            editor.putString("TK", tk);
            editor.putString("Mk", mk);
            editor.putBoolean("CHK", status);
        }
        editor.commit();
    }

    public List<String> readPreference(){
        List<String> list = new ArrayList<>();
        SharedPreferences s = getSharedPreferences("MY_FILE",MODE_PRIVATE);
        list.add(s.getString("Tk", ""));
        list.add(s.getString("Mk", ""));
        list.add(String.valueOf(s.getBoolean("CHK", false)));
        return  list;
    }
    @Override
    protected void onResume(){
        super.onResume();
        arraylist = thuThuDao.getAllThuThu();
    }

}
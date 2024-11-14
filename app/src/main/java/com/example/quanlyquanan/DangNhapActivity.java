package com.example.quanlyquanan;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.example.quanlyquanan.DAO.NhanVienDAO;
import com.example.quanlyquanan.R;

public class DangNhapActivity extends AppCompatActivity {
    Button btn_dangnhap_dangnhap, btn_dangnhap_dangky;
    TextInputLayout txtl_dangnhap_tendn, txtl_dangnhap_matkhau;
    NhanVienDAO nhanVienDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);

        //thuộc tính view
        txtl_dangnhap_tendn = (TextInputLayout)findViewById(R.id.txtl_dangnhap_tendn);
        txtl_dangnhap_matkhau = (TextInputLayout)findViewById(R.id.txtl_dangnhap_matkhau);
        btn_dangnhap_dangnhap = (Button)findViewById(R.id.btn_dangnhap_dangnhap);
        btn_dangnhap_dangky = (Button)findViewById(R.id.btn_dangnhap_dangky);

        nhanVienDAO = new NhanVienDAO(this);    //khởi tạo kết nối csdl

        btn_dangnhap_dangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!validateUserName() | !validatePassWord()){
                    return;
                }

                String tenDN = txtl_dangnhap_tendn.getEditText().getText().toString();
                String matKhau = txtl_dangnhap_matkhau.getEditText().getText().toString();
                int ktra = nhanVienDAO.KiemTraDN(tenDN,matKhau);
                int maquyen = nhanVienDAO.LayQuyenNV(ktra);
                if(ktra != 0){
                    // lưu mã quyền vào shareprefer
                    SharedPreferences sharedPreferences = getSharedPreferences("luuquyen", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor =sharedPreferences.edit();
                    editor.putInt("maquyen",maquyen);
                    editor.commit();

                    //gửi dữ liệu user qua trang chủ
                    Intent intent = new Intent(getApplicationContext(),TrangChuActivity.class);
                    intent.putExtra("tendn",txtl_dangnhap_tendn.getEditText().getText().toString());
                    intent.putExtra("manv",ktra);
                    startActivity(intent);
                }else {
                    Toast.makeText(getApplicationContext(),"Đăng nhập thất bại!",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    //Hàm quay lại màn hình chính
    public void backFromLogin(View view)
    {
        Intent intent = new Intent(getApplicationContext(),LuaChonActivity.class);
        //tạo animation cho thành phần
        Pair[] pairs = new Pair[1];
        pairs[0] = new Pair<View, String>(findViewById(R.id.layoutLogin),"transition_login");

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(DangNhapActivity.this,pairs);
            startActivity(intent,options.toBundle());
        }
        else {
            startActivity(intent);
        }
    }

    //Hàm chuyển qua trang đăng ký
    public void callRegisterFromLogin(View view)
    {
        Intent intent = new Intent(getApplicationContext(),DangKy1Activity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    //region Validate field
    private boolean validateUserName(){
        String val = txtl_dangnhap_tendn.getEditText().getText().toString().trim();

        if(val.isEmpty()){
            txtl_dangnhap_tendn.setError(getResources().getString(R.string.not_empty));
            return false;
        }else {
            txtl_dangnhap_tendn.setError(null);
            txtl_dangnhap_tendn.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validatePassWord(){
        String val = txtl_dangnhap_matkhau.getEditText().getText().toString().trim();

        if(val.isEmpty()){
            txtl_dangnhap_matkhau.setError(getResources().getString(R.string.not_empty));
            return false;
        }else{
            txtl_dangnhap_matkhau.setError(null);
            txtl_dangnhap_matkhau.setErrorEnabled(false);
            return true;
        }
    }
    //endregion
}
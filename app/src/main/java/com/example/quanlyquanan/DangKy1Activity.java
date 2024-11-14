package com.example.quanlyquanan;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Pattern;

import static com.example.quanlyquanan.R.*;

public class DangKy1Activity extends AppCompatActivity {

    ImageView img_dangky1_back;
    Button btn_dangki1_tieptheo;
    TextView txt_dangky1_Tieude;
    TextInputLayout txtl_dangki1_hoten, txtl_dangky1_tendn, txtl_dangki1_Email, txtl_dangki1_sdt, txtl_dangki1_matkhau;
    public static final String BUNDLE = "BUNDLE";
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    //"(?=.*[@#$%^&+=])" +     // at least 1 special character
                    "(?=\\S+$)" +            // no white spaces
                    ".{6,}" +                // at least 4 characters
                    "$");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_dang_ky1);

        img_dangky1_back = (ImageView)findViewById(id.img_dangky1_back);
        btn_dangki1_tieptheo = (Button)findViewById(id.btn_dangki1_tieptheo);
        txt_dangky1_Tieude = (TextView)findViewById(id.txt_dangky1_Tieude);
        txtl_dangki1_hoten = (TextInputLayout)findViewById(id.txtl_dangki1_hoten);
        txtl_dangky1_tendn = (TextInputLayout)findViewById(id.txtl_dangky1_tendn);
        txtl_dangki1_Email = (TextInputLayout)findViewById(id.txtl_dangki1_Email);
        txtl_dangki1_sdt = (TextInputLayout)findViewById(id.txtl_dangki1_sdt);
        txtl_dangki1_matkhau = (TextInputLayout)findViewById(id.txtl_dangki1_matkhau);

        btn_dangki1_tieptheo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //kiểm tra validate false => phải thỏa đk validate
                if(!validateFullName() | !validateUserName() | !validateEmail() | !validatePhone() | !validatePassWord()){
                    return;
                }
                String hoTen = txtl_dangki1_hoten.getEditText().getText().toString();
                String tenDN = txtl_dangky1_tendn.getEditText().getText().toString();
                String eMail = txtl_dangki1_Email.getEditText().getText().toString();
                String sDT = txtl_dangki1_sdt.getEditText().getText().toString();
                String matKhau = txtl_dangki1_matkhau.getEditText().getText().toString();

                byBundleNextSignupScreen(hoTen,tenDN,eMail,sDT,matKhau);
            }
        });

    }

    //Hàm quay về màn hình trước
    public void backFromRegister(View view){

        Intent intent = new Intent(getApplicationContext(),LuaChonActivity.class);
        Pair[] pairs = new Pair[1];
        pairs[0] = new Pair<View, String>(findViewById(id.layoutRegister),"transition_signup");

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(DangKy1Activity.this,pairs);
            startActivity(intent,options.toBundle());
        }else {
            startActivity(intent);
        }
    }

    //truyền dữ liệu qua trang đk thứ 2 bằng bundle
    public void byBundleNextSignupScreen(String hoTen, String tenDN, String eMail, String sDT, String matKhau){

        Intent intent = new Intent(getApplicationContext(),DangKy2Activity.class);
        Bundle bundle = new Bundle();
        bundle.putString("hoten",hoTen);
        bundle.putString("tendn",tenDN);
        bundle.putString("email",eMail);
        bundle.putString("sdt",sDT);
        bundle.putString("matkhau",matKhau);
        intent.putExtra(BUNDLE,bundle);

        startActivity(intent);
        overridePendingTransition(anim.slide_in_right, anim.slide_out_left);
    }

    //region Validate field
    private boolean validateFullName(){
        String val = txtl_dangki1_hoten.getEditText().getText().toString().trim();

        if(val.isEmpty()){
            txtl_dangki1_hoten.setError(getResources().getString(string.not_empty));
            return false;
        }else {
            txtl_dangki1_hoten.setError(null);
            txtl_dangki1_hoten.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateUserName(){
        String val = txtl_dangky1_tendn.getEditText().getText().toString().trim();
        String checkspaces = "\\A\\w{1,50}\\z";

        if(val.isEmpty()){
            txtl_dangky1_tendn.setError(getResources().getString(string.not_empty));
            return false;
        }else if(val.length()>50){
            txtl_dangky1_tendn.setError("Phải nhỏ hơn 50 ký tự");
            return false;
        }else if(!val.matches(checkspaces)){
            txtl_dangky1_tendn.setError("Không được cách chữ!");
            return false;
        }
        else {
            txtl_dangky1_tendn.setError(null);
            txtl_dangky1_tendn.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateEmail(){
        String val = txtl_dangki1_Email.getEditText().getText().toString().trim();
        String checkspaces = "[a-zA-Z0-9._-]+@[a-z]+.+[a-z]+";

        if(val.isEmpty()){
            txtl_dangki1_Email.setError(getResources().getString(string.not_empty));
            return false;
        }else if(!val.matches(checkspaces)){
            txtl_dangki1_Email.setError("Email không hợp lệ!");
            return false;
        }
        else {
            txtl_dangki1_Email.setError(null);
            txtl_dangki1_Email.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validatePhone(){
        String val = txtl_dangki1_sdt.getEditText().getText().toString().trim();


        if(val.isEmpty()){
            txtl_dangki1_sdt.setError(getResources().getString(string.not_empty));
            return false;
        }else if(val.length() != 10){
            txtl_dangki1_sdt.setError("Số điện thoại không hợp lệ!");
            return false;
        }
        else {
            txtl_dangki1_sdt.setError(null);
            txtl_dangki1_sdt.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validatePassWord(){
        String val = txtl_dangki1_matkhau.getEditText().getText().toString().trim();

        if(val.isEmpty()){
            txtl_dangki1_matkhau.setError(getResources().getString(string.not_empty));
            return false;
        }else if(!PASSWORD_PATTERN.matcher(val).matches()){
            txtl_dangki1_matkhau.setError("Mật khẩu ít nhất 6 ký tự!");
            return false;
        }
        else {
            txtl_dangki1_matkhau.setError(null);
            txtl_dangki1_matkhau.setErrorEnabled(false);
            return true;
        }
    }
}
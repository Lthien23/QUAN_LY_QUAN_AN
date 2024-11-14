package com.example.quanlyquanan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.example.quanlyquanan.DAO.NhanVienDAO;
import com.example.quanlyquanan.Model.NhanVien;
import com.example.quanlyquanan.R;

import java.util.Calendar;
import java.util.regex.Pattern;

public class ThemNhanVienActivity extends AppCompatActivity implements View.OnClickListener{

    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    //"(?=.*[@#$%^&+=])" +     // at least 1 special character
                    "(?=\\S+$)" +            // no white spaces
                    ".{6,}" +                // at least 4 characters
                    "$");

    TextInputLayout txtl_themnhanvienActivity_hoten, txtl_themnhanvienActivity_tendn, txtl_themnhanvienActivity_Email, txtl_themnhanvienActivity_SDT, txtl_themnhanvienActivity_matkhau;
    RadioGroup rg_themnhanvienActivity_GioiTinh,rg_themnhanvienActivity_quyen;
    RadioButton rd_themnhanvienActivity_Nam,rd_themnhanvienActivity_Nu,rd_themnhanvienActivity_Khac,rd_themnhanvienActivity_QuanLy,rd_themnhanvienActivity_NhanVien;
    DatePicker dt_themnhanvienActivity_NgaySinh;
    Button btn_themnhanvienActivity_ThemNV;
    NhanVienDAO nhanVienDAO;
    String hoTen,tenDN,eMail,sDT,matKhau,gioiTinh,ngaySinh;
    int manv = 0,quyen = 0;
    long ktra = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_nhan_vien);

        //region Lấy đối tượng trong view
        txtl_themnhanvienActivity_hoten = (TextInputLayout)findViewById(R.id.txtl_themnhanvienActivity_hoten);
        txtl_themnhanvienActivity_tendn = (TextInputLayout)findViewById(R.id.txtl_themnhanvienActivity_tendn);
        txtl_themnhanvienActivity_Email = (TextInputLayout)findViewById(R.id.txtl_themnhanvienActivity_Email);
        txtl_themnhanvienActivity_SDT = (TextInputLayout)findViewById(R.id.txtl_themnhanvienActivity_SDT);
        txtl_themnhanvienActivity_matkhau = (TextInputLayout)findViewById(R.id.txtl_themnhanvienActivity_matkhau);
        rg_themnhanvienActivity_GioiTinh = (RadioGroup)findViewById(R.id.rg_themnhanvienActivity_GioiTinh);
        rg_themnhanvienActivity_quyen = (RadioGroup)findViewById(R.id.rg_themnhanvienActivity_quyen);
        rd_themnhanvienActivity_Nam = (RadioButton)findViewById(R.id.rd_themnhanvienActivity_Nam);
        rd_themnhanvienActivity_Nu = (RadioButton)findViewById(R.id.rd_themnhanvienActivity_Nu);
        rd_themnhanvienActivity_Khac = (RadioButton)findViewById(R.id.rd_themnhanvienActivity_Khac);
        rd_themnhanvienActivity_QuanLy = (RadioButton)findViewById(R.id.rd_themnhanvienActivity_QuanLy);
        rd_themnhanvienActivity_NhanVien = (RadioButton)findViewById(R.id.rd_themnhanvienActivity_NhanVien);
        dt_themnhanvienActivity_NgaySinh = (DatePicker)findViewById(R.id.dt_themnhanvienActivity_NgaySinh);
        btn_themnhanvienActivity_ThemNV = (Button)findViewById(R.id.btn_themnhanvienActivity_ThemNV);

        //endregion

        nhanVienDAO = new NhanVienDAO(this);

        //region Hiển thị trang sửa nếu được chọn từ context menu sửa
        manv = getIntent().getIntExtra("manv",0);   //lấy manv từ display staff
        if(manv != 0){
            NhanVien nhanVien = nhanVienDAO.LayNVTheoMa(manv);

            //Hiển thị thông tin từ csdl
            txtl_themnhanvienActivity_hoten.getEditText().setText(nhanVien.getHOTENNV());
            txtl_themnhanvienActivity_tendn.getEditText().setText(nhanVien.getTENDN());
            txtl_themnhanvienActivity_Email.getEditText().setText(nhanVien.getEMAIL());
            txtl_themnhanvienActivity_SDT.getEditText().setText(nhanVien.getSDT());
            txtl_themnhanvienActivity_matkhau.getEditText().setText(nhanVien.getMATKHAU());

            //Hiển thị giới tính từ csdl
            String gioitinh = nhanVien.getGIOITINH();
            if(gioitinh.equals("Nam")){
                rd_themnhanvienActivity_Nam.setChecked(true);
            }else if (gioitinh.equals("Nữ")){
                rd_themnhanvienActivity_Nu.setChecked(true);
            }else {
                rd_themnhanvienActivity_Khac.setChecked(true);
            }

            if(nhanVien.getMAQUYEN() == 1){
                rd_themnhanvienActivity_QuanLy.setChecked(true);
            }else {
                rd_themnhanvienActivity_NhanVien.setChecked(true);
            }

            //Hiển thị ngày sinh từ csdl
            String date = nhanVien.getNGAYSINH();
            String[] items = date.split("/");
            int day = Integer.parseInt(items[0]);
            int month = Integer.parseInt(items[1]) - 1;
            int year = Integer.parseInt(items[2]);
            dt_themnhanvienActivity_NgaySinh.updateDate(year,month,day);
            btn_themnhanvienActivity_ThemNV.setText("Sửa nhân viên");
        }
        //endregion

        btn_themnhanvienActivity_ThemNV.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        String chucnang;
        switch (id){
            case R.id.btn_themnhanvienActivity_ThemNV:
                if( !validateAge() | !validateEmail() | !validateFullName() | !validateGender() | !validatePassWord() |
                        !validatePermission() | !validatePhone() | !validateUserName()){
                    return;
                }
                //Lấy dữ liệu từ view
                hoTen = txtl_themnhanvienActivity_hoten.getEditText().getText().toString();
                tenDN = txtl_themnhanvienActivity_tendn.getEditText().getText().toString();
                eMail = txtl_themnhanvienActivity_Email.getEditText().getText().toString();
                sDT = txtl_themnhanvienActivity_SDT.getEditText().getText().toString();
                matKhau = txtl_themnhanvienActivity_matkhau.getEditText().getText().toString();

                switch (rg_themnhanvienActivity_GioiTinh.getCheckedRadioButtonId()){
                    case R.id.rd_themnhanvienActivity_Nam: gioiTinh = "Nam"; break;
                    case R.id.rd_themnhanvienActivity_Nu: gioiTinh = "Nữ"; break;
                    case R.id.rd_themnhanvienActivity_Khac: gioiTinh = "Khác"; break;
                }
                switch (rg_themnhanvienActivity_quyen.getCheckedRadioButtonId()){
                    case R.id.rd_themnhanvienActivity_QuanLy: quyen = 1; break;
                    case R.id.rd_themnhanvienActivity_NhanVien: quyen = 2; break;
                }

                ngaySinh = dt_themnhanvienActivity_NgaySinh.getDayOfMonth() + "/" + (dt_themnhanvienActivity_NgaySinh.getMonth() + 1)
                        +"/"+dt_themnhanvienActivity_NgaySinh.getYear();

                //truyền dữ liệu vào obj nhanvien
                NhanVien nhanVien = new NhanVien();
                nhanVien.setHOTENNV(hoTen);
                nhanVien.setTENDN(tenDN);
                nhanVien.setEMAIL(eMail);
                nhanVien.setSDT(sDT);
                nhanVien.setMATKHAU(matKhau);
                nhanVien.setGIOITINH(gioiTinh);
                nhanVien.setNGAYSINH(ngaySinh);
                nhanVien.setMAQUYEN(quyen);

                if(manv != 0){
                    ktra = nhanVienDAO.SuaNhanVien(nhanVien,manv);
                    chucnang = "sua";
                }else {
                    ktra = nhanVienDAO.ThemNhanVien(nhanVien);
                    chucnang = "themnv";
                }
                //Thêm, sửa nv dựa theo obj nhanvien
                Intent intent = new Intent();
                intent.putExtra("ketquaktra",ktra);
                intent.putExtra("chucnang",chucnang);
                setResult(RESULT_OK,intent);
                finish();
                break;
        }
    }

    //region validate fields
    private boolean validateFullName(){
        String val = txtl_themnhanvienActivity_hoten.getEditText().getText().toString().trim();

        if(val.isEmpty()){
            txtl_themnhanvienActivity_hoten.setError(getResources().getString(R.string.not_empty));
            return false;
        }else {
            txtl_themnhanvienActivity_hoten.setError(null);
            txtl_themnhanvienActivity_hoten.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateUserName(){
        String val = txtl_themnhanvienActivity_tendn.getEditText().getText().toString().trim();
        String checkspaces = "\\A\\w{1,50}\\z";

        if(val.isEmpty()){
            txtl_themnhanvienActivity_tendn.setError(getResources().getString(R.string.not_empty));
            return false;
        }else if(val.length()>50){
            txtl_themnhanvienActivity_tendn.setError("Phải nhỏ hơn 50 ký tự");
            return false;
        }else if(!val.matches(checkspaces)){
            txtl_themnhanvienActivity_tendn.setError("Không được cách chữ!");
            return false;
        }
        else {
            txtl_themnhanvienActivity_tendn.setError(null);
            txtl_themnhanvienActivity_tendn.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateEmail(){
        String val = txtl_themnhanvienActivity_Email.getEditText().getText().toString().trim();
        String checkspaces = "[a-zA-Z0-9._-]+@[a-z]+.+[a-z]+";

        if(val.isEmpty()){
            txtl_themnhanvienActivity_Email.setError(getResources().getString(R.string.not_empty));
            return false;
        }else if(!val.matches(checkspaces)){
            txtl_themnhanvienActivity_Email.setError("Email không hợp lệ!");
            return false;
        }
        else {
            txtl_themnhanvienActivity_Email.setError(null);
            txtl_themnhanvienActivity_Email.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validatePhone(){
        String val = txtl_themnhanvienActivity_SDT.getEditText().getText().toString().trim();


        if(val.isEmpty()){
            txtl_themnhanvienActivity_SDT.setError(getResources().getString(R.string.not_empty));
            return false;
        }else if(val.length() != 10){
            txtl_themnhanvienActivity_SDT.setError("Số điện thoại không hợp lệ!");
            return false;
        }
        else {
            txtl_themnhanvienActivity_SDT.setError(null);
            txtl_themnhanvienActivity_SDT.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validatePassWord(){
        String val = txtl_themnhanvienActivity_matkhau.getEditText().getText().toString().trim();

        if(val.isEmpty()){
            txtl_themnhanvienActivity_matkhau.setError(getResources().getString(R.string.not_empty));
            return false;
        }else if(!PASSWORD_PATTERN.matcher(val).matches()){
            txtl_themnhanvienActivity_matkhau.setError("Mật khẩu ít nhất 6 ký tự!");
            return false;
        }
        else {
            txtl_themnhanvienActivity_matkhau.setError(null);
            txtl_themnhanvienActivity_matkhau.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateGender(){
        if(rg_themnhanvienActivity_GioiTinh.getCheckedRadioButtonId() == -1){
            Toast.makeText(this,"Hãy chọn giới tính",Toast.LENGTH_SHORT).show();
            return false;
        }else {
            return true;
        }
    }

    private boolean validatePermission(){
        if(rg_themnhanvienActivity_quyen.getCheckedRadioButtonId() == -1){
            Toast.makeText(this,"Hãy chọn quyền",Toast.LENGTH_SHORT).show();
            return false;
        }else {
            return true;
        }
    }

    private boolean validateAge(){
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int userAge = dt_themnhanvienActivity_NgaySinh.getYear();
        int isAgeValid = currentYear - userAge;

        if(isAgeValid < 10){
            Toast.makeText(this,"Bạn không đủ tuổi đăng ký!",Toast.LENGTH_SHORT).show();
            return false;
        }else {
            return true;
        }
    }
    //endregion

}
package com.example.quanlyquanan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.quanlyquanan.DAO.NhanVienDAO;
import com.example.quanlyquanan.DAO.QuyenDAO;
import com.example.quanlyquanan.Model.NhanVien;
import com.example.quanlyquanan.R;

import java.util.Calendar;

public class DangKy2Activity extends AppCompatActivity {

    RadioGroup rg_dangki2_GioiTinh;
    DatePicker dt_dangky2_ngaysinh;
    Button btn_dangky2_tieptheo;
    String hoTen,tenDN,eMail,sDT,matKhau,gioiTinh;
    NhanVienDAO nhanVienDAO;
    QuyenDAO quyenDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky2);

        //lấy đối tượng view
        rg_dangki2_GioiTinh = (RadioGroup)findViewById(R.id.rg_dangki2_GioiTinh);
        dt_dangky2_ngaysinh = (DatePicker)findViewById(R.id.dt_dangky2_ngaysinh);
        btn_dangky2_tieptheo = (Button)findViewById(R.id.btn_dangky2_tieptheo);

        //lấy dữ liệu từ bundle của register1
        Bundle bundle = getIntent().getBundleExtra(DangKy1Activity.BUNDLE);
        if(bundle != null) {
            hoTen = bundle.getString("hoten");
            tenDN = bundle.getString("tendn");
            eMail = bundle.getString("email");
            sDT = bundle.getString("sdt");
            matKhau = bundle.getString("matkhau");
        }
        //khởi tạo kết nối csdl
        nhanVienDAO = new NhanVienDAO(this);
        quyenDAO = new QuyenDAO(this);

        btn_dangky2_tieptheo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!validateAge() | !validateGender()){
                    return;
                }

                //lấy các thông tin còn lại
                switch (rg_dangki2_GioiTinh.getCheckedRadioButtonId()){
                    case R.id.rd_dangki2_Nam:
                        gioiTinh = "Nam"; break;
                    case R.id.rd_dangky2_Nu:
                        gioiTinh = "Nữ"; break;
                    case R.id.rd_dangky2_Khac:
                        gioiTinh = "Khác"; break;
                }
                String ngaySinh = dt_dangky2_ngaysinh.getDayOfMonth() + "/" + (dt_dangky2_ngaysinh.getMonth() + 1)
                        +"/"+dt_dangky2_ngaysinh.getYear();

                //truyền dữ liệu vào obj nhanvienDTO
                NhanVien nhanVien = new NhanVien();
                nhanVien.setHOTENNV(hoTen);
                nhanVien.setTENDN(tenDN);
                nhanVien.setEMAIL(eMail);
                nhanVien.setSDT(sDT);
                nhanVien.setMATKHAU(matKhau);
                nhanVien.setGIOITINH(gioiTinh);
                nhanVien.setNGAYSINH(ngaySinh);

                //nếu nhân viên đầu tiên đăng ký sẽ có quyền quản lý
                if(!nhanVienDAO.KtraTonTaiNV()){
                    quyenDAO.ThemQuyen("Quản lý");
                    quyenDAO.ThemQuyen("Nhân viên");
                    nhanVien.setMAQUYEN(1);
                }else {
                    nhanVien.setMAQUYEN(2);
                }

                //Thêm nv dựa theo obj nhanvien
                long ktra = nhanVienDAO.ThemNhanVien(nhanVien);
                if(ktra != 0){
                    Toast.makeText(DangKy2Activity.this,getResources().getString(R.string.add_sucessful),Toast.LENGTH_SHORT).show();
                    callLoginFromRegister();
                }else{
                    Toast.makeText(DangKy2Activity.this,getResources().getString(R.string.add_failed),Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    //Hàm quay về màn hình trước
    public void backFromRegister2nd(View view){
        Intent intent = new Intent(getApplicationContext(),DangKy1Activity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }

    //Hàm chuyển màn hình khi hoàn thành
    public void callLoginFromRegister(){
        Intent intent = new Intent(getApplicationContext(), LuaChonActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    //region Validate field
    private boolean validateGender(){
        if(rg_dangki2_GioiTinh.getCheckedRadioButtonId() == -1){
            Toast.makeText(this,"Hãy chọn giới tính",Toast.LENGTH_SHORT).show();
            return false;
        }else {
            return true;
        }
    }

    private boolean validateAge(){
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int userAge = dt_dangky2_ngaysinh.getYear();
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
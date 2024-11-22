package com.example.quanlyquanan;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.example.quanlyquanan.Adapter.AdapterLoaiMon;
import com.example.quanlyquanan.DAO.LoaiMonDAO;
import com.example.quanlyquanan.DAO.MonDAO;
import com.example.quanlyquanan.Model.LoaiMon;
import com.example.quanlyquanan.Model.Mon;
import com.example.quanlyquanan.R;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

public class ThemMonAnActivity extends AppCompatActivity implements View.OnClickListener{

    Button btn_themmonanActivity_themmon;
    RelativeLayout layout_trangthaimon;
    ImageView img_themmonanActivity_ThemHinh;
    TextInputLayout txtl_themmonanActivity_tenmon,txtl_themmonanActivity_giatien,txtl_themmonanActivity_loaimon;
    RadioGroup rg_themmonanActivity_tinhtrang;
    RadioButton rd_themmonanActivity_conmon, rd_themmonanActivity_hetmon;
    MonDAO monDAO;
    String tenloai, sTenMon,sGiaTien,sTinhTrang;
    Bitmap bitmapold;
    int maloai;
    int mamon = 0;

    ActivityResultLauncher<Intent> resultLauncherOpenIMG = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == Activity.RESULT_OK && result.getData() != null){
                        Uri uri = result.getData().getData();
                        try{
                            InputStream inputStream = getContentResolver().openInputStream(uri);
                            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                            img_themmonanActivity_ThemHinh.setImageBitmap(bitmap);
                        }catch (FileNotFoundException e){
                            e.printStackTrace();
                        }
                    }
                }
            });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_mon_an);

        img_themmonanActivity_ThemHinh = (ImageView)findViewById(R.id.img_themmonanActivity_ThemHinh);
        img_themmonanActivity_ThemHinh.setTag(R.drawable.ic_baseline_add_circle_24);
        txtl_themmonanActivity_tenmon = (TextInputLayout)findViewById(R.id.txtl_themmonanActivity_tenmon);
        txtl_themmonanActivity_giatien = (TextInputLayout)findViewById(R.id.txtl_themmonanActivity_giatien);
        txtl_themmonanActivity_loaimon = (TextInputLayout)findViewById(R.id.txtl_themmonanActivity_loaimon);
        btn_themmonanActivity_themmon = (Button)findViewById(R.id.btn_themmonanActivity_themmon);
        layout_trangthaimon = (RelativeLayout)findViewById(R.id.layout_trangthaimon);
        rg_themmonanActivity_tinhtrang = (RadioGroup)findViewById(R.id.rg_themmonanActivity_tinhtrang);
        rd_themmonanActivity_conmon = (RadioButton)findViewById(R.id.rd_themmonanActivity_conmon);
        rd_themmonanActivity_hetmon = (RadioButton)findViewById(R.id.rd_themmonanActivity_hetmon);


        Intent intent = getIntent();
        maloai = intent.getIntExtra("maLoai",-1);
        tenloai = intent.getStringExtra("tenLoai");
        monDAO = new MonDAO(this);  //khởi tạo đối tượng dao kết nối csdl
        txtl_themmonanActivity_loaimon.getEditText().setText(tenloai);

        BitmapDrawable olddrawable = (BitmapDrawable)img_themmonanActivity_ThemHinh.getDrawable();
        bitmapold = olddrawable.getBitmap();

        mamon = getIntent().getIntExtra("mamon",0);
        if(mamon != 0){
            Mon mon = monDAO.LayMonTheoMa(mamon);

            txtl_themmonanActivity_tenmon.getEditText().setText(mon.getTenMon());
            txtl_themmonanActivity_giatien.getEditText().setText(mon.getGiaTien());

            byte[] menuimage = mon.getHinhAnh();
            Bitmap bitmap = BitmapFactory.decodeByteArray(menuimage,0,menuimage.length);
            img_themmonanActivity_ThemHinh.setImageBitmap(bitmap);

            layout_trangthaimon.setVisibility(View.VISIBLE);
            String tinhtrang = mon.getTinhTrang();
            if(tinhtrang.equals("true")){
                rd_themmonanActivity_conmon.setChecked(true);
            }else {
                rd_themmonanActivity_hetmon.setChecked(true);
            }

            btn_themmonanActivity_themmon.setText("Sửa món");
        }


        img_themmonanActivity_ThemHinh.setOnClickListener(this);
        btn_themmonanActivity_themmon.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        boolean ktra;
        String chucnang;
        switch (id){
            case R.id.img_themmonanActivity_ThemHinh:
                Intent iGetIMG = new Intent();
                iGetIMG.setType("image/*");
                iGetIMG.setAction(Intent.ACTION_GET_CONTENT);
                resultLauncherOpenIMG.launch(Intent.createChooser(iGetIMG,getResources().getString(R.string.choseimg)));
                break;

            case R.id.btn_themmonanActivity_themmon:
                //ktra validation
                if(!validateImage() | !validateName() | !validatePrice()){
                    return;
                }

                sTenMon = txtl_themmonanActivity_tenmon.getEditText().getText().toString();
                sGiaTien = txtl_themmonanActivity_giatien.getEditText().getText().toString();
                switch (rg_themmonanActivity_tinhtrang.getCheckedRadioButtonId()){
                    case R.id.rd_themmonanActivity_conmon: sTinhTrang = "true";   break;
                    case R.id.rd_themmonanActivity_hetmon: sTinhTrang = "false";  break;
                }

                Mon mon = new Mon();
                mon.setMaLoai(maloai);
                mon.setTenMon(sTenMon);
                mon.setGiaTien(sGiaTien);
                mon.setTinhTrang(sTinhTrang);
                mon.setHinhAnh(imageViewtoByte(img_themmonanActivity_ThemHinh));
                if(mamon!= 0){
                    ktra = monDAO.SuaMon(mon,mamon);
                    chucnang = "suamon";
                }else {
                    ktra = monDAO.ThemMon(mon);
                    chucnang = "themmon";
                }

                //Thêm, sửa món dựa theo obj loaimon
                Intent intent = new Intent();
                intent.putExtra("ktra",ktra);
                intent.putExtra("chucnang",chucnang);
                setResult(RESULT_OK,intent);
                finish();

                break;
        }
    }

    private byte[] imageViewtoByte(ImageView imageView){
        Bitmap bitmap = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    private boolean validateImage(){
        BitmapDrawable drawable = (BitmapDrawable)img_themmonanActivity_ThemHinh.getDrawable();
        Bitmap bitmap = drawable.getBitmap();

        if(bitmap == bitmapold){
            Toast.makeText(getApplicationContext(),"Xin chọn hình ảnh",Toast.LENGTH_SHORT).show();
            return false;
        }else {
            return true;
        }
    }

    private boolean validateName(){
        String val = txtl_themmonanActivity_tenmon.getEditText().getText().toString().trim();
        if(val.isEmpty()){
            txtl_themmonanActivity_tenmon.setError(getResources().getString(R.string.not_empty));
            return false;
        }else {
            txtl_themmonanActivity_tenmon.setError(null);
            txtl_themmonanActivity_tenmon.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validatePrice(){
        String val = txtl_themmonanActivity_giatien.getEditText().getText().toString().trim();
        if(val.isEmpty()){
            txtl_themmonanActivity_giatien.setError(getResources().getString(R.string.not_empty));
            return false;
        }else if(!val.matches(("\\d+(?:\\.\\d+)?"))){
            txtl_themmonanActivity_giatien.setError("Giá tiền không hợp lệ");
            return false;
        }else {
            txtl_themmonanActivity_giatien.setError(null);
            txtl_themmonanActivity_giatien.setErrorEnabled(false);
            return true;
        }
    }

}
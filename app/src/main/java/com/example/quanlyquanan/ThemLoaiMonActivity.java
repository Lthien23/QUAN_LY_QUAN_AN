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
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.example.quanlyquanan.DAO.LoaiMonDAO;
import com.example.quanlyquanan.Model.LoaiMon;
import com.example.quanlyquanan.R;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class ThemLoaiMonActivity extends AppCompatActivity implements View.OnClickListener {

    Button btn_themloaimonActivity_themloaimon;
    ImageView  img_themloaimonActivity_ThemHinh;
    TextInputLayout txtl_themloaimonActivity_tenloai;
    LoaiMonDAO loaiMonDAO;
    int maloai = 0;
    Bitmap bitmapold;

    ActivityResultLauncher<Intent> resultLauncherOpenIMG = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == Activity.RESULT_OK && result.getData() != null){
                        Uri uri = result.getData().getData();
                        try{
                            InputStream inputStream = getContentResolver().openInputStream(uri);
                            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                            img_themloaimonActivity_ThemHinh.setImageBitmap(bitmap);
                        }catch (FileNotFoundException e){
                            e.printStackTrace();
                        }
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_loai_mon);

        loaiMonDAO = new LoaiMonDAO(this);

        btn_themloaimonActivity_themloaimon = (Button)findViewById(R.id.btn_themloaimonActivity_themloaimon);
        txtl_themloaimonActivity_tenloai = (TextInputLayout)findViewById(R.id.txtl_themloaimonActivity_tenloai);
        img_themloaimonActivity_ThemHinh = (ImageView)findViewById(R.id.img_themloaimonActivity_ThemHinh);


        BitmapDrawable olddrawable = (BitmapDrawable)img_themloaimonActivity_ThemHinh.getDrawable();
        bitmapold = olddrawable.getBitmap();

        maloai = getIntent().getIntExtra("maloai",0);
        if(maloai != 0){
            LoaiMon loaiMon = loaiMonDAO.LayLoaiMonTheoMa(maloai);

            txtl_themloaimonActivity_tenloai.getEditText().setText(loaiMon.getTenLoai());

            byte[] categoryimage = loaiMon.getHinhAnh();
            Bitmap bitmap = BitmapFactory.decodeByteArray(categoryimage,0,categoryimage.length);
            img_themloaimonActivity_ThemHinh.setImageBitmap(bitmap);

            btn_themloaimonActivity_themloaimon.setText("Sửa loại");
        }

        img_themloaimonActivity_ThemHinh.setOnClickListener(this);
        btn_themloaimonActivity_themloaimon.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        boolean ktra;
        String chucnang;
        switch (id){
            case R.id.img_themloaimonActivity_ThemHinh:
                Intent iGetIMG = new Intent();
                iGetIMG.setType("image/*");
                iGetIMG.setAction(Intent.ACTION_GET_CONTENT);
                resultLauncherOpenIMG.launch(Intent.createChooser(iGetIMG,getResources().getString(R.string.choseimg)));
                break;

            case R.id.btn_themloaimonActivity_themloaimon:
                if(!validateImage() | !validateName()){
                    return;
                }

                String sTenLoai = txtl_themloaimonActivity_tenloai.getEditText().getText().toString();
                LoaiMon loaiMon = new LoaiMon();
                loaiMon.setTenLoai(sTenLoai);
                loaiMon.setHinhAnh(imageViewtoByte(img_themloaimonActivity_ThemHinh));
                if(maloai != 0){
                    ktra = loaiMonDAO.SuaLoaiMon(loaiMon,maloai);
                    chucnang = "sualoai";
                }else {
                    ktra = loaiMonDAO.ThemLoaiMon(loaiMon);
                    chucnang = "themloai";
                }

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
        BitmapDrawable drawable = (BitmapDrawable)img_themloaimonActivity_ThemHinh.getDrawable();
        Bitmap bitmap = drawable.getBitmap();

        if(bitmap == bitmapold){
            Toast.makeText(getApplicationContext(),"Xin chọn hình ảnh",Toast.LENGTH_SHORT).show();
            return false;
        }else {
            return true;
        }
    }

    private boolean validateName(){
        String val = txtl_themloaimonActivity_tenloai.getEditText().getText().toString().trim();
        if(val.isEmpty()){
            txtl_themloaimonActivity_tenloai.setError(getResources().getString(R.string.not_empty));
            return false;
        }else {
            txtl_themloaimonActivity_tenloai.setError(null);
            txtl_themloaimonActivity_tenloai.setErrorEnabled(false);
            return true;
        }
    }

}
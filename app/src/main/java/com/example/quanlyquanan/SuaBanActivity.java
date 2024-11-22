package com.example.quanlyquanan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.example.quanlyquanan.DAO.BanAnDAO;
import com.example.quanlyquanan.R;

public class SuaBanActivity extends AppCompatActivity {

    TextInputLayout txtl_suabanActivity_tenban;
    Button btn_suabanActivity_SuaBan;
    BanAnDAO banAnDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sua_ban);

        txtl_suabanActivity_tenban = (TextInputLayout)findViewById(R.id.txtl_suabanActivity_tenban);
        btn_suabanActivity_SuaBan = (Button)findViewById(R.id.btn_suabanActivity_SuaBan);

        banAnDAO = new BanAnDAO(this);

        int maban = getIntent().getIntExtra("maban", 0);

        btn_suabanActivity_SuaBan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenban = txtl_suabanActivity_tenban.getEditText().getText().toString();

                if (tenban != null || !tenban.equals("")) {
                    boolean ktra = banAnDAO.CapNhatTenBan(maban, tenban);

                    Intent intent = new Intent();
                    intent.putExtra("ketquasua", ktra);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
    }
}

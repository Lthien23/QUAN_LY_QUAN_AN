package com.example.quanlyquanan;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputLayout;
import com.example.quanlyquanan.DAO.BanAnDAO;
import com.example.quanlyquanan.R;

public class ThemBanAnActivity extends AppCompatActivity {

    TextInputLayout txtl_thembanActivity_tenban;
    Button btn_thembanActivity_TaoBan;
    BanAnDAO banAnDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_ban_an);

        txtl_thembanActivity_tenban = (TextInputLayout)findViewById(R.id.txtl_thembanActivity_tenban);
        btn_thembanActivity_TaoBan = (Button)findViewById(R.id.btn_thembanActivity_TaoBan);

        banAnDAO = new BanAnDAO(this);

        btn_thembanActivity_TaoBan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sTenBanAn = txtl_thembanActivity_tenban.getEditText().getText().toString();

                if (sTenBanAn != null || !sTenBanAn.equals("")) {
                    boolean ktra = banAnDAO.ThemBanAn(sTenBanAn);

                    Intent intent = new Intent();
                    intent.putExtra("ketquathem", ktra);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
    }

    private boolean validateName() {
        String val = txtl_thembanActivity_tenban.getEditText().getText().toString().trim();

        if (val.isEmpty()) {
            txtl_thembanActivity_tenban.setError(getResources().getString(R.string.not_empty));
            return false;
        } else {
            txtl_thembanActivity_tenban.setError(null);
            txtl_thembanActivity_tenban.setErrorEnabled(false);
            return true;
        }
    }
}

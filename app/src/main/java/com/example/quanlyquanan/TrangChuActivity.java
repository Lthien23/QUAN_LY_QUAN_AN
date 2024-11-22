package com.example.quanlyquanan;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.example.quanlyquanan.Fragment.TrangChuFragment;
import com.example.quanlyquanan.Fragment.LoaiMonFragment;
import com.example.quanlyquanan.Fragment.NhanVienFragment;
import com.example.quanlyquanan.Fragment.ThongKeFragment;
import com.example.quanlyquanan.Fragment.BanAnFragment;
import com.example.quanlyquanan.R;

public class TrangChuActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    MenuItem selectedFeature, selectedManager;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    FragmentManager fragmentManager;
    TextView txt_trangchu_tennv;
    int maquyen = 0;
    SharedPreferences sharedPreferences;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trang_chu);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view_trangchu);
        toolbar = findViewById(R.id.toolbar);
        View view = navigationView.getHeaderView(0);
        txt_trangchu_tennv = view.findViewById(R.id.txt_trangchu_tennv);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.opentoggle, R.string.closetoggle
        );
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        Intent intent = getIntent();
        String tendn = intent.getStringExtra("tendn");
        txt_trangchu_tennv.setText("Xin chào " + tendn);

        sharedPreferences = getSharedPreferences("luuquyen", Context.MODE_PRIVATE);
        maquyen = sharedPreferences.getInt("maquyen", 0);

        fragmentManager = getSupportFragmentManager();
        FragmentTransaction tranDisplayHome = fragmentManager.beginTransaction();
        tranDisplayHome.replace(R.id.contentView, new TrangChuFragment());
        tranDisplayHome.commit();
        navigationView.setCheckedItem(R.id.nav_home);

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            switch (item.getItemId()) {
                case R.id.bottomTC:
                    fragmentTransaction.replace(R.id.contentView, new TrangChuFragment());
                    fragmentTransaction.commit();
                    return true;

                case R.id.bottomBA:
                    fragmentTransaction.replace(R.id.contentView, new BanAnFragment());
                    fragmentTransaction.commit();
                    return true;

                case R.id.bottomMN:
                    fragmentTransaction.replace(R.id.contentView, new LoaiMonFragment());
                    fragmentTransaction.commit();
                    return true;

                case R.id.bottomTK:
                    fragmentTransaction.replace(R.id.contentView, new ThongKeFragment());
                    fragmentTransaction.commit();
                    return true;
            }
            return false;
        });
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        switch (id) {
            case R.id.nav_home:
                fragmentTransaction.replace(R.id.contentView, new TrangChuFragment());
                fragmentTransaction.commit();
                break;

            case R.id.nav_statistic:
                fragmentTransaction.replace(R.id.contentView, new ThongKeFragment());
                fragmentTransaction.commit();
                break;

            case R.id.nav_staff:
                if (maquyen == 1) {
                    fragmentTransaction.replace(R.id.contentView, new NhanVienFragment());
                    fragmentTransaction.commit();
                } else {
                    Toast.makeText(getApplicationContext(), "Bạn không có quyền truy cập", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.nav_logout:
                showLogoutDialog();
                break;
        }
        drawerLayout.closeDrawers();
        return true;
    }

    private void showLogoutDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Xác nhận đăng xuất")
                .setMessage("Bạn có muốn đăng xuất không?")
                .setPositiveButton("Có", (dialog, which) -> {
                    Intent intent = new Intent(TrangChuActivity.this, LuaChonActivity.class);
                    startActivity(intent);
                    finish();
                })
                .setNegativeButton("Không", (dialog, which) -> dialog.dismiss())
                .create()
                .show();
    }
}

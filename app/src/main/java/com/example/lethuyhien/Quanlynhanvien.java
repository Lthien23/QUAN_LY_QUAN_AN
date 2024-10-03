package com.example.lethuyhien;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

public class Quanlynhanvien extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView na;
    Toolbar toolbar;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quanlynhanvien);

        // Thiết lập các view
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        drawerLayout = findViewById(R.id.main);
        na = findViewById(R.id.na);
        toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        na.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // Thiết lập listener cho NavigationView
        na.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.item4) {
                    Intent intent4 = new Intent(Quanlynhanvien.this, Quanlynhanvien.class);
                    startActivity(intent4);
                }  else if (itemId == R.id.item5) {
                    Intent intent5 = new Intent(Quanlynhanvien.this, Quanlymonan.class);
                    startActivity(intent5);
                }else if (itemId == R.id.item6) {
                    Intent intent6 = new Intent(Quanlynhanvien.this, Quanlyhoadon.class);
                    startActivity(intent6);
                } else if (itemId == R.id.item8) {
                    showLogoutDialog();
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.item1) {
                    Intent intent4 = new Intent(Quanlynhanvien.this, Trangchu.class);
                    startActivity(intent4);
                }  else if (itemId == R.id.item2) {
                    Intent intent5 = new Intent(Quanlynhanvien.this, menu_monan.class);
                    startActivity(intent5);
                }else if (itemId == R.id.item3) {
                    Intent intent6 = new Intent(Quanlynhanvien.this, Hoadon.class);
                    startActivity(intent6);
                } else if (itemId == R.id.item4) {
                    Intent intent7 = new Intent(Quanlynhanvien.this, thong_ke.class);
                    startActivity(intent7);
                }
                return true;
            }
        });

    }

    private void showLogoutDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Xác nhận đăng xuất")
                .setMessage("Bạn có muốn đăng xuất không?")
                .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Nếu chọn "Có", chuyển hướng đến giao diện Đăng Nhập
                        Intent intent = new Intent(Quanlynhanvien.this, Dangnhap.class);
                        startActivity(intent);
                        finish(); // Hoặc gọi finish() nếu bạn muốn đóng hoạt động hiện tại
                    }
                })
                .setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss(); // Đóng hộp thoại nếu chọn "Không"
                    }
                })
                .create()
                .show();
    }
}
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
    TextView TXT_menu_tennv;
    int maquyen = 0;
    SharedPreferences sharedPreferences;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trang_chu);

        //region thuộc tính bên view
        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        navigationView = (NavigationView)findViewById(R.id.navigation_view_trangchu);
        toolbar = (androidx.appcompat.widget.Toolbar) findViewById(R.id.toolbar);
        View view = navigationView.getHeaderView(0);
        TXT_menu_tennv = (TextView) view.findViewById(R.id.txt_menu_tennv);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        //endregion

        //xử lý toolbar và navigation
        setSupportActionBar(toolbar); //tạo toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //tạo nút mở navigation
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar
                , R.string.opentoggle, R.string.closetoggle) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        //Tụ động gán tên nv đăng nhập qua Extras
        Intent intent = getIntent();
        String tendn = intent.getStringExtra("tendn");
        TXT_menu_tennv.setText("Xin chào " + tendn + " !!");

        //lấy file share prefer
        sharedPreferences = getSharedPreferences("luuquyen", Context.MODE_PRIVATE);
        maquyen = sharedPreferences.getInt("maquyen", 0);

        //hiện thị fragment home mặc định
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction tranDisplayHome = fragmentManager.beginTransaction();
        TrangChuFragment trangChuFragment = new TrangChuFragment();
        tranDisplayHome.replace(R.id.contentView, trangChuFragment);
        tranDisplayHome.commit();
        navigationView.setCheckedItem(R.id.nav_home);

        // Xử lý sự kiện bottom navigation
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                switch (item.getItemId()) {
                    case R.id.bottomTC:
                        TrangChuFragment trangChuFragment = new TrangChuFragment();
                        fragmentTransaction.replace(R.id.contentView, trangChuFragment);
                        fragmentTransaction.commit();
                        return true;

                    case R.id.bottomBA:
                        BanAnFragment banAnFragment = new BanAnFragment();
                        fragmentTransaction.replace(R.id.contentView, banAnFragment);
                        fragmentTransaction.commit();
                        return true;

                    case R.id.bottomMN:
                        LoaiMonFragment loaiMonFragment = new LoaiMonFragment();
                        fragmentTransaction.replace(R.id.contentView, loaiMonFragment);
                        fragmentTransaction.commit();
                        return true;

                    case R.id.bottomTK:
                        ThongKeFragment thongKeFragment = new ThongKeFragment();
                        fragmentTransaction.replace(R.id.contentView, thongKeFragment);
                        fragmentTransaction.commit();
                        return true;
                }
                return false;
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        switch (id) {
            case R.id.nav_home:
                TrangChuFragment trangChuFragment = new TrangChuFragment();
                fragmentTransaction.replace(R.id.contentView, trangChuFragment);
                fragmentTransaction.commit();
                navigationView.setCheckedItem(item.getItemId());
                drawerLayout.closeDrawers();
                break;

            case R.id.nav_statistic:
                ThongKeFragment thongKeFragment = new ThongKeFragment();
                fragmentTransaction.replace(R.id.contentView, thongKeFragment);
                fragmentTransaction.commit();
                navigationView.setCheckedItem(item.getItemId());
                drawerLayout.closeDrawers();
                break;

            case R.id.nav_table:
                BanAnFragment banAnFragment = new BanAnFragment();
                fragmentTransaction.replace(R.id.contentView, banAnFragment);
                fragmentTransaction.commit();
                navigationView.setCheckedItem(item.getItemId());
                drawerLayout.closeDrawers();
                break;

            case R.id.nav_category:
                LoaiMonFragment loaiMonFragment = new LoaiMonFragment();
                fragmentTransaction.replace(R.id.contentView, loaiMonFragment);
                fragmentTransaction.commit();
                navigationView.setCheckedItem(item.getItemId());
                drawerLayout.closeDrawers();
                break;

            case R.id.nav_staff:
                if (maquyen == 1) {
                    NhanVienFragment nhanVienFragment = new NhanVienFragment();
                    fragmentTransaction.replace(R.id.contentView, nhanVienFragment);
                    fragmentTransaction.commit();
                    navigationView.setCheckedItem(item.getItemId());
                    drawerLayout.closeDrawers();
                } else {
                    Toast.makeText(getApplicationContext(), "Bạn không có quyền truy cập", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.nav_logout:
                showLogoutDialog();
                break;
        }
        return false;
    }
    private void showLogoutDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Xác nhận đăng xuất")
                .setMessage("Bạn có muốn đăng xuất không?")
                .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Nếu chọn "Có", chuyển hướng đến giao diện Đăng Nhập
                        Intent intent = new Intent(TrangChuActivity.this, LuaChonActivity.class);
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

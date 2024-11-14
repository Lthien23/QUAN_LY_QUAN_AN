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
    MenuItem selectedFeature, selectedManager; // Lưu trữ các menu item đã chọn
    DrawerLayout drawerLayout; // Layout chứa Navigation Drawer
    NavigationView navigationView; // Navigation view để hiển thị các tùy chọn điều hướng
    Toolbar toolbar; // Thanh công cụ của ứng dụng
    FragmentManager fragmentManager; // Quản lý các fragment
    TextView txt_trangchu_tennv; // Hiển thị tên nhân viên đăng nhập
    int maquyen = 0; // Quyền của người dùng
    SharedPreferences sharedPreferences; // Đối tượng lưu trữ dữ liệu chia sẻ
    BottomNavigationView bottomNavigationView; // Thanh điều hướng ở dưới

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trang_chu);

        //region thuộc tính bên view
        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        navigationView = (NavigationView)findViewById(R.id.navigation_view_trangchu);
        toolbar = (androidx.appcompat.widget.Toolbar) findViewById(R.id.toolbar);
        View view = navigationView.getHeaderView(0);
        txt_trangchu_tennv = (TextView) view.findViewById(R.id.txt_trangchu_tennv);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        //endregion

        // Xử lý toolbar và navigation
        setSupportActionBar(toolbar); // Tạo toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Tạo nút mở navigation
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

        // Tự động gán tên nv đăng nhập qua Extras
        Intent intent = getIntent();
        String tendn = intent.getStringExtra("tendn");
        txt_trangchu_tennv.setText("Xin chào "+tendn);

        // Lấy quyền người dùng từ file SharedPreferences
        sharedPreferences = getSharedPreferences("luuquyen", Context.MODE_PRIVATE);
        maquyen = sharedPreferences.getInt("maquyen", 0);

        // Hiển thị fragment Trang Chủ mặc định
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
                    case R.id.bottomTC: // Chuyển đến fragment Trang Chủ
                        TrangChuFragment trangChuFragment = new TrangChuFragment();
                        fragmentTransaction.replace(R.id.contentView, trangChuFragment);
                        fragmentTransaction.commit();
                        return true;

                    case R.id.bottomBA: // Chuyển đến fragment Bàn Ăn
                        BanAnFragment banAnFragment = new BanAnFragment();
                        fragmentTransaction.replace(R.id.contentView, banAnFragment);
                        fragmentTransaction.commit();
                        return true;

                    case R.id.bottomMN: // Chuyển đến fragment Loại Món
                        LoaiMonFragment loaiMonFragment = new LoaiMonFragment();
                        fragmentTransaction.replace(R.id.contentView, loaiMonFragment);
                        fragmentTransaction.commit();
                        return true;

                    case R.id.bottomTK: // Chuyển đến fragment Thống Kê
                        ThongKeFragment thongKeFragment = new ThongKeFragment();
                        fragmentTransaction.replace(R.id.contentView, thongKeFragment);
                        fragmentTransaction.commit();
                        return true;
                }
                return false;
            }
        });
    }

    // Xử lý sự kiện chọn item trên Navigation Drawer
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        switch (id) {
            case R.id.nav_home: // Chuyển đến fragment Trang Chủ
                TrangChuFragment trangChuFragment = new TrangChuFragment();
                fragmentTransaction.replace(R.id.contentView, trangChuFragment);
                fragmentTransaction.commit();
                navigationView.setCheckedItem(item.getItemId());
                drawerLayout.closeDrawers();
                break;

            case R.id.nav_statistic: // Chuyển đến fragment Thống Kê
                ThongKeFragment thongKeFragment = new ThongKeFragment();
                fragmentTransaction.replace(R.id.contentView, thongKeFragment);
                fragmentTransaction.commit();
                navigationView.setCheckedItem(item.getItemId());
                drawerLayout.closeDrawers();
                break;
            case R.id.nav_staff: // Chuyển đến fragment Nhân Viên (kiểm tra quyền người dùng)
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

            case R.id.nav_logout: // Đăng xuất
                showLogoutDialog();
                break;
        }
        return false;
    }

    // Hiển thị dialog xác nhận đăng xuất
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
                        finish(); // Đóng activity hiện tại
                    }
                })
                .setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss(); // Đóng dialog nếu chọn "Không"
                    }
                })
                .create()
                .show();
    }
}

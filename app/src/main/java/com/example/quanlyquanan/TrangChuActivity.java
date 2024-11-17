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

/**
 * Activity TrangChuActivity quản lý giao diện chính của ứng dụng với các chức năng như:
 * - Điều hướng qua Navigation Drawer
 * - Điều hướng qua Bottom Navigation
 * - Hiển thị fragment tương ứng với từng chức năng
 */
public class TrangChuActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    //region Khai báo các thành phần
    MenuItem selectedFeature, selectedManager; // Lưu trữ các menu item đã chọn
    DrawerLayout drawerLayout; // Layout chứa Navigation Drawer
    NavigationView navigationView; // Navigation View để hiển thị menu điều hướng
    Toolbar toolbar; // Thanh công cụ của ứng dụng
    FragmentManager fragmentManager; // Quản lý các fragment
    TextView txt_trangchu_tennv; // Hiển thị tên nhân viên đang đăng nhập
    int maquyen = 0; // Quyền của người dùng (1: quản lý, 2: nhân viên)
    SharedPreferences sharedPreferences; // Lưu trữ dữ liệu dùng chung
    BottomNavigationView bottomNavigationView; // Thanh điều hướng dưới cùng
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trang_chu);

        //region Khởi tạo các thành phần từ layout
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout); // Layout chính chứa Navigation Drawer
        navigationView = (NavigationView) findViewById(R.id.navigation_view_trangchu); // Navigation View
        toolbar = (androidx.appcompat.widget.Toolbar) findViewById(R.id.toolbar); // Toolbar ứng dụng
        View view = navigationView.getHeaderView(0); // Header của Navigation Drawer
        txt_trangchu_tennv = (TextView) view.findViewById(R.id.txt_trangchu_tennv); // TextView hiển thị tên nhân viên
        bottomNavigationView = findViewById(R.id.bottomNavigationView); // Bottom Navigation View
        //endregion

        //region Xử lý toolbar và Navigation Drawer
        setSupportActionBar(toolbar); // Thiết lập toolbar như ActionBar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Hiển thị nút back trên toolbar

        // Tạo nút mở/đóng Navigation Drawer
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.opentoggle, R.string.closetoggle
        ) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView); // Thực hiện thao tác khi Drawer được mở
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView); // Thực hiện thao tác khi Drawer được đóng
            }
        };

        drawerLayout.addDrawerListener(drawerToggle); // Gắn listener vào DrawerLayout
        drawerToggle.syncState(); // Đồng bộ trạng thái Drawer với nút toggle
        navigationView.setNavigationItemSelectedListener(this); // Đăng ký sự kiện cho Navigation View
        //endregion

        //region Thiết lập thông tin ban đầu
        Intent intent = getIntent(); // Nhận Intent từ màn hình trước
        String tendn = intent.getStringExtra("tendn"); // Lấy tên đăng nhập được truyền qua
        txt_trangchu_tennv.setText("Xin chào " + tendn); // Hiển thị tên nhân viên trên header

        // Lấy quyền người dùng từ SharedPreferences
        sharedPreferences = getSharedPreferences("luuquyen", Context.MODE_PRIVATE); // Mở file SharedPreferences
        maquyen = sharedPreferences.getInt("maquyen", 0); // Lấy mã quyền từ file SharedPreferences

        // Hiển thị fragment Trang Chủ mặc định khi mở ứng dụng
        fragmentManager = getSupportFragmentManager(); // Khởi tạo Fragment Manager
        FragmentTransaction tranDisplayHome = fragmentManager.beginTransaction(); // Bắt đầu giao dịch Fragment
        TrangChuFragment trangChuFragment = new TrangChuFragment(); // Tạo đối tượng fragment Trang Chủ
        tranDisplayHome.replace(R.id.contentView, trangChuFragment); // Thay thế nội dung bằng fragment Trang Chủ
        tranDisplayHome.commit(); // Hoàn thành giao dịch
        navigationView.setCheckedItem(R.id.nav_home); // Đặt mục Trang Chủ được chọn trên Navigation Drawer
        //endregion

        //region Xử lý sự kiện Bottom Navigation
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                switch (item.getItemId()) {
                    case R.id.bottomTC: // Chuyển đến fragment Trang Chủ
                        fragmentTransaction.replace(R.id.contentView, new TrangChuFragment());
                        fragmentTransaction.commit();
                        return true;

                    case R.id.bottomBA: // Chuyển đến fragment Bàn Ăn
                        fragmentTransaction.replace(R.id.contentView, new BanAnFragment());
                        fragmentTransaction.commit();
                        return true;

                    case R.id.bottomMN: // Chuyển đến fragment Loại Món
                        fragmentTransaction.replace(R.id.contentView, new LoaiMonFragment());
                        fragmentTransaction.commit();
                        return true;

                    case R.id.bottomTK: // Chuyển đến fragment Thống Kê
                        fragmentTransaction.replace(R.id.contentView, new ThongKeFragment());
                        fragmentTransaction.commit();
                        return true;
                }
                return false; // Không xử lý các mục khác
            }
        });
        //endregion
    }

    //region Xử lý sự kiện Navigation Drawer
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId(); // Lấy ID của mục được chọn
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        switch (id) {
            case R.id.nav_home: // Chuyển đến fragment Trang Chủ
                fragmentTransaction.replace(R.id.contentView, new TrangChuFragment());
                fragmentTransaction.commit();
                break;

            case R.id.nav_statistic: // Chuyển đến fragment Thống Kê
                fragmentTransaction.replace(R.id.contentView, new ThongKeFragment());
                fragmentTransaction.commit();
                break;

            case R.id.nav_staff: // Chuyển đến fragment Nhân Viên (nếu quyền là quản lý)
                if (maquyen == 1) {
                    fragmentTransaction.replace(R.id.contentView, new NhanVienFragment());
                    fragmentTransaction.commit();
                } else {
                    Toast.makeText(getApplicationContext(), "Bạn không có quyền truy cập", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.nav_logout: // Hiển thị dialog đăng xuất
                showLogoutDialog();
                break;
        }
        drawerLayout.closeDrawers(); // Đóng Navigation Drawer
        return true;
    }
    //endregion

    //region Hiển thị dialog xác nhận đăng xuất
    private void showLogoutDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Xác nhận đăng xuất")
                .setMessage("Bạn có muốn đăng xuất không?")
                .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Chuyển đến màn hình Đăng Nhập
                        Intent intent = new Intent(TrangChuActivity.this, LuaChonActivity.class);
                        startActivity(intent);
                        finish(); // Đóng màn hình hiện tại
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
    //endregion
}

package com.example.lethuyhien;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.example.lethuyhien.Adapter.Trang_chu_Adapter;
import com.example.lethuyhien.Model.Trang_chu;
import com.example.lethuyhien.R;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class Trangchu extends AppCompatActivity  {

    DrawerLayout drawerLayout;
    NavigationView na;
    Toolbar toolbar;
    BottomNavigationView bottomNavigationView;
    ListView listView;
    Trang_chu tc;
    Trang_chu_Adapter adapter;
    private ArrayList<Trang_chu> list=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trangchu);
        // Thiết lập các view
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        listView=findViewById(R.id.ListTrangchu);
        drawerLayout = findViewById(R.id.main);
        na = findViewById(R.id.na);
        toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        list.add(new Trang_chu(R.drawable.ban_an,1," 2 chỗ","Trống"));
        list.add(new Trang_chu(R.drawable.ban_an,2," 2 chỗ","Trống"));
        list.add(new Trang_chu(R.drawable.ban_an,3," 6 chỗ","Trống"));
        list.add(new Trang_chu(R.drawable.ban_an,4," 2 chỗ","Trống"));
        list.add(new Trang_chu(R.drawable.ban_an,5," 2 chỗ","Trống"));
        list.add(new Trang_chu(R.drawable.ban_an,6," 4 chỗ","Trống"));
        list.add(new Trang_chu(R.drawable.ban_an,7," 2 chỗ","Trống"));
        list.add(new Trang_chu(R.drawable.ban_an,8," 5 chỗ","Trống"));
        list.add(new Trang_chu(R.drawable.ban_an,9," 3 chỗ","Trống"));
        list.add(new Trang_chu(R.drawable.ban_an,10," 2 chỗ","Trống"));
        list.add(new Trang_chu(R.drawable.ban_an,11," 4 chỗ","Trống"));
        list.add(new Trang_chu(R.drawable.ban_an,12," 2 chỗ","Trống"));
        list.add(new Trang_chu(R.drawable.ban_an,13," 6 chỗ","Trống"));
        list.add(new Trang_chu(R.drawable.ban_an,14," 4 chỗ","Trống"));
        list.add(new Trang_chu(R.drawable.ban_an,15," 2 chỗ","Trống"));
        list.add(new Trang_chu(R.drawable.ban_an,16," 2 chỗ","Trống"));
        list.add(new Trang_chu(R.drawable.ban_an,17," 6 chỗ","Trống"));
        list.add(new Trang_chu(R.drawable.ban_an,18," 2 chỗ","Trống"));
        list.add(new Trang_chu(R.drawable.ban_an,19," 4 chỗ","Trống"));
        list.add(new Trang_chu(R.drawable.ban_an,20," 2 chỗ","Trống"));
        adapter=new Trang_chu_Adapter(this,list);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

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
                    Intent intent4 = new Intent(Trangchu.this, Quanlynhanvien.class);
                    startActivity(intent4);
                }  else if (itemId == R.id.item5) {
                    Intent intent5 = new Intent(Trangchu.this, Quanlymonan.class);
                    startActivity(intent5);
                }else if (itemId == R.id.item6) {
                    Intent intent6 = new Intent(Trangchu.this, Quanlyhoadon.class);
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
                    Intent intent4 = new Intent(Trangchu.this, Trangchu.class);
                    intent4.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    startActivity(intent4);
                }  else if (itemId == R.id.item2) {
                    Intent intent5 = new Intent(Trangchu.this, menu_monan.class);
                    startActivity(intent5);
                }else if (itemId == R.id.item3) {
                    Intent intent6 = new Intent(Trangchu.this, Hoadon.class);
                    startActivity(intent6);
                } else if (itemId == R.id.item4) {
                    Intent intent7 = new Intent(Trangchu.this, thong_ke.class);
                    startActivity(intent7);
                }
                return true;
            }
        });
        listView.setOnItemLongClickListener((parent, view, position, id) -> {
            // Hiển thị dialog khi nhấn giữ vào item
            showOptionDialog(position);
            return true; // Trả về true để chỉ định rằng sự kiện đã được xử lý
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
                        Intent intent = new Intent(Trangchu.this, Dangnhap.class);
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
    private void showOptionDialog(int position) {
        // Tạo AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(Trangchu.this);
        builder.setTitle("Lựa chọn hành động");
        // Thiết lập hai lựa chọn "Thanh toán" và "Gọi món"
        builder.setItems(new String[]{"Thanh toán", "Gọi món"}, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    // Chuyển sang màn hình hóa đơn
                    list.get(position).setTrangthai("Trống");
                    adapter.notifyDataSetChanged();  // Cập nhật giao diện
                    Intent intent = new Intent(Trangchu.this, Hoadon.class);
                    intent.putExtra("ban_so", position + 1); // Gửi số bàn
                    startActivity(intent);
                } else if (which == 1) {
                    // Cập nhật trạng thái bàn ăn thành "Có khách"
                    list.get(position).setTrangthai("Có khách");
                    adapter.notifyDataSetChanged();  // Cập nhật giao diện
                    // Chuyển sang màn hình gọi món
                    Intent intent = new Intent(Trangchu.this, menu_monan.class);
                    intent.putExtra("ban_so", position + 1); // Gửi số bàn
                    startActivity(intent);
                }
            }
        });
        // Hiển thị Dialog
        builder.show();
    }
    private void capNhatTrangThaiBan() {
        SharedPreferences sharedPreferences = getSharedPreferences("MonAnDaChon", MODE_PRIVATE);
        for (Trang_chu banAn : list) {
            if (sharedPreferences.contains("Bàn " + banAn.getId_ban())) {
                banAn.setTrangthai("Có khách");
            }
        }
        adapter.notifyDataSetChanged();
    }


}

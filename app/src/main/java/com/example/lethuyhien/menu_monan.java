package com.example.lethuyhien;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
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

import com.example.lethuyhien.Adapter.Trang_chu_Adapter;
import com.example.lethuyhien.Adapter.menu_adapter;
import com.example.lethuyhien.Model.Menu;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class menu_monan extends AppCompatActivity {
    ListView listView;
    DrawerLayout drawerLayout;
    NavigationView na;
    Toolbar toolbar;
    BottomNavigationView bottomNavigationView;
    Menu menu;
    menu_adapter adapter;
    ArrayList<Menu> list =new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_monan);
        // Thiết lập các view
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        listView=findViewById(R.id.ListMenu);
        drawerLayout = findViewById(R.id.main);
        na = findViewById(R.id.naQLMN);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        bottomNavigationView = findViewById(R.id.QLMA);
        list.add(new Menu(R.drawable.bap_cai_cuon,"Bắp cải cuộn",25000,"Món chính"));
        list.add(new Menu(R.drawable.bo_kho,"Bò kho",70000,"Món chính"));
        list.add(new Menu(R.drawable.ca_kho,"Cá kho",70000,"Món chính"));
        list.add(new Menu(R.drawable.canh_bi,"Canh bí",50000,"Món chính"));
        list.add(new Menu(R.drawable.canh_bi_dao,"Canh bí đao",50000,"Món chính"));
        list.add(new Menu(R.drawable.canh_kho_qua,"Canh khổ qua",50000,"Món chính"));
        list.add(new Menu(R.drawable.canh_chua,"Canh chua",50000,"Món chính"));
        list.add(new Menu(R.drawable.canh_xuong,"Canh xương",50000,"Món chính"));
        list.add(new Menu(R.drawable.cha_la_lot,"Chả lá lốt",40000,"Món chính"));
        list.add(new Menu(R.drawable.cha_trung,"Chả trứng",40000,"Món chính"));
        list.add(new Menu(R.drawable.cuon_thit,"Cuốn thịt",40000,"Món chính"));
        list.add(new Menu(R.drawable.dau_hu_nhoi,"Đậu hũ nhồi",40000,"Món chính"));
        list.add(new Menu(R.drawable.mien_tron,"Miến trộn",30000,"Món chính"));
        list.add(new Menu(R.drawable.nem_lui,"Nem lụi",40000,"Món chính"));
        list.add(new Menu(R.drawable.rau_cai_xao_toi,"Rau cải xào tỏi",25000,"Món chính"));
        list.add(new Menu(R.drawable.rau_luoc_ngu_sac,"Rau luộc ngũ sắc",25000,"Món chính"));
        list.add(new Menu(R.drawable.rau_muong_xao_toi,"Rau muống xào tỏi",25000,"Món chính"));
        list.add(new Menu(R.drawable.thit_ba_chi_rang,"Thịt ba chỉ rang",70000,"Món chính"));
        list.add(new Menu(R.drawable.thit_bo_xao,"Thịt bò xào",70000,"Món chính"));
        list.add(new Menu(R.drawable.thit_kho_tau,"Thịt kho tàu",70000,"Món chính"));
        list.add(new Menu(R.drawable.thit_luoc,"Thịt luộc",70000,"Món chính"));
        list.add(new Menu(R.drawable.vit_quay,"Vịt quay",70000,"Món chính"));
        list.add(new Menu(R.drawable.xoi_ga,"Xôi gà",70000,"Món chính"));
        list.add(new Menu(R.drawable.bap_cai_luoc,"Bắp cải luộc",25000,"Món chính"));
        adapter=new menu_adapter(this,list);
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
                    Intent intent4 = new Intent(menu_monan.this, Quanlynhanvien.class);
                    startActivity(intent4);
                }  else if (itemId == R.id.item5) {
                    Intent intent5 = new Intent(menu_monan.this, Quanlymonan.class);
                    startActivity(intent5);
                }else if (itemId == R.id.item6) {
                    Intent intent6 = new Intent(menu_monan.this, Quanlyhoadon.class);
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
                    Intent intent4 = new Intent(menu_monan.this, Trangchu.class);
                    startActivity(intent4);
                }  else if (itemId == R.id.item2) {
                    Intent intent5 = new Intent(menu_monan.this, menu_monan.class);
                    startActivity(intent5);
                }else if (itemId == R.id.item3) {
                    Intent intent6 = new Intent(menu_monan.this, Hoadon.class);
                    startActivity(intent6);
                } else if (itemId == R.id.item4) {
                    Intent intent7 = new Intent(menu_monan.this, thong_ke.class);
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
                        Intent intent = new Intent(menu_monan.this, Dangnhap.class);
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
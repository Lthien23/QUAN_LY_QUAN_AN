package com.example.lethuyhien;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import com.example.lethuyhien.Adapter.Trang_chu_Adapter;
import com.example.lethuyhien.Database.dbQLQA;
import com.example.lethuyhien.Model.NhanVien;
import com.example.lethuyhien.Model.Trang_chu;

import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Trangchu extends AppCompatActivity  {
    DrawerLayout drawerLayout;
    NavigationView na;
    Toolbar toolbar;
    BottomNavigationView bottomNavigationView;
    RecyclerView recyclerView;
    Trang_chu tc;
    EditText editTextTimKiem;
    Trang_chu_Adapter adapter;
    ArrayList<Trang_chu> imageArry = new ArrayList<Trang_chu>();
    ExecutorService executor = Executors.newSingleThreadExecutor();
    List<Trang_chu> danhSachBan;
    List<Trang_chu> danhSachTimKiem;

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
        dbQLQA db = new dbQLQA(this);
        editTextTimKiem = findViewById(R.id.editTC);
        drawerLayout = findViewById(R.id.main);
        na = findViewById(R.id.na);
        toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        recyclerView = findViewById(R.id.ListTrangchu);
        recyclerView.setLayoutManager(new LinearLayoutManager(this)); // Đặt layout manager cho RecyclerView

        // Lấy tất cả các bàn từ cơ sở dữ liệu
        danhSachBan = db.getAllContacts();
        danhSachTimKiem = new ArrayList<>(danhSachBan);

        // Khởi tạo adapter và gán cho RecyclerView
        adapter = new Trang_chu_Adapter(this, danhSachTimKiem);  // Không cần truyền layout ở đây
        recyclerView.setAdapter(adapter);

        // Thiết lập listener cho EditText tìm kiếm
        editTextTimKiem = findViewById(R.id.editTC);
        editTextTimKiem.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Không cần xử lý ở đây
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                danhSachTimKiem.clear();  // Xóa danh sách tìm kiếm cũ

                if (!s.toString().isEmpty()) {
                    String searchText = s.toString().toLowerCase();  // Lấy văn bản tìm kiếm và chuyển thành chữ thường

                    // Tìm kiếm theo idBan, số chỗ hoặc trạng thái
                    for (Trang_chu ban : danhSachBan) {
                        if (String.valueOf(ban.getId_ban()).contains(searchText) ||
                                ban.getSocho().toLowerCase().contains(searchText) ||
                                ban.getTrangthai().toLowerCase().contains(searchText)) {
                            danhSachTimKiem.add(ban);  // Thêm kết quả vào danh sách tìm kiếm
                        }
                    }

                    if (danhSachTimKiem.isEmpty()) {
                        Toast.makeText(Trangchu.this, "Không tìm thấy kết quả phù hợp!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Nếu ô tìm kiếm trống, hiển thị lại toàn bộ danh sách
                    danhSachTimKiem.addAll(danhSachBan);
                }

                // Cập nhật lại RecyclerView
                adapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Không cần xử lý ở đây
            }
        });

        // Nếu cơ sở dữ liệu trống, thêm vài bàn mẫu
        if (db.getContactsCount() == 0) {
            db.addContact(new Trang_chu(1, null, "4", "Trống"));
            db.addContact(new Trang_chu(2, null, "6", "Có khách"));
            db.addContact(new Trang_chu(3, null, "2", "Trống"));
        }

        // Thiết lập NavigationView và DrawerLayout
        drawerLayout = findViewById(R.id.main);
        na = findViewById(R.id.na);
        toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);

        View headerView = na.getHeaderView(0); // Lấy view header của NavigationView
        String username = getIntent().getStringExtra("username");

        NhanVien nhanVien = db.getThongTinNhanVien(username);
        if (nhanVien != null) {
            TextView tvEmployeeName = headerView.findViewById(R.id.tennguoidung);
            TextView tvPhoneNumber = headerView.findViewById(R.id.so_dien_thoai);
            tvEmployeeName.setText(nhanVien.getHoten());
            tvPhoneNumber.setText(nhanVien.getSdt());
        }


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
        adapter.setOnItemLongClickListener(position -> {
            // Hiển thị Log và gọi phương thức xử lý khi item được long-click
            Log.d("RecyclerView", "Item long-clicked at position: " + position);
            showOptionDialog(position);
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
        AlertDialog.Builder builder = new AlertDialog.Builder(Trangchu.this);
        builder.setTitle("Lựa chọn hành động");
        builder.setItems(new String[]{"Thanh toán", "Gọi món"}, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    // Chuyển sang màn hình hóa đơn
                    imageArry.get(position).setTrangthai("Trống");
                    adapter.notifyDataSetChanged();  // Cập nhật giao diện
                    Intent intent = new Intent(Trangchu.this, Hoadon.class);
                    intent.putExtra("ban_so", position + 1);
                    startActivity(intent);
                } else if (which == 1) {
                    // Cập nhật trạng thái bàn ăn thành "Có khách"
                    imageArry.get(position).setTrangthai("Có khách");
                    adapter.notifyDataSetChanged();  // Cập nhật giao diện
                    Intent intent = new Intent(Trangchu.this, menu_monan.class);
                    intent.putExtra("ban_so", position + 1);
                    startActivity(intent);
                }
            }
        });
        builder.show();
    }

    private void capNhatTrangThaiBan() {
        SharedPreferences sharedPreferences = getSharedPreferences("MonAnDaChon", MODE_PRIVATE);
        for (Trang_chu banAn : imageArry) {
            if (sharedPreferences.contains("Bàn " + banAn.getId_ban())) {
                banAn.setTrangthai("Có khách");
            }
        }
        adapter.notifyDataSetChanged();
    }


}
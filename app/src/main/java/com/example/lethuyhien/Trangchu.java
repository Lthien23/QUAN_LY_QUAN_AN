package com.example.lethuyhien;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class Trangchu extends AppCompatActivity {
    DrawerLayout drawerLayout;
    NavigationView na;
    Toolbar toolbar;
    BottomNavigationView bottomNavigationView;
    private boolean isBottomNavVisible = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trangchu);

        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.na);
        toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        // Ánh xạ BottomNavigationView và nút toggle
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        //ImageButton btnToggleBottomNav = findViewById(R.id.btnToggleBottomNav);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }
}

package com.example.menutest;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity  {

    private AppBarConfiguration mAppBarConfiguration;
    private DrawerLayout drawer;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ConstraintLayout content = findViewById(R.id.content);

        // Инициализируем и устанвливаем бар (верхняя монолитная иконка)
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//      Инициализируем DrawerLayout (улучшеная верся FrameLayout)
        drawer = findViewById(R.id.drawer_layout);

//      Иницилизируем NavigationView и устанавливаем слушатель для взаимодействия с другими активностями
        navigationView = findViewById(R.id.nav_view);
//        navigationView.setNavigationItemSelectedListener(this);

//      Отвечает за навигацию и баром, но необходимо изучить как именно
        mAppBarConfiguration = new AppBarConfiguration.Builder(R.id.icon_timetable, R.layout.activity_navigation)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);


//      Отвечает за смахивание содержимого при открывании меню
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string
                .navigation_drawer_close) {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);

                float slideX = drawerView.getWidth() * slideOffset;
                content.setTranslationX(slideX);

                // а также меняем размер
                content.setScaleX(1 - slideOffset);
                content.setScaleY(1 - slideOffset);
            }
        };
        drawer.addDrawerListener(toggle);
        toggle.syncState();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

}
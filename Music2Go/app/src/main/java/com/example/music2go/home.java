package com.example.music2go;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getSupportActionBar().hide();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
//        getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new MyLibraryFragment()).commit();
//        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                Fragment selectedFragment = null;
//                switch (item.getItemId()) {
//                    case R.id.myLibraryFragment:
//                        selectedFragment = new MyLibraryFragment();
//                        break;
//                    case R.id.currentlyPlayingFragment:
//                        selectedFragment = new CurrentlyPlayingFragment();
//                        break;
//                    case R.id.searchFragment:
//                        selectedFragment = new SearchFragment();
//                        break;
//                    case R.id.settingsFragment:
//                        selectedFragment = new SettingsFragment();
//                        break;
//                }
//
//                getSupportFragmentManager()
//                        .beginTransaction()
//                        .replace(R.id.fragment, selectedFragment)
//                        .commit();
//                return true;
//            }
//        });
//    }
//        getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new CurrentlyPlayingFragment()).commit();
        NavController navController = Navigation.findNavController(this, R.id.fragment);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);
//        NavigationUI.setupActionBarWithNavController(this, navController);


//    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
//        @Override
//        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//            Fragment selectedFragment = null;
//            switch (item.getItemId()) {
//                case R.id.currentlyPlayingFragment:
//                    selectedFragment = new CurrentlyPlayingFragment();
//                    break;
//                case R.id.searchFragment:
//                    selectedFragment = new SearchFragment();
//                    break;
//                case R.id.settingsFragment:
//                    selectedFragment = new SettingsFragment();
//                    break;
//            }
//
//            getSupportFragmentManager()
//                    .beginTransaction()
//                    .replace(R.id.fragment, selectedFragment)
//                    .commit();
//            return true;
//        }
//    };
}}
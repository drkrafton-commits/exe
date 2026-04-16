package com.example.sayana;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.example.sayana.fragments.HomeFragment;
import com.example.sayana.fragments.CatalogFragment;
import com.example.sayana.fragments.ProfileFragment;
import com.example.sayana.fragments.ProjectsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.example.sayana.fragments.HomeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class DashboardActivity extends AppCompatActivity {

    private String userEmail; // сохраним email пользователя

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // Получаем email из Intent (передан из LoginActivity)
        userEmail = getIntent().getStringExtra("email");

        BottomNavigationView bottomNavigation = findViewById(R.id.bottomNavigation);
        bottomNavigation.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_home) {
                replaceFragment(new HomeFragment());
                return true;
            } else if (itemId == R.id.nav_catalog) {
                replaceFragment(new CatalogFragment());
                return true;
            } else if (itemId == R.id.nav_projects) {
                replaceFragment(new ProjectsFragment());
                return true;
                // В обработчике нажатия на пункт меню
            } else if (itemId == R.id.nav_profile) {
                ProfileFragment profileFragment = new ProfileFragment();
                Bundle args = new Bundle();
                args.putString("email", userEmail);
                profileFragment.setArguments(args);
                replaceFragment(profileFragment);
                return true;
            }

            return false;
        });
        // По умолчанию показываем HomeFragment
        bottomNavigation.setSelectedItemId(R.id.nav_home);
    }

    private void replaceFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .commit();
    }
}
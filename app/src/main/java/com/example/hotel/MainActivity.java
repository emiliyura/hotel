package com.example.hotel;

import android.annotation.SuppressLint;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;


import com.example.hotel.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth auth;
    Button button;
    TextView textView;
    FirebaseUser user;
    ActivityMainBinding binding;
    BottomNavigationView bottomNavigationView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.black));

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                Fragment selectedFragment = null;

                if(item.getItemId() == R.id.search)
                {
                    selectedFragment = new SearchFragment();
                }


                else if (item.getItemId() == R.id.profile)
                {
                    selectedFragment = new ProfileFragment();
                }


                else if (item.getItemId() == R.id.settings)
                {
                    selectedFragment = new SettingsFragment();
                }


                if (selectedFragment != null) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, selectedFragment)
                            .commitNow();
                }

                return true;
            }
        });

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SearchFragment()).commit();
    }
}

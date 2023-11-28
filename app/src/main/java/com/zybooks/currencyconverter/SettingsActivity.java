package com.zybooks.currencyconverter;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class SettingsActivity extends AppCompatActivity {
    private ArrayList<String> favoritesList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Switch for dark mode using SwitchCompat
        SwitchCompat darkModeSwitch = findViewById(R.id.darkModeSwitch);
        darkModeSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                // Switch to dark mode
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            } else {
                // Switch to light mode
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
            recreate(); // Recreate the activity to apply the theme immediately
        });
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavBar);

        bottomNavigationView.setOnItemSelectedListener(
                new BottomNavigationView.OnItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull android.view.MenuItem item) {
                        int itemId = item.getItemId();
                        if (itemId == R.id.action_home) {
                            startActivity(new Intent(SettingsActivity.this, MainActivity.class));
                            return true;
                        } else if (itemId == R.id.action_favorites) {
                            startActivity(new Intent(SettingsActivity.this, FavoritesActivity.class));
                            return true;
                        } else if (itemId == R.id.action_favorites) {
                            Intent favoritesIntent = new Intent(SettingsActivity.this, FavoritesActivity.class);
                            favoritesIntent.putStringArrayListExtra("favoritesList", favoritesList);
                            startActivity(favoritesIntent);
                            return true;
                        } else if (itemId == R.id.action_settings) {
                            //Do nothing we are in settings activity
                            return true;
                        }
                        return false;
                    }
                });
    }
}

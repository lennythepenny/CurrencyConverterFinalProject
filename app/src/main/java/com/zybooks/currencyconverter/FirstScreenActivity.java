package com.zybooks.currencyconverter;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.widget.Toolbar;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class FirstScreenActivity extends AppCompatActivity {
    //First activity user sees when you click start converting button it starts the MainActivity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_screen);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        Button startMainActivityButton = findViewById(R.id.startButton);

        startMainActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FirstScreenActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
    //Creating the menu bar option in first activity if returning user
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu_bar, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull android.view.MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.action_home) {
            startActivity(new Intent(FirstScreenActivity.this, MainActivity.class));
            return true;
        } else if (itemId == R.id.action_favorites) {
            startActivity(new Intent(FirstScreenActivity.this, FavoritesActivity.class));
            return true;
        } else if (itemId == R.id.action_account_circle) {
            startActivity(new Intent(FirstScreenActivity.this, AccountActivity.class));
            return true;
        } else if (itemId == R.id.action_settings) {
            startActivity(new Intent(FirstScreenActivity.this, SettingsActivity.class));
            return true;
        }
        return false;
    }

}
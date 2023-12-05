package com.zybooks.currencyconverter;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
/*
* Add functionality for the account section, maybe a dialog box that pops up when you click on the account
* */
public class AccountActivity extends AppCompatActivity {
    private ArrayList<String> favoritesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        TextView textViewName = findViewById(R.id.textViewName);
        TextView textViewEmail = findViewById(R.id.textViewEmail);
        TextView textViewAccountType = findViewById(R.id.textViewAccountType);

        textViewName.setText("John Doe");
        textViewEmail.setText("john.doe@example.com");
        textViewAccountType.setText("Premium");
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavBar);

        bottomNavigationView.setOnItemSelectedListener(
                new BottomNavigationView.OnItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull android.view.MenuItem item) {
                        int itemId = item.getItemId();
                        if (itemId == R.id.action_home) {
                            startActivity(new Intent(AccountActivity.this, MainActivity.class));
                            return true;
                        } else if (itemId == R.id.action_favorites) {
                            Intent favoritesIntent = new Intent(AccountActivity.this, FavoritesActivity.class);
                            favoritesIntent.putStringArrayListExtra("favoritesList", favoritesList);
                            startActivity(favoritesIntent);
                            return true;
                        } else if (itemId == R.id.action_account_circle) {
                            //Do nothing we are in account activity
                            return true;
                        } else if (itemId == R.id.action_settings) {
                            startActivity(new Intent(AccountActivity.this, SettingsActivity.class));
                            return true;
                        }
                        return false;
                    }
                });
    }
}

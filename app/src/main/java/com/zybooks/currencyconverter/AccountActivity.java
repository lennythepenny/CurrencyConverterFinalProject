package com.zybooks.currencyconverter;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomappbar.BottomAppBar;

public class AccountActivity extends AppCompatActivity {

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
        BottomAppBar bottomAppBar = findViewById(R.id.bottomAppBar);
        bottomAppBar.setOnMenuItemClickListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.action_home) {
                Intent mainIntent = new Intent(AccountActivity.this, MainActivity.class);
                startActivity(mainIntent);
                return true;
            } else if (itemId == R.id.action_favorites) {
                Intent favoritesIntent = new Intent(AccountActivity.this, FavoritesActivity.class);
                startActivity(favoritesIntent);
                return true;
            } else if (itemId == R.id.action_account_circle) {
                //Do nothing, we are already in the account activity
                return true;
            } else if (itemId == R.id.action_settings) {
                Intent settingsIntent = new Intent(AccountActivity.this, SettingsActivity.class);
                startActivity(settingsIntent);
                return true;
            }
            return false;
        });
    }
}

package com.zybooks.currencyconverter;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class FavoritesActivity extends AppCompatActivity {
    private RecyclerView favoritesRecyclerView;
    private static FavoritesAdapter favoritesAdapter;
    private static ArrayList<String> favoritesList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        favoritesList = new ArrayList<>();
        favoritesAdapter = new FavoritesAdapter(favoritesList);

        // Initialize RecyclerView and set its layout manager
        favoritesRecyclerView = findViewById(R.id.favoritesRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        favoritesRecyclerView.setLayoutManager(layoutManager);

        // Initialize the adapter with the data
        favoritesAdapter = new FavoritesAdapter(favoritesList);
        favoritesRecyclerView.setAdapter(favoritesAdapter);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("selectedCurrency")) {
            String selectedCurrency = intent.getStringExtra("selectedCurrency");
            addToFavorites(selectedCurrency);
        }

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavBar);

        bottomNavigationView.setOnItemSelectedListener(
                new BottomNavigationView.OnItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull android.view.MenuItem item) {
                        int itemId = item.getItemId();
                        if (itemId == R.id.action_home) {
                            startActivity(new Intent(FavoritesActivity.this, MainActivity.class));
                            return true;
                        } else if (itemId == R.id.action_favorites) {
                            //Do nothing we are in favorites activity
                            return true;
                        } else if (itemId == R.id.action_account_circle) {
                            startActivity(new Intent(FavoritesActivity.this, AccountActivity.class));
                            return true;
                        } else if (itemId == R.id.action_settings) {
                            startActivity(new Intent(FavoritesActivity.this, SettingsActivity.class));
                            return true;
                        }
                        return false;
                    }
                });
    }
    public void addToFavorites(String selectedCurrency) {
        Log.d("CurrencyDebug", "Adding to favorites in FavoritesActivity: " + selectedCurrency);
        favoritesList.add(selectedCurrency);
        favoritesAdapter.notifyDataSetChanged();
    }
    private void removeFromFavorites(int position) {
        favoritesList.remove(position);
        favoritesAdapter.notifyDataSetChanged();
    }
}

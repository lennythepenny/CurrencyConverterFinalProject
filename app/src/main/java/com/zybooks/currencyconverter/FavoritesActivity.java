package com.zybooks.currencyconverter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.zybooks.currencyconverter.R;

import java.util.ArrayList;

public class FavoritesActivity extends AppCompatActivity {

    private ArrayList<String> favoritesList;
    private ArrayAdapter<String> favoritesAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        favoritesList = new ArrayList<>();

        ListView favoritesListView = findViewById(R.id.favoritesListView);
        favoritesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, favoritesList);
        favoritesListView.setAdapter(favoritesAdapter);
        favoritesListView.setOnItemClickListener((parent, view, position, id) -> removeFromFavorites(position));

        BottomAppBar bottomAppBar = findViewById(R.id.bottomAppBar);
        bottomAppBar.setOnMenuItemClickListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.action_home) {
                Intent mainIntent = new Intent(FavoritesActivity.this, MainActivity.class);
                startActivity(mainIntent);
                return true;
            } else if (itemId == R.id.action_favorites) {
                //Do nothing we are already in favorites
                return true;
            } else if (itemId == R.id.action_account_circle) {
                Intent accountIntent = new Intent(FavoritesActivity.this, AccountActivity.class);
                startActivity(accountIntent);
                return true;
            } else if (itemId == R.id.action_settings) {
                Intent settingsIntent = new Intent(FavoritesActivity.this, SettingsActivity.class);
                startActivity(settingsIntent);
                return true;
            }
            return false;
        });
        favoritesListView.setOnItemClickListener((parent, view, position, id) -> {
            String selectedCurrency = favoritesList.get(position);

            // Add the selected currency to favorites in FavoritesActivity
            addToFavorites(selectedCurrency);
        });
    }
    public void addToFavorites(String currency) {
        favoritesList.add(currency);
        favoritesAdapter.notifyDataSetChanged();
    }
    private void removeFromFavorites(int position) {
        favoritesList.remove(position);
        favoritesAdapter.notifyDataSetChanged();
    }
}

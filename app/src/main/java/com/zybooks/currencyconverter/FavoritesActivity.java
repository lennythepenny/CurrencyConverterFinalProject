package com.zybooks.currencyconverter;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
public class FavoritesActivity extends AppCompatActivity {
    private RecyclerView favoritesRecyclerView;
    private static FavoritesAdapter favoritesAdapter;
    private static ArrayList<String> favoritesList;
    private static Set<String> favoritesSet;
    private static final String PREFS_NAME = "MyPrefsFile";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        //Setting the favorites list, if we have previous favorites it is loaded here
        favoritesSet = loadFavorites();
        favoritesList = new ArrayList<>(favoritesSet);
        favoritesAdapter = new FavoritesAdapter(favoritesList);
        favoritesRecyclerView = findViewById(R.id.favoritesRecyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        favoritesRecyclerView.setLayoutManager(layoutManager);
        favoritesRecyclerView.setAdapter(favoritesAdapter);

        favoritesAdapter.setOnItemClickListener(new FavoritesAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String selectedCurrency) {
                // Show FunFactDialog when an item is clicked
                FunFactDialog.show(FavoritesActivity.this, selectedCurrency);
            }
        });
        //Deleting the currency from the list
        favoritesAdapter.setOnLongItemClickListener(new FavoritesAdapter.OnLongItemClickListener() {
            @Override
            public void onLongItemClick(int position) {
                showDeleteConfirmationDialog(position);
            }
        });
        //Launching the activity through the bottom nav bar and making sure it is populated with the previous data
        Intent intent = getIntent();
        if (intent != null) {
            if (intent.hasExtra("selectedCurrency")) {
                String selectedCurrency = intent.getStringExtra("selectedCurrency");
                addToFavorites(selectedCurrency);
            } else if (intent.hasExtra("favoritesList")) {
                ArrayList<String> updatedFavoritesList = intent.getStringArrayListExtra("favoritesList");
                    favoritesList.clear();
                    favoritesList.addAll(updatedFavoritesList);
                    favoritesAdapter.updateData(favoritesList);
            }
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
    //Helper function to make favorites display more detailed than three letter currency code
    private String convertCountryCodeToFullName(String countryCode) {
        switch (countryCode) {
            case "USD":
                return "United States Dollar";
            case "EUR":
                return "Euro";
            case "JPY":
                return "Japanese Yen";
            case "GBP":
                return "British Pound Sterling";
            case "AUD":
                return "Australian Dollar";
            case "CAD":
                return "Canadian Dollar";
            case "CHF":
                return "Swiss Franc";
            case "CNY":
                return "Chinese Yuan";
            case "SEK":
                return "Swedish Krona";
            case "NZD":
                return "New Zealand Dollar";
            default:
                return countryCode;
        }
    }
    //Adding to favorites list view for the activity
    public void addToFavorites(String selectedCurrency) {
        String fullName = convertCountryCodeToFullName(selectedCurrency);
        favoritesSet.add(fullName);
        favoritesAdapter.updateData(new ArrayList<>(favoritesSet));
    }
    //Removing from favorites list view for the activity
    private void removeFromFavorites(int position) {
        String removedCurrency = favoritesList.remove(position);
        favoritesAdapter.updateData(new ArrayList<>(favoritesList));
    }
    //Loading previous favorites if needed
    private Set<String> loadFavorites() {
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return settings.getStringSet("favoritesSet", new HashSet<>());
    }
    //Delete confirmation for favorites
    private void showDeleteConfirmationDialog(int position) {
        String selectedCurrency = favoritesList.get(position);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Do you want to remove " + selectedCurrency + " from favorites?")
                .setTitle("Remove Favorite");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                removeFromFavorites(position);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                //Do nothing
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
package com.zybooks.currencyconverter;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Locale;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import android.app.AlertDialog;
import android.content.DialogInterface;

/**
 * Final currency symbol format
 * Fix favorites navigation bar when you click it from another activity
 * Add Multiple Languages
 */

public class MainActivity<MenuItem> extends AppCompatActivity {
    private ImageView swapButton;
    private boolean isRotated = false;
    private boolean isFavorite = false;
    private ArrayList<String> favoritesList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        favoritesList = new ArrayList<>();
        Spinner currencyOne = findViewById(R.id.currencyOne);
        Spinner currencyTwo = findViewById(R.id.currencyTwo);
        Button convertButton = findViewById(R.id.convertButton);
        Button clearButton2 = findViewById(R.id.clearButton2);
        ImageView favoriteBorder = findViewById(R.id.favoriteBorder);
        swapButton = findViewById(R.id.swapVert);

        Intent intent = getIntent();
        if (intent.hasExtra("selectedCurrency")) {
            String selectedCurrency = intent.getStringExtra("selectedCurrency");
            addToFavorites(selectedCurrency);
        }
        favoriteBorder.setOnClickListener(view -> {
            String selectedCurrency = currencyOne.getSelectedItem().toString();
            addToFavorites(selectedCurrency);

            Intent favoritesIntent = new Intent(MainActivity.this, FavoritesActivity.class);
            favoritesIntent.putExtra("selectedCurrency", selectedCurrency);
            favoritesLauncher.launch(favoritesIntent);
        });
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavBar);

        bottomNavigationView.setOnItemSelectedListener(
                new BottomNavigationView.OnItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull android.view.MenuItem item) {
                        int itemId = item.getItemId();
                        if (itemId == R.id.action_home) {
                            // Do nothing, we are already in home
                            return true;
                        } else if (itemId == R.id.action_favorites) {
                            Intent favoritesIntent = new Intent(MainActivity.this, FavoritesActivity.class);
                            favoritesIntent.putStringArrayListExtra("favoritesList", favoritesList);
                            startActivity(favoritesIntent);
                            return true;
                        } else if (itemId == R.id.action_account_circle) {
                            startActivity(new Intent(MainActivity.this, AccountActivity.class));
                            return true;
                        } else if (itemId == R.id.action_settings) {
                            startActivity(new Intent(MainActivity.this, SettingsActivity.class));
                            return true;
                        }
                        return false;
                    }
                });

        // Initialize spinners with currency options
        ArrayAdapter<CharSequence> currencyAdapter = ArrayAdapter.createFromResource(this, R.array.currencies_array, android.R.layout.simple_spinner_item);
        currencyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        currencyOne.setAdapter(currencyAdapter);
        currencyTwo.setAdapter(currencyAdapter);

        // Set default currencies
        setDefaultCurrencies(currencyAdapter, currencyOne, currencyTwo);

        currencyOne.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Handle current currency selection
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing
            }
        });

        currencyTwo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Handle final currency selection
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing
            }
        });
        convertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Convert currency and display result
                convertCurrency();
            }
        });

        clearButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Clear input fields and result
                showClearConfirmationDialog();
            }
        });

        favoriteBorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Add animation for fading out
                String sourceCurrency = currencyOne.getSelectedItem().toString();
                addToFavorites(sourceCurrency);
                ObjectAnimator fadeOut = ObjectAnimator.ofFloat(favoriteBorder, "alpha", 1.0f, 0.0f);
                fadeOut.setDuration(250); // Faster animation, adjust as needed

                fadeOut.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        // Toggle favorite state
                        isFavorite = !isFavorite;

                        // Change the image resource based on the state
                        if (isFavorite) {
                            favoriteBorder.setImageResource(R.drawable.favorite_filled);
                            String selectedCurrency = currencyOne.getSelectedItem().toString();
                            addToFavorites(selectedCurrency);
                            Log.d("CurrencyDebug", "Selected Currency in MainActivity if (isFavorite): " + selectedCurrency);

                            // Pass the selected currency to FavoritesActivity
                            Intent favoritesIntent = new Intent(MainActivity.this, FavoritesActivity.class);
                            favoritesIntent.putExtra("selectedCurrency", selectedCurrency);
                            favoritesLauncher.launch(favoritesIntent);
                        } else {
                            favoriteBorder.setImageResource(R.drawable.favorite_border);
                        }

                        // Add animation for fading in
                        ObjectAnimator fadeIn = ObjectAnimator.ofFloat(favoriteBorder, "alpha", 0.0f, 1.0f);
                        fadeIn.setDuration(250); // Faster animation, adjust as needed
                        fadeIn.start();
                    }
                });

                fadeOut.start();
            }
        });


        swapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Swap current and final currencies
                swapCurrencies(currencyOne, currencyTwo);
            }
        });
        swapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                swapCurrencies(currencyOne, currencyTwo);
                rotateButton();
            }
        });
    }


    private void addToFavorites(String selectedCurrency) {
        Log.d("CurrencyDebug", "Adding to favorites: " + selectedCurrency);
        if (!favoritesList.contains(selectedCurrency)) {
            favoritesList.add(selectedCurrency);
            // You can also update the UI or perform any other actions related to adding to favorites here
            // Broadcast an intent with the updated favorites list
            Intent updateFavoritesIntent = new Intent("updateFavorites");
            updateFavoritesIntent.putStringArrayListExtra("favoritesList", favoritesList);
            sendBroadcast(updateFavoritesIntent);
        } else {
            // Handle the case where the currency is already in favorites
            // You can show a message or handle it in a way that fits your app logic
            Log.d("CurrencyDebug", "Currency is already in favorites: " + selectedCurrency);
        }
    }

    private void setDefaultCurrencies(ArrayAdapter<CharSequence> adapter, Spinner currencyOne, Spinner currencyTwo) {
        CharSequence defaultCurrencyOne = "USD";
        CharSequence defaultCurrencyTwo = "EUR";

        int positionCurrencyOne = adapter.getPosition(defaultCurrencyOne);
        int positionCurrencyTwo = adapter.getPosition(defaultCurrencyTwo);

        currencyOne.setSelection(positionCurrencyOne);
        currencyTwo.setSelection(positionCurrencyTwo);
    }

    private double convertCurrency() {
        EditText amountEditText = findViewById(R.id.firstCurrency);
        String amountString = amountEditText.getText().toString();

        if (amountString.isEmpty()) {
            Toast.makeText(MainActivity.this, "Please enter the amount to convert", Toast.LENGTH_SHORT).show();
            return 0.0; // Return 0 if the amount is not entered
        }

        double amountToConvert = Double.parseDouble(amountString);

        Spinner currencyOne = findViewById(R.id.currencyOne);
        Spinner currencyTwo = findViewById(R.id.currencyTwo);

        String sourceCurrency = "";
        String targetCurrency = "";

        if (currencyOne.getSelectedItem() != null) {
            sourceCurrency = currencyOne.getSelectedItem().toString();
        }

        if (currencyTwo.getSelectedItem() != null) {
            targetCurrency = currencyTwo.getSelectedItem().toString();
        }

        double exchangeRate = getExchangeRate(sourceCurrency, targetCurrency);

        double result = amountToConvert * exchangeRate;

        TextView resultTextView = findViewById(R.id.resultCurrency);
        resultTextView.setText(String.format(Locale.getDefault(), "%.2f", result));
        return result;
    }
    private void showClearConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to clear?")
                .setTitle("Clear Fields");

        // Add the buttons
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked Yes button
                clearFieldsConfirmed();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked No button
                // Do nothing, close the dialog
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void clearFieldsConfirmed() {
        EditText amountEditText = findViewById(R.id.firstCurrency);
        EditText amountEditText2 = findViewById(R.id.secondCurrency);
        Spinner currencyOne = findViewById(R.id.currencyOne);
        Spinner currencyTwo = findViewById(R.id.currencyTwo);
        TextView resultTextView = findViewById(R.id.resultCurrency);
        ImageView favoriteBorder = findViewById(R.id.favoriteBorder);

        amountEditText.setText("");
        amountEditText2.setText("");
        currencyOne.setSelection(0);
        currencyTwo.setSelection(0);
        resultTextView.setText("");
        favoriteBorder.setImageResource(R.drawable.favorite_border);
    }

    private void swapCurrencies(Spinner currencyOne, Spinner currencyTwo) {
        Spinner spinCurrencyOne = findViewById(R.id.currencyOne);
        Spinner spinCurrencyTwo = findViewById(R.id.currencyTwo);

        int selectedCurrencyOne = currencyOne.getSelectedItemPosition();
        int selectedCurrencyTwo = currencyTwo.getSelectedItemPosition();

        currencyOne.setSelection(selectedCurrencyTwo);
        currencyTwo.setSelection(selectedCurrencyOne);
    }
    private void rotateButton() {
        float fromDegrees = swapButton.getRotation();
        float toDegrees = isRotated ? 0.0f : 180.0f; // Rotate back to 0 if already rotated

        ObjectAnimator rotateAnimation = ObjectAnimator.ofFloat(
                swapButton, "rotation", fromDegrees, toDegrees
        );

        rotateAnimation.setDuration(250); // Adjust the duration as needed
        rotateAnimation.start();

        // Toggle the isRotated value after the animation completes
        rotateAnimation.addListener(new AnimatorListenerAdapter() {
        @Override
        public void onAnimationEnd(Animator animation) {
                isRotated = !isRotated;
         }
    });
    }
    private final ActivityResultLauncher<Intent> favoritesLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK) {
                    // Handle the result data here
                    Intent data = result.getData();
                    if (data != null && data.hasExtra("selectedCurrency")) {
                        String selectedCurrency = data.getStringExtra("selectedCurrency");
                        addToFavorites(selectedCurrency);
                    }
                }
            }
    );

    private double getExchangeRate(String sourceCurrency, String targetCurrency) {
        Log.d("CurrencyDebug", "Source Currency: " + sourceCurrency);
        Log.d("CurrencyDebug", "Target Currency: " + targetCurrency);
        switch (sourceCurrency + "_" + targetCurrency) {
            case "United States Dollar (USD)_Euro (EUR)":
                return 0.92;
            case "United States Dollar (USD)_Japanese Yen (JPY)":
                return 149.63;
            case "United States Dollar (USD)_British Pound Sterling (GBP)":
                return 0.85;
            case "United States Dollar (USD)_Australian Dollar (AUD)":
                return 1.54;
            case "United States Dollar (USD)_Canadian Dollar (CAD)":
                return 1.37;
            case "United States Dollar (USD)_Swiss Franc (CHF)":
                return 0.89;
            case "United States Dollar (USD)_Chinese Yuan (CNY)":
                return 7.21;
            case "United States Dollar (USD)_Swedish Krona (SEK)":
                return 10.54;
            case "United States Dollar (USD)_New Zealand Dollar (NZD)":
                return 1.67;
            case "Euro (EUR)_United States Dollar (USD)":
                return 1.09;
            case "Euro (EUR)_Japanese Yen (JPY)":
                return 163.38;
            case"Euro (EUR)_British Pound Sterling (GBP)":
                return 0.88;
            case "Euro (EUR)_Australian Dollar (AUD)":
                return 1.68;
            case "Euro (EUR)_Canadian Dollar (CAD)":
                return 1.50;
            case "Euro (EUR)_Swiss Franc (CHF)":
                return 0.97;
            case "Euro (EUR)_Chinese Yuan (CNY)":
                return 7.86;
            case "Euro (EUR)_Swedish Krona (SEK)":
                return 11.48;
            case "Euro (EUR)_New Zealand Dollar (NZD)":
                return 1.82;
            case "Japanese Yen (JPY)_United States Dollar (USD)":
                return 0.0067;
            case "Japanese Yen (JPY)_Euro (EUR)":
                return 0.0061;
            case"Japanese Yen (JPY)_British Pound Sterling (GBP)":
                return 0.0054;
            case "Japanese Yen (JPY)_Australian Dollar (AUD)":
                return 0.010;
            case "Japanese Yen (JPY)_Canadian Dollar (CAD)":
                return 0.0092;
            case "Japanese Yen (JPY)_Swiss Franc (CHF)":
                return 0.0059;
            case "Japanese Yen (JPY)_Chinese Yuan (CNY)":
                return 0.048;
            case "Japanese Yen (JPY)_Swedish Krona (SEK)":
                return 0.070;
            case "Japanese Yen (JPY)_New Zealand Dollar (NZD)":
                return 0.011;
            case "British Pound Sterling (GBP)_United States Dollar (USD)":
                return 1.24;
            case "British Pound Sterling (GBP)_Euro (EUR)":
                return 1.14;
            case "British Pound Sterling (GBP)_Japanese Yen (JPY)":
                return 186.02;
            case "British Pound Sterling (GBP)_Australian Dollar (AUD)":
                return 1.91;
            case "British Pound Sterling (GBP)_Canadian Dollar (CAD)":
                return 1.71;
            case "British Pound Sterling (GBP)_Swiss Franc (CHF)":
                return 1.10;
            case "British Pound Sterling (GBP)_Chinese Yuan (CNY)":
                return 8.98;
            case "British Pound Sterling (GBP)_Swedish Krona (SEK)":
                return 13.12;
            case "British Pound Sterling (GBP)_New Zealand Dollar (NZD)":
                return 2.08;
            case "Australian Dollar (AUD)_United States Dollar (USD)":
                return 0.65;
            case "Australian Dollar (AUD)_Euro (EUR)":
                return 0.60;
            case "Australian Dollar (AUD)_Japanese Yen (JPY)":
                return 97.54;
            case "Australian Dollar (AUD)_British Pound Sterling (GBP)":
                return 0.52;
            case "Australian Dollar (AUD)_Canadian Dollar (CAD)":
                return 0.89;
            case "Australian Dollar (AUD)_Swiss Franc (CHF)":
                return 0.58;
            case "Australian Dollar (AUD)_Chinese Yuan (CNY)":
                return 4.69;
            case "Australian Dollar (AUD)_Swedish Krona (SEK)":
                return 6.86;
            case "Australian Dollar (AUD)_New Zealand Dollar (NZD)":
                return 1.09;
            case "Canadian Dollar (CAD)_United States Dollar (USD)":
                return 0.73;
            case "Canadian Dollar (CAD)_Euro (EUR)":
                return 0.67;
            case "Canadian Dollar (CAD)_Japanese Yen (JPY)":
                return 109.25;
            case "Canadian Dollar (CAD)_British Pound Sterling (GBP)":
                return 0.59;
            case "Canadian Dollar (CAD)_Australian Dollar (AUD)":
                return 1.12;
            case "Canadian Dollar (CAD)_Swiss Franc (CHF)":
                return 0.65;
            case "Canadian Dollar (CAD)_Chinese Yuan (CNY)":
                return 5.25;
            case "Canadian Dollar (CAD)_Swedish Krona (SEK)":
                return 7.68;
            case "Canadian Dollar (CAD)_New Zealand Dollar (NZD)":
                return 1.21;
            case "Swiss Franc (CHF)_United States Dollar (USD)":
                return 1.13;
            case "Swiss Franc (CHF)_Euro (EUR)":
                return 1.04;
            case "Swiss Franc (CHF)_Japanese Yen (JPY)":
                return 169.16;
            case "Swiss Franc (CHF)_British Pound Sterling (GBP)":
                return 0.91;
            case "Swiss Franc (CHF)_Australian Dollar (AUD)":
                return 1.73;
            case "Swiss Franc (CHF)_Canadian Dollar (CAD)":
                return 1.55;
            case "Swiss Franc (CHF)_Chinese Yuan (CNY)":
                return 8.14;
            case "Swiss Franc (CHF)_Swedish Krona (SEK)":
                return 11.88;
            case "Swiss Franc (CHF)_New Zealand Dollar (NZD)":
                return 1.88;
            case "Chinese Yuan (CNY)_United States Dollar (USD)":
                return 0.14;
            case "Chinese Yuan (CNY)_Euro (EUR)":
                return 0.13;
            case "Chinese Yuan (CNY)_Japanese Yen (JPY)":
                return 20.77;
            case "Chinese Yuan (CNY)_British Pound Sterling (GBP)":
                return 0.11;
            case "Chinese Yuan (CNY)_Australian Dollar (AUD)":
                return 0.22;
            case "Chinese Yuan (CNY)_Canadian Dollar (CAD)":
                return 0.20;
            case "Chinese Yuan (CNY)_Swiss Franc (CHF)":
                return 0.12;
            case "Chinese Yuan (CNY)_Swedish Krona (SEK)":
                return 1.46;
            case "Chinese Yuan (CNY)_New Zealand Dollar (NZD)":
                return 0.23;
            case "Swedish Krona (SEK)_United States Dollar (USD)":
                return 0.095;
            case "Swedish Krona (SEK)_Euro (EUR)":
                return 0.087;
            case "Swedish Krona (SEK)_Japanese Yen (JPY)":
                return 14.23;
            case "Swedish Krona (SEK)_British Pound Sterling (GBP)":
                return 0.076;
            case "Swedish Krona (SEK)_Australian Dollar (AUD)":
                return 0.15;
            case "Swedish Krona (SEK)_Canadian Dollar (CAD)":
                return 0.130;
            case "Swedish Krona (SEK)_Swiss Franc (CHF)":
                return 0.084;
            case "Swedish Krona (SEK)_Chinese Yuan (CNY)":
                return 0.68;
            case "Swedish Krona (SEK)_New Zealand Dollar (NZD)":
                return 0.16;
            case "New Zealand Dollar (NZD)_United States Dollar (USD)":
                return 0.60;
            case "New Zealand Dollar (NZD)_Euro (EUR)":
                return 0.55;
            case "New Zealand Dollar (NZD)_Japanese Yen (JPY)":
                return 89.88;
            case "New Zealand Dollar (NZD)_British Pound Sterling (GBP)":
                return 0.48;
            case "New Zealand Dollar (NZD)_Australian Dollar (AUD)":
                return 0.920;
            case "New Zealand Dollar (NZD)_Canadian Dollar (CAD)":
                return 0.82;
            case "New Zealand Dollar (NZD)_Swiss Franc (CHF)":
                return 0.53;
            case "New Zealand Dollar (NZD)_Chinese Yuan (CNY)":
                return 4.33;
            case "New Zealand Dollar (NZD)_Swedish Krona (SEK)":
                return 6.32;
            default:
                return 1.0;
        }
    }
}
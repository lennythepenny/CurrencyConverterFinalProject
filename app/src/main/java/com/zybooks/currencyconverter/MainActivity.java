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

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Currency;
import java.util.Locale;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import android.app.AlertDialog;
import android.content.DialogInterface;

/**
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
                            Log.d("CurrencyDebug", "Sending to FavoritesActivity: " + favoritesList.toString());

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
                swapCurrencies(currencyOne, currencyTwo);
                rotateButton();
            }
        });
    }
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
    private void addToFavorites(String selectedCurrency) {
        Log.d("CurrencyDebug", "Adding to favorites: " + selectedCurrency);
        String fullName = convertCountryCodeToFullName(selectedCurrency);
        if (!favoritesList.contains(selectedCurrency)) {
            favoritesList.add(selectedCurrency);
            Intent updateFavoritesIntent = new Intent("updateFavorites");
            updateFavoritesIntent.putStringArrayListExtra("favoritesList", favoritesList);
            sendBroadcast(updateFavoritesIntent);
        } else {
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
private void convertCurrency() {
    EditText amountEditText = findViewById(R.id.firstCurrency);
    String amountString = amountEditText.getText().toString();

    if (amountString.isEmpty()) {
        Toast.makeText(MainActivity.this, "Please enter the amount to convert", Toast.LENGTH_SHORT).show();
    }

    double amountToConvert = Double.parseDouble(amountString);

    Spinner currencyOne = findViewById(R.id.currencyOne);
    Spinner currencyTwo = findViewById(R.id.currencyTwo);

    String sourceCurrency = currencyOne.getSelectedItem().toString();
    String targetCurrency = currencyTwo.getSelectedItem().toString();

    try {
        Currency targetCurrencyInstance = Currency.getInstance(targetCurrency);

        double exchangeRate = getExchangeRate(sourceCurrency, targetCurrency);

        double result = amountToConvert * exchangeRate;

        NumberFormat cf = NumberFormat.getCurrencyInstance();
        cf.setCurrency(targetCurrencyInstance);  // Set currency based on the target currency
        String formattedResult = cf.format(result);

        TextView resultTextView = findViewById(R.id.resultCurrency);
        resultTextView.setText(formattedResult);
    } catch (IllegalArgumentException e) {
        Log.e("CurrencyDebug", "Invalid currency code - Source: " + sourceCurrency + ", Target: " + targetCurrency);
        // Handle the case where either sourceCurrency or targetCurrency is not a valid currency code
    }
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
        switch (sourceCurrency.trim() + "_" + targetCurrency.trim()) {
            case "USD_EUR":
                return 0.92;
            case "USD_JPY":
                return 149.63;
            case "USD_GBP":
                return 0.85;
            case "USD_AUD":
                return 1.54;
            case "USD_CAD":
                return 1.37;
            case "USD_CHF":
                return 0.89;
            case "USD_CNY":
                return 7.21;
            case "USD_SEK":
                return 10.54;
            case "USD_NZD":
                return 1.67;
            case "EUR_USD":
                return 1.09;
            case "EUR_JPY":
                return 163.38;
            case"EUR_GBP":
                return 0.88;
            case "EUR_AUD":
                return 1.68;
            case "EUR_CAD":
                return 1.50;
            case "EUR_CHF":
                return 0.97;
            case "EUR_CNY":
                return 7.86;
            case "EUR_SEK":
                return 11.48;
            case "EUR_NZD":
                return 1.82;
            case "JPY_USD":
                return 0.0067;
            case "JPY_EUR":
                return 0.0061;
            case"JPY_GBP":
                return 0.0054;
            case "JPY_AUD":
                return 0.010;
            case "JPY_CAD":
                return 0.0092;
            case "JPY_CHF":
                return 0.0059;
            case "JPY_CNY":
                return 0.048;
            case "JPY_SEK":
                return 0.070;
            case "JPY_NZD":
                return 0.011;
            case "GBP_USD":
                return 1.24;
            case "GBP_EUR":
                return 1.14;
            case "GBP_JPY":
                return 186.02;
            case "GBP_AUD":
                return 1.91;
            case "GBP_CAD":
                return 1.71;
            case "GBP_CHF":
                return 1.10;
            case "GBP_CNY":
                return 8.98;
            case "GBP_SEK":
                return 13.12;
            case "GBP_NZD":
                return 2.08;
            case "AUD_USD":
                return 0.65;
            case "AUD_EUR":
                return 0.60;
            case "AUD_JPY":
                return 97.54;
            case "AUD_GBP":
                return 0.52;
            case "AUD_CAD":
                return 0.89;
            case "AUD_CHF":
                return 0.58;
            case "AUD_CNY":
                return 4.69;
            case "AUD_SEK":
                return 6.86;
            case "AUD_NZD":
                return 1.09;
            case "CAD_USD":
                return 0.73;
            case "CAD_EUR":
                return 0.67;
            case "CAD_JPY":
                return 109.25;
            case "CAD_GBP":
                return 0.59;
            case "CAD_AUD":
                return 1.12;
            case "CAD_CHF":
                return 0.65;
            case "CAD_CNY":
                return 5.25;
            case "CAD_SEK":
                return 7.68;
            case "CAD_NZD":
                return 1.21;
            case "CHF_USD":
                return 1.13;
            case "CHF_EUR":
                return 1.04;
            case "CHF_JPY":
                return 169.16;
            case "CHF_GBP":
                return 0.91;
            case "CHF_AUD":
                return 1.73;
            case "CHF_CAD":
                return 1.55;
            case "CHF_CNY":
                return 8.14;
            case "CHF_SEK":
                return 11.88;
            case "CHF_NZD":
                return 1.88;
            case "CNY_USD":
                return 0.14;
            case "CNY_EUR":
                return 0.13;
            case "CNY_JPY":
                return 20.77;
            case "CNY_GBP":
                return 0.11;
            case "CNY_AUD":
                return 0.22;
            case "CNY_CAD":
                return 0.20;
            case "CNY_CHF":
                return 0.12;
            case "CNY_SEK":
                return 1.46;
            case "CNY_NZD":
                return 0.23;
            case "SEK_USD":
                return 0.095;
            case "SEK_EUR":
                return 0.087;
            case "SEK_JPY":
                return 14.23;
            case "SEK_GBP":
                return 0.076;
            case "SEK_AUD":
                return 0.15;
            case "SEK_CAD":
                return 0.130;
            case "SEK_CHF":
                return 0.084;
            case "SEK_CNY":
                return 0.68;
            case "SEK_NZD":
                return 0.16;
            case "NZD_USD":
                return 0.60;
            case "NZD_EUR":
                return 0.55;
            case "NZD_JPY":
                return 89.88;
            case "NZD_GBP":
                return 0.48;
            case "NZD_AUD":
                return 0.920;
            case "NZD_CAD":
                return 0.82;
            case "NZD_CHF":
                return 0.53;
            case "NZD_CNY":
                return 4.33;
            case "NZD_SEK":
                return 6.32;
            default:
                return 1.0;
        }
    }
}
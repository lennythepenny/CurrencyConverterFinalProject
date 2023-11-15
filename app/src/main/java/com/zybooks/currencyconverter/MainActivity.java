package com.zybooks.currencyconverter;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.appcompat.widget.Toolbar;


import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Spinner currencyOne = findViewById(R.id.currencyOne);
        Spinner currencyTwo = findViewById(R.id.currencyTwo);
        Button convertButton = findViewById(R.id.convertButton);
        Button clearButton2 = findViewById(R.id.clearButton2);
        ImageView favoriteBorder = findViewById(R.id.favoriteBorder);
        ImageView swapButton = findViewById(R.id.swapVert);

        // Initialize spinners with currency options
        ArrayAdapter<CharSequence> currencyAdapter = ArrayAdapter.createFromResource(this, R.array.currencies_array, android.R.layout.simple_spinner_item);
        currencyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        currencyOne.setAdapter(currencyAdapter);
        currencyTwo.setAdapter(currencyAdapter);

        CharSequence defaultCurrencyOne = "USD"; // Change this to your desired default currency
        CharSequence defaultCurrencyTwo = "EUR"; // Change this to your desired default currency

        // Find the position of the default currencies in the adapter
        int positionCurrencyOne = currencyAdapter.getPosition(defaultCurrencyOne);
        int positionCurrencyTwo = currencyAdapter.getPosition(defaultCurrencyTwo);

        // Set the selection for each spinner
        currencyOne.setSelection(positionCurrencyOne);
        currencyTwo.setSelection(positionCurrencyTwo);

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
                // Calculate and display currency conversion
            }
        });

        convertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Clear input fields and result
            }
        });

        favoriteBorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Open the FavoritesActivity
            }
        });

        swapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Swap current and final currencies
            }
        });
        Toolbar toolbar = findViewById(R.id.topAppBar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}

package com.zybooks.currencyconverter;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;


import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText currencyOne = findViewById(R.id.currencyOne);
        TextView currencyTwo = findViewById(R.id.currencyTwo);
        //Spinner currentCurrencySpinner = findViewById(R.id.currentCurrencySpinner);
        //Spinner finalCurrencySpinner = findViewById(R.id.finalCurrencySpinner);
        Button convertButton = findViewById(R.id.convertButton);
        Button clearButton2 = findViewById(R.id.clearButton2);
        //Button favoritesButton = findViewById(R.id.favoritesButton);
        ImageView swapButton = findViewById(R.id.swapButton);

        // Initialize spinners with currency options
        ArrayAdapter<CharSequence> currencyAdapter = ArrayAdapter.createFromResource(this, R.array.currencies_array, android.R.layout.simple_spinner_item);
        currencyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //currentCurrencySpinner.setAdapter(currencyAdapter);
        //finalCurrencySpinner.setAdapter(currencyAdapter);

//        currentCurrencySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
//                // Handle current currency selection
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parentView) {
//                // Do nothing
//            }
//        });
//
//        finalCurrencySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
//                // Handle final currency selection
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parentView) {
//                // Do nothing
//            }
//        });

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

//        favoritesButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // Open the FavoritesActivity
//            }
//        });

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

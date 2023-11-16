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
                clearFields();
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
                swapCurrencies(currencyOne, currencyTwo);
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

    private void setDefaultCurrencies(ArrayAdapter<CharSequence> adapter, Spinner currencyOne, Spinner currencyTwo) {
        CharSequence defaultCurrencyOne = "USD"; // Change this to your desired default currency
        CharSequence defaultCurrencyTwo = "EUR"; // Change this to your desired default currency

        // Find the position of the default currencies in the adapter
        int positionCurrencyOne = adapter.getPosition(defaultCurrencyOne);
        int positionCurrencyTwo = adapter.getPosition(defaultCurrencyTwo);

        // Set the selection for each spinner
        currencyOne.setSelection(positionCurrencyOne);
        currencyTwo.setSelection(positionCurrencyTwo);
    }

    private void convertCurrency() {
        // Get the amount to be converted from the EditText
        EditText amountEditText = findViewById(R.id.firstCurrency);
        String amountString = amountEditText.getText().toString();

//        if (amountString.isEmpty()) {
//            // Show an error message if the amount is not entered
//            Toast.makeText(MainActivity.this, "Please enter the amount to convert", Toast.LENGTH_SHORT).show();
//            return;
//        }

        double amountToConvert = Double.parseDouble(amountString);

        // Get the selected currencies from the Spinners
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

        // Perform the currency conversion (replace with your actual conversion logic)
        double result = amountToConvert * 2.0;

        // Display the result in a TextView or any other UI element
        TextView resultTextView = findViewById(R.id.resultCurrency);
        resultTextView.setText(String.format(Locale.getDefault(), "%.2f", result));
    }

    private void clearFields() {
        // Clear input fields and result
        EditText amountEditText = findViewById(R.id.firstCurrency);
        Spinner currencyOne = findViewById(R.id.currencyOne);
        Spinner currencyTwo = findViewById(R.id.currencyTwo);
        TextView resultTextView = findViewById(R.id.resultCurrency);

        amountEditText.setText("");
        currencyOne.setSelection(0); // Set it to the first item in the list
        currencyTwo.setSelection(0);
        resultTextView.setText("");
    }

    private void swapCurrencies(Spinner currencyOne, Spinner currencyTwo) {
        // Swap the selected currencies in the Spinners
        int selectedCurrencyOne = currencyOne.getSelectedItemPosition();
        int selectedCurrencyTwo = currencyTwo.getSelectedItemPosition();

        currencyOne.setSelection(selectedCurrencyTwo);
        currencyTwo.setSelection(selectedCurrencyOne);
    }
}

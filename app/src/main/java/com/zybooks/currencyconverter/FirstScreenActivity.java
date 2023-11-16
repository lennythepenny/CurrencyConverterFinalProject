package com.zybooks.currencyconverter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class FirstScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_screen);

        Button startMainActivityButton = findViewById(R.id.startButton);

        startMainActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create an Intent to start MainActivity
                Intent intent = new Intent(FirstScreenActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
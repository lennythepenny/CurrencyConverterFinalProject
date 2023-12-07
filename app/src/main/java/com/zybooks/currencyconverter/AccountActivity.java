package com.zybooks.currencyconverter;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
/*
* Add functionality for the account section, maybe a dialog box that pops up when you click on the account
* */
public class AccountActivity extends AppCompatActivity {
    private ArrayList<String> favoritesList;
    private TextView textViewName;
    private TextView textViewEmail;
    private TextView textViewAccountType;
    private TextView textViewChangePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        textViewName = findViewById(R.id.textViewName);
        textViewEmail = findViewById(R.id.textViewEmail);
        textViewAccountType = findViewById(R.id.textViewAccountType);
        textViewChangePassword = findViewById(R.id.textViewChangePassword);

        textViewName.setText("John Doe");
        textViewEmail.setText("john.doe@example.com");
        textViewAccountType.setText("Premium");
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavBar);

        bottomNavigationView.setOnItemSelectedListener(
                new BottomNavigationView.OnItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull android.view.MenuItem item) {
                        int itemId = item.getItemId();
                        if (itemId == R.id.action_home) {
                            startActivity(new Intent(AccountActivity.this, MainActivity.class));
                            return true;
                        } else if (itemId == R.id.action_favorites) {
                            Intent favoritesIntent = new Intent(AccountActivity.this, FavoritesActivity.class);
                            favoritesIntent.putStringArrayListExtra("favoritesList", favoritesList);
                            startActivity(favoritesIntent);
                            return true;
                        } else if (itemId == R.id.action_account_circle) {
                            //Do nothing we are in account activity
                            return true;
                        } else if (itemId == R.id.action_settings) {
                            startActivity(new Intent(AccountActivity.this, SettingsActivity.class));
                            return true;
                        }
                        return false;
                    }
                });

        textViewAccountType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAccountTypeDialog();
            }
        });

        textViewName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChangeNameDialog();
            }
        });
        textViewEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChangeEmailDialog();
            }
        });
        textViewChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChangePasswordDialog();
            }
        });
    }
    private void showAccountTypeDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_account_type, null);
        builder.setView(dialogView);

        Spinner accountTypeSpinner = dialogView.findViewById(R.id.accountTypeSpinner);
        Button btnConfirmAccountType = dialogView.findViewById(R.id.btnConfirmAccountType);
        Button btnCancelAccountType = dialogView.findViewById(R.id.btnCancelAccountType);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.account_types_array,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        accountTypeSpinner.setAdapter(adapter);

        AlertDialog dialog = builder.create();

        btnConfirmAccountType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle confirmation logic here
                String selectedAccountType = accountTypeSpinner.getSelectedItem().toString();
                textViewAccountType.setText(selectedAccountType);
                dialog.dismiss();
            }
        });

        btnCancelAccountType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle cancellation logic here
                dialog.dismiss();
            }
        });

        dialog.show();
    }
    private void showChangeNameDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_change_name, null);
        builder.setView(dialogView);

        EditText editTextNewName = dialogView.findViewById(R.id.editTextNewName);
        Button btnConfirmChangeName = dialogView.findViewById(R.id.btnConfirmChangeName);
        Button btnCancelChangeName = dialogView.findViewById(R.id.btnCancelChangeName);

        AlertDialog dialog = builder.create();

        btnConfirmChangeName.setOnClickListener(v -> {
            // Handle confirmation logic here
            String newName = editTextNewName.getText().toString();
            textViewName.setText(newName);
            dialog.dismiss();
        });

        btnCancelChangeName.setOnClickListener(v -> {
            // Handle cancellation logic here
            dialog.dismiss();
        });

        dialog.show();
    }
    private void showChangeEmailDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_change_email, null);
        builder.setView(dialogView);

        EditText editTextNewEmail = dialogView.findViewById(R.id.editTextNewEmail);
        Button btnConfirmChangeEmail = dialogView.findViewById(R.id.btnConfirmChangeEmail);
        Button btnCancelChangeEmail = dialogView.findViewById(R.id.btnCancelChangeEmail);

        AlertDialog dialog = builder.create();

        btnConfirmChangeEmail.setOnClickListener(v -> {
            // Handle confirmation logic here
            String newEmail = editTextNewEmail.getText().toString();
            textViewEmail.setText(newEmail);
            dialog.dismiss();
        });

        btnCancelChangeEmail.setOnClickListener(v -> {
            // Handle cancellation logic here
            dialog.dismiss();
        });

        dialog.show();
    }
    private void showChangePasswordDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_change_password, null);
        builder.setView(dialogView);

        EditText editTextCurrentPassword = dialogView.findViewById(R.id.editTextCurrentPassword);
        EditText editTextNewPassword = dialogView.findViewById(R.id.editTextNewPassword);
        EditText editTextConfirmNewPassword = dialogView.findViewById(R.id.editTextConfirmNewPassword);
        Button btnConfirmChangePassword = dialogView.findViewById(R.id.btnConfirmChangePassword);
        Button btnCancelChangePassword = dialogView.findViewById(R.id.btnCancelChangePassword);

        AlertDialog dialog = builder.create();

        btnConfirmChangePassword.setOnClickListener(v -> {
            // Handle confirmation logic here
            String currentPassword = editTextCurrentPassword.getText().toString();
            String newPassword = editTextNewPassword.getText().toString();
            String confirmNewPassword = editTextConfirmNewPassword.getText().toString();

            // Perform password change logic (e.g., validate passwords, update database)
            // For demonstration purposes, let's assume validation passes, and the password is updated.

            // Close the dialog
            dialog.dismiss();
        });

        btnCancelChangePassword.setOnClickListener(v -> {
            // Handle cancellation logic here
            dialog.dismiss();
        });

        dialog.show();
    }

}

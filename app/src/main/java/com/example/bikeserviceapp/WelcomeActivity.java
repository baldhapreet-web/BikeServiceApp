package com.example.bikeserviceapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;

public class WelcomeActivity extends AppCompatActivity {

    MaterialButton loginBtn, registerBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences prefs =
                getSharedPreferences("APP_PREF", MODE_PRIVATE);

        boolean firstTime = prefs.getBoolean("FIRST_TIME", true);

        // ✅ Already logged in → go to Menu
        if(FirebaseAuth.getInstance().getCurrentUser()!=null){
            startActivity(new Intent(this, MenuActivity.class));
            finish();
            return;
        }

        // ✅ Not first time → skip welcome → go login
        if(!firstTime){
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }

        // FIRST TIME USER → SHOW WELCOME SCREEN
        setContentView(R.layout.activity_welcome);

        loginBtn = findViewById(R.id.loginBtn);
        registerBtn = findViewById(R.id.registerBtn);

        loginBtn.setOnClickListener(v ->
                startActivity(new Intent(this, LoginActivity.class)));

        registerBtn.setOnClickListener(v ->
                startActivity(new Intent(this, RegisterActivity.class)));

        // Save that first launch completed
        prefs.edit().putBoolean("FIRST_TIME", false).apply();
    }
}
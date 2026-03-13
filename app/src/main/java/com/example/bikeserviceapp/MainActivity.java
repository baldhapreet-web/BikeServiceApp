package com.example.bikeserviceapp;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Redirect to WelcomeActivity which handles routing logic
        startActivity(new Intent(this, WelcomeActivity.class));
        finish();
    }
}
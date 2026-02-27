package com.example.bikeserviceapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        findViewById(R.id.serviceBtn).setOnClickListener(v ->
                startActivity(new Intent(this, BookServiceActivity.class)));

        findViewById(R.id.profileBtn).setOnClickListener(v ->
                startActivity(new Intent(this, ProfileActivity.class)));

        findViewById(R.id.bookingBtn).setOnClickListener(v ->
                startActivity(new Intent(this, ViewBookingsActivity.class)));

        findViewById(R.id.deliveryBtn).setOnClickListener(v ->
                startActivity(new Intent(this, DeliveryActivity.class)));

        findViewById(R.id.reviewBtn).setOnClickListener(v ->
                startActivity(new Intent(this, ReviewActivity.class)));

        findViewById(R.id.contactBtn).setOnClickListener(v ->
               startActivity(new Intent(this, ContactActivity.class)));


        findViewById(R.id.logoutBtn).setOnClickListener(v -> {

            // Sign out from Firebase
            FirebaseAuth.getInstance().signOut();

            // Go back to Login screen
            Intent intent = new Intent(MenuActivity.this, LoginActivity.class);

            // Clear all previous activities
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

            startActivity(intent);
        });
    }
}
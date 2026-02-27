package com.example.bikeserviceapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.firebase.auth.FirebaseAuth;

public class AdminActivity extends AppCompatActivity {

    // CardViews (menu items)
    CardView requestBtn, deliveryBtn, historyBtn;
    CardView reviewBtn, contactBtn;

    // Logout button (MaterialButton or Button)
    Button logoutBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        // Match XML types EXACTLY
        requestBtn = findViewById(R.id.requestBtn);
        deliveryBtn = findViewById(R.id.deliveryBtn);
        historyBtn = findViewById(R.id.historyBtn);
        reviewBtn = findViewById(R.id.reviewBtn);
        contactBtn = findViewById(R.id.contactBtn);
        logoutBtn = findViewById(R.id.logoutBtn);

        requestBtn.setOnClickListener(v ->
                startActivity(new Intent(this, AdminBookingActivity.class)));

        deliveryBtn.setOnClickListener(v ->
                startActivity(new Intent(this, AdminDeliveryActivity.class)));

        historyBtn.setOnClickListener(v ->
                startActivity(new Intent(this, AdminHistoryActivity.class)));

        reviewBtn.setOnClickListener(v ->
                startActivity(new Intent(this, AdminReviewActivity.class)));

        contactBtn.setOnClickListener(v ->
                startActivity(new Intent(this, AdminContactActivity.class)));

        logoutBtn.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });
    }
}
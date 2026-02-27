package com.example.bikeserviceapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.*;

public class ProfileActivity extends AppCompatActivity {

    EditText name, phone, address, bikeBrand, bikeModel, bikeNumber;
    MaterialButton saveBtn;

    DatabaseReference ref;
    FirebaseAuth auth;

    boolean profileExists = false; // check first time profile

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // UI LINK
        name = findViewById(R.id.name);
        phone = findViewById(R.id.phone);
        address = findViewById(R.id.address);
        bikeBrand = findViewById(R.id.bikeBrand);
        bikeModel = findViewById(R.id.bikeModel);
        bikeNumber = findViewById(R.id.bikeNumber);
        saveBtn = findViewById(R.id.saveBtn);

        // Firebase setup
        auth = FirebaseAuth.getInstance();
        String userId = auth.getCurrentUser().getUid();

        ref = FirebaseDatabase.getInstance()
                .getReference("users")
                .child(userId);

        // ===============================
        // LOAD PROFILE IF EXISTS
        // ===============================
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                if(snapshot.exists()){
                    profileExists = true;

                    User user = snapshot.getValue(User.class);

                    if(user != null){
                        name.setText(user.name);
                        phone.setText(user.phone);
                        address.setText(user.address);
                        bikeBrand.setText(user.bikeBrand);
                        bikeModel.setText(user.bikeModel);
                        bikeNumber.setText(user.bikeNumber);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {}
        });

        // ===============================
        // SAVE PROFILE BUTTON
        // ===============================
        saveBtn.setOnClickListener(v -> {

            String userName = name.getText().toString().trim();
            String userPhone = phone.getText().toString().trim();
            String userAddress = address.getText().toString().trim();

            // REQUIRED FIELD VALIDATION
            if(userName.isEmpty()){
                name.setError("Name is required");
                name.requestFocus();
                return;
            }

            if(userPhone.isEmpty()){
                phone.setError("Phone number is required");
                phone.requestFocus();
                return;
            }

            if(userAddress.isEmpty()){
                address.setError("Address is required");
                address.requestFocus();
                return;
            }

            // CREATE USER OBJECT
            User user = new User(
                    userName,
                    userPhone,
                    userAddress,
                    bikeBrand.getText().toString(),
                    bikeModel.getText().toString(),
                    bikeNumber.getText().toString()
            );

            // SAVE TO FIREBASE
            ref.setValue(user).addOnSuccessListener(unused -> {

                Toast.makeText(ProfileActivity.this,
                        "Profile Saved Successfully",
                        Toast.LENGTH_SHORT).show();

                // FIRST TIME → GO TO MENU
                if(!profileExists){
                    startActivity(new Intent(ProfileActivity.this,
                            MenuActivity.class));
                    finish();
                }
            });
        });
    }

    // ===============================
    // DISABLE BACK BUTTON FIRST TIME
    // ===============================
    @Override
    public void onBackPressed() {
        if(profileExists){
            super.onBackPressed();
        } else {
            Toast.makeText(this,
                    "Please complete your profile first",
                    Toast.LENGTH_SHORT).show();
        }
    }
}
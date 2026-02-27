package com.example.bikeserviceapp;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.*;

public class ContactActivity extends AppCompatActivity {

    TextView phoneText, emailText, addressText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        phoneText = findViewById(R.id.phoneText);
        emailText = findViewById(R.id.emailText);
        addressText = findViewById(R.id.addressText);

        DatabaseReference ref =
                FirebaseDatabase.getInstance()
                        .getReference("contact");

        ref.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot snapshot) {

                Contact contact = snapshot.getValue(Contact.class);

                if(contact != null){
                    phoneText.setText("Phone: " + contact.phone);
                    emailText.setText("Email: " + contact.email);
                    addressText.setText("Address: " + contact.address);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {}
        });
    }
}
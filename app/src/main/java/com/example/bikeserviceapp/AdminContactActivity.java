package com.example.bikeserviceapp;

import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.*;

public class AdminContactActivity extends AppCompatActivity {

    EditText phone,email,address;
    MaterialButton saveBtn;

    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_contact);

        phone = findViewById(R.id.phone);
        email = findViewById(R.id.email);
        address = findViewById(R.id.address);
        saveBtn = findViewById(R.id.saveBtn);

        ref = FirebaseDatabase.getInstance()
                .getReference("contact");

        // Load existing data
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            public void onDataChange(DataSnapshot snapshot) {

                Contact c = snapshot.getValue(Contact.class);

                if(c != null){
                    phone.setText(c.phone);
                    email.setText(c.email);
                    address.setText(c.address);
                }
            }

            public void onCancelled(DatabaseError error){}
        });

        saveBtn.setOnClickListener(v -> {

            Contact contact = new Contact(
                    phone.getText().toString(),
                    email.getText().toString(),
                    address.getText().toString(),
                    phone.getText().toString()
            );

            ref.setValue(contact);

            Toast.makeText(this,"Updated Successfully",
                    Toast.LENGTH_SHORT).show();
        });
    }
}
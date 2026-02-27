package com.example.bikeserviceapp;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.*;

public class DeliveryActivity extends AppCompatActivity {

    TextView bikeText, serviceText, dateText, timeText, statusText;

    DatabaseReference ref;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery);

        bikeText = findViewById(R.id.bikeText);
        serviceText = findViewById(R.id.serviceText);
        dateText = findViewById(R.id.dateText);
        timeText = findViewById(R.id.timeText);
        statusText = findViewById(R.id.statusText);

        userId = FirebaseAuth.getInstance()
                .getCurrentUser().getUid();

        ref = FirebaseDatabase.getInstance()
                .getReference("bookings");

        loadDelivery();
    }

    private void loadDelivery(){

        ref.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot snapshot) {

                boolean found = false;

                for(DataSnapshot data : snapshot.getChildren()){

                    Booking booking = data.getValue(Booking.class);

                    if(booking != null &&
                            booking.userId != null &&
                            booking.userId.equals(userId) &&
                            "In Progress".equals(booking.status)){

                        found = true;

                        bikeText.setText("Bike: " + booking.bikeModel);
                        serviceText.setText("Service: " + booking.serviceType);

                        dateText.setText("Delivery: "
                                + (booking.deliveryTime == null ? "-" : booking.deliveryTime));

                        timeText.setText("");

                        statusText.setText("Status: In Progress");

                        break; // show latest only
                    }
                }

                if(!found){
                    bikeText.setText("No Active Delivery");
                    serviceText.setText("");
                    dateText.setText("");
                    timeText.setText("");
                    statusText.setText("");
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {}
        });
    }
}
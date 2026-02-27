package com.example.bikeserviceapp;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.*;
import com.google.firebase.database.*;
import java.util.*;

public class AdminDeliveryActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<Booking> list;
    AdminAdapter adapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_delivery);

        recyclerView = findViewById(R.id.deliveryRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        adapter = new AdminAdapter(this,list,"Scheduled");
        recyclerView.setAdapter(adapter);

        DatabaseReference ref =
                FirebaseDatabase.getInstance().getReference("bookings");

        ref.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot snapshot) {

                list.clear();

                for (DataSnapshot data : snapshot.getChildren()) {

                    Booking booking = data.getValue(Booking.class);

                    if (booking != null &&
                            booking.status != null &&
                            booking.status.trim().equals("In Progress")) {

                        booking.bookingId = data.getKey();
                        list.add(booking);
                    }
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error) {}
        });
    }
}
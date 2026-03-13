package com.example.bikeserviceapp;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewBookingsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<Booking> list;
    BookingAdapter adapter;
    DatabaseReference ref;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_bookings);

        recyclerView = findViewById(R.id.recyclerBookings);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        adapter = new BookingAdapter(this, list);
        recyclerView.setAdapter(adapter);

        auth = FirebaseAuth.getInstance();
        ref = FirebaseDatabase.getInstance().getReference("bookings");

        if (auth.getCurrentUser() == null) {
            finish();
            return;
        }

        String userId = auth.getCurrentUser().getUid();

        ref.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot data : snapshot.getChildren()) {
                    Booking booking = data.getValue(Booking.class);
                    if (booking != null) {
                        booking.bookingId = data.getKey();
                        // ✅ SHOW ALL USER'S BOOKINGS
                        if (userId.equals(booking.userId)) {
                            list.add(booking);
                        }
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }
}
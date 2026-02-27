package com.example.bikeserviceapp;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.*;
import com.google.firebase.database.*;
import java.util.*;

public class AdminBookingActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<Booking> list;
    AdminAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_booking);

        recyclerView = findViewById(R.id.adminRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        adapter = new AdminAdapter(this,list,"Pending");
        recyclerView.setAdapter(adapter);

        DatabaseReference ref =
                FirebaseDatabase.getInstance().getReference("bookings");

        ref.addValueEventListener(new ValueEventListener() {
            public void onDataChange(DataSnapshot snapshot) {

                list.clear();

                for(DataSnapshot data : snapshot.getChildren()){
                    Booking b = data.getValue(Booking.class);
                    b.bookingId = data.getKey();

                    if("Pending".equals(b.status))
                        list.add(b);
                }

                adapter.notifyDataSetChanged();
            }

            public void onCancelled(DatabaseError error){}
        });
    }
}
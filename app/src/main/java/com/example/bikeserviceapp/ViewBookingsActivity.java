package com.example.bikeserviceapp;

import android.os.Bundle;
import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
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
        adapter = new BookingAdapter(this,list);
        recyclerView.setAdapter(adapter);

        auth = FirebaseAuth.getInstance();
        ref = FirebaseDatabase.getInstance().getReference("bookings");

        String currentUser = auth.getCurrentUser().getUid();

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                list.clear();

                String userId = FirebaseAuth.getInstance()
                        .getCurrentUser().getUid();

                for(DataSnapshot data : snapshot.getChildren()){

                    Booking booking = data.getValue(Booking.class);
                    booking.bookingId = data.getKey();

                    // ✅ SHOW ONLY COMPLETED SERVICES
                    if(booking.userId.equals(userId)
                            && "Completed".equals(booking.status)){

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
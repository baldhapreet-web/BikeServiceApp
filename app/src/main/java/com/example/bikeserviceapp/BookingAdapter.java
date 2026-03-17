package com.example.bikeserviceapp;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.ViewHolder> {

    private final Context context;
    private final ArrayList<Booking> list;
    private static final String ADMIN_EMAIL = "baldhapreet@gmail.com";

    public BookingAdapter(Context context, ArrayList<Booking> list) {
        this.context = context;
        this.list = list;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView bike, service, address, status, pickupTime;
        MaterialButton updateBtn;

        public ViewHolder(View itemView) {
            super(itemView);
            bike = itemView.findViewById(R.id.tvBike);
            service = itemView.findViewById(R.id.tvService);
            address = itemView.findViewById(R.id.tvAddress);
            status = itemView.findViewById(R.id.tvStatus);
            pickupTime = itemView.findViewById(R.id.tvPickupTime);
            updateBtn = itemView.findViewById(R.id.btnUpdate);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.booking_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Booking booking = list.get(position);

        holder.bike.setText(booking.bikeModel);
        holder.service.setText(booking.serviceType);
        
        // Use string resources with placeholders to avoid concatenation warnings
        holder.address.setText(context.getString(R.string.label_pickup_from, booking.address));
        holder.pickupTime.setText(context.getString(R.string.label_scheduled, booking.pickupTime));
        
        if (booking.status != null) {
            holder.status.setText(booking.status.toUpperCase());

            // Status Styling
            if ("Pending".equalsIgnoreCase(booking.status)) {
                holder.status.setTextColor(Color.parseColor("#E65100")); // Orange
            } else if ("In Progress".equalsIgnoreCase(booking.status)) {
                holder.status.setTextColor(Color.parseColor("#1976D2")); // Blue
            } else if ("Completed".equalsIgnoreCase(booking.status)) {
                holder.status.setTextColor(Color.parseColor("#388E3C")); // Green
            }
        }

        // Admin Action Visibility with NPE check
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        String userEmail = (currentUser != null) ? currentUser.getEmail() : null;
        
        if (userEmail != null && userEmail.equalsIgnoreCase(ADMIN_EMAIL)) {
            holder.updateBtn.setVisibility(View.VISIBLE);
        } else {
            holder.updateBtn.setVisibility(View.GONE);
        }

        holder.updateBtn.setOnClickListener(v -> {
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("bookings");
            ref.child(booking.bookingId).child("status").setValue("Completed")
                    .addOnSuccessListener(unused -> Toast.makeText(context, "Marked as Completed", Toast.LENGTH_SHORT).show());
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
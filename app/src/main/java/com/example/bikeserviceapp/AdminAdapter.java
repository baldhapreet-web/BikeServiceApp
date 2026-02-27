package com.example.bikeserviceapp;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class AdminAdapter extends RecyclerView.Adapter<AdminAdapter.ViewHolder> {

    Context context;
    List<Booking> bookingList;

    public AdminAdapter(Context context, List<Booking> bookingList, String pending) {
        this.context = context;
        this.bookingList = bookingList;
    }

    // ================= VIEW HOLDER =================
    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView bikeText, serviceText, addressText,
                phoneText, pickupText, deliveryText, statusText;

        MaterialButton deliveryBtn, completeBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            bikeText = itemView.findViewById(R.id.bikeText);
            serviceText = itemView.findViewById(R.id.serviceText);
            addressText = itemView.findViewById(R.id.addressText);
            phoneText = itemView.findViewById(R.id.phoneText);
            pickupText = itemView.findViewById(R.id.pickupText);
            deliveryText = itemView.findViewById(R.id.deliveryText);
            statusText = itemView.findViewById(R.id.statusText);

            deliveryBtn = itemView.findViewById(R.id.deliveryBtn);
            completeBtn = itemView.findViewById(R.id.completeBtn);
        }
    }

    // ================= CREATE VIEW =================
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context)
                .inflate(R.layout.admin_booking_item, parent, false);

        return new ViewHolder(view);
    }

    // ================= BIND DATA =================
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Booking b = bookingList.get(position);

        // BASIC DETAILS
        holder.bikeText.setText("Bike: " + b.bikeModel);
        holder.serviceText.setText("Service: " + b.serviceType);
        holder.addressText.setText("Address: " + b.address);
        holder.phoneText.setText("Mobile: " + b.phone);

        holder.pickupText.setText(
                "Pickup: " + (b.pickupTime == null ? "-" : b.pickupTime));

        holder.deliveryText.setText(
                "Delivery: " + (b.deliveryTime == null ? "-" : b.deliveryTime));

        holder.statusText.setText("Status: " + b.status);

        // ================= BUTTON VISIBILITY CONTROL =================

        if ("Pending".equals(b.status)) {

            // Booking Request Screen
            holder.deliveryBtn.setVisibility(View.VISIBLE);
            holder.completeBtn.setVisibility(View.GONE);

        }
        else if ("In Progress".equals(b.status)) {

            // Delivery Management Screen
            holder.deliveryBtn.setVisibility(View.GONE);
            holder.completeBtn.setVisibility(View.VISIBLE);

        }
        else if ("Completed".equals(b.status)) {

            // Service History Screen
            holder.deliveryBtn.setVisibility(View.GONE);
            holder.completeBtn.setVisibility(View.GONE);
        }

        // ================= STATUS COLOR =================
        if ("Pending".equals(b.status)) {
            holder.statusText.setTextColor(Color.parseColor("#FFA500")); // Orange
        }
        else if ("In Progress".equals(b.status)) {
            holder.statusText.setTextColor(Color.BLUE);
        }
        else if ("Completed".equals(b.status)) {
            holder.statusText.setTextColor(Color.GREEN);
        }

        // ================= SET DELIVERY =================
        holder.deliveryBtn.setOnClickListener(v -> {

            EditText input = new EditText(context);
            input.setHint("Enter delivery date & time");

            new AlertDialog.Builder(context)
                    .setTitle("Set Delivery Time")
                    .setView(input)
                    .setPositiveButton("Save", (dialog, which) -> {

                        String delivery = input.getText().toString().trim();

                        FirebaseDatabase.getInstance()
                                .getReference("bookings")
                                .child(b.bookingId)
                                .child("deliveryTime")
                                .setValue(delivery);

                        FirebaseDatabase.getInstance()
                                .getReference("bookings")
                                .child(b.bookingId)
                                .child("status")
                                .setValue("In Progress");
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        });

        // ================= MARK COMPLETE =================
        holder.completeBtn.setOnClickListener(v -> {

            FirebaseDatabase.getInstance()
                    .getReference("bookings")
                    .child(b.bookingId)
                    .child("status")
                    .setValue("Completed");

            Toast.makeText(context,
                    "Service Completed",
                    Toast.LENGTH_SHORT).show();
        });
    }

    // ================= ITEM COUNT =================
    @Override
    public int getItemCount() {
        return bookingList.size();
    }
}
package com.example.bikeserviceapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Button;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class BookingAdapter
        extends RecyclerView.Adapter<BookingAdapter.ViewHolder> {

    Context context;
    ArrayList<Booking> list;

    public BookingAdapter(Context context, ArrayList<Booking> list){
        this.context = context;
        this.list = list;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView bike, service, address, status;
        MaterialButton updateBtn;

        public ViewHolder(View itemView){
            super(itemView);

            bike = itemView.findViewById(R.id.tvBike);
            service = itemView.findViewById(R.id.tvService);
            address = itemView.findViewById(R.id.tvAddress);
            status = itemView.findViewById(R.id.tvStatus);
            updateBtn = itemView.findViewById(R.id.btnUpdate);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,int viewType){
        View view = LayoutInflater.from(context)
                .inflate(R.layout.booking_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder,int position){

        Booking booking = list.get(position);

        holder.bike.setText("Bike: " + booking.bikeModel);
        holder.service.setText("Service: " + booking.serviceType);
        holder.address.setText("Address: " + booking.address);
        holder.status.setText("Status: " + booking.status);

        // ===== ADMIN CHECK =====
        String currentUserEmail =
                FirebaseAuth.getInstance().getCurrentUser().getEmail();

        if(currentUserEmail != null &&
                currentUserEmail.equalsIgnoreCase("baldhapreet@gmail.com")) {

            // ADMIN → SHOW BUTTON
            holder.updateBtn.setVisibility(View.VISIBLE);

        } else {

            // NORMAL USER → HIDE BUTTON
            holder.updateBtn.setVisibility(View.GONE);
        }

        // ===== BUTTON CLICK =====
        holder.updateBtn.setOnClickListener(v -> {

            DatabaseReference ref =
                    FirebaseDatabase.getInstance()
                            .getReference("bookings");

            ref.child(booking.bookingId)
                    .child("status")
                    .setValue("Completed");
        });
    }

    @Override
    public int getItemCount(){
        return list.size();
    }
}
package com.example.bikeserviceapp;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class BookServiceActivity extends AppCompatActivity {

    EditText bikeModel, serviceType, address, phone, pickupTime;
    MaterialButton bookBtn;

    DatabaseReference bookingRef;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_service);

        // UI CONNECT
        bikeModel = findViewById(R.id.bikeModel);
        serviceType = findViewById(R.id.serviceType);
        address = findViewById(R.id.address);
        phone = findViewById(R.id.phone);
        pickupTime = findViewById(R.id.pickupTime);
        bookBtn = findViewById(R.id.bookBtn);

        // FIREBASE
        bookingRef = FirebaseDatabase.getInstance()
                .getReference("bookings");

        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            Toast.makeText(this, "User not logged in",
                    Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        userId = FirebaseAuth.getInstance()
                .getCurrentUser().getUid();

        // PICKUP DATE + TIME PICKER
        pickupTime.setOnClickListener(v -> openDateTimePicker());

        // BOOK SERVICE BUTTON
        bookBtn.setOnClickListener(v -> bookService());
    }

    // ===============================
    // DATE & TIME PICKER
    // ===============================
    private void openDateTimePicker() {

        Calendar calendar = Calendar.getInstance();

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog =
                new DatePickerDialog(this,
                        (view, y, m, d) -> {

                            int hour = calendar.get(Calendar.HOUR_OF_DAY);
                            int minute = calendar.get(Calendar.MINUTE);

                            TimePickerDialog timePickerDialog =
                                    new TimePickerDialog(this,
                                            (timeView, h, min) -> {

                                                String dateTime =
                                                        d + "/" + (m + 1) + "/" + y +
                                                                "  " + h + ":" + min;

                                                pickupTime.setText(dateTime);

                                            }, hour, minute, true);

                            timePickerDialog.show();

                        }, year, month, day);

        datePickerDialog.show();
    }

    // ===============================
    // BOOK SERVICE
    // ===============================
    private void bookService() {

        String bike = bikeModel.getText().toString().trim();
        String service = serviceType.getText().toString().trim();
        String addr = address.getText().toString().trim();
        String phoneNo = phone.getText().toString().trim();
        String pickup = pickupTime.getText().toString().trim();

        // VALIDATION
        if (bike.isEmpty() || service.isEmpty() ||
                addr.isEmpty() || phoneNo.isEmpty() || pickup.isEmpty()) {

            Toast.makeText(this,
                    "Please fill all fields",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        // CREATE BOOKING ID
        String bookingId = bookingRef.push().getKey();

        Booking booking = new Booking(
                bike,
                service,
                addr,
                phoneNo,
                pickup,
                "Pending",
                userId
        );

        booking.bookingId = bookingId;

        // SAVE TO FIREBASE
        bookingRef.child(bookingId)
                .setValue(booking)
                .addOnSuccessListener(unused -> {

                    Toast.makeText(this,
                            "Service Booked Successfully",
                            Toast.LENGTH_SHORT).show();

                    finish();
                });
    }
}
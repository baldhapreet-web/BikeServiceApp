package com.example.bikeserviceapp;

import android.os.Bundle;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.*;

public class ReviewActivity extends AppCompatActivity {

    TextView bikeText;
    RatingBar ratingBar;
    EditText comment;
    MaterialButton submitReview;

    DatabaseReference bookingRef, reviewRef, userRef;

    String userId;
    Booking completedBooking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        // UI CONNECT
        bikeText = findViewById(R.id.bikeText);
        ratingBar = findViewById(R.id.ratingBar);
        comment = findViewById(R.id.comment);
        submitReview = findViewById(R.id.submitReview);


        // CURRENT USER
        userId = FirebaseAuth.getInstance()
                .getCurrentUser().getUid();

        // FIREBASE REFERENCES
        bookingRef = FirebaseDatabase.getInstance()
                .getReference("bookings");

        reviewRef = FirebaseDatabase.getInstance()
                .getReference("reviews");

        userRef = FirebaseDatabase.getInstance()
                .getReference("users")
                .child(userId);

        // LOAD COMPLETED BOOKING
        loadCompletedBooking();

        // SUBMIT REVIEW BUTTON
        submitReview.setOnClickListener(v -> submitReview());
    }

    // ===============================
    // LOAD COMPLETED SERVICE
    // ===============================
    private void loadCompletedBooking() {

        bookingRef.addListenerForSingleValueEvent(
                new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot snapshot) {

                        for (DataSnapshot data : snapshot.getChildren()) {

                            Booking booking =
                                    data.getValue(Booking.class);

                            if (booking != null &&
                                    booking.userId.equals(userId) &&
                                    "Completed".equals(booking.status)) {

                                completedBooking = booking;
                                completedBooking.bookingId =
                                        data.getKey();

                                bikeText.setText(
                                        "Bike: " + booking.bikeModel);

                                return;
                            }
                        }

                        Toast.makeText(ReviewActivity.this,
                                "No completed service available for review",
                                Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {}
                });
    }

    // ===============================
    // SUBMIT REVIEW
    // ===============================
    private void submitReview() {

        if (completedBooking == null) {
            Toast.makeText(this,
                    "No completed booking found",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        float rating = ratingBar.getRating();
        String reviewText = comment.getText().toString().trim();

        if (rating == 0) {
            Toast.makeText(this,
                    "Please give rating",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        // GET USER NAME FROM PROFILE
        userRef.child("name").get()
                .addOnSuccessListener(snapshot -> {

                    String userName = snapshot.getValue(String.class);

                    if(userName == null)
                        userName = "User";

                    String reviewId = reviewRef.push().getKey();

                    Review review = new Review(
                            reviewId,
                            completedBooking.bookingId,
                            userId,
                            userName,
                            completedBooking.bikeModel,
                            rating,
                            reviewText
                    );

                    reviewRef.child(reviewId)
                            .setValue(review)
                            .addOnSuccessListener(unused -> {

                                Toast.makeText(this,
                                        "Review Submitted Successfully",
                                        Toast.LENGTH_SHORT).show();

                                finish();
                            });
                });
    }
}
package com.example.bikeserviceapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.*;

import com.google.firebase.database.*;

import java.util.ArrayList;

public class AdminReviewActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<Review> list;
    ReviewAdapter adapter;

    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_review);

        recyclerView = findViewById(R.id.reviewRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        adapter = new ReviewAdapter(this,list);
        recyclerView.setAdapter(adapter);

        ref = FirebaseDatabase.getInstance()
                .getReference("reviews");

        ref.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot snapshot) {

                list.clear();

                for(DataSnapshot data : snapshot.getChildren()){

                    Review review =
                            data.getValue(Review.class);

                    list.add(review);
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error){}
        });
    }
}
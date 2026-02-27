package com.example.bikeserviceapp;

import android.content.Context;
import android.view.*;
import android.widget.*;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ReviewAdapter
        extends RecyclerView.Adapter<ReviewAdapter.ViewHolder>{

    Context context;
    ArrayList<Review> list;

    public ReviewAdapter(Context c, ArrayList<Review> l){
        context = c;
        list = l;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView bikeText, commentText;
        TextView userNameText;
        RatingBar ratingBar;

        public ViewHolder(View v){
            super(v);

            bikeText = v.findViewById(R.id.bikeText);
            commentText = v.findViewById(R.id.commentText);
            ratingBar = v.findViewById(R.id.ratingBar);
            userNameText = v.findViewById(R.id.userNameText);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,int viewType){
        View view = LayoutInflater.from(context)
                .inflate(R.layout.review_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder h,int position){

        Review r = list.get(position);

        h.userNameText.setText("User: " + r.userName);
        h.bikeText.setText("Bike: " + r.bikeModel);
        h.commentText.setText("Review: " + r.comment);
        h.ratingBar.setRating(r.rating);
    }

    @Override
    public int getItemCount(){
        return list.size();
    }
}
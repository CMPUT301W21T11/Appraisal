package com.example.appraisal.UI.main_menu.specific_experiment_details;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appraisal.R;
import com.example.appraisal.backend.User;

import java.util.ArrayList;

import static java.lang.String.valueOf;

public class SpecificExpContributorsViewAdapter extends RecyclerView.Adapter<SpecificExpContributorsViewAdapter.SpecificExpContributorsViewHolder> {
    // This recyclerView idea and adapter layout is taken from Steven Heung's CMPUT 301 Assignment 1
    // Author: Steven Heung (CCID: syheung)

    private final ArrayList<User> contributors;

    public SpecificExpContributorsViewAdapter(ArrayList<User> contributors) {
        this.contributors = contributors;
    }

    @NonNull
    @Override
    public SpecificExpContributorsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // inflate the recycler view with custom layout
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.fragment_specific_exp_contributors_view_holder, parent, false);
        return new SpecificExpContributorsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SpecificExpContributorsViewHolder holder, int position) {
        // This is where we should pass to model and database. However for now this is some test code to get it running
        // test codes! Will not be in final version ==================//
        if (position == 0) {
            holder.setIs_owner();
        }

        holder.getUser_name().setText("User" + valueOf(position + 1));
        holder.getUser_icon().setImageResource(R.drawable.ic_launcher_foreground);
        //============================================================//
    }

    @Override
    public int getItemCount() {
        // return contributors.size();
        return 10; // testing codes
    }

    public static class SpecificExpContributorsViewHolder extends RecyclerView.ViewHolder {

        private final ImageView user_icon;
        private final TextView user_name;
        private final TextView is_owner;

        public SpecificExpContributorsViewHolder(@NonNull View itemView) {
            super(itemView);
            // initialize views in ViewHolder
            user_icon = itemView.findViewById(R.id.fragment_specific_exp_contributors_user_image);
            user_name = itemView.findViewById(R.id.fragment_specific_exp_contributors_user_name);
            is_owner = itemView.findViewById(R.id.fragment_specific_exp_contributors_isowner);

            is_owner.setVisibility(View.GONE); // set the visibility of "Owner" to be gone
        }

        public void setIs_owner() {
            // if the user is the owner set the "Owner" text to be visible
            is_owner.setVisibility(View.VISIBLE);
        }

        public ImageView getUser_icon() {
            return user_icon;
        }

        public TextView getUser_name() {
            return user_name;
        }
    }
}

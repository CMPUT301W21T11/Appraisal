package com.example.appraisal.UI.main_menu.specific_experiment_details;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appraisal.R;
import com.example.appraisal.UI.main_menu.user_profile.UserProfileActivity;
import com.example.appraisal.backend.user.User;
import com.example.appraisal.model.MainModel;
import com.example.appraisal.model.SpecificExpModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;

import static java.lang.String.valueOf;

/**
 * This is an adapter to show the all Users who have added a trial to the experiment (i.e contributed)
 */
public class SpecificExpContributorsViewAdapter extends RecyclerView.Adapter<SpecificExpContributorsViewAdapter.SpecificExpContributorsViewHolder> {
    // This recyclerView idea and adapter layout is taken from Steven Heung's CMPUT 301 Assignment 1
    // Author: Steven Heung (CCID: syheung)

    private ArrayList<String> experimenters;
    private final SpecificExpModel model;
    private Context context;
    private SharedPreferences pref;

    public SpecificExpContributorsViewAdapter(Context context, ArrayList<String> experimenters, SpecificExpModel model, SharedPreferences pref) {
        this.experimenters = experimenters;
        this.context = context;
        this.model = model;
        this.pref = pref;

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
        String name = experimenters.get(position);
        holder.getUser_name().setText("User @" + name.substring(0, 7));
        holder.getUser_icon().setImageResource(R.drawable.ic_launcher_foreground);

        holder.getExpLayout().setOnClickListener(v -> {
            Log.d("position clicked", String.valueOf(position));
            Intent intent = new Intent(context, UserProfileActivity.class);
            intent.putExtra("flag", "Other");
            intent.putExtra("experimenter", name);
            intent.putExtra("position", position);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        // get experimenters list from Shared Preferences
        Set<String> emptySet = Collections.emptySet();             // make default an empty set
        Set<String> set = pref.getStringSet("expKey", emptySet);
        experimenters = new ArrayList<String>(set);
        Log.d("ExperimenterListSize:", String.valueOf(experimenters.size()));

        // return size of list
        return experimenters.size();
    }

    public static class SpecificExpContributorsViewHolder extends RecyclerView.ViewHolder {

        private final ImageView user_icon;
        private final TextView user_name;
        private final TextView is_owner;
        private final ConstraintLayout expLayout;

        public SpecificExpContributorsViewHolder(@NonNull View itemView) {
            super(itemView);
            // initialize views in ViewHolder
            user_icon = itemView.findViewById(R.id.fragment_specific_exp_contributors_user_image);
            user_name = itemView.findViewById(R.id.fragment_specific_exp_contributors_user_name);
            is_owner = itemView.findViewById(R.id.fragment_specific_exp_contributors_isowner);
            expLayout = itemView.findViewById(R.id.experimenter_cards);

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

        public ConstraintLayout getExpLayout() {
            return expLayout;
        }
    }
}

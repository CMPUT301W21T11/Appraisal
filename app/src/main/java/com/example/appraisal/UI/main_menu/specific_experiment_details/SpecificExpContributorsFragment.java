package com.example.appraisal.UI.main_menu.specific_experiment_details;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appraisal.R;
import com.example.appraisal.backend.User;

import java.util.ArrayList;

public class SpecificExpContributorsFragment extends Fragment {
    private RecyclerView recyclerView;
    private SpecificExpContributorsViewAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // inflate fragment
        View view = inflater.inflate(R.layout.fragment_specific_exp_contributors, container, false);
        ArrayList<User> contributors = new ArrayList<>(); // filler code to get the adapter running

        // initialize recyclerView
        recyclerView = view.findViewById(R.id.fragment_specific_exp_contributors_recyclerView);
        adapter = new SpecificExpContributorsViewAdapter(contributors); // initialize adapter
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext())); // set layout manager to simply be LinearLayout
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator()); // Use default animation for now
        return view;
    }
}

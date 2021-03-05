package com.example.appraisal.UI;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.appraisal.R;
import com.example.appraisal.UI.SpecificExpFragments.SpecificExpViewAdapter;

public class SpecificExpFragment extends Fragment {

    // This tab view and view pager UI interface is taken from android developers documentation
    // Author: Google
    // URL: https://developer.android.com/guide/navigation/navigation-swipe-view-2#java

    private SpecificExpViewAdapter specific_exp_view_adapter;
    private ViewPager2 viewpager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_specific_exp, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle saveInstanceState) {
        specific_exp_view_adapter = new SpecificExpViewAdapter(this);
        viewpager = view.findViewById(R.id.specific_exp_pager);
        viewpager.setAdapter(specific_exp_view_adapter);
    }
}
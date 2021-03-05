package com.example.appraisal.UI;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.appraisal.R;
import com.example.appraisal.UI.SpecificExpFragments.SpecificExpViewAdapter;

public class SpecificExpActivity extends FragmentActivity {

    // This tab view and view pager UI interface is taken from android developers documentation
    // Author: Google
    // URL: https://developer.android.com/guide/navigation/navigation-swipe-view-2#java
    // URL2: https://developer.android.com/training/animation/screen-slide-2

    private SpecificExpViewAdapter specific_exp_view_adapter;
    private ViewPager2 viewpager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specific_exp);

        viewpager = (ViewPager2) findViewById(R.id.specific_exp_pager);
        specific_exp_view_adapter = new SpecificExpViewAdapter(this);
        viewpager.setAdapter(specific_exp_view_adapter);
    }
}
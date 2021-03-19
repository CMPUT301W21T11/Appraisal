package com.example.appraisal.UI.main_menu.specific_experiment_details;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.appraisal.R;
import com.example.appraisal.backend.experiment.Experiment;
import com.example.appraisal.model.MainModel;
import com.example.appraisal.model.SpecificExpModel;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;

/**
 * This is the UI class for Specific Experiment Activity
 */
public class SpecificExpActivity extends AppCompatActivity {

    // This tab view and view pager UI interface is taken from android developers documentation
    // Author: Google
    // URL: https://developer.android.com/guide/navigation/navigation-swipe-view-2#java
    // URL2: https://developer.android.com/training/animation/screen-slide-2

    private SpecificExpViewAdapter specific_exp_view_adapter;
    private ViewPager2 viewpager;
    private CheckBox subscribeCheckBox;

    // tab names
    private final String[] tab_names= {"DETAILS", "QR CODE", "ANALYSIS", "Participants"};

    /**
     * When called, create an instance of the Activity. Should only be called by the android framework
     * @param savedInstanceState bundle from previous activity
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        // inflate content and initialize adapter
        setContentView(R.layout.activity_specific_exp);
        viewpager = (ViewPager2) findViewById(R.id.specific_exp_pager);
        viewpager.setUserInputEnabled(false); // disable swiping so that user can zoom on graph. User can switch by taping on the tabs
        specific_exp_view_adapter = new SpecificExpViewAdapter(this, tab_names.length);
        viewpager.setAdapter(specific_exp_view_adapter);

        // initialize tabs and attach to this activity
        TabLayout tabLayout = findViewById(R.id.specific_exp_tab_layout);
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tabLayout, viewpager, (tab, position) -> tab.setText(tab_names[position]));
        tabLayoutMediator.attach();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
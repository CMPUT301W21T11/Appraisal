package com.example.appraisal.UI.main_menu.specific_experiment_details;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.appraisal.UI.main_menu.specific_experiment_details.analysis.SpecificExpDataAnalysisFragment;
import com.example.appraisal.UI.main_menu.specific_experiment_details.details.SpecificExpDetailsFragment;
import com.example.appraisal.UI.main_menu.specific_experiment_details.discussion.SpecificExpDiscussionFragment;
import com.example.appraisal.UI.main_menu.specific_experiment_details.participants.SpecificExpContributorsFragment;
import com.example.appraisal.UI.main_menu.specific_experiment_details.qr_scanner.SpecificExpQRCodeFragment;

/**
 * This class is an Adapter that holds all the fragments for {@link SpecificExpActivity}
 */
public class SpecificExpViewAdapter extends FragmentStateAdapter {
    // This tab view and view pager UI interface is taken from android developers documentation
    // Author: Google
    // URL: https://developer.android.com/guide/navigation/navigation-swipe-view-2#java
    // URL2: https://developer.android.com/training/animation/screen-slide-2

    private final int PAGE_COUNT;

    public SpecificExpViewAdapter(FragmentActivity fm, int PAGE_COUNT) {
        super(fm);
        this.PAGE_COUNT = PAGE_COUNT;
    }

    /**
     * This method is called when a new tab is selected
     *
     * @param position -- position of the selected tab
     * @return Fragment -- the fragment for the given position
     */
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        // Returns different fragment for different tabs
        Log.d("position", Integer.toString(position));
        switch (position) {
            case 0:
                return new SpecificExpDetailsFragment();
            case 1:
                return new SpecificExpQRCodeFragment();
            case 2:
                return new SpecificExpDataAnalysisFragment();
            case 3:
                return new SpecificExpContributorsFragment();
            case 4:
                return new SpecificExpDiscussionFragment();
            default:
                // This is a safety net so that if PAGE_NUM changed and the is not updated, the app won't crash
                return new Fragment();
        }
    }

    /**
     * This method is to determine how many pages are there
     *
     * @return int -- page count
     */
    @Override
    public int getItemCount() {
        return PAGE_COUNT;
    }
}

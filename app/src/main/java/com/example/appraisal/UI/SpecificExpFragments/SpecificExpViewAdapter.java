package com.example.appraisal.UI.SpecificExpFragments;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class SpecificExpViewAdapter extends FragmentStateAdapter {
    private final int PAGE_NUM = 4;
    public SpecificExpViewAdapter(FragmentActivity fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new SpecificExpDetailsFragment();
            case 1:
                return new SpecificExpQRCodeFragment();
            case 2:
                return new SpecificExpDataAnalysisFragment();
            case 3:
                return new SpecificExpContributorsFragment();
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return PAGE_NUM;
    }
}

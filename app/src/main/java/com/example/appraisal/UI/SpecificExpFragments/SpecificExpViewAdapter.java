package com.example.appraisal.UI.SpecificExpFragments;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class SpecificExpViewAdapter extends FragmentStateAdapter {
    public SpecificExpViewAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new SpecificExpDetailsFragment();
            case 1:
                return new SpecificExpDetailsFragment();
            case 2:
                return new SpecificExpDetailsFragment();
            case 3:
                return new SpecificExpDetailsFragment();
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}

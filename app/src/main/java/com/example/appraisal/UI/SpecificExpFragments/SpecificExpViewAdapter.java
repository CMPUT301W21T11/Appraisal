package com.example.appraisal.UI.SpecificExpFragments;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class SpecificExpViewAdapter extends FragmentStateAdapter {
  // This tab view and view pager UI interface is taken from android developers
  // documentation Author: Google URL:
  // https://developer.android.com/guide/navigation/navigation-swipe-view-2#java
  // URL2: https://developer.android.com/training/animation/screen-slide-2

  private final int PAGE_COUNT;

  public SpecificExpViewAdapter(FragmentActivity fm, int PAGE_COUNT) {
    super(fm);
    this.PAGE_COUNT = PAGE_COUNT;
  }

  @NonNull
  @Override
  public Fragment createFragment(int position) {
    // Returns different fragment for different tabs
    switch (position) {
    case 0:
      return new SpecificExpDetailsFragment();
    case 1:
      return new SpecificExpQRCodeFragment();
    case 2:
      return new SpecificExpDataAnalysisFragment();
    case 3:
      return new SpecificExpContributorsFragment();
    default:
      // This is a safety net so that if PAGE_NUM changed and the is not
      // updated, the app won't crash
      return new Fragment();
    }
  }

  @Override
  public int getItemCount() {
    return PAGE_COUNT;
  }
}

package com.example.appraisal.UI.SpecificExpFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.appraisal.R;

public class SpecificExpDetailsFragment extends Fragment {
    public static final String ARG_OBJECT = "exp_details";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.item1_view_pager, container, false);
    }
}

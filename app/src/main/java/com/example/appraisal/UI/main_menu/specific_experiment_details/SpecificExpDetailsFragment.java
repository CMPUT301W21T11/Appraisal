package com.example.appraisal.UI.main_menu.specific_experiment_details;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.appraisal.R;
import com.example.appraisal.backend.experiment.Experiment;
import com.example.appraisal.model.MainModel;
import com.example.appraisal.model.SpecificExpModel;

public class SpecificExpDetailsFragment extends Fragment {

    private SpecificExpModel model;
    private Experiment current_experiment;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_specific_exp_experiment_details, container, false);

        try {
            current_experiment = MainModel.getCurrentExperiment();
        } catch (Exception e) {
            e.printStackTrace();
        }

        TextView desc = v.findViewById(R.id.specific_exp_details_experiment_title);
        TextView type = v.findViewById(R.id.specific_exp_details_experiment_type);
        TextView status = v.findViewById(R.id.specific_exp_details_experiment_status);
        TextView geo_required = v.findViewById(R.id.specific_exp_details_geolocation_required);

        desc.setText(current_experiment.getDescription());
        type.setText(current_experiment.getType());
        if(current_experiment.getIs_ended()){
            status.setText("Ended");
        }
        else {
            status.setText("Open");
        }
        if (current_experiment.getIs_geolocation_required()){
            geo_required.setText("Yes");
        }
        else {
            geo_required.setText("No");
        }

        return v;
    }
}

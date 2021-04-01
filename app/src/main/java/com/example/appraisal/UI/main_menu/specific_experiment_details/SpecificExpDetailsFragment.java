package com.example.appraisal.UI.main_menu.specific_experiment_details;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.appraisal.R;
import com.example.appraisal.UI.geolocation.GeolocationActivity;
import com.example.appraisal.UI.trial.BinomialActivity;
import com.example.appraisal.UI.trial.CounterActivity;
import com.example.appraisal.UI.trial.MeasurementActivity;
import com.example.appraisal.UI.trial.NonNegIntCountActivity;
import com.example.appraisal.backend.experiment.Experiment;
import com.example.appraisal.model.MainModel;
import com.example.appraisal.model.SpecificExpModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;

import java.util.ArrayList;

/**
 * Fragment when viewing specific experiment details
 */
public class SpecificExpDetailsFragment extends Fragment {

    private SpecificExpModel model;
    private Experiment current_experiment;
    private DocumentReference user_ref;
    private ArrayList<String> user_subscriptions;
    private CheckBox subscriptionBox;
    private Button add_trial;
    private CollectionReference exp_ref;
    private Button view_trials;
    private Button plot_trials;


    /**
     * Gets called when the fragment gets created
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_specific_exp_experiment_details, container, false);


        subscriptionBox = (CheckBox) v.findViewById(R.id.specific_exp_details_subscribe_checkBox);
        add_trial = (Button) v.findViewById(R.id.specific_exp_details_add_trial_button);
        view_trials = (Button) v.findViewById(R.id.viewTrialBtn);
        plot_trials = (Button) v.findViewById(R.id.specific_exp_details_geolocation_map_button);
        add_trial.setOnClickListener(v1 -> addTrial());
        view_trials.setOnClickListener(v2 -> goToViewTrials());
        plot_trials.setOnClickListener(v3 -> plotAllTrialsOnMap());


        try {
            current_experiment = MainModel.getCurrentExperiment();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            exp_ref = MainModel.getExperimentReference();
        } catch (Exception e) {
            e.printStackTrace();
        }


        TextView desc = v.findViewById(R.id.specific_exp_details_experiment_title);
        TextView type = v.findViewById(R.id.specific_exp_details_experiment_type);
        TextView status = v.findViewById(R.id.specific_exp_details_experiment_status);
        TextView geo_required = v.findViewById(R.id.specific_exp_details_geolocation_required);

        desc.setText(current_experiment.getDescription());
        type.setText(current_experiment.getType());
        if(current_experiment.getIsEnded()){
            status.setText("Ended");
        }
        else {
            status.setText("Open");
        }
        if (current_experiment.getIsGeolocationRequired()){
            geo_required.setText("Yes");
        }
        else {
            geo_required.setText("No");
        }


        try {
            user_ref = MainModel.getUserReference();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Author: Google
        // Reference: https://firebase.google.com/docs/firestore/manage-data/add-data
        user_ref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        user_subscriptions = (ArrayList <String>) document.get("my_subscriptions");
 
                        if (user_subscriptions != null) {
                            if (user_subscriptions.contains(current_experiment.getExpId())) {
                                subscriptionBox.setChecked(true);
                            } else {
                                subscriptionBox.setChecked(false);
                            }
 
                        }
                    }
                }
            }
        });

        subscriptionBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked) {
                    user_ref.update("my_subscriptions", FieldValue.arrayUnion(current_experiment.getExpId()));
                }
                else {
                    user_ref.update("my_subscriptions", FieldValue.arrayRemove(current_experiment.getExpId()));
                }
            }
        });


        return v;
    }

    /**
     * Add Trial to an Experiment
     */
    private void addTrial() {
        String type = current_experiment.getType();
        try {
            MainModel.setCurrentExperiment(current_experiment);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Intent intent;
        if (type.equals("Binomial Trials")) {
            intent = new Intent(getActivity(), BinomialActivity.class);
            startActivity(intent);
        } else if (type.equals("Count-based trials")) {
            intent = new Intent(getActivity(), CounterActivity.class);
            startActivity(intent);
        } else if (type.equals("Measurement Trials")) {
            intent = new Intent(getActivity(), MeasurementActivity.class);
            startActivity(intent);
        } else if (type.equals("Non-negative Integer Trials")) {
            intent = new Intent(getActivity(), NonNegIntCountActivity.class);
            startActivity(intent);
        }
    }

    /**
     * View the trials
     */
    private void goToViewTrials() {
        Intent intent = new Intent(this.getActivity(),ViewTrialActivity.class);
        startActivity(intent);
    }

    // TODO: Go to Geolocation Activity with a bundle containing a flag
    private void plotAllTrialsOnMap(){
        Intent intent = new Intent(getActivity(), GeolocationActivity.class);
        intent.putExtra("Map Request Code", "Plot Trials Map");
        startActivity(intent);
    }
}

package com.example.appraisal.UI.main_menu.specific_experiment_details.details;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.appraisal.R;
import com.example.appraisal.UI.geolocation.GeolocationActivity;
import com.example.appraisal.UI.main_menu.specific_experiment_details.SpecificExpActivity;
import com.example.appraisal.UI.trial.BinomialActivity;
import com.example.appraisal.UI.trial.CounterActivity;
import com.example.appraisal.UI.trial.MeasurementActivity;
import com.example.appraisal.UI.trial.NonNegIntCountActivity;
import com.example.appraisal.backend.experiment.Experiment;
import com.example.appraisal.backend.trial.TrialType;
import com.example.appraisal.model.core.MainModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;

/**
 * Fragment when viewing specific experiment details
 */
public class SpecificExpDetailsFragment extends Fragment {

    private Experiment current_experiment;
    private DocumentReference user_ref;
    private ArrayList<String> user_subscriptions;
    private CheckBox subscriptionBox;
    private Button add_trial;
    private CollectionReference exp_ref;
    private Button plot_trials;
    private TextView is_open_logo_text;
    private TextView type_logo_text;
    private TextView geo_logo_text;
    private TextView current_count;

    /**
     * Gets called when the fragment gets created
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_specific_exp_details, container, false);


        subscriptionBox = (CheckBox) v.findViewById(R.id.specific_exp_details_subscribe_checkBox);
        add_trial = (Button) v.findViewById(R.id.specific_exp_details_add_trial_button);
        plot_trials = (Button) v.findViewById(R.id.specific_exp_details_geolocation_map_button);
        add_trial.setOnClickListener(v1 -> addTrial());
        plot_trials.setOnClickListener(v3 -> plotAllTrialsOnMap());

        is_open_logo_text = v.findViewById(R.id.is_open_logo_text);
        type_logo_text = v.findViewById(R.id.type_logo_text);
        geo_logo_text = v.findViewById(R.id.geo_logo_text);

        current_count = v.findViewById(R.id.specific_exp_details_current_trial_count);


        try {
            current_experiment = MainModel.getCurrentExperiment();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (!current_experiment.getIsGeolocationRequired()) {
            plot_trials.setVisibility(View.INVISIBLE);
        }

        try {
            exp_ref = MainModel.getExperimentReference();
        } catch (Exception e) {
            e.printStackTrace();
        }

        ((SpecificExpActivity) getActivity()).getSupportActionBar().setTitle(current_experiment.getDescription());

        TextView desc = v.findViewById(R.id.specific_exp_details_experiment_title);
        TextView owner = v.findViewById(R.id.specific_exp_details_owner);
        TextView rules_constraints = v.findViewById(R.id.specific_exp_details_rules_constraints);
        ImageView icon = v.findViewById(R.id.type_icon);

        if (current_experiment.getType().equals(TrialType.COUNT_TRIAL.getLabel())) {
            icon.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_count));
            type_logo_text.setText("Count-Trial");
        } else if (current_experiment.getType().equals(TrialType.BINOMIAL_TRIAL.getLabel())) {
            icon.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_coin));
            type_logo_text.setText("Binomial");
        } else if (current_experiment.getType().equals(TrialType.NON_NEG_INT_TRIAL.getLabel())) {
            icon.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_num));
            type_logo_text.setText("Non-Neg-Trial");
        } else {
            icon.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_thermometer_three_quarters_solid));
            type_logo_text.setText("Measurement");
        }
        icon.setOnClickListener((listener) -> madeToast());

        add_trial.setEnabled(!current_experiment.getIsEnded());

        ImageView ended_icon = v.findViewById(R.id.is_ended_icon);
        if (current_experiment.getIsEnded()) {
            ended_icon.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.cross));
            is_open_logo_text.setText("Ended");
        } else {
            ended_icon.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_check_circle_solid));
            is_open_logo_text.setText("Open");
        }

        desc.setText(current_experiment.getDescription());
        owner.setText(current_experiment.getOwner().substring(0, 7));

        TextView region = v.findViewById(R.id.specific_exp_details_region);
        if (!current_experiment.getRegion().equals("")) {
            region.setText(current_experiment.getRegion());
        } else {
            region.setText("N/A");
        }

        if (!current_experiment.getRules().equals("")) {
            rules_constraints.setText((current_experiment.getRules()));
        } else {
            rules_constraints.setText("N/A");
        }

        TextView min_trials = v.findViewById(R.id.specific_exp_details_min_trials);
        min_trials.setText(current_experiment.getMinimumTrials().toString());
        updateCount();

        ImageView geo_icon = v.findViewById(R.id.geo_icon);
        if (current_experiment.getIsGeolocationRequired()) {
            geo_icon.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_map_marker_alt_solid));
            geo_logo_text.setText("Geo-Required");
        } else {
            geo_icon.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_map_marker_crossed));
            geo_logo_text.setText("Non-Geo");
        }

        try {
            user_ref = MainModel.getUserReference();
        } catch (Exception e) {
            e.printStackTrace();
        }

        checkIfUserSubscribed();

        subscriptionBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    user_ref.update("mySubscriptions", FieldValue.arrayUnion(current_experiment.getExpId()));
                } else {
                    user_ref.update("mySubscriptions", FieldValue.arrayRemove(current_experiment.getExpId()));
                }
            }
        });

        return v;
    }


    /**
     * This method checks if the user is subscribed when on resume is called
     */
    @Override
    public void onResume() {
        super.onResume();
        checkIfUserSubscribed();
    }


    /**
     * Add Trial to an Experiment
     */
    private void addTrial() {
        TrialType type;
        try {
            type = TrialType.getInstance(current_experiment.getType());
            MainModel.setCurrentExperiment(current_experiment);
        } catch (IllegalArgumentException e) {
            Log.e("Error", "experiment type is invalid. Falling back.");
            e.printStackTrace();
            type = TrialType.MEASUREMENT_TRIAL;
        } catch (Exception e) {
            Log.e("Error:", e.toString());
            e.printStackTrace();
            type = TrialType.MEASUREMENT_TRIAL;
        }

        // Initialize intent
        Intent intent = new Intent(getActivity(), AppCompatActivity.class);

        // assign intent based on type
        switch (type) {
            case BINOMIAL_TRIAL:
                intent = new Intent(getActivity(), BinomialActivity.class);
                break;
            case COUNT_TRIAL:
                intent = new Intent(getActivity(), CounterActivity.class);
                break;
            case MEASUREMENT_TRIAL:
                intent = new Intent(getActivity(), MeasurementActivity.class);
                break;
            case NON_NEG_INT_TRIAL:
                intent = new Intent(getActivity(), NonNegIntCountActivity.class);
        }

        startActivity(intent);
    }

    /**
     * This method gets the updated number of trials from firebase
     */
    private void updateCount() {
        exp_ref.document(current_experiment.getExpId()).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                current_count.setText(value.getData().get("numOfTrials").toString());
            }
        });

    }

    /**
     * This method gets called when clicks on Map button
     */
    private void plotAllTrialsOnMap() {
        Intent intent = new Intent(getActivity(), GeolocationActivity.class);
        intent.putExtra("Map Request Code", "Plot Trials Map");
        intent.putExtra("Experiment Description", current_experiment.getDescription());
        startActivity(intent);
    }


    /**
     * This method check if user is subscribed or not and updates the subscription box accordingly
     */
    private void checkIfUserSubscribed() {
        // Author: Google
        // Reference: https://firebase.google.com/docs/firestore/manage-data/add-data
        user_ref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        user_subscriptions = (ArrayList<String>) document.get("mySubscriptions");

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
    }

    /**
     * Create toast when user click exp type icon
     */
    private void madeToast() {
        String type = current_experiment.getType().toString();

        Toast toast = Toast.makeText(getContext(),
                "This is a " + type,
                Toast.LENGTH_SHORT);

        toast.show();
    }
}

package com.example.appraisal.UI.trial;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.appraisal.R;
import com.example.appraisal.backend.experiment.Experiment;
import com.example.appraisal.model.MainModel;
import com.example.appraisal.model.trial.MeasurementModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class MeasurementActivity extends AppCompatActivity {
    private EditText input_measurement;
    private MeasurementModel model;
    private DecimalFormat dp3 = new DecimalFormat("#.##");

    /**
     * create the activity and inflate it with layout. initialize model
     * @param savedInstanceState
     *      bundle from the previous activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_measurement);

        input_measurement = findViewById(R.id.inputMeasurement);
        try {
            Experiment experiment = MainModel.getCurrentExperiment();
            model = new MeasurementModel(experiment);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Save measurement to experiment
     * @param view save button
     */
    public void addMeasurement(View view) {
        String input = input_measurement.getText().toString();
        model.addMeasurement(input);
        model.toExperiment();
        storeTrialInFireBase();
        finish();
    }

    public void storeTrialInFireBase() {
        Experiment current_exp = null;
        CollectionReference experiment_reference = null;

        try {
            experiment_reference = MainModel.getExperimentReference();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            current_exp = MainModel.getCurrentExperiment();
        } catch (Exception e) {
            e.printStackTrace();
        }


        String experiment_ID = current_exp.getExp_id();
        Integer num_of_trials = current_exp.getTrial_count() + 1;
        String name = "Trial" + num_of_trials;
        Map<String, Object> trial_info = new HashMap<>();
        trial_info.put("Result", dp3.format(model.getMeasurement()));
        // create new document for experiment with values from hash map
        experiment_reference.document(experiment_ID).collection("Trials").document(name).set(trial_info)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("***", "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("***", "Error writing document", e);
                    }
                });

        experiment_reference.document(experiment_ID).update("numOfTrials", num_of_trials);
        current_exp.setTrial_count(num_of_trials);
    }
}

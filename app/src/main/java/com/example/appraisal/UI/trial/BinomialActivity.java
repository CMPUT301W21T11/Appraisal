package com.example.appraisal.UI.trial;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appraisal.R;
import com.example.appraisal.backend.experiment.Experiment;
import com.example.appraisal.model.MainModel;
import com.example.appraisal.model.trial.BinomialModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;

import java.util.HashMap;
import java.util.Map;

/**
 * This is the activity for conducting binomial activity
 */
public class BinomialActivity extends AppCompatActivity {
    private BinomialModel model;

    /**
     * create the activity and inflate it with layout. initialize model
     * @param savedInstanceState
     *      bundle from the previous activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_binomial);

        Experiment current_experiment;
        try {
            current_experiment = MainModel.getCurrentExperiment();
            model = new BinomialModel(current_experiment);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Increase the success count of the trial
     * @param v increase button
     */
    public void incrementSuccess(View v){
        // adjust model
        model.addSuccess();
        storeTrialInFireBase(true);
        finish();
    }

    /**
     * Increase the failure count of the trial
     * @param v increase button
     */
    public void incrementFailure(View v){
        //adjust model
        model.addFailure();
        storeTrialInFireBase(false);
        finish();
    }


    public void storeTrialInFireBase(Boolean outcome) {
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
        if (outcome == true) {
            trial_info.put("Result", "Success");
        } else {
            trial_info.put("Result", "Failure");
        }

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

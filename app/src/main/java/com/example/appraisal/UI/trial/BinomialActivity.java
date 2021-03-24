package com.example.appraisal.UI.trial;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appraisal.R;
import com.example.appraisal.backend.experiment.Experiment;
import com.example.appraisal.backend.user.User;
import com.example.appraisal.model.MainModel;
import com.example.appraisal.model.trial.BinomialModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;

import java.util.HashMap;
import java.util.Map;

/**
 * This is the activity for conducting binomial activity
 */
public class BinomialActivity extends AppCompatActivity {
    private BinomialModel model;
    private Experiment current_exp;
    private CollectionReference experiment_reference;
    private int firebase_num_trials = 0;

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
            User conductor = MainModel.getCurrentUser();
            model = new BinomialModel(current_experiment, conductor);
        } catch (Exception e) {
            e.printStackTrace();
        }

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

        listenToNumOfTrials();


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
        addContributor();
        finish();
    }


    public void storeTrialInFireBase(Boolean outcome) {

        // save locally
        model.toExperiment();

        String experiment_ID = current_exp.getExpId();
        Integer num_of_trials = firebase_num_trials + 1;
        String name = "Trial" + num_of_trials;
        Map<String, Object> trial_info = new HashMap<>();
        if (outcome) {
            trial_info.put("result", "1"); // 1 indicates success
        } else {
            trial_info.put("result", "0"); // 0 indicates failure
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
        current_exp.setTrialCount(num_of_trials);
    }


    private void addContributor() {

        try {
            experiment_reference.document(current_exp.getExpId()).update("experimenters", FieldValue.arrayUnion(MainModel.getCurrentUser().getID()));
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    private void listenToNumOfTrials() {

        experiment_reference.document(current_exp.getExpId()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();

                    if (document.exists()) {
                        firebase_num_trials = Integer.valueOf(document.get("numOfTrials").toString());
                        Log.d("numtrials listener", String.valueOf(firebase_num_trials));
                    }

                }
            }
        });


    }
}

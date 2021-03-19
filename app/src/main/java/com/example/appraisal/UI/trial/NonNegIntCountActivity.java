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
import com.example.appraisal.model.trial.NonNegIntCountModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FieldValue;

import java.util.HashMap;
import java.util.Map;

public class NonNegIntCountActivity extends AppCompatActivity {

    private NonNegIntCountModel model;
    private EditText counter_view;
    private Experiment current_exp;
    private CollectionReference experiment_reference;

    /**
     * create the activity and inflate it with layout. initialize model
     * @param savedInstanceState
     *      bundle from the previous activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nonneg_count_layout);

        counter_view = findViewById(R.id.nonneg_count_input);
        try {
            Experiment experiment = MainModel.getCurrentExperiment();
            model = new NonNegIntCountModel(experiment);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * save to experiment
     * @param v save button
     */
    public void saveAndReturn(View v) {
        // get input
        String user_input = counter_view.getText().toString();

        // Adjust the model
        model.addIntCount(user_input);
        model.toExperiment();
        storeTrialInFireBase();
        addContributor();
        finish();
    }

    public void storeTrialInFireBase() {

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
        trial_info.put("Result", String.valueOf(model.getCount()));
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

    private void addContributor() {

        try {
            experiment_reference.document(current_exp.getExp_id()).update("experimenters", FieldValue.arrayUnion(MainModel.getCurrentUser().getID()));
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}

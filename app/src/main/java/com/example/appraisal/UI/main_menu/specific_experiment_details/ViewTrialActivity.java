package com.example.appraisal.UI.main_menu.specific_experiment_details;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appraisal.R;
import com.example.appraisal.backend.experiment.Experiment;
import com.example.appraisal.backend.specific_experiment.ViewTrial;
import com.example.appraisal.model.MainModel;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ViewTrialActivity extends AppCompatActivity {
    private ListView trial_display;
    private static ArrayAdapter<ViewTrial> adapter;
    private ArrayList<ViewTrial> trial_list;
    private CollectionReference exp_ref;
    private Experiment current_experiment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_trial);
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

        trial_display = findViewById(R.id.trialList);
        trial_list = new ArrayList<>();
        adapter = new ViewTrialAdapter(this, trial_list);
        getDbTrials();
        trial_display.setAdapter(adapter);
    }


    private void getDbTrials() {
        exp_ref.document(current_experiment.getExpId()).collection("Trials").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                // clear old list
                trial_list.clear();

                // check each experiment document
                for (QueryDocumentSnapshot doc : value) {

                    // get all the fields of the experiment
                    String trial_ID = doc.getId();
                    String outcome = doc.getData().get("Result").toString();
                    ViewTrial trial = new ViewTrial(trial_ID, outcome);

                    // add experiment to the list to display
                    trial_list.add(trial);


                }
                // notify adapter that data has change and to update the UI
                adapter.notifyDataSetChanged();
            }
        });
    }
}
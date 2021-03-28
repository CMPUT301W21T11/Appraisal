package com.example.appraisal.UI.main_menu.specific_experiment_details.details.trial_list;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appraisal.R;
import com.example.appraisal.UI.main_menu.user_profile.UserProfileActivity;
import com.example.appraisal.backend.experiment.Experiment;
import com.example.appraisal.backend.specific_experiment.ViewTrial;
import com.example.appraisal.model.core.MainModel;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

/**
 * Activity to display the Trials
 */
public class ViewTrialActivity extends AppCompatActivity {
    private ListView trial_display;
    private static ArrayAdapter<ViewTrial> adapter;
    private ArrayList<ViewTrial> trial_list;
    private CollectionReference exp_ref;
    private Experiment current_experiment;
    private Context context;
    private Intent previous_intent;
    private String experimenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_trial);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        context = this;
        previous_intent = getIntent();

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

        // set field of experimenter ID
        experimenter = previous_intent.getStringExtra("experimenter");
        TextView id = findViewById(R.id.view_trial_experimenter);
        id.setText(experimenter.substring(0, 7));

        // set functionality of View Profile button
        Button view_profile = findViewById(R.id.view_profile_button);
        view_profile.setOnClickListener(v -> viewProfile());

        trial_display = findViewById(R.id.trialList);
        trial_list = new ArrayList<>();
        adapter = new ViewTrialAdapter(this, trial_list);
        getDbTrials();
        trial_display.setAdapter(adapter);
    }

    /**
     * Queries the database to get the list of Trials
     */
    private void getDbTrials() {
        exp_ref.document(current_experiment.getExpId()).collection("Trials").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                // clear old list
                trial_list.clear();

                // check each experiment document
                for (QueryDocumentSnapshot doc : value) {
                    String experimenterID = doc.getData().get("experimenterID").toString();
                    if (experimenter.equals(experimenterID)) {
                        // get all the fields of the trial belonging to the experimenter clicked on
                        String trial_ID = doc.getId();
                        String outcome = doc.getData().get("result").toString();
                        String date = doc.getData().get("date").toString();
                        ViewTrial trial = new ViewTrial(trial_ID, outcome, date);

                        // add experiment to the list to display
                        trial_list.add(trial);
                    }

                }
                // notify adapter that data has change and to update the UI
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void viewProfile() {
        Intent intent = new Intent(context, UserProfileActivity.class);
        intent.putExtras(previous_intent.getExtras());
        context.startActivity(intent);
    }

    /**
     * When the up button gets clicked, the activity is killed.
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
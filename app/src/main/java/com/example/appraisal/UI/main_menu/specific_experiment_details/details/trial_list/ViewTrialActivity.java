package com.example.appraisal.UI.main_menu.specific_experiment_details.details.trial_list;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import com.example.appraisal.R;
import com.example.appraisal.UI.main_menu.user_profile.UserProfileActivity;
import com.example.appraisal.backend.experiment.Experiment;
import com.example.appraisal.backend.specific_experiment.ViewTrial;
import com.example.appraisal.model.core.MainModel;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
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
    private Button ignore_button;
    private ArrayList<String> ignored_list;
    private TextView status;
    private String current_user;
    private Button view_profile;

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

        try {
            current_user = MainModel.getCurrentUser().getId();
        } catch (Exception e) {
            e.printStackTrace();
        }

        status = findViewById(R.id.ignore_status);
        ignore_button = findViewById(R.id.ignore_button);

        checkOwner();

        // set field of experimenter ID
        experimenter = previous_intent.getStringExtra("experimenter");
        TextView id = findViewById(R.id.view_trial_experimenter);
        id.setText(experimenter.substring(0, 7));

        // set functionality of View Profile button
        view_profile = findViewById(R.id.view_profile_button);
        view_profile.setOnClickListener(v -> viewProfile());


        trial_display = findViewById(R.id.trialList);
        trial_list = new ArrayList<>();
        adapter = new ViewTrialAdapter(this, trial_list);
        getDbTrials();
        trial_display.setAdapter(adapter);
    }

    private void checkOwner() {
        // Sal, 2017-12-31, CC BY-SA 2.5, https://stackoverflow.com/questions/48043461/how-can-i-change-constraint-programmatically/48044912

        TextView text = findViewById(R.id.text_ignore);
        View separator = findViewById(R.id.separator);
        ConstraintSet set = new ConstraintSet();
        ConstraintLayout layout = findViewById(R.id.view_trial_constraint_layout);
        set.clone(layout);

        if (current_user.equals(current_experiment.getOwner())){
            // check if current experimenter is ignored, updated fields accordingly
            checkIfIgnored();
            // set functionality to ignore experimenter
            ignore_button.setOnClickListener(v1 -> ignoreButtonPressed());
        }
        else {
            set.connect(R.id.separator, ConstraintSet.TOP, R.id.view_profile_button, ConstraintSet.BOTTOM);
            set.applyTo(layout);
            text.setVisibility(View.GONE);
            status.setVisibility(View.GONE);
            ignore_button.setVisibility(View.GONE);
        }
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

                // check each trial document
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

    /**
     * Query database to see if experimenter is ignored
     */
    private void checkIfIgnored() {
        exp_ref.document(current_experiment.getExpId()).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                // get list of ignored experimenters
                ignored_list  = (ArrayList<String>) value.getData().get("ignoredExperimenters");

                // updated field values accordingly
                if (ignored_list != null && ignored_list.contains(experimenter)){
                    Log.d("Status:", "Already ignored");
                    status.setText("Yes");
                    ignore_button.setText("Unignore");
                }
                else {
                    Log.d("Status:", "Not ignored");
                    status.setText("No");
                    ignore_button.setText("Ignore");
                }
            }
        });
    }

    /**
     * When View Profile button is pressed, opens new activity
     */
    private void viewProfile() {
        Intent intent = new Intent(context, UserProfileActivity.class);
        intent.putExtras(previous_intent.getExtras());
        context.startActivity(intent);
    }

    /**
     * When Ignore (or Unignore) button is pressed, sets proper fields and calls methods to update firebase
     */
    private void ignoreButtonPressed() {
        // if status is No, then user wants to ignore trial
        if (status.getText().equals("No")) {
            ignore_button.setText("Unignore");
            status.setText("Yes");
            addToIgnored();
        }
        else {              // else status is Yes, user wants to unignore trial
            ignore_button.setText("Ignore");
            status.setText("No");
            removeFromIgnored();
        }
    }

    /**
     * Adds experimenter to ignoredExperimenters list in firebase
     */
    private void addToIgnored() {
        Log.d("Experimenter:", "adding to ignored list");
        try {
            exp_ref.document(current_experiment.getExpId()).update("ignoredExperimenters", FieldValue.arrayUnion(experimenter));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Removes experimenter from ignoredExperimenters list in firebase
     */
    private void removeFromIgnored() {
        Log.d("Experimenter:", "removing from ignored list");
        try {
            exp_ref.document(current_experiment.getExpId()).update("ignoredExperimenters", FieldValue.arrayRemove(experimenter));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * When the up button gets clicked, the activity is killed.
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
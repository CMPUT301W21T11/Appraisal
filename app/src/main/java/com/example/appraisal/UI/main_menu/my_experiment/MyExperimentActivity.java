package com.example.appraisal.UI.main_menu.my_experiment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;

import com.example.appraisal.UI.main_menu.MainMenuCommonActivity;
import com.example.appraisal.backend.experiment.ExpAdapter;
import com.example.appraisal.R;
import com.example.appraisal.backend.experiment.Experiment;
import com.example.appraisal.backend.user.User;
import com.example.appraisal.model.MainModel;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

/**
 * This is the Activity that displays the experiments the user has created.
 * It also allows them to publish more experiments.
 */
public class MyExperimentActivity extends MainMenuCommonActivity implements ExpStatusFragment.OnFragmentInteractionListener{
    private ListView my_experiment_display;
    private static ArrayAdapter<Experiment> adapter;
    private ArrayList<Experiment> exp_list;
    private CollectionReference reference;
    private String userID;

    /**
     * Creates the MyExperimentActivity
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_exp);

        // get reference to Experiment Collection
        try {
            reference = MainModel.getExperimentReference();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // get Current User's anonymous id
        try {
            User user = MainModel.getCurrentUser();
            userID = user.getID();
        } catch (Exception e) {
            e.printStackTrace();
        }

        my_experiment_display = findViewById(R.id.my_experiments);

        exp_list = new ArrayList<>();                           // make new list to store experiments

        adapter = new ExpAdapter(this, exp_list);      // connect adapter

        getDbExperiments();                                     // get all experiments from Database

        my_experiment_display.setOnItemClickListener(selectExListener);
        my_experiment_display.setAdapter(adapter);
    }

    /**
     * This method is called when the user clicks on the Floating Action Button.
     * It opens the PublishExpActivity
     * @param v
     */
    public void publishNewExperiment(View v) {
        Intent publishExp_intent = new Intent(this, PublishExpActivity.class);
        startActivity(publishExp_intent);
    }

    /**
     * This method gets the item in list the user clicks on, and opens up a dialog with the corresponding info.
     */
    private final AdapterView.OnItemClickListener selectExListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Experiment experiment = exp_list.get(position);
            try {
                MainModel.setCurrentExperiment(experiment);
            } catch (Exception e) {
                e.printStackTrace();
            }
            ExpStatusFragment fragment = ExpStatusFragment.newInstance(experiment);
            fragment.show(getSupportFragmentManager(), "Edit Experiment Status");

        }
    };

    /**
     * This method queries the database and gets all experiments the user has created.
     * These experiments are displayed on the UI.
     */
    private void getDbExperiments(){
        reference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                // clear old list
                exp_list.clear();

                // check each experiment document
                for (QueryDocumentSnapshot doc: value){
                    // get the experiment's owner id
                    String db_user_ID = (String)doc.getData().get("owner");

                    // if experiment's owner id is the same as the current user's id
                    if (db_user_ID.equals(userID)){
                        // get all the fields of the experiment
                        String exp_ID = doc.getId();
                        String description = (String)doc.getData().get("description");
                        String type = (String)doc.getData().get("type");
                        Boolean geo_required = (Boolean)doc.getData().get("isGeolocationRequired");
                        Integer min_trials = Integer.valueOf(doc.getData().get("minTrials").toString());
                        String rules = (String)doc.getData().get("rules");
                        String region = (String)doc.getData().get("region");
                        Boolean is_ended = (Boolean)doc.getData().get("isEnded");
                        Boolean is_published = (Boolean)doc.getData().get("isPublished");
                        Integer num_of_trials = Integer.valueOf(doc.getData().get("numOfTrials").toString());

                        // make a new experiment object with these fields
                        Experiment experiment = new Experiment(exp_ID, db_user_ID, description, type, geo_required, min_trials, rules, region);

                        // set the values of publish and ended status
                        experiment.setIs_ended(is_ended);
                        experiment.setIs_published(is_published);
                        experiment.setTrial_count(num_of_trials);

                        // add experiment to the list to display
                        exp_list.add(experiment);
                        }
                    // notify adapter that data has change and to update the UI
                    adapter.notifyDataSetChanged();
                }

            }
        });
    }

}

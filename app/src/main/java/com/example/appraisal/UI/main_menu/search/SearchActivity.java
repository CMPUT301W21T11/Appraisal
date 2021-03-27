package com.example.appraisal.UI.main_menu.search;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;

import android.view.MenuItem;


import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.appraisal.R;
import com.example.appraisal.UI.main_menu.MainMenuCommonActivity;
import com.example.appraisal.UI.main_menu.my_experiment.ExpAdapter;
import com.example.appraisal.UI.main_menu.my_experiment.ExpStatusFragment;
import com.example.appraisal.UI.main_menu.specific_experiment_details.SpecificExpActivity;
import com.example.appraisal.backend.experiment.Experiment;
import com.example.appraisal.model.MainModel;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class SearchActivity extends MainMenuCommonActivity implements ExpStatusFragment.OnFragmentInteractionListener {
    private CollectionReference exp_ref;
    private ListView search_result_display;
    private ArrayList<Experiment> exp_list;
    private ExpAdapter exp_adapter;
    private SearchView exp_search;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        try {
            exp_ref = MainModel.getExperimentReference();
        } catch (Exception e) {
            e.printStackTrace();
        }

        context = this;

        search_result_display = findViewById(R.id.search_results);
        exp_search = findViewById(R.id.exp_search_bar);

        exp_list = new ArrayList<>();                           // make new list to store experiments

        exp_adapter = new ExpAdapter(this, exp_list, "Search");      // connect adapter

        getDbExperiments();                                     // get all experiments from Database


        // Listens to the search box for any updates and filters the results accordingly
        exp_search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                SearchActivity.this.exp_adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                SearchActivity.this.exp_adapter.getFilter().filter(newText);
                return false;
            }
        });

        // passing the experiment data into the list
        search_result_display.setAdapter(exp_adapter);

        // function to select a specific experiment from the list
        search_result_display.setOnItemClickListener(selectExListener);

    }

    /**
     * Click on a specific experiment to open its main page
     */
    private AdapterView.OnItemClickListener selectExListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            Experiment experiment = exp_adapter.getItem(position);
            Intent intent = new Intent(context, SpecificExpActivity.class);
            try {
                MainModel.setCurrentExperiment(experiment);
            } catch (Exception e) {
                e.printStackTrace();
            }
            startActivity(intent);
        }
    };

    /**
     * Get all experiments from Database that are published
     */
    private void getDbExperiments() {
        exp_ref.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                // clear old list
                exp_list.clear();

                // check each experiment document
                for (QueryDocumentSnapshot doc : value) {
                    // get the experiment's owner id
                    String db_user_ID = (String) doc.getData().get("owner");

                    // get all the fields of the experiment
                    String exp_ID = doc.getId();
                    Boolean is_published = (Boolean) doc.getData().get("isPublished");

                    if (!exp_ID.equals("Exp0000") && is_published) {

                        String description = (String) doc.getData().get("description");
                        String type = (String) doc.getData().get("type");
                        Boolean geo_required = (Boolean) doc.getData().get("isGeolocationRequired");
                        Integer min_trials = Integer.valueOf(doc.getData().get("minTrials").toString());
                        String rules = (String) doc.getData().get("rules");
                        String region = (String) doc.getData().get("region");
                        Boolean is_ended = (Boolean) doc.getData().get("isEnded");


                        // make a new experiment object with these fields
                        Experiment experiment = new Experiment(exp_ID, db_user_ID, description, type, geo_required, min_trials, rules, region);

                        // set the values of publish and ended status
                        experiment.setIsEnded(is_ended);
                        experiment.setIsPublished(is_published);

                        // add experiment to the list to display
                        exp_list.add(experiment);

                    }
                }
                // notify adapter that data has change and to update the UI
                exp_adapter.notifyDataSetChanged();
            }
        });
    }


}

package com.example.appraisal.UI.main_menu.specific_experiment_details.participants;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appraisal.R;
import com.example.appraisal.backend.experiment.Experiment;
import com.example.appraisal.model.core.MainModel;
import com.example.appraisal.model.main_menu.specific_experiment_details.SpecificExpModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * This fragment shows the contributors list to an experiment in the participants tab
 */
public class SpecificExpContributorsFragment extends Fragment {
    private RecyclerView recyclerView;
    private SpecificExpContributorsViewAdapter adapter;
    private SpecificExpModel model;
    private Experiment current_experiment;
    private ArrayList<String> experimenters;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private String user;

    /**
     * This method creates view for the contributors fragment
     *
     * @param inflater -- LayoutInflater to inflate the view
     * @param container -- The parent ViewGroup
     * @param savedInstanceState -- previous saved instance state
     * @return View -- the inflated view for this fragment
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // inflate fragment
        View view = inflater.inflate(R.layout.fragment_specific_exp_contributors, container, false);
        experimenters = new ArrayList<>(); // filler code to get the adapter running
        // initialize Shared Preferences
        pref = this.getActivity().getSharedPreferences("prefKey", Context.MODE_PRIVATE);
        editor = pref.edit();

        // get current experiment
        try {
            current_experiment = MainModel.getCurrentExperiment();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            user = MainModel.getCurrentUser().getId();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Boolean is_owner = user.equals(current_experiment.getOwner());

        // initialize model
        model = new SpecificExpModel(current_experiment);

        queryDB();


        // initialize recyclerView
        recyclerView = view.findViewById(R.id.fragment_specific_exp_contributors_recyclerView);
        adapter = new SpecificExpContributorsViewAdapter(this.getActivity(), experimenters, model, pref, is_owner); // initialize adapter
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext())); // set layout manager to simply be LinearLayout
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator()); // Use default animation for now
        return view;
    }

    /**
     * This method query the database and notify the adapter when on resume is called
     */
    @Override
    public void onResume() {
        super.onResume();
        Log.d("Resume:", "contributors");
        queryDB();
        adapter.notifyDataSetChanged();
    }

    /**
     * This method queries firebase to obtain a list of experimenters
     */
    private void queryDB() {
        // get document reference
        DocumentReference doc = null;
        try {
            doc = MainModel.getExperimentReference().document(current_experiment.getExpId());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // query database
        doc.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot doc = task.getResult();
                experimenters.clear();
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        // get experimenter list
                        experimenters = (ArrayList<String>) document.getData().get("experimenters");
                        Log.d("ExpSize:", String.valueOf(experimenters.size()));
                        // store in Shared Preferences
                        Set<String> set = new HashSet<String>();
                        set.addAll(experimenters);
                        editor.putStringSet("expKey", set);
                        editor.commit();
                    } else {
                        Log.d("Document Non Existed", "No such document");
                    }
                } else {
                    Log.d("Exception", "get failed with ", task.getException());
                }
                adapter.notifyDataSetChanged();
            }
        });
    }

}
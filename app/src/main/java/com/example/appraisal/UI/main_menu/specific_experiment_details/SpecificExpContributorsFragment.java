package com.example.appraisal.UI.main_menu.specific_experiment_details;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appraisal.R;
import com.example.appraisal.backend.experiment.Experiment;
import com.example.appraisal.backend.user.User;
import com.example.appraisal.model.MainModel;
import com.example.appraisal.model.SpecificExpModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class SpecificExpContributorsFragment extends Fragment {
    private RecyclerView recyclerView;
    private SpecificExpContributorsViewAdapter adapter;
    private SpecificExpModel model;
    private Experiment current_experiment;
    private ArrayList<String> experimenters;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // inflate fragment
        View view = inflater.inflate(R.layout.fragment_specific_exp_contributors, container, false);
        experimenters = new ArrayList<>(); // filler code to get the adapter running

        // initialize model
        model = new SpecificExpModel();

        try {
            current_experiment = MainModel.getCurrentExperiment();
        } catch (Exception e) {
            e.printStackTrace();
        }
//        try {
//            getExperimentersFirestore();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        DocumentReference doc = null;
        try {
            doc = MainModel.getExperimentReference().document(current_experiment.getExp_id());
        } catch (Exception e) {
            e.printStackTrace();
        }

        pref = this.getActivity().getSharedPreferences("prefKey", Context.MODE_PRIVATE);
        editor = pref.edit();

        doc.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot doc = task.getResult();
                experimenters.clear();
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        experimenters = (ArrayList<String>) document.getData().get("experimenters");
                        Log.d("Size:", String.valueOf(experimenters.size()));

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

        Log.d("SizeOutside:", String.valueOf(experimenters.size()));


//        // initialize recyclerView
        recyclerView = view.findViewById(R.id.fragment_specific_exp_contributors_recyclerView);
        adapter = new SpecificExpContributorsViewAdapter(this.getActivity(), experimenters, model, pref); // initialize adapter
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext())); // set layout manager to simply be LinearLayout
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator()); // Use default animation for now
        return view;
    }


    public Boolean getExperimentersFirestore() throws Exception {

        DocumentReference doc = MainModel.getExperimentReference().document(current_experiment.getExp_id());

        doc.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot doc = task.getResult();
                experimenters.clear();
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        experimenters = (ArrayList<String>) document.getData().get("experimenters");
                        Log.d("Size:", String.valueOf(experimenters.size()));
                    } else {
                        Log.d("Document Non Existed", "No such document");
                    }
                } else {
                    Log.d("Exception", "get failed with ", task.getException());
                }
                adapter.notifyDataSetChanged();
            }
        });

        Log.d("SizeOutside:", String.valueOf(experimenters.size()));
        return true;
    }
}


//doc.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//@Override
//public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//        DocumentSnapshot doc = task.getResult();
//        experimenters.clear();
//        if (task.isSuccessful()) {
//        DocumentSnapshot document = task.getResult();
//        if (document.exists()) {
//        experimenters = (ArrayList<String>) document.getData().get("experimenters");
////                        adapter = new SpecificExpContributorsViewAdapter(context, experimenters, model, pref); // initialize adapter
////                        recyclerView.setAdapter(adapter);
////                        recyclerView.setItemAnimator(new DefaultItemAnimator()); // Use default animation for now
////                        if (experimenters == null){
////                            experimenters = new ArrayList<>();
////                        }
//        Set<String> set = new HashSet<String>();
//        set.addAll(experimenters);
//        editor.putStringSet("expKey", set);
//        editor.commit();
//        Log.d("Pass Firestore", "DocumentSnapshot data: " + experimenters);
//        Log.d("Size:", String.valueOf(experimenters.size()));
//        } else {
//        Log.d("Document Non Existed", "No such document");
//        }
//        } else {
//        Log.d("Exception", "get failed with ", task.getException());
//        }
//        adapter.notifyDataSetChanged();
//        }
//        });
//        Log.d("SizeOutside:", String.valueOf(experimenters.size()));


//        doc.addSnapshotListener(new EventListener<DocumentSnapshot>() {
//            @Override
//            public void onS(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
//                experimenters = (ArrayList<String>) value.getData().get("experimenters");
//                Log.d("ListInside", ""+experimenters);
//
//            }
//        });
//        Log.d("List", ""+experimenters);
//
//        Context context = this.getActivity();
//        pref = this.getActivity().getSharedPreferences("sharedPrefKey", Context.MODE_PRIVATE);
//        editor = pref.edit();

//        recyclerView = view.findViewById(R.id.fragment_specific_exp_contributors_recyclerView);
//
//        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext())); // set layout manager to simply be LinearLayout

//        ArrayList<String> contributors = model.getExperimenters(); // filler code to get the adapter running
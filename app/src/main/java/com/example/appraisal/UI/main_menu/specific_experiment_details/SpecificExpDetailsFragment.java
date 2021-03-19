package com.example.appraisal.UI.main_menu.specific_experiment_details;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.appraisal.R;
import com.example.appraisal.backend.experiment.Experiment;
import com.example.appraisal.model.MainModel;
import com.example.appraisal.model.SpecificExpModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class SpecificExpDetailsFragment extends Fragment {

    private SpecificExpModel model;
    private Experiment current_experiment;
    private DocumentReference user_ref;
    private ArrayList<String> user_subscriptions;
    private CheckBox subscriptionBox;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_specific_exp_experiment_details, container, false);

        subscriptionBox = (CheckBox) v.findViewById(R.id.specific_exp_details_subscribe_checkBox);

        try {
            current_experiment = MainModel.getCurrentExperiment();

        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            user_ref = MainModel.getUserReference();
        } catch (Exception e) {
            e.printStackTrace();
        }

        user_ref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        user_subscriptions = (ArrayList <String>) document.get("my_subscriptions");

                        if (user_subscriptions != null) {
                            if (user_subscriptions.contains(current_experiment.getExp_id())) {
                                subscriptionBox.setChecked(true);
                            } else {
                                subscriptionBox.setChecked(false);
                            }
                        }
                    }
                }
            }
        });

        subscriptionBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked) {
                    user_ref.update("my_subscriptions", FieldValue.arrayUnion(current_experiment.getExp_id()));
                }
                else {
                    user_ref.update("my_subscriptions", FieldValue.arrayRemove(current_experiment.getExp_id()));
                }
            }
        });

        return v;
    }

}

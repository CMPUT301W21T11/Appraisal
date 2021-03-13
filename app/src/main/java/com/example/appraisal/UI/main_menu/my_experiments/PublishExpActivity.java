package com.example.appraisal.UI.main_menu.my_experiments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appraisal.R;
import com.example.appraisal.UI.main_menu.my_experiments.MyExperimentActivity;
import com.example.appraisal.backend.experiment.ExpContainer;
import com.example.appraisal.backend.experiment.Experiment;
import com.example.appraisal.backend.user.User;
import com.example.appraisal.model.MainModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Source;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PublishExpActivity extends AppCompatActivity {
    private EditText description_field;
    private Spinner type_field;
    private RadioButton geo_yes;
    private EditText min_trials_field;
    private EditText rules_field;
    private EditText region_field;
    private CollectionReference model;

    public static ArrayList<Experiment> data_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish_exp);

        //initialize the data
        description_field = findViewById(R.id.expDesc);
        type_field = findViewById(R.id.exp_types);
        geo_yes = findViewById(R.id.radioButtonYes);
        min_trials_field = (EditText) findViewById(R.id.expMinTrials);
        rules_field = findViewById(R.id.expRules);
        region_field = findViewById(R.id.expRegion);

        try {
            model = MainModel.getExperimentReference();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createNewExp(View view) throws Exception {
        //String owner = "TempOwner";
        String description = description_field.getText().toString();
        String type = type_field.getSelectedItem().toString();
        boolean is_geolocation_required = geo_yes.isChecked();
        String rules = rules_field.getText().toString();
        String region = region_field.getText().toString();

        int min_trials;
        if (min_trials_field.getText().length() == 0){
            min_trials = 0;
        }
        else {
            min_trials = Integer.parseInt(min_trials_field.getText().toString());
        }

        DocumentReference owner = MainModel.getUserReference();

        User user = MainModel.getCurrentUser();

//        String expID = owner.toString();
//        owner.addSnapshotListener(new EventListener<DocumentSnapshot>() {
//            @Override
//            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
//                String num_of_exp = value.get("num_of_my_exp").toString();
//                int num = Integer.valueOf(num_of_exp);
//                num++;
//                String expID = owner.getId();
//                expID = expID+num;
//                try {
//                    MainModel.setCurrentExperiment(experiment);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
        Integer number = user.getNum_of_exp() + 1;
        String owner_name = user.getID();
        String expID = owner_name + number;


                Map<String, Object> exp_info = new HashMap<>();
                exp_info.put("description", description);
                exp_info.put("type", type);
                exp_info.put("owner", owner_name);
                exp_info.put("rules", rules);
                exp_info.put("region", region);
                exp_info.put("minTrials", min_trials);
                exp_info.put("isGeolocationRequired", is_geolocation_required);
                exp_info.put("isEnded", false);
                exp_info.put("isPublished", true);


                model.document(expID).set(exp_info)
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
//            }});

        owner.update("num_of_my_exp", number);
        user.setNum_of_exp(number);

        //Experiment experiment = new Experiment(owner.toString(), description, type, is_geolocation_required, min_trials, rules, region);
        //Experiment experiment = new Experiment(owner, description, type, is_geolocation_required, min_trials, rules, region);

        //data_list = ExpContainer.getExpList();
        //data_list.add(experiment);

//        Map<String, Object> exp_info = new HashMap<>();
//        exp_info.put("description", description);
//        exp_info.put("type", type);
//        exp_info.put("owner", owner.toString());
//        exp_info.put("rules", rules);
//        exp_info.put("region", region);
//        exp_info.put("minTrials", min_trials);
//        exp_info.put("isGeolocationRequired", is_geolocation_required);
//        exp_info.put("isEnded", false);
//        exp_info.put("isPublished", true);
//
//
//        model.document("");


        navigateUpTo(new Intent(getBaseContext(), MyExperimentActivity.class));
    }


}

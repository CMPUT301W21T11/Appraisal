package com.example.appraisal.UI.main_menu.my_experiment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appraisal.R;
import com.example.appraisal.backend.user.User;
import com.example.appraisal.model.core.MainModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This Activity allows the user to create a new experiment.
 */
public class PublishExpActivity extends AppCompatActivity {
    private EditText description_field;
    private Spinner type_field;
    private RadioButton geo_yes;
    private EditText min_trials_field;
    private EditText rules_field;
    private EditText region_field;
    private CollectionReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish_exp);

        // get fields from xml by id
        description_field = findViewById(R.id.expDesc);
        type_field = findViewById(R.id.exp_types);
        geo_yes = findViewById(R.id.radioButtonYes);
        min_trials_field = (EditText) findViewById(R.id.expMinTrials);
        rules_field = findViewById(R.id.expRules);
        region_field = findViewById(R.id.expRegion);


        List<String> expTypes = Arrays.asList("Count-based trials",
                "Binomial Trials",
                "Non-negative Integer Trials",
                "Measurement Trials");

        // use custom spinner layout to change text color
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, R.layout.view_holder_experiment_spinner, expTypes);
        type_field.setAdapter(spinnerAdapter);

        // get reference of Experiments Collection
        try {
            reference = MainModel.getExperimentReference();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method created the new experiment when user presses Publish Button
     *
     * @param view
     * @throws Exception
     */
    public void createNewExp(View view) throws Exception {
        // get values from input fields
        String description = description_field.getText().toString();
        String type = type_field.getSelectedItem().toString();

        if (description.trim().equals("")) {
            // TODO switch to snack bar
            Toast.makeText(this, "Experiment description cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean is_geolocation_required = geo_yes.isChecked();
        String rules = rules_field.getText().toString();
        String region = region_field.getText().toString();

        int min_trials;
        if (min_trials_field.getText().length() == 0) {
            min_trials = 0;
        } else {
            min_trials = Integer.parseInt(min_trials_field.getText().toString());
        }

        // get reference to User Document
        DocumentReference owner = MainModel.getUserReference();
        User user = MainModel.getCurrentUser();         // get current user
        Integer number = user.getNumOfExp() + 1;      // increment experiment count
        String owner_name = user.getId();               // get anonymous user id
        String expID = owner_name + number;             // create unique experiment id

        // create Hash Map
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
        exp_info.put("experimenters", new ArrayList<>());
        exp_info.put("numOfTrials", 0);

        // create new document for experiment with values from hash map
        reference.document(expID).set(exp_info)
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

        // update count of experiments in database and local user object
        owner.update("numOfMyExp", number);
        user.setNumOfExp(number);

        // return to MyExperimentActivity
        navigateUpTo(new Intent(getBaseContext(), MyExperimentActivity.class));
    }

}

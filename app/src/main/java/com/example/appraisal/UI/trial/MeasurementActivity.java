package com.example.appraisal.UI.trial;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.example.appraisal.R;
import com.example.appraisal.UI.geolocation.CurrentMarker;
import com.example.appraisal.UI.geolocation.GeolocationActivity;
import com.example.appraisal.UI.geolocation.GeolocationWarningDialog;
import com.example.appraisal.backend.experiment.Experiment;
import com.example.appraisal.backend.user.User;
import com.example.appraisal.model.core.MainModel;
import com.example.appraisal.model.trial.MeasurementModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.GeoPoint;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * This activity is for adding a measurement trial
 */
public class MeasurementActivity extends AppCompatActivity implements GeolocationWarningDialog.OnFragmentInteractionListener {
    private EditText input_measurement;
    private MeasurementModel model;
    private final DecimalFormat dp3 = new DecimalFormat("#.##");
    private Experiment current_exp;
    private CollectionReference experiment_reference;
    private int firebase_num_trials = 0;
    private String experimenterID;
    private static final int MAP_REQUEST_CODE = 0;
    private CurrentMarker trial_location;
    private GeoPoint trial_geopoint;
    private Button geolocation_button;
    private DocumentReference user_ref;

    /**
     * create the activity and inflate it with layout. initialize model
     * @param savedInstanceState -- bundle of the saved instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_measurement);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        geolocation_button = findViewById(R.id.add_geo);
        input_measurement = findViewById(R.id.inputMeasurement);
        try {
            current_exp = MainModel.getCurrentExperiment();
            User conductor = MainModel.getCurrentUser();
            model = new MeasurementModel(current_exp, conductor);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (current_exp.getIsGeolocationRequired()) {
            GeolocationWarningDialog geolocation_warning = GeolocationWarningDialog.newInstance();
            geolocation_warning.show(getFragmentManager(), "Geolocation Dialog");
        }
        else {
            geolocation_button.setVisibility(View.GONE);
        }

        try {
            experiment_reference = MainModel.getExperimentReference();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            user_ref = MainModel.getUserReference();
        } catch (Exception e) {
            e.printStackTrace();
        }

        getSupportActionBar().setTitle(current_exp.getDescription());

        listenToNumOfTrials();
    }


    /**
     * Save measurement to experiment
     * @param view save button
     */
    public void addMeasurement(View view) {

        if (trial_location == null && current_exp.getIsGeolocationRequired()) {
            CoordinatorLayout snackbar_layout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
            Snackbar snackbar = Snackbar.make(snackbar_layout, "You must add your trial geolocation", Snackbar.LENGTH_LONG);
            snackbar.setAction("DISMISS", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    snackbar.dismiss();
                }
            });
            snackbar.show();
        }

        else {
            String input = input_measurement.getText().toString();
            model.addMeasurement(input);
            model.toExperiment();
            storeTrialInFireBase();
            addContributor();
            user_ref.update("mySubscriptions", FieldValue.arrayUnion(current_exp.getExpId()));
            finish();
        }
    }


    /**
     * This method stores the trial to firebase
     */
    public void storeTrialInFireBase() {

        String experiment_ID = current_exp.getExpId();
        Integer num_of_trials = firebase_num_trials + 1;
        String name = "Trial" + num_of_trials;
        Map<String, Object> trial_info = new HashMap<>();
        trial_info.put("result", dp3.format(model.getMeasurement()));

        // put trial date as current date
        DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
        String date_string = formatter.format(Calendar.getInstance().getTime());
        trial_info.put("date", date_string);

        // put current User as the experimenter
        try {
            experimenterID = MainModel.getCurrentUser().getId();
        } catch (Exception e) {
            e.printStackTrace();
        }
        trial_info.put("experimenterID", experimenterID);

        if (trial_location != null) {
            trial_geopoint = new GeoPoint(trial_location.getLatitude(), trial_location.getLongitude());
            trial_info.put("geolocation", trial_geopoint);
        }

        Context self = this;
        // create new document for experiment with values from hash map
        experiment_reference.document(experiment_ID).collection("Trials").document(name).set(trial_info)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("***", "DocumentSnapshot successfully written!");
                        Toast.makeText(self, "Trial Addition successful!", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("***", "Error writing document", e);
                        Toast.makeText(self, "Trail Addition failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

        experiment_reference.document(experiment_ID).update("numOfTrials", num_of_trials);
        current_exp.setTrialCount(num_of_trials);
    }


    private void addContributor() {

        try {
            experiment_reference.document(current_exp.getExpId()).update("experimenters", FieldValue.arrayUnion(MainModel.getCurrentUser().getId()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void listenToNumOfTrials() {

        experiment_reference.document(current_exp.getExpId()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();

                    if ((document != null) && document.exists()) {
                        try {
                            firebase_num_trials = Integer.parseInt(document.get("numOfTrials").toString());
                            Log.d("numtrials listener", String.valueOf(firebase_num_trials));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
            }
        });
    }


    /**
     * This method adds a geolocation for the trial
     * @param v -- the add geolocation button
     */
    public void addGeolocation(View v) {
        Intent intent = new Intent(this, GeolocationActivity.class);
        intent.putExtra("Map Request Code", "User Location");
        intent.putExtra("Experiment Description", current_exp.getDescription());
        startActivityForResult(intent, MAP_REQUEST_CODE);
    }


    /**
     * Dispatch incoming result to the correct fragment.
     *
     * @param requestCode -- the activity which started for result
     * @param resultCode -- the result of the activity
     * @param data -- any Intent date from the activity
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == MAP_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                trial_location = (CurrentMarker) data.getParcelableExtra("currentMarker");

                geolocation_button.setText("Edit Geolocation");

                CoordinatorLayout snackbar_layout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
                Snackbar location_saved_snackbar = Snackbar.make(snackbar_layout, "Your trial geolocation has been saved", Snackbar.LENGTH_LONG);
                location_saved_snackbar.setAction("DISMISS", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        location_saved_snackbar.dismiss();
                    }
                });
                location_saved_snackbar.show();
            }
        }
    }

    /**
     * If the back button is pressed, close this activity and go back to previous one
     *
     * @param item -- MenuItem
     * @return boolean -- if the button is pressed
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

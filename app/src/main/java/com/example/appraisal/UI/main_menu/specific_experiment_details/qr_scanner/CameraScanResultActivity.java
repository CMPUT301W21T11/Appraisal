package com.example.appraisal.UI.main_menu.specific_experiment_details.qr_scanner;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appraisal.R;
import com.example.appraisal.backend.geolocation.CurrentMarker;
import com.example.appraisal.UI.geolocation.GeolocationActivity;
import com.example.appraisal.backend.specific_experiment.QRValues;
import com.example.appraisal.backend.trial.TrialType;
import com.example.appraisal.model.core.MainModel;
import com.example.appraisal.model.main_menu.specific_experiment_details.QRAnalyzerModel;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.GeoPoint;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;

/**
 * This class handles the result from the {@link CameraScanner}
 */
public class CameraScanResult extends AppCompatActivity {

    private static final int CAMERA_SCANNER_REQUEST_CODE = 0x00000001;
    private static final int MAP_REQUEST_CODE = 0x00000000;

    private QRAnalyzerModel model;
    private Activity self;

    private Button add_geo_button;
    private CurrentMarker trial_location;
    private String exp_id;

    /**
     * This method creates the CameraScanResultActivity
     * @param savedInstanceState -- Bundle from saved instance
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_scan_result);
        self = this;
        model = new QRAnalyzerModel(this);
        // Initialize the cancel button
        Button cancel_button = findViewById(R.id.camera_scan_result_cancel);
        add_geo_button = findViewById(R.id.camera_scan_result_add_geo);
        add_geo_button.setVisibility(View.GONE);
        cancel_button.setOnClickListener(v -> {
            try {
                // remove the stored barcode result
                MainModel.setBarcodeResult(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
            finish();
        });

        Log.d("CameraScannerResult:","Starting camera scanner");
        Intent intent = new Intent(this, CameraScanner.class);
        startActivityForResult(intent, CAMERA_SCANNER_REQUEST_CODE);
    }

    /**
     * This method overrides the parent method and obtain the scanned result code
     * @param requestCode -- check which activity has finished
     * @param resultCode -- check if activity is properly terminated
     * @param data -- any intent data from the previous activity
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_SCANNER_REQUEST_CODE) { // This is the request code for camera scanner
            if (resultCode == Activity.RESULT_OK) {
                try {
                    Result result = MainModel.getBarcodeResult(); // get result bar code
                    if (result == null) {// if null return
                        return;
                    }
                    displayResult(result);
                } catch (Exception e) {
                    Log.e("CameraScanResult:", "Error when processing QR codes: " + e.getMessage());
                    e.printStackTrace();
                }
            } else {
                finish();
            }
        } else if (requestCode == MAP_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                trial_location = (CurrentMarker) data.getParcelableExtra("currentMarker");
                add_geo_button.setText("Edit Geolocation");
                Toast.makeText(self, "Trial geolocation has been saved", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(self, "No geolocation is set", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void displayResult(Result result) throws Exception {
        // We are gonna display the detected code on the activity
        model.displayBarCode(result);

        final TextView activity_title = findViewById(R.id.camera_scan_result_title);
        final TextView experiment_desc_display = findViewById(R.id.camera_scan_result_exp_desc_display);
        final TextView trialType = findViewById(R.id.camera_scan_result_trial_type_display);
        final TextView trialValue = findViewById(R.id.camera_scan_result_trial_value_display);
        final Button finish_button = findViewById(R.id.camera_scan_result_finish_button);

        if (result.getBarcodeFormat() == BarcodeFormat.QR_CODE) { // If the scanned code is QR code
            activity_title.setText("Detected QR Code");
            QRValues values = model.decodeTrialQR(result.getText());

            if (values != null && values.checkSignature()) {
                trialType.setText(values.getType().getLabel());
                switch (values.getType()) {
                    case BINOMIAL_TRIAL:
                        if (Math.round(values.getValue()) == 0) {
                            trialValue.setText("Failure");
                        } else {
                            trialValue.setText("Success");
                        }
                        break;
                    case NON_NEG_INT_TRIAL:
                    case COUNT_TRIAL:
                        trialValue.setText(String.valueOf((int) values.getValue()));
                        break;
                    default:
                        trialValue.setText(String.valueOf(values.getValue()));
                }

                checkIfClosed(values.getExpId());
                setExperimentDesc(experiment_desc_display, values.getExpId());

                CollectionReference experiment = MainModel.getExperimentReference();

                experiment.document(values.getExpId()).get().addOnCompleteListener(task -> {
                    finish_button.setText("ADD QR TRIAL TO EXPERIMENT");
                    finish_button.setOnClickListener(v -> {
                        // get if geolocation is required
                        Boolean geo_required;
                        if (task.isSuccessful()) {
                            DocumentSnapshot result1 = task.getResult();
                            geo_required = result1.getBoolean("isGeolocationRequired");
                            if (geo_required == null) {
                                geo_required = false;
                            }
                        } else {
                            geo_required = false;
                        }

                        if (geo_required != null && geo_required) {
                            // obtain trial location
                            if (trial_location == null) {
                                Toast.makeText(self, "Geolocation is not set", Toast.LENGTH_SHORT).show();
                                return;
                            } else {
                                GeoPoint geoPoint = new GeoPoint(trial_location.getLatitude(), trial_location.getLongitude());
                                values.setGeoPoint(geoPoint);
                            }
                        }
                        try {
                            model.addToExperiment(values);
                            // clear barcode result
                            MainModel.setBarcodeResult(null);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        addContributor();
                        finish();
                    });
                });
            } else {
                Toast.makeText(self, "QR Code is not compatible", Toast.LENGTH_SHORT).show();
                trialType.setText(R.string.not_recognized);
                trialValue.setText(R.string.not_recognized);
                finish_button.setOnClickListener(v -> {
                    // clear barcode result
                    try {
                        MainModel.setBarcodeResult(null);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    finish();
                });
            }
        } else { // else if it is barcode
            trialType.setText(R.string.not_recognized);
            trialValue.setText(R.string.not_recognized);
            activity_title.setText("Detected Bar Code");

            // try to find the barcode inside the registered barcodes
            try {
                DocumentReference user = MainModel.getUserReference();
                String barcode_value = result.getText();

                // locate registered barcode list
                CollectionReference barcode_list = user.collection("Barcodes");

                // query the barcode list
                barcode_list.document(barcode_value).get().addOnCompleteListener(task -> {
                    // If task is not successful or document does not exist, return
                    if (task == null || !task.isSuccessful() || !task.getResult().exists()) {
                        if (task.getException() != null) { // print all possible errors
                            task.getException().printStackTrace();
                        }
                        Toast.makeText(self, "Barcode is not registered", Toast.LENGTH_SHORT).show();
                        // set the finish button to end the activity
                        finish_button.setOnClickListener(v -> {
                            // clear barcode result
                            try {
                                MainModel.setBarcodeResult(null);
                            } catch (Exception f) {
                                f.printStackTrace();
                            }
                            finish();
                        });
                    } else {
                        DocumentSnapshot document = task.getResult();
                        String registered_barcode = document.getId();
                        finish_button.setText("ADD BARCODE TRIAL TO EXPERIMENT");

                        String trial_type = getField(document, "trialType");
                        String data = getField(document, "action");
                        String exp_id = getField(document, "targetExperimentId");
                        String exp_desc = getField(document, "targetExperimentDesc");

                        checkIfClosed(exp_id);

                        experiment_desc_display.setText(exp_desc);
                        trialType.setText(trial_type);
                        trialValue.setText(data);
                        if (registered_barcode.equalsIgnoreCase(result.getText())) {
                            // Set the finish button to add to firebase when clicked and end the scanner
                            Boolean geo_location_required = document.getBoolean("isGeolocationRequired");
                            finish_button.setOnClickListener(v -> {
                                try {
                                    String signature = getResources().getString(R.string.app_name);
                                    TrialType type = TrialType.getInstance(trial_type);
                                    double trial_value = Double.parseDouble(data);
                                    QRValues values = new QRValues(self, signature, type, trial_value, exp_id);

                                    // check if geolocation is required
                                    if (geo_location_required != null && geo_location_required) {
                                        if (trial_location == null) {
                                            throw new Exception("Geolocation not set");
                                        } else {
                                            GeoPoint geoPoint = new GeoPoint(trial_location.getLatitude(), trial_location.getLongitude());
                                            values.setGeoPoint(geoPoint);
                                        }
                                    }
                                    MainModel.setBarcodeResult(null); // clear the result in main model
                                    model.addToExperiment(values); // add to firebase
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    Toast.makeText(self, "Failed to add to experiment: "+ e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                                finish();
                            });
                        }
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(self, "Unable to handle error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                finish_button.setOnClickListener(v -> {
                    // clear barcode result
                    try {
                        MainModel.setBarcodeResult(null);
                    } catch (Exception f) {
                        f.printStackTrace();
                    }
                    finish();
                });
            }
        }
    }

    private void checkIfClosed(String exp_id) {
        try {
            CollectionReference exp_list = MainModel.getExperimentReference();
            exp_list.document(exp_id).get().addOnCompleteListener(task -> {
                if(task.isSuccessful()) {
                    DocumentSnapshot experiment = task.getResult();
                    Boolean is_closed = experiment.getBoolean("isEnded");
                    Boolean is_published = experiment.getBoolean("isPublished");
                    if ((is_closed != null && is_closed) || (is_published != null && !is_published)) {
                        AlertDialog alertDialog = new AlertDialog.Builder(self, R.style.AlertDialogTheme)
                                .setCancelable(false)
                                .setMessage("Sorry, the target experiment you scanned is closed or unpublished")
                                .setPositiveButton("Exit", (dialog, which) -> {
                                    try {
                                        MainModel.setBarcodeResult(null);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    self.finish();
                                })
                                .create();
                        alertDialog.show();
                        alertDialog.getButton(alertDialog.BUTTON_POSITIVE).setTextColor(Color.WHITE);
                    } else { // proceed to next step (add geolocation)
                        addGeolocation(exp_id);
                    }
                } else { // ignore this step and proceed
                   if (task.getException() != null) {
                       task.getException().printStackTrace();
                   }
                   addGeolocation(exp_id);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @NonNull
    private String getField(@NonNull DocumentSnapshot doc, @NonNull String field_name) {
        Object attempt = doc.get(field_name);
        String result;
        if (attempt == null) {
            Toast.makeText(self, "Error: Unable to get " + field_name, Toast.LENGTH_SHORT).show();
            Log.e("Camera Scan result:", "Unable to get " + field_name);
            result = getResources().getString(R.string.not_recognized);
        } else {
            result = attempt.toString();
        }
        return result.trim();
    }

    private void addGeolocation(String exp_id) {
        try {
            CollectionReference exp_list = MainModel.getExperimentReference();
            exp_list.document(exp_id).get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    DocumentSnapshot experiment = task.getResult();
                    Boolean is_required = experiment.getBoolean("isGeolocationRequired");
                    if (is_required != null && is_required) {
                        showWarningDialog();
                        String exp_desc = getField(experiment, "description");
                        add_geo_button.setVisibility(View.VISIBLE);
                        add_geo_button.setOnClickListener(v -> {
                            Intent intent = new Intent(this, GeolocationActivity.class);
                            intent.putExtra("Map Request Code", "User Location");
                            intent.putExtra("Experiment Description", exp_desc);
                            startActivityForResult(intent, MAP_REQUEST_CODE);
                        });
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showWarningDialog() {
        AlertDialog.Builder alert_builder = new AlertDialog.Builder(self, R.style.AlertDialogTheme);
        alert_builder.setCancelable(false);
        alert_builder.setMessage("Warning: The target experiment requires your current location\n" +
                "Do you agree on sharing your location data?");
        alert_builder.setPositiveButton("Agree", (dialog, which) -> dialog.cancel());
        alert_builder.setNegativeButton("Decline", (dialog, which) -> {
            dialog.cancel();
            Toast.makeText(self, "Sorry, your location is required for this experiment", Toast.LENGTH_SHORT).show();
            try {
                MainModel.setBarcodeResult(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
            self.finish();
        });
        AlertDialog dialog = alert_builder.create();
        dialog.show();
        // NOTE: setting color is effective only after the dialog is shown
        dialog.getButton(dialog.BUTTON_NEGATIVE).setTextColor(Color.WHITE);
        dialog.getButton(dialog.BUTTON_POSITIVE).setTextColor(Color.WHITE);
    }

    private void setExperimentDesc(@NonNull TextView exp_desc, String exp_id) {
        try {
            CollectionReference experiments = MainModel.getExperimentReference();
            experiments.document(exp_id).get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    String text = getField(task.getResult(), "description");
                    exp_desc.setText(text);
                } else {
                    exp_desc.setText(R.string.not_recognized);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            exp_desc.setText(R.string.not_recognized);
        }
    }

    private void addContributor() {

        try {
            CollectionReference experiments = MainModel.getExperimentReference();
            experiments.document(exp_id).update("experimenters", FieldValue.arrayUnion(MainModel.getCurrentUser().getId()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
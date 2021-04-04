package com.example.appraisal.UI.main_menu.specific_experiment_details.qr_scanner;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appraisal.R;
import com.example.appraisal.backend.experiment.Experiment;
import com.example.appraisal.backend.trial.TrialType;
import com.example.appraisal.model.core.MainModel;
import com.example.appraisal.model.main_menu.specific_experiment_details.QRAnalyzerModel;
import com.example.appraisal.backend.specific_experiment.QRValues;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;

import org.w3c.dom.Document;

public class CameraScanResult extends AppCompatActivity {

    private final int CAMERA_SCANNER_REQUEST_CODE = 0x00000001;

    private QRAnalyzerModel model;
    private Activity self;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_scan_result);
        self = this;
        model = new QRAnalyzerModel(this);
        // Initialize the cancel button
        Button cancel_button = findViewById(R.id.camera_scan_result_cancel);
        cancel_button.setOnClickListener(v -> {
            try {
                // remove the stored barcode result
                MainModel.setBarcodeResult(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
            finish();
        });

        try {
            // Check if result is already obtained
            Result test = MainModel.getBarcodeResult();
            if (test == null) {
                throw new Exception("Barcode is null");
            } else {
                displayResult(test);
            }
        } catch (Exception e) {
            Log.d("CameraScannerResult:","No Result detected. Starting camera scanner");
            Intent intent = new Intent(this, CameraScanner.class);
            startActivityForResult(intent, CAMERA_SCANNER_REQUEST_CODE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_SCANNER_REQUEST_CODE) { // we are only processing camera scanner activity
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
        }
    }

    private void displayResult(Result result) throws Exception {
        // We are gonna display the detected code on the activity
        model.displayBarCode(result);

        final TextView trialType = findViewById(R.id.camera_scan_result_trial_type_display);
        final TextView trialValue = findViewById(R.id.camera_scan_result_trial_value_display);
        final Button finish_button = findViewById(R.id.camera_scan_result_finish_button);

        if (result.getBarcodeFormat() == BarcodeFormat.QR_CODE) { // If the scanned code is QR code
            QRValues values = model.decodeTrialQR(result.getText());

            if (values != null && values.checkSignature()) {
                trialType.setText(values.getType().getLabel());
                trialValue.setText(String.valueOf(values.getValue()));
                finish_button.setText("ADD TO EXPERIMENT");
                finish_button.setOnClickListener(v -> {
                    try {
                        model.addToExperiment(values);
                        // clear barcode result
                        MainModel.setBarcodeResult(null);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    finish();
                });
            } else {
                trialType.setText("Not recognized");
                trialValue.setText("Not recognized");
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
            trialType.setText("Not registered");
            trialValue.setText("Not registered");

            // try to find the barcode inside the registered barcodes
            try {
                DocumentReference user = MainModel.getUserReference();
                String barcode_value = result.getText();

                // locate registered barcode list
                CollectionReference barcode_list = user.collection("Barcodes");

                // query the barcode list
                barcode_list.document(barcode_value).get().addOnCompleteListener(task -> {
                    // If task is not successful or document does not exist, return
                    if (!task.isSuccessful() || !task.getResult().exists()) {
                        if (task.getException() != null) { // print all possible errors
                            task.getException().printStackTrace();
                        }
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

                        // attempt to get trial type
                        Object attempt = document.get("trialType");
                        String trial_type;
                        if (attempt == null) {
                            trial_type = "NOT RECOGNIZED";
                        } else {
                            trial_type = attempt.toString();
                        }

                        // attempt to get barcode action
                        attempt = document.get("action");
                        String data;
                        if (attempt == null) {
                            data = "NOT RECOGNIZED";
                        } else {
                            data = attempt.toString();
                        }

                        // attempt to get target experiment id
                        attempt = document.get("targetExperimentId");
                        String exp_id;
                        if (attempt == null) {
                            Toast.makeText(self, "Error: Unable to get target experiment ID", Toast.LENGTH_SHORT).show();
                            Log.e("Camera Scan result:", "Unable to get target experiment ID");
                            exp_id = "NOT RECOGNIZED";
                        } else {
                            exp_id = attempt.toString();
                        }

                        trialType.setText(trial_type);
                        trialValue.setText(data);
                        if (registered_barcode.equalsIgnoreCase(result.getText())) {
                            // Set the finish button to add to firebase when clicked and end the scanner
                            finish_button.setOnClickListener(v -> {
                                try {
                                    String signature = getResources().getString(R.string.app_name);
                                    TrialType type = TrialType.getInstance(trial_type);
                                    double trial_value = Double.parseDouble(data);

                                    QRValues values = new QRValues(self, signature, type, trial_value, exp_id);
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
}
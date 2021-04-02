package com.example.appraisal.UI.main_menu.specific_experiment_details.qr_scanner;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appraisal.R;
import com.example.appraisal.backend.experiment.Experiment;
import com.example.appraisal.model.core.MainModel;
import com.example.appraisal.model.main_menu.specific_experiment_details.QRAnalyzerModel;
import com.example.appraisal.backend.specific_experiment.QRValues;
import com.google.zxing.Result;

public class CameraScanResult extends AppCompatActivity {

    private final int CAMERA_SCANNER_REQUEST_CODE = 0x00000001;

    private QRAnalyzerModel model;
    private Experiment target_experiment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_scan_result);

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
            target_experiment = MainModel.getCurrentExperiment();
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
                    //TODO: Once QR code decoding has finished, need to perform corresponding query
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
        QRValues values = model.decodeTrialQR(result.getText());
        TextView trialType = findViewById(R.id.camera_scan_result_trial_type_display);
        TextView trialValue = findViewById(R.id.camera_scan_result_trial_value_display);
        Button finish_button = findViewById(R.id.camera_scan_result_finish_button);
        if (values != null) {
            //TODO
            String qr_type = values.getType().toString();
            if (!qr_type.trim().equalsIgnoreCase(target_experiment.getType())) {
                Log.d("CameraScanResult", "Error: QR type incompatible");
            }
        } else if (values != null && values.checkSignature()) {
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
    }
}
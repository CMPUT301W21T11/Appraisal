package com.example.appraisal.UI.main_menu.specific_experiment_details.qr_scanner;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appraisal.R;
import com.example.appraisal.backend.experiment.Experiment;
import com.example.appraisal.backend.user.User;
import com.example.appraisal.model.core.MainModel;
import com.example.appraisal.model.main_menu.specific_experiment_details.Barcode;
import com.example.appraisal.model.main_menu.specific_experiment_details.BarcodeAnalyzerModel;
import com.google.zxing.Result;

public class BarcodeScanResult extends AppCompatActivity {

    private final int CAMERA_SCANNER_REQUEST_CODE = 0x00000001;
    private BarcodeAnalyzerModel model;
    private String currentExperimentType = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get current experiment type to display barcode page accordingly
        try {
            currentExperimentType = MainModel.getCurrentExperiment().getType();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (currentExperimentType.equals("Count-based trials")) { // need to fix MainModel for inconsistency
            setContentView(R.layout.activity_barcode_scan_result_count);
        } else if (currentExperimentType.equals("Binomial Trials")) {
            setContentView(R.layout.activity_barcode_scan_result_binomial);
        } else if (currentExperimentType.equals("Non-negative Integer Trials")) {
            setContentView(R.layout.activity_barcode_scan_result_integer);
        } else if (currentExperimentType.equals("Measurement Trials")) {
            setContentView(R.layout.activity_barcode_scan_result_measurement);
        }


        model = new BarcodeAnalyzerModel(this);

        Intent intent = new Intent(this, CameraScanner.class);
        startActivityForResult(intent, CAMERA_SCANNER_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_SCANNER_REQUEST_CODE) {
            try {
                Result result = MainModel.getBarcodeResult();
                if (result == null) {
                    return;
                }

                model.readingQRCode(result);
                String[] commands = model.decodeTrialQR(result.getText());

                // test code
                for (int i = 0; i < commands.length; i++){
                    Log.d("length", String.valueOf(commands.length));
                    Log.d("Commands", commands[i]);
                }

                TextView trialType = findViewById(R.id.barcode_scan_result_trial_type_display);
                TextView trialValue = findViewById(R.id.barcode_scan_result_trial_value_display);
                Button finish_button = findViewById(R.id.barcode_scan_result_finish_button);
//                if (commands != null && (commands.length > 4) && model.checkSignature(commands[0])) {
//                    trialType.setText(commands[1]);
//                    trialValue.setText(commands[2]);
//                    finish_button.setText("ADD TO EXPERIMENT");
//                    finish_button.setOnClickListener(v -> {
//                        try {
//                            model.addToExperiment(commands[3], commands[1], commands[2]);
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                        finish();
//                    });
//                } else {
//                    trialType.setText("Not recognized");
//                    trialValue.setText("Not recognized");
//                    finish_button.setOnClickListener(v -> finish());
//                }

                // Once the barcode is read
                trialType.setText(currentExperimentType);
                trialValue.setText("placeholder value"); // possibly deleting value space later
                finish_button.setOnClickListener(v -> finish());

                // set onClickListener based on experiment type
                if (currentExperimentType.equals("Count-based trials")) { // need to fix MainModel for inconsistency
                    Button increment_button = findViewById(R.id.barcode_scan_result_count_increment);
                    increment_button.setOnClickListener(v -> {
                        try {
                            createBarcode(result.getText(), MainModel.getCurrentUser(), MainModel.getCurrentExperiment(), null);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                } else if (currentExperimentType.equals("Binomial Trials")) {
                    Button success_button = findViewById(R.id.barcode_scan_result_binomial_sucess);
                    Button failure_button = findViewById(R.id.barcode_scan_result_binomial_failure);
                    success_button.setOnClickListener(v -> {
                        try {
                            createBarcode(result.getText(), MainModel.getCurrentUser(), MainModel.getCurrentExperiment(), "1");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                    failure_button.setOnClickListener(v -> {
                        try {
                            createBarcode(result.getText(), MainModel.getCurrentUser(), MainModel.getCurrentExperiment(), "0");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                } else if (currentExperimentType.equals("Non-negative Integer Trials")) {
                    Button integer_assign = findViewById(R.id.barcode_scan_result_integer_assign);
                    EditText integer_field = findViewById(R.id.barcode_scan_result_integer_input);
                    integer_assign.setOnClickListener(v -> {
                        try {
                            createBarcode(result.getText(), MainModel.getCurrentUser(), MainModel.getCurrentExperiment(), integer_field.getText().toString());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                } else if (currentExperimentType.equals("Measurement Trials")) {
                    Button measurement_assign = findViewById(R.id.barcode_scan_result_measurement_assign);
                    EditText textField = findViewById(R.id.barcode_scan_result_measurement_input);
                    measurement_assign.setOnClickListener(v -> {
                        try {
                            createBarcode(result.getText(), MainModel.getCurrentUser(), MainModel.getCurrentExperiment(), textField.getText().toString());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                }
            } catch (Exception e) {
                Log.e("CameraScanResult: ", "Error when processing barcode: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            finish();

        }
    }

    public void createBarcode(String rawValue, User user, Experiment experiment, String data) {
        Barcode barcode = new Barcode(rawValue, user, experiment, data);
        boolean barcodeSuccess = model.assignBarcode(barcode);
        if (barcodeSuccess) {
            finish();
            Toast.makeText(this, "Successfully registered barcode", Toast.LENGTH_SHORT).show();
            model.showBarcodes();
        } else {
            Toast.makeText(this, "Barcode already registered", Toast.LENGTH_SHORT).show();
        }
    }
}

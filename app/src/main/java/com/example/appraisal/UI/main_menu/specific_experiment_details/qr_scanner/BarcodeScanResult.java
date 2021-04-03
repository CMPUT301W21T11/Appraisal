package com.example.appraisal.UI.main_menu.specific_experiment_details.qr_scanner;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appraisal.R;
import com.example.appraisal.backend.experiment.Experiment;
import com.example.appraisal.backend.trial.TrialType;
import com.example.appraisal.backend.user.User;
import com.example.appraisal.model.core.MainModel;
import com.example.appraisal.backend.specific_experiment.Barcode;
import com.example.appraisal.model.main_menu.specific_experiment_details.BarcodeAnalyzerModel;
import com.google.zxing.Result;

public class BarcodeScanResult extends AppCompatActivity {

    private final int BARCODE_REGISTER_REQUEST_CODE = 0x00000002;
    private BarcodeAnalyzerModel model;
    private TrialType currentExperimentType;
    private User current_user;
    private Experiment current_experiment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get current experiment type to display barcode page accordingly
        try {
            currentExperimentType = TrialType.getInstance(MainModel.getCurrentExperiment().getType());
            current_user = MainModel.getCurrentUser();
            current_experiment = MainModel.getCurrentExperiment();
        } catch (Exception e) {
            e.printStackTrace();
        }

        switch (currentExperimentType) {
            case COUNT_TRIAL:
                setContentView(R.layout.activity_barcode_scan_result_count);
                break;
            case BINOMIAL_TRIAL:
                setContentView(R.layout.activity_barcode_scan_result_binomial);
                break;
            case NON_NEG_INT_TRIAL:
                setContentView(R.layout.activity_barcode_scan_result_integer);
                break;
            case MEASUREMENT_TRIAL:
                setContentView(R.layout.activity_barcode_scan_result_measurement);
                break;
        }


        model = new BarcodeAnalyzerModel(this);

        Intent intent = new Intent(this, CameraScanner.class);
        startActivityForResult(intent, BARCODE_REGISTER_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == BARCODE_REGISTER_REQUEST_CODE) {
            try {
                Result result = MainModel.getBarcodeResult();
                if (result == null) {
                    return;
                }

                model.displayBarCode(result);

                TextView trialType = findViewById(R.id.barcode_scan_result_trial_type_display);
                TextView trialValue = findViewById(R.id.barcode_scan_result_trial_value_display);
                Button finish_button = findViewById(R.id.barcode_scan_result_finish_button);

                // Once the barcode is read
                trialType.setText(currentExperimentType.getLabel());
                trialValue.setText("placeholder value"); // possibly deleting value space later
                finish_button.setOnClickListener(v -> finish());

                // set onClickListener based on experiment type
                switch (currentExperimentType) {
                    case COUNT_TRIAL:
                        Button increment_button = findViewById(R.id.barcode_scan_result_count_increment);
                        increment_button.setOnClickListener(v -> createBarcode(result.getText(), current_user, current_experiment, "1"));
                        break;
                    case BINOMIAL_TRIAL:
                        Button success_button = findViewById(R.id.barcode_scan_result_binomial_sucess);
                        Button failure_button = findViewById(R.id.barcode_scan_result_binomial_failure);
                        success_button.setOnClickListener(v -> createBarcode(result.getText(), current_user, current_experiment, "1"));
                        failure_button.setOnClickListener(v -> createBarcode(result.getText(), current_user, current_experiment, "0"));
                        break;
                    case NON_NEG_INT_TRIAL:
                        Button integer_assign = findViewById(R.id.barcode_scan_result_integer_assign);
                        EditText integer_field = findViewById(R.id.barcode_scan_result_integer_input);
                        integer_assign.setOnClickListener(v -> createBarcode(result.getText(), current_user, current_experiment, integer_field.getText().toString()));
                        break;
                    case MEASUREMENT_TRIAL:
                        Button measurement_assign = findViewById(R.id.barcode_scan_result_measurement_assign);
                        EditText textField = findViewById(R.id.barcode_scan_result_measurement_input);
                        measurement_assign.setOnClickListener(v -> createBarcode(result.getText(), current_user, current_experiment, textField.getText().toString()));
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
        model.checkBarcode(barcode);
    }

    public boolean askIfOverride(Barcode barcode, String old_action) {
        //TODO create a dialog to as user
        return false;
    }
}

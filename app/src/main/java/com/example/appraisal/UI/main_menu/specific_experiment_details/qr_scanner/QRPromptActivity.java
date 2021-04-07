package com.example.appraisal.UI.main_menu.specific_experiment_details.qr_scanner;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appraisal.R;
import com.example.appraisal.backend.experiment.Experiment;
import com.example.appraisal.backend.trial.TrialType;
import com.example.appraisal.model.core.MainModel;

import org.jetbrains.annotations.NotNull;

/**
 * This method prompt for QR value input
 */
public class QRPromptActivity extends AppCompatActivity {

    private Button generate_button;
    private TextView prompt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Experiment current_exp;
        try {
            current_exp = MainModel.getCurrentExperiment();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        TrialType exp_type = TrialType.getInstance(current_exp.getType());
        switch (exp_type) {
            case NON_NEG_INT_TRIAL:
                setContentView(R.layout.activity_qr_prompt_value_input);
                setInputNonNeg(findViewById(R.id.activity_qr_bin_succ_generator));
                generate_button = findViewById(R.id.activity_qr_bin_fail_generator);
                generate_button.setOnClickListener(v -> generateQRTrialValue(exp_type));
                break;
            case MEASUREMENT_TRIAL:
                setContentView(R.layout.activity_qr_prompt_value_input);
                setInputMeasurement(findViewById(R.id.activity_qr_bin_succ_generator));
                generate_button = findViewById(R.id.activity_qr_bin_fail_generator);
                generate_button.setOnClickListener(v -> generateQRTrialValue(exp_type));
                break;
            case COUNT_TRIAL:
                setContentView(R.layout.activity_qr_prompt_value_input);
                generateCountTrial();
                break;
            default:
                setContentView(R.layout.activity_qr_prompt_bin_trial);
                generateQRBinTrial();
        }

        prompt = findViewById(R.id.qr_prompt_exp_title);
        prompt.setText(current_exp.getDescription());
        TextView exp_type_display = findViewById(R.id.activity_qr_prompt_exp_type);
        exp_type_display.setText(exp_type.getLabel());
    }

    private void setInputNonNeg(@NotNull EditText trial_value_qr_input) {
        trial_value_qr_input.setHint("Enter Integer value");
        trial_value_qr_input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_NUMBER);
    }

    private void setInputMeasurement(@NotNull EditText trial_value_qr_input) {
        trial_value_qr_input.setHint("Enter Measurement");
        trial_value_qr_input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
    }

    private void generateQRTrialValue(TrialType exp_type) {
        EditText trial_value_qr_input = findViewById(R.id.activity_qr_bin_succ_generator);
        if (trial_value_qr_input.getText().toString().trim().equals("")) {
            return;
        }

        Intent intent = new Intent(this, QRPhotoActivity.class);

        String value = trial_value_qr_input.getText().toString();
        intent.putExtra("val", value);
        finish();
        startActivity(intent);
    }

    private void generateQRBinTrial() {
        Intent intent = new Intent(this, QRPhotoActivity.class);

        Button fail_button = findViewById(R.id.activity_qr_bin_fail_generator);
        fail_button.setOnClickListener(v -> {
            intent.putExtra("val", "0"); // failure
            finish();
            startActivity(intent);
        });

        Button succ_button = findViewById(R.id.activity_qr_bin_succ_generator);
        succ_button.setOnClickListener(v -> {
            intent.putExtra("val", "1"); // success
            finish();
            startActivity(intent);
        });
    }

    private void generateCountTrial() {
        Intent intent = new Intent(this, QRPhotoActivity.class);
        intent.putExtra("val","1");
        finish(); // finish this generator prompt and start the QR generator
        startActivity(intent);
    }
}

package com.example.appraisal.UI.main_menu.specific_experiment_details.qr_scanner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appraisal.R;
import com.example.appraisal.backend.experiment.Experiment;
import com.example.appraisal.model.core.MainModel;

public class QRPromptActivity extends AppCompatActivity {

    private EditText trial_value_qr_input;
    private TextView prompt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_prompt);

        trial_value_qr_input = findViewById(R.id.qr_input_display);
        prompt = findViewById(R.id.qr_prompt_display);

        Experiment current_exp = null;
        try {
            current_exp = MainModel.getCurrentExperiment();
        } catch (Exception e) {
            e.printStackTrace();
        }

        prompt.setText("This is a ... experiment, set the value for this trial:");
    }

    public void generateQR(View view) {
        if (trial_value_qr_input.getText().toString().trim().equals("")) {
            return;
        }

        Intent intent = new Intent(this, QRPhotoActivity.class);

        String value = trial_value_qr_input.getText().toString();
        intent.putExtra("val", value);

        startActivity(intent);
    }
}

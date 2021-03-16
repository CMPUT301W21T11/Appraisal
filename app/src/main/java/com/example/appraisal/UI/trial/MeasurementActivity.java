package com.example.appraisal.UI.trial;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import com.example.appraisal.R;
import com.example.appraisal.model.trial.MeasurementModel;

public class MeasurementActivity extends AppCompatActivity {
    private EditText input_measurement;
    private MeasurementModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_measurement);

        input_measurement = findViewById(R.id.inputMeasurement);
        model = new MeasurementModel();
    }

    public void addMeasurement(View view) {
        String input = input_measurement.getText().toString();
        model.addMeasurement(input);
        input_measurement.setText("0.00");
        model.toExperiment();
    }
}

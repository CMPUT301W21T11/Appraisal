package com.example.appraisal.UI.trial;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import com.example.appraisal.R;
import com.example.appraisal.backend.experiment.Experiment;
import com.example.appraisal.model.MainModel;
import com.example.appraisal.model.trial.MeasurementModel;

public class MeasurementActivity extends AppCompatActivity {
    private EditText input_measurement;
    private MeasurementModel model;

    /**
     * create the activity and inflate it with layout. initialize model
     * @param savedInstanceState
     *      bundle from the previous activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_measurement);

        input_measurement = findViewById(R.id.inputMeasurement);
        try {
            Experiment experiment = MainModel.getCurrentExperiment();
            model = new MeasurementModel(experiment);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Save measurement to experiment
     * @param view save button
     */
    public void addMeasurement(View view) {
        String input = input_measurement.getText().toString();
        model.addMeasurement(input);
        input_measurement.setText("0.00");
        model.toExperiment();
    }
}

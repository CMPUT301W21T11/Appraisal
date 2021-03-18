package com.example.appraisal.UI.trial;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import com.example.appraisal.R;
import com.example.appraisal.backend.experiment.Experiment;
import com.example.appraisal.model.MainModel;
import com.example.appraisal.model.trial.NonNegIntCountModel;

public class NonNegIntCountActivity extends AppCompatActivity {

    private NonNegIntCountModel model;
    private EditText counter_view;

    /**
     * create the activity and inflate it with layout. initialize model
     * @param savedInstanceState
     *      bundle from the previous activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nonneg_count_layout);

        counter_view = findViewById(R.id.nonneg_count_input);
        try {
            Experiment experiment = MainModel.getCurrentExperiment();
            model = new NonNegIntCountModel(experiment);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * save to experiment
     * @param v save button
     */
    public void saveAndReturn(View v) {
        // get input
        String user_input = counter_view.getText().toString();

        // Adjust the model
        model.addIntCount(user_input);
        counter_view.setText("0");
        model.toExperiment();
    }
}

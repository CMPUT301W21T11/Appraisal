package com.example.appraisal.UI.trial;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.appraisal.R;
import com.example.appraisal.backend.experiment.Experiment;
import com.example.appraisal.model.MainModel;
import com.example.appraisal.model.trial.CounterModel;

/**
 * This is the activity for adding a counter trial
 */
public class CounterActivity extends AppCompatActivity {

    private CounterModel model;
    private TextView counter_view;

    /**
     * create the activity and inflate it with layout. initialize model
     * @param savedInstanceState
     *      bundle from the previous activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counter_layout);

        counter_view = (TextView) findViewById(R.id.count_view);
        try {
            Experiment experiment = MainModel.getCurrentExperiment();
            model = new CounterModel(experiment);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * increase the counter
     * @param v onClick view
     */
    public void increment(View v) {
        // Adjust the model
        model.increase();

        // The model will change. Then request the data from model, update display
        // accordingly
        String result = String.valueOf(model.getCount());
        counter_view.setText(result);
    }

    /**
     * Save the trial to the experiment
     * @param v save button
     */
    public void save(View v) {
        model.toExperiment();
        counter_view.setText("0");
    }
}
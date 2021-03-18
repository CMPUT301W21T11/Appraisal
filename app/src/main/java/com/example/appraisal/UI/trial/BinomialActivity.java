package com.example.appraisal.UI.trial;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appraisal.R;
import com.example.appraisal.backend.experiment.Experiment;
import com.example.appraisal.model.MainModel;
import com.example.appraisal.model.trial.BinomialModel;

/**
 * This is the activity for conducting binomial activity
 */
public class BinomialActivity extends AppCompatActivity {
    private TextView success_txt;
    private TextView failure_txt;
    private BinomialModel model;

    /**
     * create the activity and inflate it with layout. initialize model
     * @param savedInstanceState
     *      bundle from the previous activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_binomial);

        success_txt = findViewById(R.id.success_count);
        failure_txt = findViewById(R.id.failure_count);

        Experiment current_experiment;
        try {
            current_experiment = MainModel.getCurrentExperiment();
            model = new BinomialModel(current_experiment);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Increase the success count of the trial
     * @param v increase button
     */
    public void incrementSuccess(View v){
        // adjust model
        model.addSuccess();

        // get new data from model, and set text
        String count = String.valueOf(model.getSuccessCount());
        success_txt.setText(count);
    }

    /**
     * Increase the failure count of the trial
     * @param v increase button
     */
    public void incrementFailure(View v){
        //adjust model
        model.addFailure();

        // get new data from model, and set text
        String count = String.valueOf(model.getFailureCount());
        failure_txt.setText(count);
    }

    /**
     * Save the trial to the experiment
     * @param v save button
     */
    public void save(View v) {
        model.toExperiment();
        success_txt.setText("0");
        failure_txt.setText("0");
    }

}

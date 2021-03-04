package com.example.appraisal.UI;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.example.appraisal.R;
import com.example.appraisal.backend.Experiment;
import com.example.appraisal.model.CounterModel;

public class CounterActivity extends AppCompatActivity {

    private CounterModel model;
    private TextView counter_view, exp_description, exp_type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counter_layout);
        TextViewInit();

        // Experiment currentExperiment = (Experiment) getIntent().getSerializableExtra("currentExperiment");
        Experiment currentExperiment = new Experiment("Watching blue cars", "Test description", "Count trial",null,null);
        model = new CounterModel(currentExperiment);
        exp_description.setText(currentExperiment.getDescription());
        exp_type.setText(currentExperiment.getType());
    }

    public void increment(View v) {
        // Adjust the model
        try {
            model.increase();
        } catch (ArithmeticException e) {
            Toast toast = new Toast(this);
            toast.setText("No more count can be added.");
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.show();
        }


        // The model will change. Then request the data from model, update display
        // accordingly
        String result = String.valueOf(model.getCount());
        counter_view.setText(result);
    }

    private void TextViewInit() {
        counter_view = (TextView) findViewById(R.id.activityCounterLayoutCounterView);
        exp_description = (TextView) findViewById(R.id.activityCounterLayoutDescription);
        exp_type = (TextView) findViewById(R.id.activityCounterLayoutExperimentType);
    }
}
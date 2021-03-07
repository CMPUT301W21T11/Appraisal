package com.example.appraisal.UI.trial;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.appraisal.R;
import com.example.appraisal.model.trial.CounterModel;

public class CounterActivity extends AppCompatActivity {

    private CounterModel model;
    private TextView counter_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counter_layout);

        counter_view = (TextView) findViewById(R.id.count_view);
        model = new CounterModel();
    }

    public void increment(View v) {
        // Adjust the model
        model.increase();

        // The model will change. Then request the data from model, update display
        // accordingly
        String result = String.valueOf(model.getCount());
        counter_view.setText(result);
    }
}
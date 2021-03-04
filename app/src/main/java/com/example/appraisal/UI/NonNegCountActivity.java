package com.example.appraisal.UI;

import android.os.Bundle;
import android.widget.TextView;

import com.example.appraisal.R;
import com.example.appraisal.backend.Experiment;
import com.example.appraisal.model.NonNegativeIntegerModel;

import androidx.appcompat.app.AppCompatActivity;

public class NonNegCountActivity extends AppCompatActivity {

    private NonNegativeIntegerModel model;
    private TextView nonNegDescriptionText, nonNegTypeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nonneg_count);
        TextViewInit();

        Experiment currentExperiment = new Experiment("Watching blue cars", "Test description", "Non-Negative integer trial",null,null);
        model = new NonNegativeIntegerModel(currentExperiment);
        nonNegDescriptionText.setText(currentExperiment.getDescription());
        nonNegTypeText.setText(currentExperiment.getType());
    }

    private void TextViewInit() {
        nonNegDescriptionText = (TextView) findViewById(R.id.nonNegDescriptionText);
        nonNegTypeText = (TextView) findViewById(R.id.nonNegTypeText);
    }
}

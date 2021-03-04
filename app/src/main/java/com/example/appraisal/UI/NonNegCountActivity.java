package com.example.appraisal.UI;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appraisal.R;
import com.example.appraisal.backend.Experiment;
import com.example.appraisal.model.NonNegativeIntegerModel;

import androidx.appcompat.app.AppCompatActivity;

public class NonNegCountActivity extends AppCompatActivity {

    private NonNegativeIntegerModel model;
    private TextView nonNegDescriptionText, nonNegTypeText;
    private EditText countResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nonneg_count);
        ViewInit();

        // test code
        Experiment currentExperiment = new Experiment("Watching blue cars", "Test description", "Non-Negative integer trial",null,null);

        model = new NonNegativeIntegerModel(currentExperiment);
        nonNegDescriptionText.setText(currentExperiment.getDescription());
        nonNegTypeText.setText(currentExperiment.getType());
    }

    public void saveToExperiment(View v) {
        Toast toast = new Toast(this);
        toast.setDuration(Toast.LENGTH_SHORT);
        try {
            String userInput = countResult.getText().toString();
            long count = Long.parseLong(userInput);
            model.saveToExperiment(count);
        } catch (NumberFormatException e) {
            toast.setText("Holy crap what are you trying to count my goddess");
            toast.show();
            return;
        } catch (Exception e) {
            toast.setText("Integer must be non-negative!");
            toast.show();
            return;
        }
        toast.setText("Success!");
        toast.show();
        finish();
    }

    private void ViewInit() {
        nonNegDescriptionText = (TextView) findViewById(R.id.activityNonNegDescriptionText);
        nonNegTypeText = (TextView) findViewById(R.id.activityNonNegTypeText);
        countResult = (EditText) findViewById(R.id.activityNonnegCountResultEditText);
    }
}

package com.example.appraisal.UI.trial;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import com.example.appraisal.R;
import com.example.appraisal.model.trial.NonNegIntCountModel;

public class NonNegIntCountActivity extends AppCompatActivity {

    private NonNegIntCountModel model;
    private EditText counter_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nonneg_count_layout);

        counter_view = findViewById(R.id.nonneg_count_input);
        model = new NonNegIntCountModel();
    }

    public void saveAndReturn(View v) {
        // get input
        String user_input = counter_view.getText().toString();

        // Adjust the model
        model.addIntCount(user_input);
        counter_view.setText("");
    }
}

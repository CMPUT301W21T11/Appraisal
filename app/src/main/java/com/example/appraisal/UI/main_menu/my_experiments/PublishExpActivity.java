package com.example.appraisal.UI.main_menu.my_experiments;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appraisal.R;
import com.example.appraisal.UI.main_menu.my_experiments.MyExperimentActivity;
import com.example.appraisal.backend.experiment.ExpContainer;
import com.example.appraisal.backend.experiment.Experiment;

import java.util.ArrayList;

public class PublishExpActivity extends AppCompatActivity {
    private EditText description_field;
    private Spinner type_field;
    private RadioButton geo_yes;
    private EditText min_trials_field;
    private EditText rules_field;
    private EditText region_field;

    public static ArrayList<Experiment> data_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish_exp);

        //initialize the data
        description_field = findViewById(R.id.expDesc);
        type_field = findViewById(R.id.exp_types);
        geo_yes = findViewById(R.id.radioButtonYes);
        min_trials_field = (EditText) findViewById(R.id.expMinTrials);
        rules_field = findViewById(R.id.expRules);
        region_field = findViewById(R.id.expRegion);

    }

    public void createNewExp(View view){
        String owner = "TempOwner";
        String description = description_field.getText().toString();
        String type = type_field.getSelectedItem().toString();
        boolean is_geolocation_required = geo_yes.isChecked();
        String rules = rules_field.getText().toString();
        String region = region_field.getText().toString();

        int min_trials;
        if (min_trials_field.getText().length() == 0){
            min_trials = 0;
        }
        else {
            min_trials = Integer.parseInt(min_trials_field.getText().toString());
        }

        Experiment experiment = new Experiment(owner, description, type, is_geolocation_required, min_trials, rules, region);

        data_list = ExpContainer.getExpList();
        data_list.add(experiment);
        navigateUpTo(new Intent(getBaseContext(), MyExperimentActivity.class));
    }
}

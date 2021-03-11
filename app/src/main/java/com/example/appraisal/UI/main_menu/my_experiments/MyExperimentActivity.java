package com.example.appraisal.UI.main_menu.my_experiments;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.appraisal.UI.main_menu.MainMenuCommonActivity;
import com.example.appraisal.backend.experiment.ExpAdapter;
import com.example.appraisal.R;
import com.example.appraisal.backend.experiment.ExpContainer;
import com.example.appraisal.backend.experiment.Experiment;
import com.example.appraisal.model.MainModel;
import com.example.appraisal.model.MyExperimentModel;

import java.util.ArrayList;

public class MyExperimentActivity extends MainMenuCommonActivity {
    private ListView my_experiment_display;
    private MyExperimentModel model;
    private ArrayAdapter<Experiment> adapter;
    private ArrayList<Experiment> exp_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_exp);
        ExpContainer.getInstance();

        my_experiment_display = findViewById(R.id.my_experiments);

        exp_list = ExpContainer.getExpList();
        model = new MyExperimentModel();

        MainModel.getInstance();

        adapter = new ExpAdapter(this, exp_list);
        my_experiment_display.setAdapter(adapter);
    }

    public void publishNewExperiment(View v) {
        Intent publishExp_intent = new Intent(this, PublishExpActivity.class);
        startActivity(publishExp_intent);
    }


}

package com.example.appraisal.UI.main_menu.my_experiment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.example.appraisal.R;
import com.example.appraisal.UI.main_menu.MainMenuCommonActivity;
import com.example.appraisal.UI.main_menu.SelectionActivity;
import com.example.appraisal.backend.experiment.Experiment;
import com.example.appraisal.backend.user.User;
import com.example.appraisal.model.MainModel;
import com.example.appraisal.model.MyExperimentModel;

public class MyExperimentActivity extends MainMenuCommonActivity {
    private ListView my_experiment_display;
    private MyExperimentModel model;
    private ArrayAdapter<String> adapter;
    //private final CollectionReference my_exp_reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_exp);

        my_experiment_display = findViewById(R.id.my_experiments);
        model = new MyExperimentModel();

        MainModel.createInstance();

        //my_exp_reference = db.collection("Cities");
        
    }

    public void toNewExperiment(View view) throws Exception {
        Intent intent = new Intent(this, SelectionActivity.class);

        // TODO to be refactored
        Experiment dummy_experiment = new Experiment("Dummy title", "Dummy description",
               MainModel.getCurrentUser());
    }
}
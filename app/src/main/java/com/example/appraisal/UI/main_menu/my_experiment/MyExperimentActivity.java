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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MyExperimentActivity extends MainMenuCommonActivity {
    private ListView my_experiment_display;
    private MyExperimentModel model;
    private ArrayAdapter<String> adapter;
    private List<Experiment> dummy_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_exp);

        my_experiment_display = findViewById(R.id.my_experiments);
        model = new MyExperimentModel();

        MainModel.createInstance();

        User local_var_user = null;
        try {
            local_var_user = MainModel.getCurrentUser();
        } catch (Exception e) {
            e.printStackTrace();
        }

        dummy_list = new ArrayList<>();
        dummy_list.add(new Experiment("Dummy title1", "Dummy description1", local_var_user));
        dummy_list.add(new Experiment("Dummy title2", "Dummy description2", local_var_user));
        dummy_list.add(new Experiment("Dummy title3", "Dummy description3", local_var_user));

        List<String> name_list;

        name_list = dummy_list.stream().map(Experiment::getTitle).collect(Collectors.toList());

        adapter = new ArrayAdapter<>(this, R.layout.list_content, name_list);
        my_experiment_display.setAdapter(adapter);
    }

    public void toNewExperiment(View view) throws Exception {
        Intent intent = new Intent(this, SelectionActivity.class);

        // TODO to be refactored
        Experiment dummy_experiment = new Experiment("Dummy title", "Dummy description",
               MainModel.getCurrentUser());

        MainModel.setCurrentExperiment(dummy_experiment);
        startActivity(intent);
    }
}

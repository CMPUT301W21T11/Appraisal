package com.example.appraisal.UI;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.appraisal.R;
import com.example.appraisal.model.MainModel;
import com.example.appraisal.model.MeasurementModel;
import com.example.appraisal.model.MyExperimentModel;
import com.google.firebase.firestore.CollectionReference;

import java.util.List;

public class MyExperimentActivity extends AppCompatActivity {
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

        MainModel.getInstance();

        //my_exp_reference = db.collection("Cities");
        
    }


}

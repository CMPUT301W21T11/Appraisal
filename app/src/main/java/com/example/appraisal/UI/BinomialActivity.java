package com.example.appraisal.UI;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appraisal.R;
import com.example.appraisal.model.BinomialModel;


public class BinomialActivity extends AppCompatActivity {
    private TextView success_txt;
    private TextView failure_txt;
    private BinomialModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_binomial);

        success_txt = findViewById(R.id.success_count);
        failure_txt = findViewById(R.id.failure_count);
        model = new BinomialModel();
    }

    public void incrementSuccess(View v){
        // adjust model
        model.addSuccess();

        // get new data from model, and set text
        String count = String.valueOf(model.getSuccessCount());
        success_txt.setText(count);
    }

    public void incrementFailure(View v){
        //adjust model
        model.addFailure();

        // get new data from model, and set text
        String count = String.valueOf(model.getFailureCount());
        failure_txt.setText(count);
    }

}

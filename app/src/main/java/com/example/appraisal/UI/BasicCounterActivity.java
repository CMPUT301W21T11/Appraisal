package com.example.appraisal.UI;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appraisal.R;

public class BasicCounterActivity extends AppCompatActivity {

     
    private int counter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counter_layout);

        TextView counterView = (TextView) findViewById(R.id.count_view);

        counter = 0;

        Button counterButton = (Button) findViewById(R.id.add_btn);

        counterButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                // increment the counter by 1
                counter++;
                
                counterView.setText(Integer.toString(counter));
            }
        });






    

    }





}
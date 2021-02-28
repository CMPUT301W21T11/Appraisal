package com.example.appraisal.UI;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appraisal.R;



public class BinomialActivity extends AppCompatActivity {
    int success_counter = 0;
    int failure_counter = 0;

    Button success_btn;
    Button failure_btn;

    TextView success_txt;
    TextView failure_txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_binomial);
        success_btn_func();
        failure_btn_func();

    }

    public void increaseType1Result(View v) {
        // TODO
    }

    public void increaseType2Result(View v) {
        // TODO
    }

    public void submit(View v) {
        // TODO
    }


    public void failure_btn_func() {
        failure_btn = findViewById(R.id.failure_button);
        failure_txt = findViewById(R.id.failure_count);

        failure_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                failure_counter = failure_counter + 1;
                failure_txt.setText(Integer.toString(failure_counter));
            }
        });
    }

    public void success_btn_func() {
        success_btn = findViewById(R.id.success_button);
        success_txt = findViewById(R.id.success_count);

        success_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                success_counter = success_counter + 1;
                success_txt.setText(Integer.toString(success_counter));
            }
        });
    }
}

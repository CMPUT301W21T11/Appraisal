package com.example.appraisal.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import com.example.appraisal.R;

public class SelectionActivity extends AppCompatActivity {
    // TODO
    // Needs 4 button action for the user to choose the experiment type, then bring the user
    // to the corresponding activity

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);



    }

    public void toBinomial(View v) {
        // TODO
    }

    public void toCounter(View v) {
            
        Intent intent = new Intent(this, BasicCounterActivity.class);
        startActivity(intent);
    }

    public void toMeasurement(View v) {
        // TODO
    }

    public void toIntCount(View v) {
        // TODO
    }
}
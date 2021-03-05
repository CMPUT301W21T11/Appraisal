package com.example.appraisal.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appraisal.R;

public class DetailSelectionActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_selection);

    }

    public void toDetails(View v) {
        Intent intent = new Intent(this, DetailsActivity.class);
        startActivity(intent);
    }

    public void toDataAnalysis(View v) {
        Intent intent = new Intent(this, DataAnalysisActivity.class);
        startActivity(intent);
    }

    public void toContributors(View v) {

    }
}

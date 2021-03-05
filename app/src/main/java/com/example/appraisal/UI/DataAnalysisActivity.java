package com.example.appraisal.UI;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appraisal.R;

public class DataAnalysisActivity extends AppCompatActivity {
    ImageView quartilesImage;
    ImageView histogramImage;
    ImageView plotsImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.experiment_data_analysis);
        quartilesImage = (ImageView) findViewById(R.id.quartilesImage);
        histogramImage = (ImageView) findViewById(R.id.histogramImage);
        plotsImage = (ImageView) findViewById(R.id.plotsImage);
        // Hide image until button is clicked
        quartilesImage.setVisibility(View.GONE);
        histogramImage.setVisibility(View.GONE);
        plotsImage.setVisibility(View.GONE);

    }

    public void toggle_quartiles(View v) {
        quartilesImage.setVisibility(quartilesImage.isShown()
                                    ? View.GONE
                                    : View.VISIBLE);

    }

    public void toggle_histogram(View v) {
        histogramImage.setVisibility(histogramImage.isShown()
                ? View.GONE
                : View.VISIBLE);

    }

    public void toggle_plots(View v) {
        plotsImage.setVisibility(plotsImage.isShown()
                ? View.GONE
                : View.VISIBLE);

    }
}

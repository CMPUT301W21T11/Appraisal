package com.example.appraisal.UI.SpecificExpFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.appraisal.R;

public class SpecificExpDataAnalysisFragment extends Fragment {
    private ImageView quartilesImage;
    private ImageView histogramImage;
    private ImageView plotsImage;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_experiment_data_analysis, container, false);
        imageViewInit(v);
        dropInit(v);
        return v;
    }

    private void imageViewInit(View v) {
        // initialize views
        quartilesImage = (ImageView) v.findViewById(R.id.fragment_experiment_data_analysis_quartilesImage);
        histogramImage = (ImageView) v.findViewById(R.id.fragment_experiment_data_analysis_histogramImage);
        plotsImage = (ImageView) v.findViewById(R.id.fragment_experiment_data_analysis_plotsImage);
        // Hide image until button is clicked
        quartilesImage.setVisibility(View.GONE);
        histogramImage.setVisibility(View.GONE);
        plotsImage.setVisibility(View.GONE);
    }

    private void dropInit(View v) {
        // initialize buttons and set on click listeners for expanding details
        ImageView histogram_drop = v.findViewById(R.id.fragment_experiment_data_analysis_histogramDrop);
        ImageView quartiles_drop = v.findViewById(R.id.fragment_experiment_data_analysis_quartilesDrop);
        ImageView plot_drop = v.findViewById(R.id.fragment_experiment_data_analysis_plotsDrop);

        histogram_drop.setOnClickListener(v1 -> toggle_histogram(v1));
        quartiles_drop.setOnClickListener(v2 -> toggle_quartiles(v2));
        plot_drop.setOnClickListener(v3 -> toggle_plots(v3));
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

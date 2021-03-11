package com.example.appraisal.UI.main_menu.specific_experiment_details;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.appraisal.R;
import com.jjoe64.graphview.GraphView;

public class SpecificExpDataAnalysisFragment extends Fragment {
    private GraphView quartileGraph;
    private GraphView histogram;
    private GraphView exp_plot_over_time;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_experiment_data_analysis, container, false);
        imageViewInit(v);
        dropInit(v);
        return v;
    }

    private void imageViewInit(View v) {
        // initialize graphs
        quartileGraph = v.findViewById(R.id.fragment_experiment_data_analysis_quartilesGraph);
        histogram = v.findViewById(R.id.fragment_experiment_data_analysis_histogramGraph);
        exp_plot_over_time = v.findViewById(R.id.fragment_experiment_data_analysis_plotsGraph);
        // Hide graphs until button is clicked
        quartileGraph.setVisibility(View.GONE);
        histogram.setVisibility(View.GONE);
        exp_plot_over_time.setVisibility(View.GONE);
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
        quartileGraph.setVisibility(quartileGraph.isShown()
                ? View.GONE
                : View.VISIBLE);

    }

    public void toggle_histogram(View v) {
        histogram.setVisibility(histogram.isShown()
                ? View.GONE
                : View.VISIBLE);

    }

    public void toggle_plots(View v) {
        exp_plot_over_time.setVisibility(exp_plot_over_time.isShown()
                ? View.GONE
                : View.VISIBLE);

    }
}

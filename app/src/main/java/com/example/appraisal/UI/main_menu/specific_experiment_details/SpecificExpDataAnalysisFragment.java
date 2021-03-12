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
import com.example.appraisal.model.SpecificExpModel;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.Date;

public class SpecificExpDataAnalysisFragment extends Fragment {
    private GraphView quartileGraph;
    private GraphView histogram;
    private GraphView exp_plot_over_time;

    private SpecificExpModel model;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // obtain model
        model = new SpecificExpModel();

        // initialize layout
        View v = inflater.inflate(R.layout.fragment_experiment_data_analysis, container, false);
        graphViewInit(v);
        generateGraphs();
        graphDropInit(v);
        return v;
    }

    private void graphViewInit(View v) {
        // initialize graphs
        quartileGraph = v.findViewById(R.id.fragment_experiment_data_analysis_quartilesGraph);
        histogram = v.findViewById(R.id.fragment_experiment_data_analysis_histogramGraph);
        exp_plot_over_time = v.findViewById(R.id.fragment_experiment_data_analysis_plotsGraph);

        // Hide graphs until button is clicked
        quartileGraph.setVisibility(View.GONE);
        histogram.setVisibility(View.GONE);
        exp_plot_over_time.setVisibility(View.GONE);
    }

    private void graphDropInit(View v) {
        // initialize buttons and set on click listeners for expanding details
        ImageView histogram_drop = v.findViewById(R.id.fragment_experiment_data_analysis_histogramDrop);
        ImageView quartiles_drop = v.findViewById(R.id.fragment_experiment_data_analysis_quartilesDrop);
        ImageView plot_drop = v.findViewById(R.id.fragment_experiment_data_analysis_plotsDrop);

        histogram_drop.setOnClickListener(v1 -> toggle_histogram());

        quartiles_drop.setOnClickListener(v2 -> toggle_quartiles());

        plot_drop.setOnClickListener(v3 -> toggle_plots());
    }

    private void generateGraphs() {
        // Initialize time plot graph
        // The graph date plot initialization is taken from GraphView's documentation
        // Author:
        // URL: https://github.com/jjoe64/GraphView/wiki/Dates-as-labels
        DataPoint[] data_points = model.getTimePlotDataPoints(); // obtain datapoints from  model

        exp_plot_over_time.addSeries(new LineGraphSeries<>(data_points)); // add to graph

        // initialize axises
        exp_plot_over_time.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(getActivity()));
        exp_plot_over_time.getGridLabelRenderer().setNumHorizontalLabels(data_points.length);

        if (data_points.length > 0) {
            exp_plot_over_time.getViewport().setMinX(data_points[0].getX());
            exp_plot_over_time.getViewport().setMaxX(data_points[data_points.length - 1].getX());
        } else {
            exp_plot_over_time.getViewport().setMinX(new Date().getTime());
        }
        exp_plot_over_time.getViewport().setMinY(0);

        exp_plot_over_time.getViewport().setXAxisBoundsManual(true);
        exp_plot_over_time.getGridLabelRenderer().setHumanRounding(false);
    }

    private void toggle_quartiles() {
        if (quartileGraph.isShown()) {
            quartileGraph.setVisibility(View.GONE);
        } else {
            quartileGraph.setVisibility(View.VISIBLE);
        }
    }

    private void toggle_histogram() {
        if (histogram.isShown()) {
            histogram.setVisibility(View.GONE);
        } else {
            histogram.setVisibility(View.VISIBLE);
        }
    }

    private void toggle_plots() {
        if (exp_plot_over_time.isShown()) {
            exp_plot_over_time.setVisibility(View.GONE);
        } else {
            exp_plot_over_time.setVisibility(View.VISIBLE);
        }
    }
}

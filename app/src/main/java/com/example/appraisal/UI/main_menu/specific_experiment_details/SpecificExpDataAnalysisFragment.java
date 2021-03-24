package com.example.appraisal.UI.main_menu.specific_experiment_details;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.example.appraisal.R;
import com.example.appraisal.backend.experiment.Experiment;
import com.example.appraisal.backend.specific_experiment.Quartile;
import com.example.appraisal.backend.trial.NonNegIntCountTrial;
import com.example.appraisal.backend.trial.Trial;
import com.example.appraisal.backend.trial.TrialFactory;
import com.example.appraisal.backend.trial.TrialType;
import com.example.appraisal.backend.user.User;
import com.example.appraisal.model.MainModel;
import com.example.appraisal.model.SpecificExpModel;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SpecificExpDataAnalysisFragment extends Fragment {
    private GraphView histogram;
    private GraphView exp_plot_over_time;
    private ConstraintLayout quartileTable;

    private SpecificExpModel model;
    private Experiment current_experiment;
    private User current_experimenter;

    /**
     * This is the Overridden onCreateView method from the Fragment class
     * @param inflater -- LayoutInflater object for inflating the Fragment
     * @param container -- ViewGroup object that contains the layout
     * @param savedInstanceState -- Bundle object
     * @return v -- View of the initialized Fragment
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // initialize layout
        View v = inflater.inflate(R.layout.fragment_experiment_data_analysis, container, false);

        // obtain current experiment and experimenter
        current_experiment = null;
        current_experimenter = null;
        try {
            current_experiment = MainModel.getCurrentExperiment();
            current_experimenter = MainModel.getCurrentUser();
        } catch (Exception e) {
            Log.e("Error:","Current experiment not set");
            e.printStackTrace();
            return v;
        }

        // initialize model
        model = new SpecificExpModel(current_experiment);
        viewInitThread(v);
        trialOnChangeListener(v);

        return v;
    }

    private void trialOnChangeListener(View v) {
        CollectionReference trials = null;
        try {
            CollectionReference experiment = MainModel.getExperimentReference();
            trials = experiment.document(current_experiment.getExpId()).collection("Trials");
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("ERROR:","Unable to setup trial on change listener");
            return;
        }

        // Since the trials are also saved locally, there is no need to query the database
        // Instead, only need to reconstruct the model locally and refresh the layout
        // This is big brain time
        trials.addSnapshotListener((value, error) -> {
            model = new SpecificExpModel(current_experiment);
            // refresh the views
            viewInitThread(v);
        });
    }

    private void viewInitThread(View v) {
        graphViewInit(v);
        generateTimePlot();
        generateHistogram();
        generateQuartileTable();
        generateExpStats(v);
        graphDropInit(v);
    }

    private void generateExpStats(View v) {
        // initialize and set experiment stats
        TextView mean = v.findViewById(R.id.fragment_experiment_data_analysis_experimentMeanText);
        TextView median = v.findViewById(R.id.fragment_experiment_data_analysis_experimentMedianText);
        TextView stdDev = v.findViewById(R.id.fragment_experiment_data_analysis_experimentStdevText);

        mean.setText(model.getMean());
        median.setText(String.format("%.2f",model.getQuartileInfo().getMedian()));
        stdDev.setText(model.getStdDev());
    }

    private void graphViewInit(View v) {
        // initialize graphs
        histogram = v.findViewById(R.id.fragment_experiment_data_analysis_histogramGraph);
        exp_plot_over_time = v.findViewById(R.id.fragment_experiment_data_analysis_plotsGraph);
        quartileTable = v.findViewById(R.id.fragment_experiment_data_analysis_quartilesTable);

        // Hide graphs until button is clicked
        histogram.setVisibility(View.GONE);
        exp_plot_over_time.setVisibility(View.GONE);
        quartileTable.setVisibility(View.GONE);
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

    private void generateQuartileTable() {
        // Note: Box Plot does not have good, off the shelf and free to use library
        // And no I'm not writing my own renderer, I don't have that time
        // So ya it will be a table instead, which still gets the job done

        // Initialize tables textview
        TextView min = quartileTable.findViewById(R.id.fragment_exp_data_analysis_minimum);
        TextView q1 = quartileTable.findViewById(R.id.fragment_exp_data_analysis_q1);
        TextView q3 = quartileTable.findViewById(R.id.fragment_exp_data_analysis_q3);
        TextView max = quartileTable.findViewById(R.id.fragment_exp_data_analysis_maximum);
        TextView iqr = quartileTable.findViewById(R.id.fragment_exp_data_analysis_iqr);
        TextView outlier_percent = quartileTable.findViewById(R.id.fragment_exp_data_analysis_outlier_percent);

        // obtain quartile info from model
        Quartile quartiles = model.getQuartileInfo();

        // Calculate Maximum and minimum excluding outliers
        double minimum = quartiles.getFirstQuartile() - (1.5 * quartiles.getIQR());
        if (minimum < quartiles.getTrialMinValue()) {
            minimum = quartiles.getTrialMinValue();
        }
        double maximum = quartiles.getThirdQuartile() + (1.5 * quartiles.getIQR());
        if (maximum > quartiles.getTrialMaxValue()) {
            maximum = quartiles.getTrialMaxValue();
        }

        // Calculate outlier percentage
        int outlier_count = quartiles.getOutLiers().size();
        int total = quartiles.getTotalNumTrial();
        double percent = (outlier_count / (double) total) * 100;

        // Set values to TextViews
        min.setText(String.valueOf(minimum));
        max.setText(String.valueOf(maximum));
        q1.setText(String.valueOf(quartiles.getFirstQuartile()));
        q3.setText(String.valueOf(quartiles.getThirdQuartile()));
        iqr.setText(String.valueOf(quartiles.getIQR()));
        outlier_percent.setText(String.format("%.2f%%", percent));
    }

    private void generateHistogram() {
        // obtain data points

        DataPoint[] dataPoints = model.getHistogramDataPoints();
        BarGraphSeries<DataPoint> barGraphSeries = new BarGraphSeries<>(dataPoints);
        barGraphSeries.setSpacing(5); // set a bit of spacing between bars for readability
        histogram.removeAllSeries();            // clear any previous series
        histogram.addSeries(barGraphSeries);    // set to new series

        histogram.getGridLabelRenderer().setPadding(50);

        float interval = model.getHistogramIntervalWidth();

        if (interval == 0) {
            histogram.getViewport().setMinX(barGraphSeries.getLowestValueX() - 0.1);
            histogram.getViewport().setMaxX(barGraphSeries.getHighestValueX() + 0.1);
        } else {
            histogram.getViewport().setMinX(barGraphSeries.getLowestValueX() - (0.5 * interval));
            histogram.getViewport().setMaxX(barGraphSeries.getHighestValueX() + (0.5 * interval));
        }
        histogram.getViewport().setMinY(0);

        histogram.getGridLabelRenderer().setNumHorizontalLabels(dataPoints.length + 1);
        histogram.getViewport().setXAxisBoundsManual(true);
        histogram.getViewport().setYAxisBoundsManual(true);

        histogram.getGridLabelRenderer().setHorizontalLabelsAngle(135);

    }

    private void generateTimePlot() {
        // Initialize time plot graph
        // The graph date plot initialization is taken from GraphView's documentation
        // Author:
        // URL: https://github.com/jjoe64/GraphView/wiki/Dates-as-labels
        DataPoint[] data_points = model.getTimePlotDataPoints(); // obtain datapoints from  model

        // set datapoints visible
        LineGraphSeries<DataPoint> time_plot_data = new LineGraphSeries<>(data_points);
        time_plot_data.setDrawDataPoints(true);
        time_plot_data.setDataPointsRadius(13f);
        exp_plot_over_time.removeAllSeries();           // clear any previous series
        exp_plot_over_time.addSeries(time_plot_data);   // add to graph

        // initialize axises
        exp_plot_over_time.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(getActivity()));
        exp_plot_over_time.getGridLabelRenderer().setNumHorizontalLabels(data_points.length);
        exp_plot_over_time.getGridLabelRenderer().setPadding(50);
        exp_plot_over_time.getGridLabelRenderer().setHorizontalLabelsAngle(135);

        if (data_points.length > 0) {
            exp_plot_over_time.getViewport().setMinX(data_points[0].getX());
            exp_plot_over_time.getViewport().setMaxX(data_points[0].getX() + 5*24*60*60*1000); // increment 5 days
        } else {
            exp_plot_over_time.getViewport().setMinX(new Date().getTime());
        }

        // setting axises intervals
        // The ideal of able to set integer intervals on GraphView is taken from this StackOverflow Thread:
        // Author: user3261759 (URL: https://stackoverflow.com/users/3261759/user3261759)
        // Thread URL: https://stackoverflow.com/posts/21505958/revisions

        exp_plot_over_time.getViewport().setMinY(0);

        int max_value = (int) Math.floor(time_plot_data.getHighestValueY() + 1);
        int interval = 1;
        if ((max_value <= 10)) {
            interval = 1;
        } else if (max_value <= 50) {
            interval = 5;
        } else if (max_value <= 100) {
            interval = 100;
        } else if (max_value >= 200){
            interval = 200;
        }
        int max_label = max_value;
        while (max_label % interval != 0) {
            max_label++;
        }
        exp_plot_over_time.getGridLabelRenderer().setNumVerticalLabels(max_label / interval + 1);
        exp_plot_over_time.getGridLabelRenderer().setNumHorizontalLabels(5);

        exp_plot_over_time.getViewport().setMaxY(max_label);
        exp_plot_over_time.getViewport().setXAxisBoundsManual(true);
        exp_plot_over_time.getViewport().setYAxisBoundsManual(true);

        exp_plot_over_time.getGridLabelRenderer().setHumanRounding(false);

        exp_plot_over_time.getViewport().setScalable(true);
        exp_plot_over_time.getViewport().setScrollable(true);
    }

    private void toggle_quartiles() {
        if (quartileTable.isShown()) {
            quartileTable.setVisibility(View.GONE);
        } else {
            quartileTable.setVisibility(View.VISIBLE);
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

package com.example.appraisal.model;

import android.util.Log;

import com.example.appraisal.backend.experiment.Experiment;
import com.example.appraisal.backend.specific_experiment.Quartile;
import com.example.appraisal.backend.specific_experiment.SpecificExperiment;
import com.example.appraisal.backend.trial.CountTrial;
import com.example.appraisal.backend.user.User;
import com.jjoe64.graphview.series.DataPoint;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;

public class SpecificExpModel {
    private final SpecificExperiment specificExperiment;
    private final double stdDev;
    private final Quartile quartile;
    private final double mean;

    public SpecificExpModel() {
        Experiment current_experiment = null;
        try {
            current_experiment = MainModel.getCurrentExperiment();
        } catch (Exception e) {
            // Create dummy data
            Log.d("Error","MainModel.getCurrentExperiment() returned null. Filling in dummy data to prevent crash");
            current_experiment = new Experiment("Test", "Test", new User("Test", "Test", "Test@mail.com", "1234"));
            current_experiment.addTrial(new CountTrial());
            current_experiment.addTrial(new CountTrial());

        }
        specificExperiment = new SpecificExperiment(current_experiment);
        stdDev = specificExperiment.getExperimentStDev();
        quartile = specificExperiment.getQuartile();
        mean = specificExperiment.getExperimentMean();
    }

    /**
     * This function returns an array of DataPoint objects for plotting the Trial over Time Plot
     * @return DataPoint[]
     *      Return DataPoint objects for plotting. Used by GraphView
     */
    public DataPoint[] getTimePlotDataPoints() {
        SortedMap<Date, Integer> data_points = specificExperiment.getTrialsPerDate();

        // Convert HashMap to DataPoint
        List<DataPoint> data_list = new ArrayList<>();
        for (Map.Entry<Date, Integer> entry: data_points.entrySet()) {
            Date key = entry.getKey();
            int value = entry.getValue();
            data_list.add(new DataPoint(key, value));
        }

        return data_list.toArray(new DataPoint[0]);
    }

    public DataPoint[] getHistogramDataPoints() {
        SortedMap<Float, Integer> data_points = specificExperiment.getHistogramIntervalFrequency();
        List<DataPoint> data_list = new ArrayList<>();
        for (Map.Entry<Float, Integer> entry: data_points.entrySet()) {
            float interval = entry.getKey();
            int count = entry.getValue();
            data_list.add(new DataPoint(interval, count));
        }

        return data_list.toArray(new DataPoint[0]);
    }
    /**
     * This function returns the number of trials conducted by the given Experiment
     * @return trial_length_string
     *      This is the length of list_of_trials (Note: NOT necessary the no. of trials conducted
     */
    public String getListOfTrialLength() {
        return String.valueOf(specificExperiment.getList_of_trials().size());
    }

    /**
     * This function returns the standard deviation of the given experiment
     * @return stdDev_string
     *      Standard Deviation of the given experiment
     */
    public String getStdDev() {
        return String.format("%.2f",stdDev);
    }

    /**
     * This function returns the Quartile object of the given experiment
     * @return quartile {@link Quartile}
     *      The Quartile object contains all the quartile information
     */
    public Quartile getQuartileInfo() {
        return quartile;
    }

    /**
     * This function returns the mean of the given experiment
     * @return mean_string
     *      Mean of the given experiment
     */
    public String getMean() {
        return String.format("%.2f",mean);
    }

    /**
     * This function retruns the interval width of the histogram
     * @return
     */
    public float getHistogramIntervalWidth() {
        return (float) specificExperiment.getHistogramIntervalWidth();
    }
}

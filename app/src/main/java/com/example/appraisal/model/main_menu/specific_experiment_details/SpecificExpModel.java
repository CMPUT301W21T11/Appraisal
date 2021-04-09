package com.example.appraisal.model.main_menu.specific_experiment_details;

import com.example.appraisal.backend.experiment.Experiment;
import com.example.appraisal.backend.specific_experiment.Quartile;
import com.example.appraisal.backend.specific_experiment.SpecificExperimentStatistics;
import com.jjoe64.graphview.series.DataPoint;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;

/**
 * This model is for communicating with {@link SpecificExperimentStatistics} class
 * for obtaining graphing data points and other experiment stats
 */
public class SpecificExpModel {
    private final SpecificExperimentStatistics specificExperimentStatistics;
    private final double stdDev;
    private final Quartile quartile;
    private final double mean;

    public SpecificExpModel(Experiment current_experiment) {
        specificExperimentStatistics = new SpecificExperimentStatistics(current_experiment);
        stdDev = specificExperimentStatistics.getExperimentStDev();
        quartile = specificExperimentStatistics.getQuartile();
        mean = specificExperimentStatistics.getExperimentMean();
    }

    /**
     * This function returns an array of DataPoint objects for plotting the Trial over Time Plot
     *
     * @return DataPoint[]
     * Return DataPoint objects for plotting. Used by GraphView
     */
    public DataPoint[] getTimePlotDataPoints() {
        SortedMap<Date, Double> data_points = specificExperimentStatistics.getTrialsPerDate();

        // Convert HashMap to DataPoint
        List<DataPoint> data_list = new ArrayList<>();
        for (Map.Entry<Date, Double> entry : data_points.entrySet()) {
            Date key = entry.getKey();
            double value = entry.getValue();
            data_list.add(new DataPoint(key, value));
        }

        return data_list.toArray(new DataPoint[0]);
    }

    /**
     * This function returns an array of DataPoint objects for plotting the histogram
     *
     * @return DataPoint[]
     * Return DataPoint objects for plotting. Used by GraphView
     */
    public DataPoint[] getHistogramDataPoints() {
        SortedMap<Float, Integer> data_points = specificExperimentStatistics.getHistogramIntervalFrequency();
        List<DataPoint> data_list = new ArrayList<>();
        for (Map.Entry<Float, Integer> entry : data_points.entrySet()) {
            float interval = entry.getKey();
            int count = entry.getValue();
            data_list.add(new DataPoint(interval, count));
        }

        return data_list.toArray(new DataPoint[0]);
    }

    /**
     * This function returns the standard deviation of the given experiment
     *
     * @return stdDev_string
     * Standard Deviation of the given experiment
     */
    public String getStdDev() {
        return String.format("%.2f", stdDev);
    }

    /**
     * This function returns the Quartile object of the given experiment
     *
     * @return quartile {@link Quartile}
     * The Quartile object contains all the quartile information
     */
    public Quartile getQuartileInfo() {
        return quartile;
    }

    /**
     * This function returns the mean of the given experiment
     *
     * @return mean_string
     * Mean of the given experiment
     */
    public String getMean() {
        return String.format("%.2f", mean);
    }

    /**
     * This function retruns the interval width of the histogram
     *
     * @return
     */
    public float getHistogramIntervalWidth() {
        return (float) specificExperimentStatistics.getHistogramIntervalWidth();
    }

}

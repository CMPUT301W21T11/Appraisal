package com.example.appraisal.model;

import android.util.Log;

import com.example.appraisal.backend.experiment.Experiment;
import com.example.appraisal.backend.specific_experiment.SpecificExperiment;
import com.example.appraisal.backend.trial.CountTrial;
import com.example.appraisal.backend.trial.Trial;
import com.example.appraisal.backend.user.User;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public class SpecificExpModel {
    private final SpecificExperiment specificExperiment;

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

    /**
     * This function returns the number of trials conducted by the given Experiment
     * @return int
     *      This is the length of list_of_trials (Note: NOT necessary the no. of trials conducted
     */
    public int getListOfTrialLength() {
        return specificExperiment.getList_of_trials().size();
    }
}

package com.example.appraisal.model;

import com.example.appraisal.backend.specific_experiment.SpecificExperiment;
import com.example.appraisal.backend.trial.Trial;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public class SpecificExpModel {
    private SpecificExperiment specificExperiment;

    public SpecificExpModel() {
        specificExperiment = new SpecificExperiment();
    }

    public DataPoint[] getTimePlotDataPoints() {
        List<Trial> list_of_trials = specificExperiment.getList_of_trials();

        // hashmap to store all the trial count for a given date
        // sort by date
        SortedMap<Date, Integer> data_points = new TreeMap<>();
        for (Trial trial:list_of_trials) {
            Date key = trial.getTrialDate();
            if (data_points.containsKey(key)) { // i.e. date entry already exist
                // increase trial count
                int count = data_points.get(key);
                count++;
                data_points.put(key, count);
            } else {
                // create new entry
                data_points.put(key, 1);
            }
        }

        // Convert HashMap to DataPoint
        List<DataPoint> data_list = new ArrayList<>();
        for (Map.Entry<Date, Integer> entry: data_points.entrySet()) {
            Date key = entry.getKey();
            int value = entry.getValue();
            data_list.add(new DataPoint(key, value));
        }

        return data_list.toArray(new DataPoint[0]);
    }

    public int getTrialCount() {
        return specificExperiment.getTrialCount();
    }
}

package com.example.appraisal.model.trial;

import android.util.Log;

import com.example.appraisal.backend.experiment.Experiment;
import com.example.appraisal.backend.trial.MeasurementTrial;

public class MeasurementModel {
    private MeasurementTrial data;

    public MeasurementModel(Experiment experiment) {
        data = new MeasurementTrial(experiment);
    }

    public void addMeasurement(String measurement) {
        float value = 0;
        try {
            value = Float.parseFloat(measurement);
        }
        catch (Exception e) {
            Log.i("PROBLEM WITH INPUT!: ", measurement);
        }
        data.setMeasurement(value);
    }

    public void toExperiment() { data.getParent_experiment().addTrial(data);}
}

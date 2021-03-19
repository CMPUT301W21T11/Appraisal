package com.example.appraisal.model.trial;

import android.util.Log;

import com.example.appraisal.backend.experiment.Experiment;
import com.example.appraisal.backend.trial.MeasurementTrial;

/**
 * This is the model for measurement trial
 */
public class MeasurementModel {
    private MeasurementTrial data;

    public MeasurementModel(Experiment experiment) {
        data = new MeasurementTrial(experiment);
    }

    /**
     * Add a measurement to the trial
     * @param measurement
     *      measurement of the trial
     */
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

    public float getMeasurement() {
        return data.getMeasurement();
    }

    /**
     * Save the trial to the parent experiment
     */
    public void toExperiment() { data.getParent_experiment().addTrial(data);}
}

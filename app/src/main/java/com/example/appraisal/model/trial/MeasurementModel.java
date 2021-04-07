package com.example.appraisal.model.trial;

import android.util.Log;

import com.example.appraisal.backend.experiment.Experiment;
import com.example.appraisal.backend.trial.MeasurementTrial;
import com.example.appraisal.backend.trial.TrialFactory;
import com.example.appraisal.backend.trial.TrialType;
import com.example.appraisal.backend.user.User;

/**
 * This is the model for measurement trial
 */
public class MeasurementModel {
    private final MeasurementTrial data;

    public MeasurementModel(Experiment experiment, User conductor) {
        TrialFactory factory = new TrialFactory();
        data = (MeasurementTrial) factory.createTrial(TrialType.MEASUREMENT_TRIAL, experiment, conductor);
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
        data.setValue(value);
    }

    /**
     * This method returns the measurement of the given trial
     * @return float -- measurement of the trial
     */
    public float getMeasurement() {
        return (float) data.getValue();
    }

    /**
     * Save the trial to the parent experiment
     */
    public void toExperiment() { data.getParentExperiment().addTrial(data);}
}

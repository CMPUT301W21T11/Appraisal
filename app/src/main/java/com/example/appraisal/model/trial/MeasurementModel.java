package com.example.appraisal.model.trial;

import android.util.Log;
import com.example.appraisal.backend.trial.MeasurementTrial;

public class MeasurementModel {
    private MeasurementTrial data;

    public MeasurementModel() {
        data = new MeasurementTrial();
    }

    public void addMeasurement(String measurement) {
        try {
            double value = Double.parseDouble(measurement);
        }
        catch (Exception e) {
            Log.i("PROBLEM WITH INPUT!: ", measurement);
        }
    }

    public void toExperiment() { data.getExperiment().addTrial(data);}
}

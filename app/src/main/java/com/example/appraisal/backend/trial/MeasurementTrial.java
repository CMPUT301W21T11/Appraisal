package com.example.appraisal.backend.trial;

public class MeasurementTrial extends Trial {
    float measurement;
    public MeasurementTrial() {
        measurement = 0;
    }

    public void setMeasurement(float measurement) {
        this.measurement = measurement;
    }

    public float getMeasurement() {
        return measurement;
    }
}

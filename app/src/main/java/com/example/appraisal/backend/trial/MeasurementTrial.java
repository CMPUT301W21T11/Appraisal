package com.example.appraisal.backend.trial;

import com.example.appraisal.backend.experiment.Experiment;

/**
 * This class represents a Measurement Trial
 */
public class MeasurementTrial extends Trial {
    float measurement;

    /**
     * Create a measurement trial
     * @param parent_experiment
     *      The parent experiment which the trial belongs to
     */
    public MeasurementTrial(Experiment parent_experiment) {
        super(parent_experiment);
        measurement = 0;
    }

    /**
     * Set the measurement of the trial
     * @param measurement
     *      The measurement obtained
     */
    public void setMeasurement(float measurement) {
        this.measurement = measurement;
    }

    /**
     * Get the measurement of the trial
     * @return measurement
     *      The measurement of the trial
     */
    public float getMeasurement() {
        return measurement;
    }
}

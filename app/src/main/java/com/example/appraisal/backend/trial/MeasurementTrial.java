package com.example.appraisal.backend.trial;

import com.example.appraisal.backend.experiment.Experiment;
import com.example.appraisal.backend.user.User;

import java.util.Date;

/**
 * This class represents a Measurement Trial
 */
public class MeasurementTrial extends Trial {
    private double measurement;

    /**
     * Create a measurement trial
     * @param parent_experiment
     *      The parent experiment which the trial belongs to
     */
    public MeasurementTrial(Experiment parent_experiment, User conductor) {
        super(parent_experiment, conductor, TrialType.MEASUREMENT_TRIAL);
        measurement = 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setValue(double value) {
        measurement = value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getValue() {
        return measurement;
    }
}

package com.example.appraisal.backend.trial;

import com.example.appraisal.backend.experiment.Experiment;
import com.example.appraisal.backend.user.User;

import java.util.Date;

/**
 * This class represents a Non negative Integer count trial
 */
public class NonNegIntCountTrial extends Trial {
    private int counter;

    /**
     * Creates a new Non negative Integer count trial
     * @param parent_experiment
     *      This is the parent experiment the trial belongs to
     */
    public NonNegIntCountTrial(Experiment parent_experiment, User conductor) {
        super(parent_experiment, conductor, TrialType.NON_NEG_INT_TRIAL);
        counter = 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setValue(double value) {
        counter = (int) Math.round(value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getValue() {
        return counter;
    }
}

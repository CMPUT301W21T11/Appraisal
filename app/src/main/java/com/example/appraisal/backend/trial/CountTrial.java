package com.example.appraisal.backend.trial;

import com.example.appraisal.backend.experiment.Experiment;
import com.example.appraisal.backend.user.User;

import java.util.Date;

/**
 * This class represents a Count Trial
 */
public class CountTrial extends Trial {
    private int counter;

    /**
     * Create a count trial object
     * @param parent_experiment
     *      The parent experiment which the object belongs to
     */
    public CountTrial(Experiment parent_experiment, User conductor) {
        super(parent_experiment, conductor, TrialType.COUNT_TRIAL);
        counter = 0;
    }

    /**
     * Increment the counter of the trial
     */
    public void addCount() {
        counter++;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setValue(double value) {
        this.counter = (int) Math.round(value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getValue() {
        return counter;
    }
}

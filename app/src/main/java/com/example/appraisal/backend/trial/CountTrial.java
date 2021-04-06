package com.example.appraisal.backend.trial;

import com.example.appraisal.backend.experiment.Experiment;
import com.example.appraisal.backend.user.User;

import java.util.Date;

/**
 * This class represents a Count Trial
 */
public class CountTrial extends Trial {
    private int count;

    /**
     * Create a count trial object
     * @param parent_experiment
     *      The parent experiment which the object belongs to
     */
    public CountTrial(Experiment parent_experiment, User conductor) {
        super(parent_experiment, conductor, TrialType.COUNT_TRIAL);
        count = 1;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setValue(double value) {
        if (Math.round(value) == 0) {
            count = 0;
        } else {
            count = 1;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getValue() {
        return count;
    }
}

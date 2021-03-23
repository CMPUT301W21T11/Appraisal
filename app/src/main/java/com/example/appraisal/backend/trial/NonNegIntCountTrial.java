package com.example.appraisal.backend.trial;

import com.example.appraisal.backend.experiment.Experiment;
import com.example.appraisal.backend.user.User;

import java.util.Date;

/**
 * This class represents a Non negative Integer count trial
 */
public class NonNegIntCountTrial implements Trial {
    private int counter;
    private int trial_count;

    private final Experiment parent_experiment;
    private final Date trial_date;
    private final User conductor;

    /**
     * Creates a new Non negative Integer count trial
     * @param parent_experiment
     *      This is the parent experiment the trial belongs to
     */
    public NonNegIntCountTrial(Experiment parent_experiment, User conductor) {
        counter = 0;
        trial_count = 0;

        this.parent_experiment = parent_experiment;
        this.conductor = conductor;
        trial_date = new Date();
    }

    /**
     * This sets the counter to the supplied value
     * @param value -- the new counter value
     */
    public void addCount(int value) {
        counter += value;
        trial_count++;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getValue() {
        return counter;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getSubTrialCount() {
        return trial_count;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Experiment getParentExperiment() {
        return parent_experiment;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Date getTrialDate() {
        return trial_date;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User getConductor() {
        return conductor;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TrialType getType() {
        return TrialType.NON_NEG_INT_TRIAL;
    }
}

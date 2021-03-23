package com.example.appraisal.backend.trial;

import com.example.appraisal.backend.experiment.Experiment;
import com.example.appraisal.backend.user.User;

import java.util.Date;

/**
 * This class represents a Count Trial
 */
public class CountTrial implements Trial {
    private int counter;

    private Experiment parent_experiment;
    private User conductor;
    private Date trial_date;

    /**
     * Create a count trial object
     * @param parent_experiment
     *      The parent experiment which the object belongs to
     */
    public CountTrial(Experiment parent_experiment, User conductor) {
        counter = 0;
        this.parent_experiment = parent_experiment;
        this.conductor = conductor;
        this.trial_date = new Date();
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
    public double getValue() {
        return counter;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getSubTrialCount() {
        return 1;
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
        return TrialType.COUNT_TRIAL;
    }
}

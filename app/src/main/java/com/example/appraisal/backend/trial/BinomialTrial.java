package com.example.appraisal.backend.trial;


import com.example.appraisal.backend.experiment.Experiment;
import com.example.appraisal.backend.user.User;

import java.util.Date;

/**
 * This class represents a binomial trial
 */
public class BinomialTrial implements Trial {
    private int success_counter;
    private int failure_counter;

    private Experiment parent_experiment;
    private User conductor;
    private Date conduct_date;

    /**
     * Create a new binomial trial
     * @param parent_experiment
     *      The parent experiment which this trial belongs to
     */
    public BinomialTrial(Experiment parent_experiment, User conductor){
        success_counter = 0;
        failure_counter = 0;
        this.parent_experiment = parent_experiment;
        this.conductor = conductor;
        this.conduct_date = new Date();
    }

    /**
     * This method increase the success count of the trial by 1
     */
    public void addSuccess() {
        success_counter++;
    }

    /**
     * This method increase the failure count of the trial by 1
     */
    public void addFailure() {
        failure_counter++;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getValue() {
        return success_counter;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getSubTrialCount() {
        return success_counter + failure_counter;
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
        return conduct_date;
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
        return TrialType.BINOMIAL_TRIAL;
    }
}

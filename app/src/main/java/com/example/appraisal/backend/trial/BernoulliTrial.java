package com.example.appraisal.backend.trial;


import com.example.appraisal.backend.experiment.Experiment;
import com.example.appraisal.backend.user.User;

import java.util.Date;

/**
 * This class represents a binomial trial
 */
public class BernoulliTrial implements Trial {
    private boolean result; // This represents if the trial is a success or failure

    private Experiment parent_experiment;
    private User conductor;
    private Date conduct_date;

    /**
     * Create a new binomial trial
     * @param parent_experiment
     *      The parent experiment which this trial belongs to
     */
    public BernoulliTrial(Experiment parent_experiment, User conductor){
        this.parent_experiment = parent_experiment;
        this.conductor = conductor;
        this.conduct_date = new Date();
    }

    /**
     * This method set the bernoulli trial to be a success
     */
    public void setToSuccess() {
        result = true;
    }

    /**
     * This method set the bernoulli trial to be a failure
     */
    public void setToFailure() {
        result = false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setValue(double value) {
        // Yes I know it could be simpler but for readability's sake I'll keep this
        if (Math.round(value) == 0) {
            result = false;
        } else {
            result = true;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void overrideDate(Date date) {
        conduct_date = date;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getValue() {
        if (result) { // i.e. a success
            return 1.0;
        } else {
            return 0.0;
        }
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

    /**
     * {@inheritDoc}
     */
    @Override
    public int compareTo(Trial o) {
        return Double.compare(this.getValue(), o.getValue());
    }
}

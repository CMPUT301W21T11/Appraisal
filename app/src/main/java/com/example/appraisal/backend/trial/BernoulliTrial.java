package com.example.appraisal.backend.trial;


import com.example.appraisal.backend.experiment.Experiment;
import com.example.appraisal.backend.user.User;

import java.util.Date;

/**
 * This class represents a binomial trial
 */
public class BernoulliTrial extends Trial {
    private boolean result; // This represents if the trial is a success or failure

    /**
     * Create a new binomial trial
     * @param parent_experiment
     *      The parent experiment which this trial belongs to
     */
    public BernoulliTrial(Experiment parent_experiment, User conductor){
        super(parent_experiment, conductor, TrialType.BINOMIAL_TRIAL);
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
    public double getValue() {
        if (result) { // i.e. a success
            return 1.0;
        } else {
            return 0.0;
        }
    }
}

package com.example.appraisal.backend.trial;

import androidx.annotation.NonNull;

import com.example.appraisal.backend.experiment.Experiment;
import com.example.appraisal.backend.user.User;

/**
 * This class is the factory for creating Trials
 */
public class TrialFactory {

    /**
     * This method tells the factory to create a trial of given type
     *
     * @param create_key -- the key to which type of trial to create
     * @param parent_experiment -- the experiment which the trial belongs to
     * @param conductor -- the experimenter
     * @return Trial -- a specific Type of Trial (BinomialTrial, CountTrial, MeasurementTrial, NonNegIntCountTrial)
     */
    public Trial createTrial(@NonNull TrialType create_key, Experiment parent_experiment, User conductor) {
        Trial created_trial = null;

        switch (create_key) {
            case BINOMIAL_TRIAL:
                created_trial = new BernoulliTrial(parent_experiment, conductor);
                break;
            case COUNT_TRIAL:
                created_trial = new CountTrial(parent_experiment, conductor);
                break;
            case MEASUREMENT_TRIAL:
                created_trial = new MeasurementTrial(parent_experiment, conductor);
                break;
            case NON_NEG_INT_TRIAL:
                created_trial = new NonNegIntCountTrial(parent_experiment, conductor);
                break;
        }

        return created_trial;
    }
}

package com.example.appraisal.model.trial;

import com.example.appraisal.backend.experiment.Experiment;
import com.example.appraisal.backend.trial.BinomialTrial;
import com.example.appraisal.backend.trial.TrialFactory;
import com.example.appraisal.backend.trial.TrialType;
import com.example.appraisal.backend.user.User;

/**
 * This is the model class for binomial trial
 */
public class BinomialModel {
    private final BinomialTrial bin_trial;

    public BinomialModel(Experiment parent_experiment, User conductor){
        TrialFactory factory = new TrialFactory();
        bin_trial = (BinomialTrial) factory.createTrial(TrialType.BINOMIAL_TRIAL, parent_experiment, conductor);

    }

    /**
     * Add a success to the trial
     */
    public void addSuccess(){
        bin_trial.setToSuccess();
    }

    /**
     * Add a failure to the trial
     */
    public void addFailure() {
        bin_trial.setToFailure();
    }

    /**
     * Save the trial to the parent experiment
     */
    public void toExperiment() { bin_trial.getParentExperiment().addTrial(bin_trial);}
}

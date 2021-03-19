package com.example.appraisal.model.trial;

import com.example.appraisal.backend.experiment.Experiment;
import com.example.appraisal.backend.trial.BinomialTrial;

/**
 * This is the model class for binomial trial
 */
public class BinomialModel {
    private BinomialTrial bin_trial;

    public BinomialModel(Experiment parent_experiment){
        bin_trial = new BinomialTrial(parent_experiment);
    }

    /**
     * Add a success to the trial
     */
    public void addSuccess(){
        bin_trial.addSuccess();
    }

    /**
     * Add a failure to the trial
     */
    public void addFailure(){
        bin_trial.addFailure();
    }

    /**
     * Get the success count of the trial
     * @return success_count
     */
    public int getSuccessCount(){
        return bin_trial.getSuccessCount();
    }

    /**
     * Get the failure count of the trial
     * @return failure_count
     */
    public int getFailureCount(){
        return bin_trial.getFailureCount();
    }

    public void setSuccessCount(Integer count) {
        bin_trial.setSuccess_counter(count);
    }

    public void setFailureCount(Integer count) {
        bin_trial.setFailure_counter(count);
    }

    /**
     * Save the trial to the parent experiment
     */
    public void toExperiment() { bin_trial.getParent_experiment().addTrial(bin_trial);}
}

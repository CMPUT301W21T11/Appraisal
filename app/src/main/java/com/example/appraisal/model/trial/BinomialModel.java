package com.example.appraisal.model.trial;

import com.example.appraisal.backend.trial.BinomialTrial;

public class BinomialModel {
    private BinomialTrial bin_trial;

    public BinomialModel(){
        bin_trial = new BinomialTrial();
    }

    public void addSuccess(){
        bin_trial.addSuccess();
    }

    public void addFailure(){
        bin_trial.addFailure();
    }

    public int getSuccessCount(){
        return bin_trial.getSuccessCount();
    }

    public int getFailureCount(){
        return bin_trial.getFailureCount();
    }
}

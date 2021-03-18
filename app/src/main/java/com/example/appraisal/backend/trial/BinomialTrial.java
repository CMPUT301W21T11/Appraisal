package com.example.appraisal.backend.trial;


import com.example.appraisal.backend.experiment.Experiment;

public class BinomialTrial extends Trial {
    private int success_counter;
    private int failure_counter;

    public BinomialTrial(Experiment parent_experiment){
        super(parent_experiment);
        success_counter = 0;
        failure_counter = 0;
    }

    public void addSuccess(){
        success_counter++;
    }

    public void addFailure(){
        failure_counter++;
    }

    public int getSuccessCount(){
        return success_counter;
    }

    public int getFailureCount(){
        return failure_counter;
    }

}

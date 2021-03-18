package com.example.appraisal.backend.trial;


import com.example.appraisal.backend.experiment.Experiment;

/**
 * This class represents a binomial trial
 */
public class BinomialTrial extends Trial {
    private int success_counter;
    private int failure_counter;

    /**
     * Create a new binomial trial
     * @param parent_experiment
     *      The parent experiment which this trial belongs to
     */
    public BinomialTrial(Experiment parent_experiment){
        super(parent_experiment);
        success_counter = 0;
        failure_counter = 0;
    }

    /**
     * Increment its number of success
     */
    public void addSuccess(){
        success_counter++;
    }

    /**
     * Increment its number of failures
     */
    public void addFailure(){
        failure_counter++;
    }

    /**
     * get the number of success
     * @return success_counter
     *      The number of success of this trial
     */
    public int getSuccessCount(){
        return success_counter;
    }

    /**
     * get the number of failure
     * @return failure_counter
     *      The number of failure of this trial
     */
    public int getFailureCount(){
        return failure_counter;
    }

}

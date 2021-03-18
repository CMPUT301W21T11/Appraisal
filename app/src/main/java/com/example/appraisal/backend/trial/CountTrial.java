package com.example.appraisal.backend.trial;

import com.example.appraisal.backend.experiment.Experiment;

/**
 * This class represents a Count Trial
 */
public class CountTrial extends Trial {

    private int counter;

    /**
     * Create a count trial object
     * @param parent_experiment
     *      The parent experiment which the object belongs to
     */
    public CountTrial(Experiment parent_experiment) {
        super(parent_experiment);
        counter = 0;
    }

    /**
     * Increase the count of the trial
     */
    public void increase() {
        counter++;
    }

    /**
     * get the count of the trial
     * @return counter
     *      The count of the trial
     */
    public int getCount() {
        return counter;
    }

}

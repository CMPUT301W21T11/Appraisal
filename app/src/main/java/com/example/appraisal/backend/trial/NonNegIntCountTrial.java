package com.example.appraisal.backend.trial;

import android.util.Log;

import com.example.appraisal.backend.experiment.Experiment;

/**
 * This class represents a Non negative Integer count trial
 */
public class NonNegIntCountTrial extends Trial {
    private int counter;

    /**
     * Creates a new Non negative Integer count trial
     * @param parent_experiment
     *      This is the parent experiment the trial belongs to
     */
    public NonNegIntCountTrial(Experiment parent_experiment) {
        super(parent_experiment);
        counter = 0;
    }

    /**
     * Increase the count of the trial
     * @param s
     *      The count to be increased by
     */
    public void addIntCount(String s) {
        int count = 0;
        try {
            count = Integer.parseInt(s);
        } catch (NumberFormatException e) {
            Log.d("Warning", "User input caused integer overflow");
        }

        counter += count;
    }

    /**
     * Returns the current count of the trial
     * @return counter
     *      current count of the trial
     */
    public int getCount() {
        return counter;
    }


}

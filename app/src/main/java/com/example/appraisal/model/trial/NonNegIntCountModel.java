package com.example.appraisal.model.trial;

import com.example.appraisal.backend.experiment.Experiment;
import com.example.appraisal.backend.trial.NonNegIntCountTrial;

/**
 * This is the model class for Non Negative Integer count trial
 */
public class NonNegIntCountModel {
    private NonNegIntCountTrial data;

    public NonNegIntCountModel(Experiment parent_experiment) {
        data = new NonNegIntCountTrial(parent_experiment);
    }

    /**
     * Add an integer count to the trial
     * @param s
     *      The string of the count
     */
    public void addIntCount(String s) {
        data.addIntCount(s);
    }

    /**
     * Get the count of the trial
     * @return count
     */
    public long getCount() {
        return data.getCount();
    }

    /**
     * Save the trial to the experiment
     */
    public void toExperiment() { data.getParent_experiment().addTrial(data);}
}

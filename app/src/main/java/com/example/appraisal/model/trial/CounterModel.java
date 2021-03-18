package com.example.appraisal.model.trial;

import com.example.appraisal.backend.experiment.Experiment;
import com.example.appraisal.backend.trial.CountTrial;

/**
 * This is the model class for a counter trial
 */
public class CounterModel {
    private CountTrial data;

    /**
     * Initialize the model for count trial
     * @param parent_experiment
     *      The parent experiment which the trial belongs to
     */
    public CounterModel(Experiment parent_experiment) {
        data = new CountTrial(parent_experiment);
    }

    /**
     * increase the count of the trial
     */
    public void increase() {
        data.increase();
    }

    /**
     * Return the count of the trial
     * @return count
     *      the count of the trial
     */
    public int getCount() {
        return data.getCount();
    }

    /**
     * Save the trial to the parent experiment
     */
    public void toExperiment() { data.getParent_experiment().addTrial(data);}
}

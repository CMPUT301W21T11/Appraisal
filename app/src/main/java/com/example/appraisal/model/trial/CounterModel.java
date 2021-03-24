package com.example.appraisal.model.trial;

import com.example.appraisal.backend.experiment.Experiment;
import com.example.appraisal.backend.trial.CountTrial;
import com.example.appraisal.backend.trial.TrialFactory;
import com.example.appraisal.backend.trial.TrialType;
import com.example.appraisal.backend.user.User;

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
    public CounterModel(Experiment parent_experiment, User conductor) {
        TrialFactory factory = new TrialFactory();
        data = (CountTrial) factory.createTrial(TrialType.COUNT_TRIAL, parent_experiment, conductor);
    }

    /**
     * increase the count of the trial
     */
    public void increase() {
        data.addCount();
    }

    /**
     * Return the count of the trial
     * @return count
     *      the count of the trial
     */
    public int getCount() {
        return (int) data.getValue();
    }

    /**
     * Save the trial to the parent experiment
     */
    public void toExperiment() { data.getParentExperiment().addTrial(data);}
}

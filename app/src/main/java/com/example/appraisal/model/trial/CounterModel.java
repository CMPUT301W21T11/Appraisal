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
    private final CountTrial trial;

    /**
     * Initialize the model for count trial
     * @param parent_experiment
     *      The parent experiment which the trial belongs to
     */
    public CounterModel(Experiment parent_experiment, User conductor) {
        TrialFactory factory = new TrialFactory();
        trial = (CountTrial) factory.createTrial(TrialType.COUNT_TRIAL, parent_experiment, conductor);
    }

    /**
     * This method returns the representation of a count trial as int
     * @return int -- count trial representation
     */
    public int getCountTrial() {
        return (int) trial.getValue();
    }

    /**
     * Save the trial to the parent experiment
     */
    public void toExperiment() { trial.getParentExperiment().addTrial(trial);}
}

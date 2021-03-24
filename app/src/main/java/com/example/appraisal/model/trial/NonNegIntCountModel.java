package com.example.appraisal.model.trial;

import android.util.Log;

import com.example.appraisal.backend.experiment.Experiment;
import com.example.appraisal.backend.trial.NonNegIntCountTrial;
import com.example.appraisal.backend.trial.TrialFactory;
import com.example.appraisal.backend.trial.TrialType;
import com.example.appraisal.backend.user.User;

/**
 * This is the model class for Non Negative Integer count trial
 */
public class NonNegIntCountModel {
    private NonNegIntCountTrial data;

    public NonNegIntCountModel(Experiment parent_experiment, User conductor) {
        TrialFactory factory = new TrialFactory();
        data = (NonNegIntCountTrial) factory.createTrial(TrialType.NON_NEG_INT_TRIAL, parent_experiment, conductor);
    }

    /**
     * Add an integer count to the trial
     * @param s
     *      The string of the count
     */
    public void addIntCount(String s) {
        try {
            int count = Integer.parseInt(s);
            data.setValue(count);
        } catch (Exception e) {
            Log.d("ERROR","Input not properly limited");
            e.printStackTrace();
        }
    }

    /**
     * Get the count of the trial
     * @return count
     */
    public long getCount() {
        return (long) data.getValue();
    }

    /**
     * Save the trial to the experiment
     */
    public void toExperiment() { data.getParentExperiment().addTrial(data);}
}

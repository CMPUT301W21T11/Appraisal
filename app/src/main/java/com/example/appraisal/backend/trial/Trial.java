package com.example.appraisal.backend.trial;

import com.example.appraisal.backend.experiment.Experiment;

import java.util.Date;

/**
 * This is the Trial abstract class that represent trials in general
 */
abstract public class Trial {
    private Experiment parent_experiment;
    private Date trial_date = new Date(); //Need to record time of the trial, in order to show the plot of trials over time

    /**
     * Create the trial object
     * @param parent_experiment
     *      The parent experiment of this trail
     */
    public Trial(Experiment parent_experiment) {
        this.parent_experiment = parent_experiment;
    }

    /**
     * This returns the experiment object that this trial belongs to
     * @return parent_experiment
     *      The parent experiment this object belongs
     */
    public Experiment getParent_experiment() {
        return parent_experiment;
    }

    /**
     * This returns the date which the trial was conducted
     * @return trial_date
     *      The Date object of the conducted date
     */
    public Date getTrialDate() {
        return trial_date;
    }
}

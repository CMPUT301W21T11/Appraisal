package com.example.appraisal.backend.trial;

import com.example.appraisal.backend.experiment.Experiment;

import java.util.Date;

public abstract class Trial {
    public Experiment current_experiment;
    //Need to record time of the trial, in order to show the plot of trials over time
    private Date trial_date = new Date();

    public void setExperiment(Experiment experiment) {
        this.current_experiment = experiment;
    } // TEST CODE

    public Experiment getExperiment() {
        return current_experiment;
    }

    public void overrideDate(Date date) {
        this.trial_date = date;
    }

    public Date getTrialDate() {
        return trial_date;
    }
}
 

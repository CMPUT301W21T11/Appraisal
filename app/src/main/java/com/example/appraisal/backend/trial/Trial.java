package com.example.appraisal.backend.trial;

import java.time.ZoneId;
import java.util.Date;

abstract public class Trial {
    //Need to record time of the trial, in order to show the plot of trials over time
    private Date trial_date = new Date();
    public void overrideDate(Date date) {
        this.trial_date = date;
    }
    public Date getTrialDate() {
        return trial_date;
    }
}

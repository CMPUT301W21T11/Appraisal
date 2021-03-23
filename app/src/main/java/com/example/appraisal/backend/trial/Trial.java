package com.example.appraisal.backend.trial;

import com.example.appraisal.backend.experiment.Experiment;
import com.example.appraisal.backend.user.User;

import java.util.Date;

/**
 * This is the Trial interface that all trial subclass should share
 * This mainly ensures reading data from all trial subclass are unified
 * Notice there are no setters for this interface
 */
public interface Trial {

    /**
     * This method get the value of a trial
     * For Binomial Trial, it will return its success count
     *
     * @return double -- value of the trial
     */
    double getValue();

    /**
     * This method return how many sub trial is conducted
     * For binomial trial, there is totally (success + failure) amount of sub trials
     * For Non negative integer count trial, it will return the number of time the count is entered
     * For others, it will return 1
     *
     * @return int -- the number of sub trials conducted
     */
    int getSubTrialCount();

    /**
     * This method returns which experiment the trial belongs to
     *
     * @return Experiment -- the parent experiment
     */
    Experiment getParentExperiment();

    /**
     * This method returns the date which the trial is conducted
     *
     * @return Date -- date which the trial is conducted
     */
    Date getTrialDate();

    /**
     * This method returns the User that conducted the trial
     *
     * @return User -- the conductor of the trial
     */
    User getConductor();

    /**
     * This method returns the type of the experiment. It matches the create key of TrialFactory
     *
     * @return TrialType -- the create key of this type of trial
     */
    TrialType getType();
}


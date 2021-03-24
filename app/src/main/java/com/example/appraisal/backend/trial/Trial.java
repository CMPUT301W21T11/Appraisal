package com.example.appraisal.backend.trial;

import com.example.appraisal.backend.experiment.Experiment;
import com.example.appraisal.backend.user.User;

import java.util.Date;

/**
 * This is the Trial interface that all trial subclass should share
 * This mainly ensures reading and writing data from all trial subclass are unified
 */
public interface Trial extends Comparable<Trial> {

    /**
     * This method sets the current value of the trial
     * For Bernoulli trials (i.e. Binomials), 1 = success, 0 = failure
     * Note: It is recommended to use the class native method if possible
     * (e.g. for BernoulliTrial it is setToSuccess() and setToFailure())
     *
     * @param value -- the value that the trial sets to
     */
    void setValue(double value);

    /**
     * This method overrides the create date of the trial
     * The trial's create date is default to the current date
     *
     * @param date -- the date which the trial date overrides to
     */
    void overrideDate(Date date);

    /**
     * This method get the value of a trial
     * For Binomial Trial, it will return its success count
     *
     * @return double -- value of the trial
     */
    double getValue();

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


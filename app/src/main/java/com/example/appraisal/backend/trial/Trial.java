package com.example.appraisal.backend.trial;

import androidx.annotation.NonNull;

import com.example.appraisal.backend.experiment.Experiment;
import com.example.appraisal.backend.user.User;

import java.util.Calendar;
import java.util.Date;

/**
 * This is the Trial abstract class that all trial subclass should share
 * This mainly ensures reading and writing data from all trial subclass are unified
 *
 * The trial objects can be sorted by its trial value.
 * If other sorts are desired, please implement custom Comparator
 */
public abstract class Trial implements Comparable<Trial> {
    private final Experiment parent_experiment;
    private Date trial_date;
    private final User conductor;
    private final TrialType type;

    protected Trial(Experiment parent_experiment, User conductor, TrialType type) {
        this.parent_experiment = parent_experiment;
        this.trial_date = Calendar.getInstance().getTime(); // more reliable way of getting current date
        this.conductor = conductor;
        this.type = type;
    }

    /**
     * This method makes the Trial class comparable based on its trial value
     *
     * @param o -- the other trial object being compared to
     */
    @Override
    public int compareTo(@NonNull Trial o) {
        return Double.compare(this.getValue(), o.getValue());
    }

    /**
     * This method sets the current value of the trial
     * For Bernoulli trials (i.e. Binomials), 1 = success, 0 = failure
     * For Count trial, setting the value to be 0 makes the trial invalid
     *
     * Note: It is recommended to use the class native method if possible
     * (e.g. for BernoulliTrial it is setToSuccess() and setToFailure())
     *
     * @param value -- the value that the trial sets to
     */
    public abstract void setValue(double value);

    /**
     * This method get the value of a trial
     * For Bernoulli trials, it wil return 1 to indicate success, and 0 for failure
     * For count trial, it will return 1 if the trial is valid
     *
     * @return double -- value of the trial
     */
    public abstract double getValue();

    /**
     * This method overrides the create date of the trial
     * The trial's create date is default to the current date
     *
     * @param date -- the date which the trial date overrides to
     */
    public void overrideDate(Date date) {
        this.trial_date = date;
    }

    /**
     * This method returns which experiment the trial belongs to
     *
     * @return Experiment -- the parent experiment
     */
    public Experiment getParentExperiment() {
        return parent_experiment;
    }

    /**
     * This method returns the date which the trial is conducted
     *
     * @return Date -- date which the trial is conducted
     */
    public Date getTrialDate() {
        return trial_date;
    }

    /**
     * This method returns the User that conducted the trial
     *
     * @return User -- the conductor of the trial
     */
    public User getConductor() {
        return conductor;
    }

    /**
     * This method returns the type of the experiment. It matches the create key of TrialFactory
     *
     * @return TrialType -- the create key of this type of trial
     */
    public TrialType getType() {
        return type;
    }
}


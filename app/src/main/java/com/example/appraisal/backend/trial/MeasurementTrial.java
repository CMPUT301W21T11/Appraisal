package com.example.appraisal.backend.trial;

import com.example.appraisal.backend.experiment.Experiment;
import com.example.appraisal.backend.user.User;

import java.util.Date;

/**
 * This class represents a Measurement Trial
 */
public class MeasurementTrial implements Trial {
    private double measurement;

    private Experiment parent_experiment;
    private User conductor;
    private Date trial_date;

    /**
     * Create a measurement trial
     * @param parent_experiment
     *      The parent experiment which the trial belongs to
     */
    public MeasurementTrial(Experiment parent_experiment, User conductor) {
        measurement = 0;
        this.parent_experiment = parent_experiment;
        this.conductor = conductor;
        this.trial_date = new Date();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setValue(double value) {
        measurement = value;
    }

    /**
     * {@@inheritDoc}
     */
    @Override
    public void overrideDate(Date date) {
        trial_date = date;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getValue() {
        return measurement;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Experiment getParentExperiment() {
        return parent_experiment;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Date getTrialDate() {
        return trial_date;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User getConductor() {
        return conductor;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TrialType getType() {
        return TrialType.MEASUREMENT_TRIAL;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int compareTo(Trial o) {
        return Double.compare(this.getValue(), o.getValue());
    }
}

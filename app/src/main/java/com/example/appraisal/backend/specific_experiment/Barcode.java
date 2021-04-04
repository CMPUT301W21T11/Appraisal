package com.example.appraisal.backend.specific_experiment;

import com.example.appraisal.backend.experiment.Experiment;
import com.example.appraisal.backend.user.User;

/**
 * This class represents a scanned barcode
 */
public class Barcode {
    private final String rawValue;
    private final User currentUser;
    private final Experiment currentExperiment;
    private final String data;

    public Barcode(String rawValue, User user, Experiment experiment, String data) {
        this.rawValue = rawValue;
        this.currentUser = user;
        this.currentExperiment = experiment;
        this.data = data;
    }

    /**
     * This method returns the raw value of the barcode
     * @return String -- raw value of the barcode
     */
    public String getRawValue() {
        return rawValue;
    }

    /**
     * This method gets the user of the barcode
     * @return {@link User} -- user of the barcode
     */
    public User getCurrentUser() {
        return currentUser;
    }

    /**
     * This method gets the experiment of the barcode
     * @return {@link Experiment} -- experiment of the barcode
     */
    public Experiment getCurrentExperiment() {
        return currentExperiment;
    }

    /**
     * This method get the represented action for the barcode
     * @return String -- represented action for the barcode
     */
    public String getData() {
        return data;
    }

}

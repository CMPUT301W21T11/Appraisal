package com.example.appraisal.backend.specific_experiment;

import androidx.annotation.Nullable;

import com.example.appraisal.backend.experiment.Experiment;
import com.example.appraisal.backend.user.User;

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

    public String getRawValue() {
        return rawValue;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public Experiment getCurrentExperiment() {
        return currentExperiment;
    }

    public String getData() {
        return data;
    }
}

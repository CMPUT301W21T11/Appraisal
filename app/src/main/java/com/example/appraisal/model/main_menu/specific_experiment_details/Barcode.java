package com.example.appraisal.model.main_menu.specific_experiment_details;

import androidx.annotation.Nullable;

import com.example.appraisal.backend.experiment.Experiment;
import com.example.appraisal.backend.user.User;

public class Barcode {
    private String rawValue;
    private User currentUser;
    private Experiment currentExperiment;
    private String data;

    public Barcode(String rawValue, User user, Experiment experiment,@Nullable String data) {
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

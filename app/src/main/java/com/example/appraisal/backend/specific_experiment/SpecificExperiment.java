package com.example.appraisal.backend.specific_experiment;

import android.util.Log;

import com.example.appraisal.backend.experiment.Experiment;
import com.example.appraisal.backend.trial.CountTrial;
import com.example.appraisal.backend.trial.Trial;
import com.example.appraisal.backend.user.User;
import com.example.appraisal.model.MainModel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class SpecificExperiment {
    private Experiment current_experiment;
    private ArrayList<Trial> list_of_trials;

    public SpecificExperiment() {
        try {
            current_experiment = MainModel.getCurrentExperiment();
        } catch (Exception e) {
            // Create dummy data
            Log.d("Error","MainModel.getCurrentExperiment() returned null. Filling in dummy data to prevent crash");
            current_experiment = new Experiment("Test", "Test", new User("Test", "Test", "Test@mail.com", "1234"));
            current_experiment.addTrial(new CountTrial());
        }
        list_of_trials = current_experiment.getTrials();
    }

    public ArrayList<Trial> getList_of_trials() {
        return new ArrayList<>(list_of_trials);
    }

    public int getTrialCount() {
        return list_of_trials.size();
    }

    public double getMedian() {
        return 0.0;
    }

    public double getMean() {
        return 0.0;
    }

    public double getStdev() {
        return 0.0;
    }

    public Set<User> getContributors() {
        return new HashSet<User>();
    }

    public User getExperimentOwner() {
        return current_experiment.getOwner();
    }
}

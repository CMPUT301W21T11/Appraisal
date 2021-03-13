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
    private final Experiment current_experiment;
    private ArrayList<Trial> list_of_trials;

    public SpecificExperiment(Experiment current_experiment) {
        this.current_experiment = current_experiment;
        list_of_trials = current_experiment.getTrials();
    }

    public ArrayList<Trial> getList_of_trials() {
        return new ArrayList<>(list_of_trials);
    }

    public int getTrialCount() {
        return list_of_trials.size();
    }
}

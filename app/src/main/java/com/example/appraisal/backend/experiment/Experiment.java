package com.example.appraisal.backend.experiment;

import com.example.appraisal.backend.trial.Trial;

import java.util.List;

public class Experiment {
    private List<Trial> trial_list;

    public void addTrial(Trial trial) {
        trial_list.add(trial);
    }

}

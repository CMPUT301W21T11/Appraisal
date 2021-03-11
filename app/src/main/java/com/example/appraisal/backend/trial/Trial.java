package com.example.appraisal.backend.trial;


import com.example.appraisal.backend.experiment.Experiment;

abstract public class Trial {
    public Experiment current_experiment;

    public Experiment getExperiment() { return current_experiment; }
}

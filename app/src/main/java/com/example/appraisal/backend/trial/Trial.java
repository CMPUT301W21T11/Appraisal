package com.example.appraisal.backend.trial;


import com.example.appraisal.backend.experiment.Experiment;

abstract public class Trial {
    public Experiment current_experiment;

    public void setExperiment(Experiment experiment) { this.current_experiment = experiment;} // TEST CODE

    public Experiment getExperiment() { return current_experiment; }
}

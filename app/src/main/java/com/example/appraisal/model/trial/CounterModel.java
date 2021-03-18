package com.example.appraisal.model.trial;

import com.example.appraisal.backend.experiment.Experiment;
import com.example.appraisal.backend.trial.CountTrial;

public class CounterModel {
    private CountTrial data;

    public CounterModel(Experiment parent_experiment) {
        data = new CountTrial(parent_experiment);
    }

    public void increase() {
        data.increase();
    }

    public int getCount() {
        return data.getCount();
    }

    public void toExperiment() { data.getParent_experiment().addTrial(data);}
}

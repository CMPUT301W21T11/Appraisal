package com.example.appraisal.model.trial;

import com.example.appraisal.backend.trial.NonNegIntCountTrial;

public class NonNegIntCountModel {
    private NonNegIntCountTrial data;

    public NonNegIntCountModel() {
        data = new NonNegIntCountTrial();
    }

    public void addIntCount(String s) {
        data.addIntCount(s);
    }

    public long getCount() {
        return data.getCount();
    }

    public void toExperiment() { data.getExperiment().addTrial(data);}
}

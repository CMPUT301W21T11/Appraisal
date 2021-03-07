package com.example.appraisal.model.trial;

import com.example.appraisal.backend.trial.CountTrial;

public class CounterModel {
    private CountTrial data;

    public CounterModel() {
        data = new CountTrial();
    }

    public void increase() {
        data.increase();
    }

    public int getCount() {
        return data.getCount();
    }
}

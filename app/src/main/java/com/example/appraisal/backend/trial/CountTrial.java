package com.example.appraisal.backend.trial;

import com.example.appraisal.backend.trial.Trial;

public class CountTrial extends Trial {
    private int counter;

    public CountTrial() {
        counter = 0;
    }

    public void increase() {
        counter++;
    }

    public int getCount() {
        return counter;
    }
}

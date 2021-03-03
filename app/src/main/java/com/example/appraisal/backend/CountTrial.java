package com.example.appraisal.backend;

import android.os.Build;

public class CountTrial extends Trial {
    private int counter;

    public CountTrial(User conductor, String parentExperimentID) {
        super(conductor, parentExperimentID);
        counter = 0;
    }

    public void increase() {
        counter++;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public int getCount() {
        return counter;
    }
}

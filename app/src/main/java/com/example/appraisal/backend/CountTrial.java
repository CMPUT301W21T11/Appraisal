package com.example.appraisal.backend;

import android.os.Build;

public class CountTrial extends Trial {
    private int counter;

    public CountTrial(User conductor, GeoLocation location) {
        super(conductor, location);
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

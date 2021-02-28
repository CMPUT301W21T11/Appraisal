package com.example.appraisal.backend;

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

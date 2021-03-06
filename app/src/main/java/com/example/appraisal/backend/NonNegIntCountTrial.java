package com.example.appraisal.backend;

public class NonNegIntCountTrial extends Trial {
    private long counter;
    public NonNegIntCountTrial() {
        counter = 0;
    }

    public long getCounter() {
        return counter;
    }

    public void setCounter(long counter) {
        this.counter = counter;
    }
}

package com.example.appraisal.backend;

public class NonNegIntCountTrial extends Trial {

    // probably should change variable name lol
    private int integer;

    public NonNegIntCountTrial(User conductor, GeoLocation location) {
        super(conductor, location);
        integer = 0;
    }

    // should probably rename this too sry 3 am brain
    public int getInteger() {
        return integer;
    }
}

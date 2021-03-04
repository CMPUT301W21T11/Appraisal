package com.example.appraisal.backend;

public class NonNegIntCountTrial extends Trial {
    private long nonNegIntCount;

    public NonNegIntCountTrial(User conductor, GeoLocation location) {
        super(conductor, location);
        nonNegIntCount = 0;
    }

    public long getNonNegIntCount() {
        return nonNegIntCount;
    }

    public void setNonNegIntCount(long nonNegIntCount) throws Exception{
        if (nonNegIntCount < 0) {
            throw new Exception("Error: Negative integer");
        } else {
            this.nonNegIntCount = nonNegIntCount;
        }
    }
}

package com.example.appraisal.model;

import androidx.annotation.NonNull;

import com.example.appraisal.backend.Experiment;
import com.example.appraisal.backend.NonNegIntCountTrial;
import com.example.appraisal.backend.User;

public class NonNegativeIntegerModel {
    private final NonNegIntCountTrial data;
    private final Experiment currentExperiment;
    private final long maxTrailCount;

    public NonNegativeIntegerModel(@NonNull Experiment currentExperiment) {
        User currentUser = MainModel.getInstance().getCurrent_user();
        this.currentExperiment = currentExperiment;
        this.maxTrailCount = Long.MAX_VALUE;
        data = new NonNegIntCountTrial(currentUser);
    }

    public long getCount() {
        return data.getNonNegIntCount();
    }

    public void saveToExperiment(long count) throws Exception{
        if (data.getNonNegIntCount() < maxTrailCount) {
            data.setNonNegIntCount(count);
            //currentExperiment.addTrial(data);
        } else {
            throw new NumberFormatException("Integer overflow");
        }
    }
}

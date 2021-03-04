package com.example.appraisal.model;

import androidx.annotation.NonNull;

import com.example.appraisal.backend.Experiment;
import com.example.appraisal.backend.NonNegIntCountTrial;
import com.example.appraisal.backend.User;

public class NonNegativeIntegerModel {
    private final NonNegIntCountTrial data;
    private final Experiment currentExperiment;
    private final int maxTrailCount;

    public NonNegativeIntegerModel(@NonNull Experiment currentExperiment) {
        User currentUser = MainModel.getInstance().getCurrent_user();
        this.currentExperiment = currentExperiment;
        this.maxTrailCount = Integer.MAX_VALUE;
        data = new NonNegIntCountTrial(currentUser, null);
    }

    public int getCount() {
        return data.getInteger();
    }

    public void saveToExperiment() {
        currentExperiment.addTrial(data);
    }
}

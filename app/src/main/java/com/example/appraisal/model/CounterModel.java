package com.example.appraisal.model;

import android.util.Log;

import com.example.appraisal.backend.CountTrial;
import com.example.appraisal.backend.Experiment;
import com.example.appraisal.backend.User;

public class CounterModel {
    private final CountTrial data;
    private final Experiment currentExperiment;
    private final int maxTrailCount = Integer.MAX_VALUE;

    public CounterModel(User currentUser, Experiment currentExperiment) {
        this.currentExperiment = currentExperiment;
        data = new CountTrial(currentUser, currentExperiment.getPid());
    }

    public void increase() {
        if (data.getCount() < maxTrailCount){
            data.increase();
        } else {
            throw new ArithmeticException("integer overflow");
        }
    }

    public int getCount() {
        return data.getCount();
    }

    public void saveToExperiment() {
        try {
            currentExperiment.addTrail(data);
        } catch (Exception e) {
            Log.d("Error", "Mismatch PID");
        }
    }
}

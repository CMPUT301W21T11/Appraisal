package com.example.appraisal.backend.trial;

import android.util.Log;

import com.example.appraisal.backend.experiment.Experiment;

public class NonNegIntCountTrial extends Trial {

//    Experiment testExperiment = new Experiment("title", "desc", null); // TEST CODE
    private int counter;
    public NonNegIntCountTrial() {
//        setExperiment(testExperiment); // TEST CODE
        counter = 0;
    }

    public void addIntCount(String s) {
        int count = 0;
        try {
            count = Integer.parseInt(s);
        } catch (NumberFormatException e) {
            Log.d("Warning", "User input caused integer overflow");
        }

        counter += count;
    }

    public int getCount() {
        return counter;
    }


}

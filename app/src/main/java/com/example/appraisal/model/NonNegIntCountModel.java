package com.example.appraisal.model;

import android.util.Log;
import com.example.appraisal.backend.NonNegIntCountTrial;

public class NonNegIntCountModel {
    private NonNegIntCountTrial data;

    public NonNegIntCountModel() {
        data = new NonNegIntCountTrial();
    }

    public void addIntCount(String s) {
        data.addIntCount(s);
    }

    public long getCount() {
        return data.getCount();
    }
}

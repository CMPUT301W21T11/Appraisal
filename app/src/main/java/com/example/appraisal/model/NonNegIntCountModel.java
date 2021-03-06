package com.example.appraisal.model;

import android.util.Log;

import com.example.appraisal.backend.NonNegIntCountTrial;

public class NonNegIntCountModel {
    private NonNegIntCountTrial data;

    public NonNegIntCountModel() {
        data = new NonNegIntCountTrial();
    }

    public void saveCount(String user_input) {
        if (user_input.trim().isEmpty()) {
            Log.d("Info","Empty input");
            user_input = "0";
        }
        long count = Long.parseLong(user_input);
        data.setCounter(count);
    }

    public long getCount() {
        return data.getCounter();
    }
}

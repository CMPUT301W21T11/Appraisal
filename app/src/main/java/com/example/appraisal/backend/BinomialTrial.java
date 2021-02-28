package com.example.appraisal.backend;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class BinomialTrial extends Trial {
    private int success_counter;
    private int failure_counter;

    public BinomialTrial(){
        success_counter = 0;
        failure_counter = 0;
    }

    public void addSuccess(){
        success_counter++;
    }

    public void addFailure(){
        failure_counter++;
    }

    public int getSuccessCount(){
        return success_counter;
    }

    public int getFailureCount(){
        return failure_counter;
    }

}

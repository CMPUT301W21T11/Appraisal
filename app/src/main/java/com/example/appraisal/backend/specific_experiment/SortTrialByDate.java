package com.example.appraisal.backend.specific_experiment;

import androidx.annotation.NonNull;

import com.example.appraisal.backend.trial.Trial;

import java.util.Comparator;
import java.util.Date;


/**
 * This class is a comparator that sorts the trials based on date with the nearest day
 */
public class SortTrialByDate implements Comparator<Trial> {
    @Override
    public int compare(@NonNull Trial o1, @NonNull Trial o2) {
        Date t1_date = o1.getTrialDate();
        Date t2_date = o2.getTrialDate();

        return t1_date.compareTo(t2_date);
    }
}

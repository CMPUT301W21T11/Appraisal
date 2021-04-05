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

        if (t1_date.after(t2_date)) {
            return 1;
        } else if (t1_date.before(t2_date)) {
            return -1;
        } else {
            return 0;
        }
    }
}

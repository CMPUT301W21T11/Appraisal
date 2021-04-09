package com.example.appraisal.backend.specific_experiment;

import androidx.annotation.NonNull;

import com.example.appraisal.backend.trial.Trial;

import java.util.Comparator;
import java.util.Date;


/**
 * This class is a comparator that sorts the trials based on date with the nearest day
 */
public class SortTrialByDate implements Comparator<Trial> {

    /**
     * This method compares 2 Trial object's date
     *
     * @param o1 -- trial being compared
     * @param o2 -- trial to compare to
     * @return int -- indicates which object is conducted first
     * 1 if o1 is conducted before o2
     * -1 if o1 is conducted after o2
     * 0 if they are conducted at the same date
     */
    @Override
    public int compare(@NonNull Trial o1, @NonNull Trial o2) {
        Date t1_date = o1.getTrialDate();
        Date t2_date = o2.getTrialDate();

        return t1_date.compareTo(t2_date);
    }
}

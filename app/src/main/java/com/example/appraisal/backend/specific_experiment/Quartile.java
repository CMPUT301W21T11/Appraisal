package com.example.appraisal.backend.specific_experiment;

import androidx.annotation.NonNull;

import com.example.appraisal.backend.trial.Trial;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This class is for storing all the quartile related information of a given list of trials
 */
public class Quartile {
    private final List<Trial> sorted_list_of_trials;

    /**
     * Constructor of the Quartile class
     * @param list_of_trials {@link Trial}
     *      The list of trials that need to generate its quartiles
     */
    public Quartile(@NonNull List<Trial> list_of_trials) {
        this.sorted_list_of_trials = list_of_trials;
        Collections.sort(sorted_list_of_trials);
    }

    /**
     * Return the number of trials conducted in the experiment.
     * Note: It is NOT always the length of list_of_trials.
     * @return total
     *      Total number of trials conducted by the experiment
     */
    public int getTotalNumTrial() {
        return sorted_list_of_trials.size();
    }

    public List<Trial> getSortedListOfTrials() {
        return this.sorted_list_of_trials;
    }
    /**
     * Return the minimum value of all the Trials of the supplied experiment
     * @return min
     *      Min value of the Trials
     */
    public double getTrialMinValue() {
        if (sorted_list_of_trials.isEmpty()) {
            return 0;
        }
        return Collections.min(sorted_list_of_trials).getValue();
    }

    /**
     * Return the maximum value of all the Trials of the supplied experiment
     * @return max
     *      Max value of the Trials
     */
    public double getTrialMaxValue() {
        if (sorted_list_of_trials.isEmpty()) {
            return 0;
        }
        return Collections.max(sorted_list_of_trials).getValue();
    }

    /**
     * Return the median of the list of trials
     * @return median
     *      Median of the Trials
     */
    public double getMedian() {
        if (sorted_list_of_trials.isEmpty()) {
            return 0;
        }
        int median_index = sorted_list_of_trials.size() / 2;
        return sorted_list_of_trials.get(median_index).getValue();
    }

    /**
     * Return the first quartile of the list of trials
     * @return q1
     *      Q1 of the Trials
     */
    public double getFirstQuartile() {
        if (sorted_list_of_trials.isEmpty()) {
            return 0;
        }
        int q1_index = sorted_list_of_trials.size() / 4;
        return sorted_list_of_trials.get(q1_index).getValue();
    }

    /**
     * Return the third quartile of the list of trials
     * @return q3
     *      Q3 of the Trials
     */
    public double getThirdQuartile() {
        if (sorted_list_of_trials.isEmpty()) {
            return 0;
        }
        int q3_index = (sorted_list_of_trials.size() * 3) / 4;
        return sorted_list_of_trials.get(q3_index).getValue();
    }

    /**
     * Return the Inter Quartile Range of the list of trials
     * @return iqr
     *      IQR of the trials
     */
    public double getIQR() {
        return getThirdQuartile() - getFirstQuartile();
    }

    /**
     * Return the list of outlier values
     * Outliers are values that exceed the min and max range.
     * @return outLiers
     *      List of outliers
     */
    public List<Float> getOutLiers() {
        List<Float> outLiers = new ArrayList<>();
        double upper = getFirstQuartile() + (1.5 * getIQR());
        double lower = getThirdQuartile() - (1.5 * getIQR());

        for (Trial t: sorted_list_of_trials) {
            float value = (float) t.getValue();
            if ((value > upper) || (value < lower)) {
                outLiers.add(value);
            }
        }

        return outLiers;
    }
}

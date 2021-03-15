package com.example.appraisal.backend.specific_experiment;

import com.example.appraisal.backend.trial.BinomialTrial;
import com.example.appraisal.backend.trial.CountTrial;
import com.example.appraisal.backend.trial.MeasurementTrial;
import com.example.appraisal.backend.trial.NonNegIntCountTrial;
import com.example.appraisal.backend.trial.Trial;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This class is for storing all the quartile related information of a given list of trials
 */
public class Quartile {
    private final List<Trial> list_of_trials;
    private final int total;
    private final List<Float> sorted_list_of_trials_as_double;
    private float q1;
    private float q3;

    /**
     * Constructor of the Quartile class
     * @param list_of_trials {@link Trial}
     *      The list of trials that need to generate its quartiles
     */
    public Quartile(List<Trial> list_of_trials) {
        this.list_of_trials = list_of_trials;
        total = initTotal();
        sorted_list_of_trials_as_double = generateTrialListToDouble();
        q1 = initQ1();
        q3 = initQ3();
    }

    private int initTotal() {
        Trial trial = list_of_trials.get(0);
        if (trial instanceof BinomialTrial) {
            int total = 0;
            // Binomial trials are sum of Bernoulli trials. Therefore total += total trial of each Binomial
            for (Trial trl: list_of_trials) {
                BinomialTrial t = (BinomialTrial) trl;
                total = total + t.getFailureCount() + t.getSuccessCount();
            }
            return total;
        } else {
            return list_of_trials.size();
        }
    }

    private List<Float> generateTrialListToDouble() {
        List<Float> trial_list = new ArrayList<>();
        for (Trial trial:list_of_trials) {
            if (trial instanceof BinomialTrial) {
                BinomialTrial t1 = (BinomialTrial) trial;
                float prob = t1.getSuccessCount() / (float) total;
                trial_list.add(prob);

            } else if (trial instanceof CountTrial) {
                CountTrial t2 = (CountTrial) trial;
                trial_list.add((float) t2.getCount());

            } else if (trial instanceof NonNegIntCountTrial) {
                NonNegIntCountTrial t3 = (NonNegIntCountTrial) trial;
                trial_list.add((float) t3.getCount());

            } else if (trial instanceof MeasurementTrial) {
                MeasurementTrial t4 = (MeasurementTrial) trial;
                // TODO: after measurement trial is implemented
            }
        }

        Collections.sort(trial_list);
        return trial_list;
    }

    private float initQ1() {
        int q1_index = sorted_list_of_trials_as_double.size() / 4;
        return sorted_list_of_trials_as_double.get(q1_index);
    }

    private float initQ3() {
        int q3_index = (sorted_list_of_trials_as_double.size() * 3) / 4;
        return sorted_list_of_trials_as_double.get(q3_index);
    }

    /**
     * Return the number of trials conducted in the experiment.
     * Note: It is NOT always the length of list_of_trials.
     * @return total
     *      Total number of trials conducted by the experiment
     */
    public int getTotalNumTrial() {
        return total;
    }

    public List<Float> getListOfTrialsAsFloat() {
        return this.sorted_list_of_trials_as_double;
    }
    /**
     * Return the minimum value of all the Trials of the supplied experiment
     * @return min
     *      Min value of the Trials
     */
    public float getTrialMinValue() {
        return Collections.min(sorted_list_of_trials_as_double);
    }

    /**
     * Return the maximum value of all the Trials of the supplied experiment
     * @return max
     *      Max value of the Trials
     */
    public float getTrialMaxValue() {
        return Collections.max(sorted_list_of_trials_as_double);
    }

    /**
     * Return the median of the list of trials
     * @return median
     *      Median of the Trials
     */
    public float getMedian() {
        int median_index = sorted_list_of_trials_as_double.size() / 2;
        return sorted_list_of_trials_as_double.get(median_index);
    }

    /**
     * Return the first quartile of the list of trials
     * @return q1
     *      Q1 of the Trials
     */
    public float getFirstQuartile() {
        return q1;
    }

    /**
     * Return the third quartile of the list of trials
     * @return q3
     *      Q3 of the Trials
     */
    public float getThirdQuartile() {
        return q3;
    }

    /**
     * Return the Inter Quartile Range of the list of trials
     * @return iqr
     *      IQR of the trials
     */
    public float getIQR() {
        return getThirdQuartile() - getFirstQuartile();
    }

    public List<Float> getOutLiers() {
        List<Float> outLiers = new ArrayList<>();
        double upper = q3 + (1.5 * getIQR());
        double lower = q1 - (1.5 * getIQR());

        for (Float value: sorted_list_of_trials_as_double) {
            if ((value > upper) || (value < lower)) {
                outLiers.add(value);
            }
        }

        return outLiers;
    }
}

package com.example.appraisal.backend.specific_experiment;

import android.icu.util.Measure;
import android.util.Log;

import com.example.appraisal.backend.experiment.Experiment;
import com.example.appraisal.backend.trial.BinomialTrial;
import com.example.appraisal.backend.trial.CountTrial;
import com.example.appraisal.backend.trial.MeasurementTrial;
import com.example.appraisal.backend.trial.NonNegIntCountTrial;
import com.example.appraisal.backend.trial.Trial;
import com.example.appraisal.backend.user.User;
import com.example.appraisal.model.MainModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.function.BinaryOperator;

/**
 * This class represents the statistical details of each experiment, e.g. Mean, Standard Deviations, etc.
 */
public class SpecificExperiment {
    private final Experiment current_experiment;
    private final ArrayList<Trial> list_of_trials;
    private final int total;
    /**
     * Creates an instance of the Specific Experiment wrapper
     * @param current_experiment
     *      This is the experiment that needs to generate statistics
     */
    public SpecificExperiment(Experiment current_experiment) {
        this.current_experiment = current_experiment;
        list_of_trials = current_experiment.getTrials();
        total = getTotal();
    }

    private int getTotal() {
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

    /**
     * Return the list of trials of the experiment
     * @return list_of_trials
     *      Copy of the list_of_trials in the experiment
     */
    public ArrayList<Trial> getList_of_trials() {
        return new ArrayList<>(list_of_trials);
    }

    /**
     * Return the number of trials conducted in the experiment.
     * Note: It is NOT always the length of list_of_trials.
     * @return total
     *      Total number of trials conducted by the experiment
     */
    public int getTotalNumberOfTrials() {
        return total;
    }

    /**
     * Return the number of trials conducted per Date of the experiment
     * @return SortedMap<Date, Integer>
     *      SortedMap of Date and number of trials
     */
    public SortedMap<Date, Integer> getTrialsPerDate() {
        // hashmap to store all the trial count for a given date
        // sort by date
        SortedMap<Date, Integer> data_points = new TreeMap<>();
        for (Trial trial:list_of_trials) {
            Date key = trial.getTrialDate();
            if (data_points.containsKey(key)) { // i.e. date entry already exist
                // increase trial count
                int count = data_points.get(key);
                count++;
                data_points.put(key, count);
            } else {
                // create new entry
                data_points.put(key, 1);
            }
        }
        return data_points;
    }

    /**
     * Return the mean (average) of the supplied experiment
     * @return double
     *      Mean of the experiment
     */
    public double getExperimentMean() {
        if (total == 0) {
            return 0.0;
        }

        long result_sum = 0;
        for (Trial trial:list_of_trials) {
            if (trial instanceof BinomialTrial) {
                BinomialTrial t1 = (BinomialTrial) trial;
                result_sum += t1.getSuccessCount();

            } else if (trial instanceof CountTrial) {
                CountTrial t2 = (CountTrial) trial;
                result_sum += t2.getCount();

            } else if (trial instanceof NonNegIntCountTrial) {
                NonNegIntCountTrial t3 = (NonNegIntCountTrial) trial;
                result_sum += t3.getCount();

            } else if (trial instanceof MeasurementTrial) {
                MeasurementTrial t4 = (MeasurementTrial) trial;
                // TODO: after measurement trial is implemented
            }
        }

        return result_sum / (double) total;
    }

    /**
     * Return the standard deviation of the supplied experiment
     * @return double
     *      Standard Deviation of the experiment
     */
    public double getExperimentStDev() {
        if (total == 0) {
            return 0.0;
        }

        double mean = getExperimentMean();
        double square_error = 0.0;

        for (Trial trial: list_of_trials) {
            if (trial instanceof BinomialTrial) {
                BinomialTrial t1 = (BinomialTrial) trial;
                square_error += Math.pow(t1.getSuccessCount() - mean, 2);

            } else if (trial instanceof CountTrial) {
                CountTrial t2 = (CountTrial) trial;
                square_error += Math.pow(t2.getCount() - mean, 2);

            } else if (trial instanceof NonNegIntCountTrial) {
                NonNegIntCountTrial t3 = (NonNegIntCountTrial) trial;
                square_error += Math.pow(t3.getCount() - mean, 2);

            } else if (trial instanceof MeasurementTrial) {
                MeasurementTrial t4 = (MeasurementTrial) trial;
                // TODO: after measurement trial is implemented
            }
        }
        return Math.sqrt((square_error / total));
    }

    /**
     * Return the minimum value of all the Trials of the supplied experiment
     * @return double
     *      Min value of the Trials
     */
    public double getTrialMinValue() {
        double max_value = Double.MIN_VALUE;
        for (Trial trial: list_of_trials) {
            float value = 0;
            if (trial instanceof BinomialTrial) {
                // Note: For binomial trials, we actually want its probability
                BinomialTrial t1 = (BinomialTrial) trial;
                value = t1.getSuccessCount() / (float) total;

            } else if (trial instanceof CountTrial) {
                CountTrial t2 = (CountTrial) trial;
                value = t2.getCount();

            } else if (trial instanceof NonNegIntCountTrial) {
                NonNegIntCountTrial t3 = (NonNegIntCountTrial) trial;
                value = t3.getCount();

            } else if (trial instanceof MeasurementTrial) {
                MeasurementTrial t4 = (MeasurementTrial) trial;
                // TODO: after measurement trial is implemented
            }

            // Update max values
            if (value > max_value) {
                max_value = value;
            }
        }
        return max_value;
    }

    /**
     * Return the maximum value of all the Trials of the supplied experiment
     * @return double
     *      Max value of the Trials
     */
    public double getTrialMaxValue() {
        double min_value = Double.MAX_VALUE;
        for (Trial trial: list_of_trials) {
            float value = 0;
            if (trial instanceof BinomialTrial) {
                // Note: For binomial trials, we actually want its probability
                BinomialTrial t1 = (BinomialTrial) trial;
                value = t1.getSuccessCount() / (float) total;

            } else if (trial instanceof CountTrial) {
                CountTrial t2 = (CountTrial) trial;
                value = t2.getCount();

            } else if (trial instanceof NonNegIntCountTrial) {
                NonNegIntCountTrial t3 = (NonNegIntCountTrial) trial;
                value = t3.getCount();

            } else if (trial instanceof MeasurementTrial) {
                MeasurementTrial t4 = (MeasurementTrial) trial;
                // TODO: after measurement trial is implemented
            }

            // Update min value
            if (value < min_value) {
                min_value = value;
            }
        }
        return min_value;
    }

    /**
     * Return the Frequency of each trial measurements for a predefined interval
     * @Return SortedMap<Double, Integer>
     *     This is the hash table of no. of trials for each interval
     */
    public SortedMap<Double, Integer> getMeasurementFrequency() {
        // How to calculate measurement frequency and intervals are taken from MoreStream
        // Author: MoreStream
        // URL: https://www.moresteam.com/toolbox/histogram.cfm
        SortedMap<Double, Integer> data_points = new TreeMap<>();
        int INTERVAL_NUM = 12; // Fixed to 12 intervals. This is the most our app could reasonably display
        if (Math.sqrt(total) < INTERVAL_NUM) {
            // If the sample does not require that many intervals we can reduce it
            INTERVAL_NUM = (int) Math.floor(Math.sqrt(total));
        }

        // find min and max values
        double min_value = getTrialMinValue();
        double max_value = getTrialMaxValue();

        // calculate interval width
        double width = (max_value - min_value) / INTERVAL_NUM;
        ArrayList<Double> avaliable_interval_start_values = new ArrayList<>(); // cache the interval start values
        // initialize data_points and record interval values
        for (double i = min_value; i < max_value; i += width) {
            data_points.put(i, 0);
            avaliable_interval_start_values.add(i);
        }

        // record frequencies
        for (Trial trial: list_of_trials) {
            int interval_index = 0;
            if (trial instanceof BinomialTrial) {
                BinomialTrial t1 = (BinomialTrial) trial;
                float prob = t1.getSuccessCount() / (float) total;
                interval_index = (int) Math.floor((prob - min_value) / width);

            } else if (trial instanceof CountTrial) {
                CountTrial t2 = (CountTrial) trial;
                long count = t2.getCount();
                interval_index = (int) Math.floor(count / width);

            } else if (trial instanceof NonNegIntCountTrial) {
                NonNegIntCountTrial t3 = (NonNegIntCountTrial) trial;
                long count = t3.getCount();
                interval_index = (int) Math.floor(count / width);

            } else if (trial instanceof MeasurementTrial) {
                MeasurementTrial t4 = (MeasurementTrial) trial;
                // TODO: after measurement trial is implemented
            }
            // obtain interval value key
            double interval_key = avaliable_interval_start_values.get(interval_index);

            // increment count at interval by 1
            data_points.put(interval_key, data_points.get(interval_key) + 1);
        }

        return data_points;
    }
}

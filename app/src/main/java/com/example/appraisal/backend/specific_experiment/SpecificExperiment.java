package com.example.appraisal.backend.specific_experiment;

import com.example.appraisal.backend.experiment.Experiment;
import com.example.appraisal.backend.trial.Trial;
import com.example.appraisal.backend.trial.TrialType;
import com.example.appraisal.backend.user.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * This class represents the statistical details of each experiment, e.g. Mean, Standard Deviations, Owner etc.
 */
public class SpecificExperiment {
    private final Experiment current_experiment;
    private final ArrayList<Trial> list_of_trials;
    private List<Float> list_of_trials_as_float;
    private final int total;
    private Quartile quartile;

    /**
     * Creates an instance of the Specific Experiment wrapper
     * @param current_experiment
     *      This is the experiment that needs to generate statistics
     */
    public SpecificExperiment(Experiment current_experiment) {
        this.current_experiment = current_experiment;
        list_of_trials = current_experiment.getTrialList();
        quartile = new Quartile(list_of_trials);
        total = quartile.getTotalNumTrial();
        List<Trial> sorted_trials = quartile.getSortedListOfTrials();
        list_of_trials_as_float = new ArrayList<>();
        for (Trial t: sorted_trials) {
            list_of_trials_as_float.add((float) t.getValue());
        }
    }

    /**
     * Return the owner of the provided experiment
     * @return {@link User}
     *      This is the owner of the experiment
     */
    public String getOwner() {
        return current_experiment.getOwner();
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
     * @return data_points
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
                data_points.put(key, data_points.get(key) + 1);
            } else {
                // create new entry
                data_points.put(key, 1);
            }
        }
        return data_points;
    }

    /**
     * Return the mean (average) of the supplied experiment
     * @return mean
     *      Mean of the experiment as float
     */
    public float getExperimentMean() {
        if (total == 0) {
            return 0;
        }

        double sum = 0;
        for (float d: list_of_trials_as_float) {
            sum += d;
        }

        return (float) (sum / total);
    }

    /**
     * Return the standard deviation of the supplied experiment
     * @return stdDev
     *      Standard Deviation of the experiment
     */
    public float getExperimentStDev() {
        if (total <= 1) { // when there is only 1 sample there is no variance
            return 0;
        }

        float mean = getExperimentMean();
        double square_error = 0;

        for (float d_i: list_of_trials_as_float) {
            square_error += Math.pow(d_i - mean, 2);
        }

        return (float) Math.sqrt((square_error / (total - 1))); // for sample variance, we need to subtract 1 from total
        // the math proof is a bit too much to write in a program comment. Google "Unbiased estimators" if you want to know more
    }

    /**
     * Return the width of the interval of the generated histogram
     * @return width
     *      Width of the interval
     */
    public double getHistogramIntervalWidth() {
        // safety check
        if (total == 0) {
            return 1;
        }

        // using non dynamic width
        int INTERVAL_NUM = 18;
        if (list_of_trials.get(0).getType() == TrialType.BINOMIAL_TRIAL) {
            INTERVAL_NUM = 1; // For bernoulli trials there are only 2 possible values
        } else if (Math.round(Math.sqrt(list_of_trials.size())) < INTERVAL_NUM) {
            INTERVAL_NUM = (int) Math.ceil(Math.sqrt(list_of_trials.size()));
        }
        // find min and max values
        double min_value = quartile.getTrialMinValue();
        double max_value = quartile.getTrialMaxValue();

        // calculate interval width
        return (max_value - min_value) / INTERVAL_NUM;
    }

    /**
     * Return the Frequency of each trial measurements for a predefined interval
     * @return data_points
     *     This is the hash table of no. of trials for each interval
     */
    public SortedMap<Float, Integer> getHistogramIntervalFrequency() {
        // How to calculate measurement frequency and intervals are taken from MoreStream
        // Author: MoreStream
        // URL: https://www.moresteam.com/toolbox/histogram.cfm
        SortedMap<Float, Integer> data_points = new TreeMap<>();

        double min_value = quartile.getTrialMinValue();
        double max_value = quartile.getTrialMaxValue();

        double width = getHistogramIntervalWidth();

        ArrayList<Float> available_interval_start_values = new ArrayList<>(); // cache the interval start values
        // initialize data_points and record interval values
        for (double i = min_value; i <= max_value; i += width) {
            data_points.put((float) i, 0);
            available_interval_start_values.add((float) i);
            if (width == 0) { // edge case when the experiment is empty
                data_points.put(0.0f, 0);
                return data_points;
            }
        }

        // record frequencies
        for (float measurement_i: list_of_trials_as_float) {
            // calculate which interval the value belongs to
            int interval_index = (int) Math.floor((measurement_i - min_value) / width);

            // safety check to prevent index errors
            if (interval_index < 0) {
                interval_index = 0;
            } else if (interval_index >= available_interval_start_values.size()) {
                interval_index = available_interval_start_values.size() - 1;
            }

            // obtain interval value key
            float interval_key = available_interval_start_values.get(interval_index);

            // increment count at interval by 1
            data_points.put(interval_key, data_points.get(interval_key) + 1);
        }

        return data_points;
    }

    /**
     * Return the Quartile object that contains all the quartile informations of the given experiment
     * @return
     */
    public Quartile getQuartile() {
        return quartile;
    }

}

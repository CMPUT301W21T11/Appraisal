package com.example.appraisal.backend.specific_experiment;

import com.example.appraisal.backend.experiment.Experiment;
import com.example.appraisal.backend.trial.CountTrial;
import com.example.appraisal.backend.trial.Trial;
import com.example.appraisal.backend.user.User;
import com.example.appraisal.model.MainModel;

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
//    private final ArrayList<String> trial_id_list;
    private final List<Float> list_of_trials_as_float;
    private final int total;
    private Quartile quartile;

    /**
     * Creates an instance of the Specific Experiment wrapper
     * @param current_experiment
     *      This is the experiment that needs to generate statistics
     */
    public SpecificExperiment(Experiment current_experiment) {
        this.current_experiment = current_experiment;
//        trial_id_list = current_experiment.getTrials();
        list_of_trials = current_experiment.getTrialList();
        quartile = new Quartile(list_of_trials);
        total = quartile.getTotalNumTrial();
        list_of_trials_as_float = quartile.getListOfTrialsAsFloat();
//        experimenters_list = current_experiment.getExperimenters();
    }

    /**
     * Return the owner of the provided experiment
     * @return {@link User}
     *      This is the owner of the experiment
     */
    public String getOwner() {
        return current_experiment.getOwner();
    }

//    /**
//     * Return the list of contributors (i.e. people who have added trials to the experiment)
//     * @return contributors
//     *      list of contributors of the experiment
//     */
//    public List getContributors() {
//        return experimenters;
//    }

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
        if (total == 0) {
            return 0;
        }

        float mean = getExperimentMean();
        double square_error = 0;

        for (float d_i: list_of_trials_as_float) {
            square_error += Math.pow(d_i - mean, 2);
        }

        return (float) Math.sqrt((square_error / total));
    }

    /**
     * Return the width of the interval of the generated histogram
     * @return width
     *      Width of the interval
     */
    public double getHistogramIntervalWidth() {
        // using non dynamic width
        int INTERVAL_NUM = 10;

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

        float min_value = quartile.getTrialMinValue();
        float max_value = quartile.getTrialMaxValue();

        double width = getHistogramIntervalWidth();

        ArrayList<Float> available_interval_start_values = new ArrayList<>(); // cache the interval start values
        // initialize data_points and record interval values
        for (float i = min_value; i <= max_value; i += width) {
            data_points.put(i, 0);
            available_interval_start_values.add(i);
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

package com.example.appraisal.backend.specific_experiment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.appraisal.backend.trial.Trial;
import com.example.appraisal.backend.trial.TrialType;
import com.google.common.collect.Comparators;
import com.google.common.collect.Ordering;

import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * This class is for generating the plot of trial results over time
 */
public class TrialResultsOverTime {

    private final List<Trial> sorted_trial_list_by_date;
    private final Date trial_start_date;

    public TrialResultsOverTime(@NonNull List<Trial> sorted_trial_list_by_date) {
        this.sorted_trial_list_by_date = sorted_trial_list_by_date;
        if (sorted_trial_list_by_date.size() > 0) {
            // check if the input list is sorted
            Comparator<Trial> sort_by_date = new SortTrialByDate();
            if (!Ordering.from(sort_by_date).isOrdered(sorted_trial_list_by_date)) {
                sorted_trial_list_by_date.sort(sort_by_date);
            }
            trial_start_date = roundToDay(sorted_trial_list_by_date.get(0).getTrialDate());
        } else {
            trial_start_date = new Date();
        }
    }

    /**
     * This method creates the result over time represented by a Sorted Map
     * @param trialType -- the type of the trials as a {@link TrialType} enum
     * @return SortedMap -- result against date
     */
    @NonNull
    public SortedMap<Date, Double> createTrialResultsOverTime(@NonNull TrialType trialType) {
        // check for empty list of trials
        if (sorted_trial_list_by_date.isEmpty()) {
            return new TreeMap<>();
        } else if (trialType == TrialType.COUNT_TRIAL) {
            // count trials per day
            return countTrialsPerDate();
        } else {
            // mean per day
            return trialResultsPerDate();
        }
    }

    @NonNull
    private SortedMap<Date, Double> trialResultsPerDate() {
        SortedMap<Date, Double> data_points = new TreeMap<>();

        Map<Date, Integer> counts_per_day_interval = new HashMap<>();
        Map<Date, Double> result_per_day_interval = new HashMap<>();

        Date date_interval = trial_start_date;
        int prev_count = 0;
        double prev_result = 0;

        // get the trials per day and success per day
        for (Trial trial: sorted_trial_list_by_date) {
            Date trial_date = roundToDay(trial.getTrialDate());

            while (date_interval.before(trial_date)) {
                counts_per_day_interval.put(date_interval, prev_count);
                result_per_day_interval.put(date_interval, prev_result);
                date_interval = incrementDayByOne(date_interval);
            }

            // increment count and value
            prev_count++;
            prev_result += trial.getValue();

            // store to map
            counts_per_day_interval.put(date_interval, prev_count);
            result_per_day_interval.put(date_interval, prev_result);
        }

        // calculate success rate and assign to data points
        for (Map.Entry<Date, Integer> entry: counts_per_day_interval.entrySet()) {
            Date trial_date = entry.getKey();
            int total_count = entry.getValue();
            double result = ifNullDouble(result_per_day_interval.get(trial_date));

            double success_rate;
            if (total_count == 0) {
                success_rate = 0.0;
            } else {
                success_rate = result / total_count;
            }
            data_points.put(trial_date, success_rate);
        }

        return data_points;
    }

    @NonNull
    private SortedMap<Date, Double> countTrialsPerDate() {
        SortedMap<Date, Double> data_points = new TreeMap<>();

        Date date_interval = roundToDay(trial_start_date); // get trial start date
        int current_count = 0; // initialize current count

        for (Trial trial: sorted_trial_list_by_date) {
            Date trial_date = roundToDay(trial.getTrialDate());
            // If the date interval is before the trial date, increase it until they are matched.
            while (date_interval.before(trial_date)) {
                data_points.put(date_interval, (double) current_count);
                date_interval = incrementDayByOne(date_interval);
            }
            current_count += trial.getValue();
            data_points.put(date_interval, (double) current_count);
        }

        return data_points;
    }


    @NonNull
    private Date roundToDay(@NonNull Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    @NonNull
    private Date incrementDayByOne(@NonNull Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, 1);
        return cal.getTime();
    }

    private double ifNullDouble(@Nullable Double value) {
        if (value == null) {
            return 0;
        } else {
            return value;
        }
    }
}

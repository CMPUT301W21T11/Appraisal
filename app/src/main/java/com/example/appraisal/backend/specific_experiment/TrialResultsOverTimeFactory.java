package com.example.appraisal.backend.specific_experiment;

import androidx.annotation.NonNull;

import com.example.appraisal.backend.trial.Trial;
import com.example.appraisal.backend.trial.TrialType;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public class TrialResultsOverTimeFactory {

    private final List<Trial> trial_list_by_date;
    private final Date trial_start_date;

    public TrialResultsOverTimeFactory(@NonNull List<Trial> sorted_date_trial_list) {
        this.trial_list_by_date = sorted_date_trial_list;
        if (trial_list_by_date.size() > 0) {
            trial_start_date = roundToDay(trial_list_by_date.get(0).getTrialDate());
        } else {
            trial_start_date = new Date();
        }
    }

    @NonNull
    public SortedMap<Date, Double> createTrialsPerDate(@NonNull TrialType trialType) {
        // check for empty list of trials
        if (trial_list_by_date.isEmpty()) {
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

        // get the trials per day and success per day
        for (Trial trial: trial_list_by_date) {
            Date trial_date = roundToDay(trial.getTrialDate());

            if (trial_date.equals(date_interval)) {
                // get previous total count and success count
                int prev_count = ifNullInt(counts_per_day_interval.get(trial_date));
                double prev_result = ifNullDouble(result_per_day_interval.get(trial_date));

                // increment total count and result value per day
                prev_count++;
                prev_result += trial.getValue();

                // store to map
                counts_per_day_interval.put(trial_date, prev_count);
                result_per_day_interval.put(trial_date, prev_result);
            } else {
                // create entry if not exist
                if (!counts_per_day_interval.containsKey(trial_date)) {
                    counts_per_day_interval.put(trial_date, 0);
                    result_per_day_interval.put(trial_date, 0.0);
                }
            }

            date_interval = incrementDayByOne(date_interval);
        }

        // calculate success rate and assign to data points
        for(Map.Entry<Date, Integer> entry: counts_per_day_interval.entrySet()) {
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

        Date date_interval = trial_start_date; // get trial start date
        int current_count = 0; // initialize current count

        for (Trial trial: trial_list_by_date) {
            Date trial_date = roundToDay(trial.getTrialDate()); // get the conduct date
            if (trial_date.equals(date_interval)) {
                // increment current count
                long count_trial = Math.round(trial.getValue());
                current_count += count_trial;
                data_points.put(trial_date, (double) current_count);
            } else {
                data_points.put(date_interval, (double) current_count);
            }
            date_interval = incrementDayByOne(date_interval);
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

    private int ifNullInt(Integer value) {
        if (value == null) {
            return 0;
        } else {
            return value;
        }
    }

    private double ifNullDouble(Double value) {
        if (value == null) {
            return 0;
        } else {
            return value;
        }
    }
}

package com.example.appraisal.backend.specific_experiment;

import androidx.annotation.NonNull;

import com.example.appraisal.backend.experiment.Experiment;
import com.example.appraisal.backend.trial.CountTrial;
import com.example.appraisal.backend.trial.MeasurementTrial;
import com.example.appraisal.backend.trial.NonNegIntCountTrial;
import com.example.appraisal.backend.trial.Trial;
import com.example.appraisal.backend.trial.TrialFactory;
import com.example.appraisal.backend.trial.TrialType;
import com.example.appraisal.backend.user.User;

import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

/**
 * Test class for Specific Experiment
 */
public class SpecificExpTest {
    private SpecificExperimentStatistics test_obj_int;
    private SpecificExperimentStatistics test_obj_float;

    private final int TRIAL_SIZE = 1000;
    private final double MARGIN_OF_ERROR = 0.5;

    private Experiment experiment_int;
    private Experiment experiment_float;

    private List<Integer> control_list_int;
    private List<Float> control_list_float;

    /**
     * Initialize the required classes. Also produce an array for control
     */
    @Before
    public void init() {
        experiment_int = new Experiment("temp","temp", "temp", TrialType.NON_NEG_INT_TRIAL.getLabel(), false, 0, "temp", "temp");
        User test_user = new User("test", "test", "test", "test");
        control_list_int = new ArrayList<>();
        for (int i = 0; i < TRIAL_SIZE; i++) {
            int count = (int) (Math.random() * (100 * Math.round(Math.random() * 10)));
            control_list_int.add(count);
            NonNegIntCountTrial trial = new NonNegIntCountTrial(experiment_int, test_user);
            trial.setValue(count);
            experiment_int.addTrial(trial);
        }

        experiment_float = new Experiment("temp","temp", "temp", TrialType.MEASUREMENT_TRIAL.getLabel(), false, 0, "temp", "temp");
        control_list_float = new ArrayList<>();
        for (int i = 0; i < TRIAL_SIZE; i++) {
            float measurement = (float) (Math.random());
            control_list_float.add(measurement);
            MeasurementTrial trial = new MeasurementTrial(experiment_float, test_user);
            trial.setValue(measurement);
            experiment_float.addTrial(trial);
        }

        test_obj_float = new SpecificExperimentStatistics(experiment_float);
        test_obj_int = new SpecificExperimentStatistics(experiment_int);
    }

    /**
     * Test mean calculation for float trials
     */
    @Test
    public void testFloatMean() {
        float sum = 0;
        for (Float i: control_list_float) {
            sum += i;
        }
        float mean = sum / control_list_float.size();

//        assertEquals(mean, test_obj_float.getExperimentMean(), MARGIN_OF_ERROR);
        assertTrue(Math.abs(mean - test_obj_float.getExperimentMean()) < MARGIN_OF_ERROR);
    }

    /**
     * Test mean calculation for int trials
     */
    @Test
    public void testIntMean() {
        int sum = 0;
        for (Integer i: control_list_int) {
            sum += i;
        }
        float mean = (float) sum / control_list_int.size();

        assertEquals(mean, test_obj_int.getExperimentMean(), MARGIN_OF_ERROR);

    }

    /**
     * Test standard deviation calculations for float
     */
    @Test
    public void testStdDevFloat() {
        float mean = test_obj_float.getExperimentMean();

        float stdev = 0;
        for (Float i: control_list_float) {
            stdev += Math.pow(i - mean, 2);
        }
        stdev = (float) Math.sqrt(stdev / control_list_float.size());

        assertEquals(stdev, test_obj_float.getExperimentStDev(), MARGIN_OF_ERROR);
    }

    /**
     * Test standard deviation calculations for int
     */
    @Test
    public void testStdDevInt() {
        float mean = test_obj_int.getExperimentMean();
        float stdev = 0;
        for (Integer i: control_list_int) {
            stdev += Math.pow(i - mean, 2);
        }
        stdev = (float) Math.sqrt(stdev / (control_list_int.size() - 1));

        assertEquals(stdev, test_obj_int.getExperimentStDev(), MARGIN_OF_ERROR);
    }

    /**
     * Test histogram interval generation
     */
    @Test
    public void testHistogramInterval() {
        double int_width = (Collections.max(control_list_int) - Collections.min(control_list_int)) / (double) 18;
        assertEquals(int_width, test_obj_int.getHistogramIntervalWidth(), MARGIN_OF_ERROR);

        double float_width = (Collections.max(control_list_float) - Collections.min(control_list_float)) / (double) 18;
        assertEquals(float_width, test_obj_float.getHistogramIntervalWidth(), MARGIN_OF_ERROR);
    }

    /**
     * Test trials over time generation
     */
    @Test
    public void testTrialOverTime() {
        TrialFactory factory = new TrialFactory();
        Experiment test_count_exp = new Experiment(null, null, null, TrialType.COUNT_TRIAL.getLabel(), null, null, null, null);

        final Date test_start_date = roundToDay(new Date());
        SortedMap<Date, Double> trial_generator_list = new TreeMap<>();

        Date time_interval = test_start_date;
        for (int i = 1; i <= 10; i++) {
            trial_generator_list.put(time_interval, (double) i);
            time_interval = incrementDayByOne(time_interval);
        }

        List<Trial> test_count_list = new ArrayList<>();
        for (Map.Entry<Date, Double> entry: trial_generator_list.entrySet()) {
            Date create_date = entry.getKey();
            double value = entry.getValue();
            int target = (int) value;

            for (int i = 0; i < target; i++) {
                CountTrial trial = (CountTrial) factory.createTrial(TrialType.COUNT_TRIAL, test_count_exp, null);
                trial.overrideDate(create_date);
                test_count_list.add(trial);
            }
        }

        SortedMap<Date, Double> control_result = new TreeMap<>();
        time_interval = test_start_date;
        int prev = 0;
        for (int i = 1; i <= 10; i++) {
            control_result.put(time_interval, (double) i + prev);
            prev += i;
            time_interval = incrementDayByOne(time_interval);
        }

        TrialResultsOverTime resultsOverTime = new TrialResultsOverTime(test_count_list);
        assertEquals(control_result, resultsOverTime.createTrialResultsOverTime(TrialType.COUNT_TRIAL));

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
}
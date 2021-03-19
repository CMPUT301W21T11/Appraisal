package com.example.appraisal;

import android.icu.util.Measure;

import com.example.appraisal.backend.experiment.Experiment;
import com.example.appraisal.backend.specific_experiment.SpecificExperiment;
import com.example.appraisal.backend.trial.CountTrial;
import com.example.appraisal.backend.trial.MeasurementTrial;
import com.example.appraisal.backend.trial.NonNegIntCountTrial;
import com.example.appraisal.backend.trial.Trial;
import com.example.appraisal.backend.user.User;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.SortedMap;

import static org.junit.Assert.*;

/**
 * Test class for Specific Experiment
 */
public class SpecificExpTest {
    private SpecificExperiment test_obj_int;
    private SpecificExperiment test_obj_float;

    private final int TRIAL_SIZE = 1000;
    private final double MARGIN_OF_ERROR = 0.004;

    private Experiment experiment_int;
    private Experiment experiment_float;

    private List<Integer> control_list_int;
    private List<Float> control_list_float;

    /**
     * Initialize the required classes. Also produce an array for control
     */
    @Before
    public void init() {
        experiment_int = new Experiment("temp","temp", "temp", "temp", false, 0, "temp", "temp");
        control_list_int = new ArrayList<>();
        for (int i = 0; i < TRIAL_SIZE; i++) {
            int count = (int) (Math.random() * (100 * Math.round(Math.random() * 10)));
            control_list_int.add(count);
            NonNegIntCountTrial trial = new NonNegIntCountTrial(experiment_int);
            trial.addIntCount(String.valueOf(count));
            experiment_int.addTrial(trial);
        }

        experiment_float = new Experiment("temp","temp", "temp", "temp", false, 0, "temp", "temp");
        control_list_float = new ArrayList<>();
        for (int i = 0; i < TRIAL_SIZE; i++) {
            float measurement = (float) (Math.random());
            control_list_float.add(measurement);
            MeasurementTrial trial = new MeasurementTrial(experiment_float);
            trial.setMeasurement(measurement);
            experiment_float.addTrial(trial);
        }

        test_obj_float = new SpecificExperiment(experiment_float);
        test_obj_int = new SpecificExperiment(experiment_int);
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

        assertEquals(mean, test_obj_float.getExperimentMean(), MARGIN_OF_ERROR);
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
        stdev = (float) Math.sqrt(stdev / control_list_int.size());

        assertEquals(stdev, test_obj_int.getExperimentStDev(), MARGIN_OF_ERROR);
    }

    /**
     * Test histogram interval generation
     */
    @Test
    public void testHistogramInterval() {
        double int_width = (Collections.max(control_list_int) - Collections.min(control_list_int)) / (double) 10;
        assertEquals(int_width, test_obj_int.getHistogramIntervalWidth(), MARGIN_OF_ERROR);

        double float_width = (Collections.max(control_list_float) - Collections.min(control_list_float)) / (double) 10;
        assertEquals(float_width, test_obj_float.getHistogramIntervalWidth(), MARGIN_OF_ERROR);
    }
}
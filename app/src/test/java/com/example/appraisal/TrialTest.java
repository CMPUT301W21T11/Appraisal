package com.example.appraisal;

import com.example.appraisal.backend.experiment.Experiment;
import com.example.appraisal.backend.trial.BinomialTrial;
import com.example.appraisal.backend.trial.CountTrial;
import com.example.appraisal.backend.trial.MeasurementTrial;
import com.example.appraisal.backend.trial.NonNegIntCountTrial;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TrialTest {
    private Experiment test_parent;

    /**
     * Initialize parent experiment
     */
    @Before
    public void init() {
        test_parent = new Experiment("test_id",
                "test_owner",
                "test_desc",
                "Count-based trials",
                false,
                0,
                "None",
                "Earth");
    }

    /**
     * Test get parent experiment functionality
     */
    @Test
    public void testParent() {
        BinomialTrial trial = new BinomialTrial(test_parent);
        assertEquals(test_parent.getClass(), trial.getParent_experiment().getClass());
    }

    /**
     * Test binomial trial functionality
     */
    @Test
    public void testBinomial() {
        BinomialTrial trial = new BinomialTrial(test_parent);
        assertEquals(0, trial.getFailureCount());
        assertEquals(0, trial.getSuccessCount());

        trial.addSuccess();
        trial.addFailure();

        assertEquals(1, trial.getFailureCount());
        assertEquals(1, trial.getSuccessCount());

        trial.setFailure_counter(10);
        trial.setSuccess_counter(12);

        assertEquals(10, trial.getFailureCount());
        assertEquals(12, trial.getSuccessCount());
    }

    /**
     * Test CountTrial functionality
     */
    @Test
    public void testCountTrial() {
        CountTrial trial = new CountTrial(test_parent);
        assertEquals(0, trial.getCount());

        trial.increase();
        assertEquals(1, trial.getCount());
    }

    /**
     * Test Measurement trial functionality
     */
    @Test
    public void testMeasurement() {
        MeasurementTrial trial = new MeasurementTrial(test_parent);
        assertEquals(0.0, trial.getMeasurement(), 0.1);

        trial.setMeasurement(21.5f);
        assertEquals(21.5f, trial.getMeasurement(), 0.1);
    }

    /**
     * Test Non negative integer trial
     */
    @Test
    public void testNonNeg() {
        NonNegIntCountTrial trial = new NonNegIntCountTrial(test_parent);
        assertEquals(0, trial.getCount());

        trial.addIntCount("10");
        assertEquals(10, trial.getCount());

        trial.addIntCount("10");
        assertEquals(20, trial.getCount());
    }
}

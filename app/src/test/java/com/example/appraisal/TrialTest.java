package com.example.appraisal;

import com.example.appraisal.backend.experiment.Experiment;
import com.example.appraisal.backend.trial.BinomialTrial;
import com.example.appraisal.backend.trial.CountTrial;
import com.example.appraisal.backend.trial.MeasurementTrial;
import com.example.appraisal.backend.trial.NonNegIntCountTrial;
import com.example.appraisal.backend.user.User;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TrialTest {
    private Experiment test_parent;
    private User test_user;

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
        test_user = new User("test_id",
                "test name",
                "Test email",
                "8888");
    }

    /**
     * Test get parent experiment functionality
     */
    @Test
    public void testParent() {
        BinomialTrial trial = new BinomialTrial(test_parent, test_user);
        assertEquals(test_parent.getClass(), trial.getParentExperiment().getClass());
    }

    /**
     * Test binomial trial functionality
     */
    @Test
    public void testBinomial() {
        BinomialTrial trial = new BinomialTrial(test_parent, test_user);
        assertEquals(0, (int) trial.getValue());
        assertEquals(0, trial.getSubTrialCount());

        trial.addSuccess();
        trial.addFailure();

        assertEquals(1, (int) trial.getValue());
        assertEquals(2, trial.getSubTrialCount());
    }

    /**
     * Test CountTrial functionality
     */
    @Test
    public void testCountTrial() {
        CountTrial trial = new CountTrial(test_parent, test_user);
        assertEquals(0, (int) trial.getValue());

        trial.addCount();
        assertEquals(1, (int) trial.getValue());
    }

    /**
     * Test Measurement trial functionality
     */
    @Test
    public void testMeasurement() {
        MeasurementTrial trial = new MeasurementTrial(test_parent, test_user);
        assertEquals(0.0, trial.getValue(), 0.1);

        trial.setMeasurement(21.5);
        assertEquals(21.5, trial.getValue(), 0.1);
    }

    /**
     * Test Non negative integer trial
     */
    @Test
    public void testNonNeg() {
        NonNegIntCountTrial trial = new NonNegIntCountTrial(test_parent, test_user);
        assertEquals(0, (int) trial.getValue());

        trial.addCount(10);
        assertEquals(10, (int) trial.getValue());

        trial.addCount(10);
        assertEquals(20, (int) trial.getValue());
    }
}

package com.example.appraisal;

import com.example.appraisal.backend.experiment.Experiment;
import com.example.appraisal.backend.trial.BernoulliTrial;
import com.example.appraisal.backend.trial.CountTrial;
import com.example.appraisal.backend.trial.MeasurementTrial;
import com.example.appraisal.backend.trial.NonNegIntCountTrial;
import com.example.appraisal.backend.trial.Trial;
import com.example.appraisal.backend.trial.TrialFactory;
import com.example.appraisal.backend.trial.TrialType;
import com.example.appraisal.backend.user.User;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

/**
 * This class is for testing the functionality of the Trial package
 * Including the Trial class and Trial factory
 */
public class TrialTest {
    private Experiment test_parent;
    private User test_user;
    private TrialFactory factory;

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
        factory = new TrialFactory();
    }

    /**
     * Test get parent experiment functionality
     */
    @Test
    public void testParent() {
        BernoulliTrial trial = (BernoulliTrial) factory.createTrial(TrialType.BINOMIAL_TRIAL, test_parent, test_user);
        assertEquals(test_parent.getClass(), trial.getParentExperiment().getClass());
    }

    /**
     * Test binomial trial functionality
     */
    @Test
    public void testBinomial() {
        BernoulliTrial trial = (BernoulliTrial) factory.createTrial(TrialType.BINOMIAL_TRIAL, test_parent, test_user);
        assertEquals(0, (int) trial.getValue());

        trial.setToSuccess();
        assertEquals(1, (int) trial.getValue());

        trial.setToFailure();
        assertEquals(0, (int) trial.getValue());
    }

    /**
     * Test CountTrial functionality
     */
    @Test
    public void testCountTrial() {
        CountTrial trial = (CountTrial) factory.createTrial(TrialType.COUNT_TRIAL, test_parent, test_user);
        assertEquals(0, (int) trial.getValue());

        trial.addCount();
        assertEquals(1, (int) trial.getValue());

        trial.setValue(10.0);
        assertEquals(10, (int) trial.getValue());
    }

    /**
     * Test Measurement trial functionality
     */
    @Test
    public void testMeasurement() {
        MeasurementTrial trial = (MeasurementTrial) factory.createTrial(TrialType.MEASUREMENT_TRIAL, test_parent, test_user);
        assertEquals(0.0, trial.getValue(), 0.1);

        trial.setValue(21.5);
        assertEquals(21.5, trial.getValue(), 0.1);
    }

    /**
     * Test Non negative integer trial
     */
    @Test
    public void testNonNeg() {
        NonNegIntCountTrial trial = (NonNegIntCountTrial) factory.createTrial(TrialType.NON_NEG_INT_TRIAL, test_parent, test_user);
        assertEquals(0, (int) trial.getValue());

        trial.setValue(10);
        assertEquals(10, (int) trial.getValue());
    }
}

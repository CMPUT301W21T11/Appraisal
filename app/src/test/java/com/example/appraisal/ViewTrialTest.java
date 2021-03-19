package com.example.appraisal;

import com.example.appraisal.backend.specific_experiment.ViewTrial;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Test if ViewTrial class is working as expected
 */
public class ViewTrialTest {

    private final String id = "Test_id";
    private final String outcome = "Test_outcome";
    private ViewTrial test_obj;

    /**
     * Init the test
     */
    @Before
    public void init() {
        test_obj = new ViewTrial(id, outcome);
    }

    /**
     * Test if getID works
     */
    @Test
    public void testID() {
        assertEquals(id, test_obj.getID());
    }

    /**
     * Test if getOutcome works
     */
    @Test
    public void testOutcome() {
        assertEquals(outcome, test_obj.getOutcome());
    }
}

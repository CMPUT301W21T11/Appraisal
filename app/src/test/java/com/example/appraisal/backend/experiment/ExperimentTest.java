package com.example.appraisal.backend.experiment;

import com.example.appraisal.backend.trial.CountTrial;
import com.example.appraisal.backend.trial.Trial;
import com.example.appraisal.backend.user.User;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * This class is for testing the Experiment class
 */
public class ExperimentTest {

    private Experiment test_obj;

    /**
     * Initialize the test
     */
    @Before
    public void init() {
        test_obj = new Experiment("test_id",
                "test_owner",
                "test_desc",
                "Count-based trials",
                false,
                0,
                "None",
                "Earth");
    }

    /**
     * Test if fields are initialized
     */
    @Test
    public void testConstruct() {
        assertEquals("test_id", test_obj.getExpId());
        assertEquals("test_owner", test_obj.getOwner());
        assertEquals("test_desc", test_obj.getDescription());
        assertEquals("Count-based trials", test_obj.getType());
        assertEquals(0, test_obj.getMinimumTrials(), 0);
        assertEquals("None", test_obj.getRules());
        assertEquals("Earth", test_obj.getRegion());
    }

    /**
     * Test if adding trials work
     */
    @Test
    public void testAddTrial() {
        List<Trial> control_list = new ArrayList<>();
        CountTrial test_trial = new CountTrial(test_obj, new User("Test", "Test", "Test", "Test"));

        control_list.add(test_trial);
        test_obj.addTrial(test_trial);

        assertEquals(control_list, test_obj.getTrialList());
    }
}

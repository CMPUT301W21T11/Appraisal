package com.example.appraisal.backend.specific_experiment;

import com.example.appraisal.backend.experiment.Experiment;
import com.example.appraisal.backend.trial.MeasurementTrial;
import com.example.appraisal.backend.trial.NonNegIntCountTrial;
import com.example.appraisal.backend.trial.Trial;
import com.example.appraisal.backend.user.User;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

/**
 * This class is for testing the quartile class
 */
public class QuartileTest {
    private final int TRIAL_SIZE = 1000;
    private final double MARGIN_OF_ERROR = 0.5;

    private Quartile quartile_int;
    private Quartile quartile_float;

    private List<Trial> trial_list_int;
    private List<Trial> trial_list_float;

    private List<Integer> control_list_int;
    private List<Float> control_list_float;

    /**
     * Initialize the required classes. Also produce an array for control
     */
    @Before
    public void init() {
        Experiment temp = new Experiment("temp","temp", "temp", "temp", false, 0, "temp", "temp");
        User temp_user = new User("Test", "test", "test", "test");

        trial_list_int = new ArrayList<>();
        control_list_int = new ArrayList<>();
        for (int i = 0; i < TRIAL_SIZE; i++) {
            int count = (int) (Math.random() * (100 * Math.round(Math.random() * 10)));
            control_list_int.add(count);
            NonNegIntCountTrial trial = new NonNegIntCountTrial(temp, temp_user);
            trial.setValue(count);
            trial_list_int.add(trial);
        }

        trial_list_float = new ArrayList<>();
        control_list_float = new ArrayList<>();
        for (int i = 0; i < TRIAL_SIZE; i++) {
            float measurement = (float) (Math.random());
            control_list_float.add(measurement);
            MeasurementTrial trial = new MeasurementTrial(temp, temp_user);
            trial.setValue(measurement);
            trial_list_float.add(trial);
        }

        quartile_float = new Quartile(trial_list_float);
        quartile_int = new Quartile(trial_list_int);

        Collections.sort(control_list_float);
        Collections.sort(control_list_int);
    }

    /**
     * Test the total calculation
     */
    @Test
    public void testTotal() {
        int total = control_list_float.size();
        assertEquals(total, quartile_float.getTotalNumTrial(), MARGIN_OF_ERROR);

        total = control_list_int.size();
        assertEquals(total, quartile_int.getTotalNumTrial(), MARGIN_OF_ERROR);
    }

    /**
     * Test first quartile calculation
     */
    @Test
    public void testQ1() {
        float q1 = control_list_float.get(Math.round(control_list_float.size() / (float) 4));
        assertEquals(q1, quartile_float.getFirstQuartile(), MARGIN_OF_ERROR);

        q1 = control_list_int.get(Math.round(control_list_int.size() / (float) 4));
        assertEquals(q1, quartile_int.getFirstQuartile(), MARGIN_OF_ERROR);
    }

    /**
     * Test third quartile calculation
     */
    @Test
    public void testQ3() {
        float q3 = control_list_float.get(Math.round(control_list_float.size() * 3 / (float) 4));
        assertEquals(q3, quartile_float.getThirdQuartile(), MARGIN_OF_ERROR);

        q3 = control_list_int.get(Math.round(control_list_int.size() * 3 / (float) 4));
        assertEquals(q3, quartile_int.getThirdQuartile(), MARGIN_OF_ERROR);
    }

    /**
     * Test min value calculation
     */
    @Test
    public void testMin() {
        float min = Collections.min(control_list_float);
        assertEquals(min, quartile_float.getTrialMinValue(), MARGIN_OF_ERROR);

        int min2 = Collections.min(control_list_int);
        assertEquals(min2, quartile_int.getTrialMinValue(), MARGIN_OF_ERROR);
    }

    /**
     * Test max value calculation
     */
    @Test
    public void testMax() {
        float max = Collections.max(control_list_float);
        assertEquals(max, quartile_float.getTrialMaxValue(), MARGIN_OF_ERROR);

        int max2 = Collections.max(control_list_int);
        assertEquals(max2, quartile_int.getTrialMaxValue(), MARGIN_OF_ERROR);
    }
}

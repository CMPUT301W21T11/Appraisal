package com.example.appraisal.backend.specific_experiment;

import com.example.appraisal.backend.experiment.Experiment;
import com.example.appraisal.backend.user.User;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;

public class BarCodeTest {

    Barcode barcode;
    User user;
    Experiment experiment;
    String data;

    @Before
    public void init() {
        user = mock(User.class);
        experiment = mock(Experiment.class);
        data = "dummy";

        barcode = new Barcode("message", user, experiment, data);
    }

    @Test
    public void getRawValueTest() {
        Assert.assertEquals("message", barcode.getRawValue());
    }

    @Test
    public void getCurrentUserTest() {
        Assert.assertEquals(user, barcode.getCurrentUser());
    }

    @Test
    public void getCurrentExperimentTest() {
        Assert.assertEquals(experiment, barcode.getCurrentExperiment());
    }

    @Test
    public void getDataTest() {
        Assert.assertEquals(data, barcode.getData());
    }

    @After
    public void end() {
    }
}

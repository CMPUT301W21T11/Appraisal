package com.example.appraisal;

import com.example.appraisal.model.core.MainModel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class FirebaseQueryTest {

    /**
     * Initialize the test
     */
    @Before
    public void init() {
        MainModel.createInstance();
    }

    @Test
    public void getUserTest() {

    }

    /**
     * Finish test and remove main model
     */
    @After
    public void end() {
        MainModel.kill();
    }
}

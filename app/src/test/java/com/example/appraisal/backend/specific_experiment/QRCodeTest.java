package com.example.appraisal.backend.specific_experiment;

import android.content.Context;

import com.example.appraisal.backend.trial.TrialType;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

public class QRCodeTest {

    QRValues qrValues;

    @Before
    public void init() {
        Context context = mock(Context.class);
        qrValues = new QRValues(context,"Appraisal", TrialType.BINOMIAL_TRIAL, 1, "123");
    }

    @Test
    public void signatureTest() {
        assertEquals("Appraisal", qrValues.getSignature());
    }

    @Test
    public void getValue() {
        assertEquals(1, qrValues.getValue(), 0.1);
    }

    @Test
    public void typeTest() {
        assertEquals(TrialType.BINOMIAL_TRIAL, qrValues.getType());
    }

    @Test
    public void IDTest() {
        assertEquals("123", qrValues.getExpId());
    }

    @After
    public void end() {
    }
}

package com.example.appraisal.backend.specific_experiment;

import com.example.appraisal.backend.experiment.Experiment;
import com.example.appraisal.backend.trial.Trial;
import com.example.appraisal.backend.trial.TrialFactory;
import com.example.appraisal.backend.trial.TrialType;
import com.example.appraisal.backend.user.User;

import org.junit.Before;
import org.junit.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static org.junit.Assert.assertEquals;

public class TrialDateComparatorTest {
    private Experiment test_parent;
    private User test_user;
    private TrialFactory factory;

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

    @Test
    public void testO1BeforeO2() throws ParseException {
        Trial trial = factory.createTrial(TrialType.BINOMIAL_TRIAL, test_parent, test_user);
        Trial trial1 = factory.createTrial(TrialType.COUNT_TRIAL, test_parent, test_user);

        DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
        Date date = formatter.parse("04/08/2021");
        Date date1 = formatter.parse("04/09/2021");

        trial.overrideDate(date);
        trial1.overrideDate(date1);

        TrialDateComparator dateComparator = new TrialDateComparator();
        assertEquals(-1, dateComparator.compare(trial, trial1));
    }


    @Test
    public void testO1AfterO2() throws ParseException {
        Trial trial = factory.createTrial(TrialType.BINOMIAL_TRIAL, test_parent, test_user);
        Trial trial1 = factory.createTrial(TrialType.COUNT_TRIAL, test_parent, test_user);

        DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
        Date date = formatter.parse("04/09/2021");
        Date date1 = formatter.parse("04/08/2021");

        trial.overrideDate(date);
        trial1.overrideDate(date1);

        TrialDateComparator dateComparator = new TrialDateComparator();
        assertEquals(1, dateComparator.compare(trial, trial1));
    }


    @Test
    public void testSameDate() throws ParseException {
        Trial trial = factory.createTrial(TrialType.BINOMIAL_TRIAL, test_parent, test_user);
        Trial trial1 = factory.createTrial(TrialType.COUNT_TRIAL, test_parent, test_user);

        DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
        Date date = formatter.parse("04/09/2021");
        Date date1 = formatter.parse("04/09/2021");

        trial.overrideDate(date);
        trial1.overrideDate(date1);

        TrialDateComparator dateComparator = new TrialDateComparator();
        assertEquals(0, dateComparator.compare(trial, trial1));
    }


}

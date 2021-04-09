package com.example.appraisal;

import android.app.Activity;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.example.appraisal.R;
import com.example.appraisal.UI.MainActivity;
import com.example.appraisal.UI.main_menu.my_experiment.MyExperimentActivity;
import com.example.appraisal.UI.main_menu.subscription.ExpSubscriptionActivity;
import com.robotium.solo.Solo;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.Random;

import static java.lang.Math.abs;

/**
 * Test class for publishing an experiments and subscribing to it. All the UI tests are written here.
 * Robotium test framework is used
 *
 * Covers User Story 01.04.01
 *
 */
public class SubscribeExpTest {
    private Solo solo;
    int delay_time = 50;

    @Rule
    public ActivityTestRule<MainActivity> rule =
            new ActivityTestRule<>(MainActivity.class, true, true);

    /**
     * Runs before all tests and creates solo instance.
     *
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        solo = new Solo(InstrumentationRegistry.getInstrumentation(), rule.getActivity());
    }

    /**
     * Gets the Activity
     *
     * @throws Exception
     */
    @Test
    public void start() throws Exception {
        Activity activity = rule.getActivity();
    }

    /**
     * Publishes the experiment and subscribes to it
     */
    @Test
    public void testSubscribe() {

        //Generating a random exp name for intent test
        Random rn = new Random();
        final String exp_name = "Subscribe Exp Test " + String.valueOf(abs(rn.nextInt()));

        //Asserts that the current activity is the MainActivity. Otherwise, show “Wrong Activity”
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        View BeginButton = solo.getView("begin_button");
        solo.clickOnView(BeginButton);

        //Asserts that the current activity is the ExpSubscriptionActivity. Otherwise, show “Wrong Activity”
        solo.assertCurrentActivity("Wrong activity", ExpSubscriptionActivity.class);
        View CTButton = solo.getView("experiment_bottom_nav");
        solo.clickOnView(CTButton);

        //Asserts that the current activity is the MyExperimentActivity. Otherwise, show “Wrong Activity”
        solo.assertCurrentActivity("Wrong activity", MyExperimentActivity.class);
        View fab = solo.getView("AddExperimentButton");
        solo.clickOnView(fab);

        //Entering in test data
        solo.enterText((EditText) solo.getView(R.id.expDesc), exp_name);
        solo.clickOnView((RadioButton) solo.getView(R.id.radioButtonYes));
        solo.enterText((EditText) solo.getView(R.id.expMinTrials), "20");
        solo.enterText((EditText) solo.getView(R.id.expRules), "IntentTest Rule #1");
        solo.enterText((EditText) solo.getView(R.id.expRegion), "Canada");

        //Publishing the test data
        View PubButton = solo.getView("publish_confirm");
        solo.clickOnView(PubButton);

        //Verify that the experiment was published
        solo.waitForText(exp_name, 1, delay_time);
        solo.waitForText("Status: Published & Open", 1, delay_time);

        //Testing the dialogue box
        solo.clickOnText(exp_name);
        solo.waitForText("Publish Status: Published", 1, delay_time);
        solo.waitForText("Ended Status: Open", 1, delay_time);

        //Testing the Details tab
        View ResultsButton = solo.getView("view_results_button");
        solo.clickOnView(ResultsButton);
        solo.waitForText(exp_name, 1, 300);
        solo.waitForText("Count-based trials", 1, 300);
        solo.waitForText("Open", 1, 300);
        solo.waitForText("Geo-Required", 1, 300);

        //Subscribe to an experiment
        solo.clickOnCheckBox(0);

        solo.goBack();

        //Go to the My Subscription page
        View SubscriptionButton = solo.getView("subscription_bottom_nav");
        solo.clickOnView(SubscriptionButton);

        //Verify that the experiment is subscribed to
        solo.waitForText(exp_name, 1, delay_time);


    }
}

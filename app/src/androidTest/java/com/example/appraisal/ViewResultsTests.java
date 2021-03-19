package com.example.appraisal;

import android.app.Activity;

import com.example.appraisal.UI.main_menu.my_experiment.MyExperimentActivity;
import com.example.appraisal.UI.main_menu.subscription.ExpSubscriptionActivity;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;

import com.example.appraisal.UI.MainActivity;
import com.robotium.solo.Solo;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * Test class for Viewing Results. All the UI tests are written here. Robotium test framework is
 * used
 */
public class ViewResultsTests {
    private Solo solo;

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
     * Publishes the experiment
     */
    @Test
    public void testResults() {
        //Asserts that the current activity is the MainActivity. Otherwise, show “Wrong Activity”
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.clickOnButton("Begin");

//        //Asserts that the current activity is the BeginActivity. Otherwise, show “Wrong Activity”
//        solo.assertCurrentActivity("Wrong activity", BeginActivity.class);
//        solo.clickOnButton("GUEST");

        //Asserts that the current activity is the ExpSubscriptionActivity. Otherwise, show “Wrong Activity”
        solo.assertCurrentActivity("Wrong activity", ExpSubscriptionActivity.class);
        View CTButton = solo.getView("my_exp_button");
        solo.clickOnView(CTButton);

        //Asserts that the current activity is the MyExperimentActivity. Otherwise, show “Wrong Activity”
        solo.assertCurrentActivity("Wrong activity", MyExperimentActivity.class);
        View fab = solo.getView("AddExperimentButton");
        solo.clickOnView(fab);

        //Entering in test data
        solo.enterText((EditText) solo.getView(R.id.expDesc), "Details Demo");
        solo.clickOnView((RadioButton) solo.getView(R.id.radioButtonYes));
        solo.enterText((EditText) solo.getView(R.id.expMinTrials), "20");
        solo.enterText((EditText) solo.getView(R.id.expRules), "IntentTest Rule #1");
        solo.enterText((EditText) solo.getView(R.id.expRegion), "Canada");

        //Publishing the test data
        View PubButton = solo.getView("publish_confirm");
        solo.clickOnView(PubButton);

        //Verify that the experiment was published
        solo.waitForText("Details Demo", 1, 300);
        solo.waitForText("Status: Published & Open", 1, 300);

        //Testing the dialogue box
        solo.clickOnText("Details Demo");
        solo.waitForText("Publish Status: Published", 1, 300);
        solo.waitForText("Ended Status: Open", 1, 300);

        //Testing the Details tab
        View ResultsButton = solo.getView("view_results_button");
        solo.clickOnView(ResultsButton);
        solo.waitForText("Details Demo", 1, 300);
        solo.waitForText("Count-based trials", 1, 300);
        solo.waitForText("Status: Open", 1, 300);
        solo.waitForText("Geo-location required: Yes", 1, 300);

        solo.goBack();
        solo.goBack();

        //Ending the experiment
        solo.clickOnText("Details Demo");
        View EndButton = solo.getView("end_button");
        solo.clickOnView(EndButton);
        solo.waitForText("Ended Status: Ended", 1, 300);
        solo.clickOnButton("Done");
        solo.waitForText("Status: Published & Ended", 1, 300);

        //Verifying the change in the Details tab
        solo.clickOnText("Details Demo");
        solo.clickOnView(ResultsButton);
        solo.waitForText("Details Demo", 1, 300);
        solo.waitForText("Count-based trials", 1, 300);
        solo.waitForText("Status: Ended", 1, 300);
        solo.waitForText("Geo-location required: Yes", 1, 300);

    }
}
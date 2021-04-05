package com.example.appraisal;

import android.app.Activity;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.example.appraisal.UI.MainActivity;
import com.example.appraisal.UI.main_menu.my_experiment.MyExperimentActivity;
import com.example.appraisal.UI.main_menu.subscription.ExpSubscriptionActivity;
import com.example.appraisal.UI.trial.CounterActivity;
import com.example.appraisal.model.core.MainModel;
import com.robotium.solo.Solo;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

/**
 * Test class for Ignoring Users. All the UI tests are written here. Robotium test framework is
 * used
 */
public class IgnoringUsersTest {
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
     * Publishes the experiment and checks if owner is listed as the contributor
     */
    @Test
    public void testAddTrials() {
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
        solo.enterText((EditText) solo.getView(R.id.expDesc), "Ignore Users Demo");
        solo.clickOnView((RadioButton) solo.getView(R.id.radioButtonYes));
        solo.enterText((EditText) solo.getView(R.id.expMinTrials), "20");
        solo.enterText((EditText) solo.getView(R.id.expRules), "IntentTest Rule #1");
        solo.enterText((EditText) solo.getView(R.id.expRegion), "Canada");

        //Publishing the test data
        View PubButton = solo.getView("publish_confirm");
        solo.clickOnView(PubButton);

        //Verify that the experiment was published
        solo.waitForText("Ignore Users Demo", 1, 300);
        solo.waitForText("Status: Published & Open", 1, 300);

        //Testing the dialogue box
        solo.clickOnText("Ignore Users Demo");
        solo.waitForText("Publish Status: Published", 1, 300);
        solo.waitForText("Ended Status: Open", 1, 300);

        //Testing the Details tab
        View ResultsButton = solo.getView("view_results_button");
        solo.clickOnView(ResultsButton);
        solo.waitForText("Ignore Users Demo", 1, 300);
        solo.waitForText("Count-based trials", 1, 300);
        solo.waitForText("Status: Open", 1, 300);
        solo.waitForText("Geo-location required: Yes", 1, 300);

        //Adding a trial
        View AddTrialButton = solo.getView("specific_exp_details_add_trial_button");
        solo.clickOnView(AddTrialButton);

        //Asserts that the current activity is the CounterActivity. Otherwise, show “Wrong Activity”
        solo.assertCurrentActivity("Wrong activity", CounterActivity.class);

        //Adding 3 trials
        View AddButton = solo.getView("add_btn");
        solo.clickOnView(AddButton);
        solo.clickOnView(AddButton);
        solo.clickOnView(AddButton);

        //Saving the trials
        View UploadButton = solo.getView("save_btn");
        solo.clickOnView(UploadButton);

        //Adding 4 trials
        solo.clickOnView(AddTrialButton);
        solo.clickOnView(AddButton);
        solo.clickOnView(AddButton);
        solo.clickOnView(AddButton);
        solo.clickOnView(AddButton);

        //Saving the trials
        solo.clickOnView(UploadButton);

        //Adding 5 trials
        solo.clickOnView(AddTrialButton);
        solo.clickOnView(AddButton);
        solo.clickOnView(AddButton);
        solo.clickOnView(AddButton);
        solo.clickOnView(AddButton);
        solo.clickOnView(AddButton);

        //Saving the trials
        solo.clickOnView(UploadButton);


        // go to analysis tab
        solo.clickOnText("ANALYSIS");
        try {
            solo.waitForText(MainModel.getCurrentExperiment().getOwner(), 1, delay_time);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // test if analysis results are correct
        solo.waitForText("Median: 4.00", 1, 300);
        solo.waitForText("Mean: 4.00", 1, 300);
        solo.waitForText("Stdev.: 0.82", 1, 300);

    }
}

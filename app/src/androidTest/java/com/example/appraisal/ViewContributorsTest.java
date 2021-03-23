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
import com.example.appraisal.model.MainModel;
import com.robotium.solo.Solo;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.Random;

/**
 * Test class for Viewing Contributors. All the UI tests are written here. Robotium test framework is
 * used
 */
public class ViewContributorsTest {
    private Solo solo;
    int delay_time = 300;

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
    public void testContributors() throws Exception {
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

        Random rn = new Random(52512563);
        int hash = rn.nextInt();

        //Entering in test data
        final String exp_name = "Test Contributors Demo " + Integer.toString(hash);

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
        solo.waitForText(exp_name, 1, delay_time);
        solo.waitForText("Count-based trials", 1, delay_time);
        solo.waitForText("Status: Open", 1, delay_time);
        solo.waitForText("Geo-location required: Yes", 1, delay_time);

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

        //View the trials added
        View ViewTrialsButton = solo.getView("viewTrialBtn");
        solo.clickOnView(ViewTrialsButton);

        //Verify that the trial was added
        solo.waitForText("Trial1", 1, delay_time);
        solo.waitForText("Result 3", 1, delay_time);

        solo.goBack();

        //Verifying if the owner is added to the list
        solo.clickOnText("Participants");
        solo.waitForText(MainModel.getCurrentExperiment().getOwner(), 1, delay_time);



    }
}
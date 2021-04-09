package com.example.appraisal;

import android.app.Activity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.example.appraisal.UI.MainActivity;
import com.example.appraisal.UI.main_menu.my_experiment.MyExperimentActivity;
import com.example.appraisal.UI.main_menu.specific_experiment_details.details.trial_list.ViewTrialActivity;
import com.example.appraisal.UI.main_menu.subscription.ExpSubscriptionActivity;
import com.example.appraisal.model.core.MainModel;
import com.google.android.material.tabs.TabLayout;
import com.robotium.solo.Solo;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.Random;

import static java.lang.Math.abs;

/**
 * Test class for Ignoring Users. All the UI tests are written here. Robotium test framework is
 * used
 *
 * Covers User Story 01.08.01
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
     * Publishes the experiment and ignores the users and verifies statistics
     */
    @Test
    public void testIgnoreUser() {
        //Generating a random exp name for intent test
        Random rn = new Random();
        final String exp_name = "IgnoringUsersTest" + String.valueOf(abs(rn.nextInt()));

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
        solo.clickOnView((RadioButton) solo.getView(R.id.radioButtonNo));
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
        solo.clickOnText(exp_name, 1, true);
        solo.waitForText("Publish Status: Published", 1, delay_time);
        solo.waitForText("Ended Status: Open", 1, delay_time);

        //Testing the Details tab
        View ResultsButton = solo.getView("view_results_button");
        solo.clickOnView(ResultsButton);
        solo.waitForText(exp_name, 1, 300);
        solo.waitForText("Count-based trials", 1, 300);
        solo.waitForText("Open", 1, 300);
        solo.waitForText("Non-Geo", 1, 300);

        //Adding a trial
        View AddTrialButton = solo.getView("specific_exp_details_add_trial_button");
        solo.clickOnView(AddTrialButton);
        View SaveCountTrial = solo.getView("save_btn");
        solo.clickOnView(SaveCountTrial);

        //Opening the Analysis tab
        TabLayout tabs = (TabLayout) solo.getView(R.id.specific_exp_tab_layout);

        TextView tv = (TextView) (((LinearLayout) ((LinearLayout) tabs.getChildAt(0)).getChildAt(2)).getChildAt(1));
        solo.clickOnView(tv);

        // test if analysis results are correct
        solo.waitForText("Median: 1.00", 1, 300);
        solo.waitForText("Mean: 1.00", 1, 300);


        //Opening the participants tab
        TextView tv22 = (TextView) (((LinearLayout) ((LinearLayout) tabs.getChildAt(0)).getChildAt(4)).getChildAt(1));
        solo.clickOnView(tv22);
        TextView tv5 = (TextView) (((LinearLayout) ((LinearLayout) tabs.getChildAt(0)).getChildAt(3)).getChildAt(1));
        solo.clickOnView(tv5);


        //Click on name of user
        String userID = null;
        try {
            userID = "User @" + MainModel.getCurrentExperiment().getOwner().substring(0, 7);
        } catch (Exception e) {
            e.printStackTrace();
        }
        solo.waitForText(userID, 1, 300);

        //Test ViewTrialActivity was opened
        solo.clickOnText(userID);
        solo.assertCurrentActivity("Wrong activity", ViewTrialActivity.class);

        //Verify that trial was added
        solo.waitForText("Result: 1", 1, 300);

        // Ignore the user
        View viewProfileButton = solo.getView("ignore_button");
        solo.clickOnView(viewProfileButton);

        solo.goBack();

        // go to analysis tab
        TextView tvv1 = (TextView) (((LinearLayout) ((LinearLayout) tabs.getChildAt(0)).getChildAt(2)).getChildAt(1));
        solo.clickOnView(tvv1);

        // test if analysis results are correct
        solo.waitForText("Median: 0.00", 1, 300);
        solo.waitForText("Mean: 0.00", 1, 300);

    }
}

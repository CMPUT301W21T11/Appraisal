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
import com.example.appraisal.UI.main_menu.specific_experiment_details.participants.trial_list.ViewTrialActivity;
import com.example.appraisal.UI.main_menu.subscription.ExpSubscriptionActivity;
import com.example.appraisal.UI.main_menu.user_profile.UserProfileActivity;
import com.example.appraisal.model.core.MainModel;
import com.google.android.material.tabs.TabLayout;
import com.robotium.solo.Solo;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.Random;

import static java.lang.Math.abs;

/**
 * Test class for Viewing Contributors. All the UI tests are written here. Robotium test framework is
 * used
 *
 * Covers US 04.03.01
 *
 */
public class ViewContributorsTest {
    private Solo solo;
    int delay_time = 500;

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
     * Publishes the experiment and checks if owner is listed as the contributor.
     */
    @Test
    public void testContributors() throws Exception {
        //Generating a random exp name for intent test
        Random rn = new Random();
        final String exp_name = "ContributorsTest" + String.valueOf(abs(rn.nextInt()));

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
        solo.waitForText(exp_name, 1, delay_time);
        solo.waitForText("Count-based trials", 1, delay_time);
        solo.waitForText("Open", 1, delay_time);
        solo.waitForText("Non-Geo", 1, delay_time);

        //Adding a trial
        View AddTrialButton = solo.getView("specific_exp_details_add_trial_button");
        solo.clickOnView(AddTrialButton);
        View SaveCountTrial = solo.getView("save_btn");
        solo.clickOnView(SaveCountTrial);


        //Opening the participants tab
        TabLayout tabs = (TabLayout) solo.getView(R.id.specific_exp_tab_layout);

        TextView tv = (TextView) (((LinearLayout) ((LinearLayout) tabs.getChildAt(0)).getChildAt(3)).getChildAt(1));
        solo.clickOnView(tv);
        TextView tv1 = (TextView) (((LinearLayout) ((LinearLayout) tabs.getChildAt(0)).getChildAt(4)).getChildAt(1));
        solo.clickOnView(tv1);
        TextView tv2 = (TextView) (((LinearLayout) ((LinearLayout) tabs.getChildAt(0)).getChildAt(3)).getChildAt(1));
        solo.clickOnView(tv2);

        //Click on name of user
        String userID = null;
        try {
            userID = "User @" + MainModel.getCurrentExperiment().getOwner().substring(0, 7);
        } catch (Exception e) {
            e.printStackTrace();
        }
        solo.waitForText(userID, 1, delay_time);

        //Test ViewTrialActivity was opened
        solo.clickOnText(userID);
        solo.assertCurrentActivity("Wrong activity", ViewTrialActivity.class);

        //Verify that trial was added
        solo.waitForText("Result: 1", 1, delay_time);

        // Open other users profile
        View viewProfileButton = solo.getView("view_profile_button");
        solo.clickOnView(viewProfileButton);
        solo.assertCurrentActivity("Wrong activity", UserProfileActivity.class);

        // Verify correct profile is opened
        solo.waitForText(userID);

    }
}
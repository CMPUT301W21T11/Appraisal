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
import com.example.appraisal.UI.main_menu.subscription.ExpSubscriptionActivity;
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
 * Covers User Story 01.06.01, 01.07.01, 01.09.01,
 */
public class StatsTest {
    private Solo solo;
    int delay_time = 250;

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
     * Publishes the experiment, adds trials and tests statistics
     */
    @Test
    public void testStatistics() {
        //Generating a random exp name for intent test
        Random rn = new Random();
        final String exp_name = "StatisticsTest" + String.valueOf(abs(rn.nextInt()));

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
        solo.clickOnText("Count-based trials");
        solo.clickOnText("Non-negative Integer Trials");

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
        View AddTrialBtn = solo.getView("specific_exp_details_add_trial_button");
        solo.clickOnView(AddTrialBtn);
        solo.enterText((EditText) solo.getView(R.id.nonneg_count_input), "20");
        View SaveTrialBtn = solo.getView("upload_observe_btn");
        solo.clickOnView(SaveTrialBtn);

        solo.clickOnView(AddTrialBtn);
        solo.enterText((EditText) solo.getView(R.id.nonneg_count_input), "30");
        solo.clickOnView(SaveTrialBtn);

        solo.clickOnView(AddTrialBtn);
        solo.enterText((EditText) solo.getView(R.id.nonneg_count_input), "40");
        solo.clickOnView(SaveTrialBtn);

        //Opening the Analysis tab
        TabLayout tabs = (TabLayout) solo.getView(R.id.specific_exp_tab_layout);
        TextView tv = (TextView) (((LinearLayout) ((LinearLayout) tabs.getChildAt(0)).getChildAt(2)).getChildAt(1));
        solo.clickOnView(tv);


        //Test if analysis results are correct
        solo.waitForText("Median: 30.00", 1, 300);
        solo.waitForText("Mean: 30.00", 1, 300);

        //View trials over time
        View TrialsOverTime = solo.getView("fragment_experiment_data_analysis_plotsDrop");
        solo.clickOnView(TrialsOverTime);

        //View histogram
        View Histogram = solo.getView("fragment_experiment_data_analysis_histogramDrop");
        solo.clickOnView(Histogram);

        //View quartiles
        View Quartiles = solo.getView("fragment_experiment_data_analysis_quartilesDrop");
        solo.clickOnView(Quartiles);

    }
}

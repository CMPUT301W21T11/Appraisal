package com.example.appraisal;

import android.app.Activity;
import android.view.View;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.example.appraisal.UI.MainActivity;
import com.example.appraisal.UI.main_menu.specific_experiment_details.details.trial_list.ViewTrialActivity;
import com.example.appraisal.model.core.MainModel;
import com.robotium.solo.Solo;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.Random;

/**
 * Test class for Adding Trials. All the UI tests are written here. Robotium test framework is
 * used
 */
public class AddTrialTests {
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
     * Publishes the experiment, adds a trial, and checks if owner is listed as the contributor
     */
    @Test
    public void testAddTrials() {

        //Generating a random exp name for intent test
        Random rn = new Random(23231222);
        final String exp_name = "Add Trials Test " + String.valueOf(rn.nextInt());

        //Asserts that the current activity is the MainActivity. Otherwise, show “Wrong Activity”
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        View BeginButton = solo.getView("begin_button");
        solo.clickOnView(BeginButton);

//        //Asserts that the current activity is the ExpSubscriptionActivity. Otherwise, show “Wrong Activity”
//        solo.assertCurrentActivity("Wrong activity", ExpSubscriptionActivity.class);
//        View CTButton = solo.getView("experiment_bottom_nav");
//        solo.clickOnView(CTButton);
//
//        //Asserts that the current activity is the MyExperimentActivity. Otherwise, show “Wrong Activity”
//        solo.assertCurrentActivity("Wrong activity", MyExperimentActivity.class);
//        View fab = solo.getView("AddExperimentButton");
//        solo.clickOnView(fab);
//
//        //Entering in test data
//        solo.enterText((EditText) solo.getView(R.id.expDesc), exp_name);
//        solo.clickOnView((RadioButton) solo.getView(R.id.radioButtonYes));
//        solo.enterText((EditText) solo.getView(R.id.expMinTrials), "20");
//        solo.enterText((EditText) solo.getView(R.id.expRules), "IntentTest Rule #1");
//        solo.enterText((EditText) solo.getView(R.id.expRegion), "Canada");
//
//        //Publishing the test data
//        View PubButton = solo.getView("publish_confirm");
//        solo.clickOnView(PubButton);
//
//        //Verify that the experiment was published
//        solo.waitForText(exp_name, 1, delay_time);
//        solo.waitForText("Status: Published & Open", 1, delay_time);
//
//        //Testing the dialogue box
//        solo.clickOnText(exp_name);
//        solo.waitForText("Publish Status: Published", 1, delay_time);
//        solo.waitForText("Ended Status: Open", 1, delay_time);
//
//        //Testing the Details tab
//        View ResultsButton = solo.getView("view_results_button");
//        solo.clickOnView(ResultsButton);
//        solo.waitForText(exp_name, 1, 300);
//        solo.waitForText("Count-based trials", 1, 300);
//        solo.waitForText("Open", 1, 300);
//        solo.waitForText("Geo-Required", 1, 300);

        solo.clickOnText("11nonneg");

//        //Adding a trial
//        View AddTrialButton = solo.getView("specific_exp_details_add_trial_button");
//        solo.clickOnView(AddTrialButton);
//
//        //Adding geolocation
//        solo.clickOnButton("Accept");
//        View AddGeoButton = solo.getView("add_geo");
//        solo.clickOnView(AddGeoButton);
//        View SaveGeoButton = solo.getView("save_geo_btn");
//        solo.clickOnView(SaveGeoButton);
//
//        //Saving observation for count based trial
//        View SaveCountTrial = solo.getView("save_btn");
//        solo.clickOnView(SaveCountTrial);

//        DisplayMetrics displayMetrics = new DisplayMetrics();
//        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
//        int height = displayMetrics.heightPixels;
//        int width = displayMetrics.widthPixels;

//        int[] xy = new int[2];
//        View all_tabs = (View) solo.getView(R.id.specific_exp_tab_layout);
//
//
//
//        WindowMetrics windowMetrics =  rule.getActivity().getWindowManager().getCurrentWindowMetrics();
//        Insets insets = windowMetrics.getWindowInsets().getInsetsIgnoringVisibility(WindowInsets.Type.systemBars());
//        int width = windowMetrics.getBounds().width() - insets.left - insets.right;
//        int height = windowMetrics.getBounds().height() - insets.top - insets.bottom;
//        Log.d("Width", String.valueOf(width));
//        Log.d("Height", String.valueOf(height));
//
//        solo.clickOnScreen(windowMetrics.getBounds().width(), windowMetrics.getBounds().height(), 1);

        solo.clickOnMenuItem("PARTICIPANTS_TAB");

//        solo.clickOnScreen(1420, 2800, 1);


//        all_tabs.getLocationOnScreen(xy);
//        int width = all_tabs.getWidth();
//        int height = all_tabs.getHeight();
//        Log.d("width", String.valueOf(width));
//        Log.d("height", String.valueOf(height));

//        View participants_tab = //change x to the index you want.


        //go to contributors tab
//        solo.clickOnImage(3);

        try {
            solo.waitForText(MainModel.getCurrentExperiment().getOwner(), 1, delay_time);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // click on name of user
        String userID = null;
        try {
            userID = "User @" + MainModel.getCurrentExperiment().getOwner().substring(0, 7);
        } catch (Exception e) {
            e.printStackTrace();
        }
        solo.waitForText(userID, 1, 300);

        // test view trial activity was opened
        solo.clickOnText(userID);
        solo.assertCurrentActivity("Wrong activity", ViewTrialActivity.class);

        // test is trial was added
        solo.waitForText("Result: 1", 1, 300);

    }
}
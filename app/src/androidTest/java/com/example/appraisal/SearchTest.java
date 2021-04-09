package com.example.appraisal;

import android.app.Activity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;

import androidx.appcompat.widget.SearchView;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.example.appraisal.UI.MainActivity;
import com.example.appraisal.UI.main_menu.my_experiment.MyExperimentActivity;
import com.example.appraisal.UI.main_menu.search.SearchActivity;
import com.example.appraisal.UI.main_menu.subscription.ExpSubscriptionActivity;
import com.robotium.solo.Solo;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.util.Random;

import static java.lang.Math.abs;

/**
 * Test class for publishing an experiment and searching it. All the UI tests are written here.
 * Robotium test framework is used
 *
 * Covers User Stories 05.01.01, 05.02.01
 */
public class SearchTest {
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
     * Publishes an experiment and searches for it
     */
    @Test
    public void testSearch() {

        //Generating a random exp name for intent test
        Random rn = new Random();
        final String exp_name = "SearchExpTest " +  String.valueOf(abs(rn.nextInt()));

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

        //Go to the Search page
        View SearchButton = solo.getView("search_bottom_nav");
        solo.clickOnView(SearchButton);

        //Click on exp search bar and typing in the search input
        View SearchBar = solo.getView("exp_search_bar");
        solo.clickOnView(SearchBar);
        solo.sendKey(KeyEvent.KEYCODE_S);
        solo.sendKey(KeyEvent.KEYCODE_E);
        solo.sendKey(KeyEvent.KEYCODE_A);
        solo.sendKey(KeyEvent.KEYCODE_R);
        solo.sendKey(KeyEvent.KEYCODE_C);
        solo.sendKey(KeyEvent.KEYCODE_H);
        solo.sendKey(KeyEvent.KEYCODE_E);
        solo.sendKey(KeyEvent.KEYCODE_X);
        solo.sendKey(KeyEvent.KEYCODE_P);
        solo.sendKey(KeyEvent.KEYCODE_T);
        solo.sendKey(KeyEvent.KEYCODE_E);
        solo.sendKey(KeyEvent.KEYCODE_S);
        solo.sendKey(KeyEvent.KEYCODE_T);
        solo.sendKey(KeyEvent.KEYCODE_ENTER);

        //Verify if search was found
        solo.waitForText(exp_name, 1, 700);


    }
}





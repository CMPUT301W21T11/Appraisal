package com.example.appraisal;

import android.app.Activity;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.example.appraisal.UI.MainActivity;
import com.example.appraisal.UI.main_menu.search.SearchActivity;
import com.example.appraisal.UI.main_menu.subscription.ExpSubscriptionActivity;
import com.robotium.solo.Solo;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.Random;

public class PublishAndCloseTest {

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
     * Edits the profile
     */
    @Test
    public void testForum() {
        //Asserts that the current activity is the MainActivity. Otherwise, show “Wrong Activity”
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.clickOnButton("Begin");

        //Asserts that the current activity is the ExpSubscriptionActivity. Otherwise, show “Wrong Activity”
        solo.assertCurrentActivity("Wrong activity", ExpSubscriptionActivity.class);

        View list_button = solo.getView("my_exp_button");
        solo.clickOnView(list_button);

        View add_button = solo.getView("AddExperimentButton");
        solo.clickOnView(add_button);

        Random rn = new Random(2391392);
        String append = String.valueOf(rn.nextInt());
        final String name = "Publish Or Not" + append;

        solo.enterText((EditText) solo.getView(R.id.expDesc), name);
        solo.clickOnView((RadioButton) solo.getView(R.id.radioButtonYes));
        solo.enterText((EditText) solo.getView(R.id.expMinTrials), "20");
        solo.enterText((EditText) solo.getView(R.id.expRules), "IntentTest Rule #1");
        solo.enterText((EditText) solo.getView(R.id.expRegion), "Canada");

        //Publishing the test data
        View PubButton = solo.getView("publish_confirm");
        solo.clickOnView(PubButton);

        solo.waitForText(name, 1, delay_time);

        // Transition to find
        View search_button = solo.getView("search_button");
        solo.clickOnView(search_button);
        solo.assertCurrentActivity("Wrong activity", SearchActivity.class);

        solo.waitForText(name, 1, delay_time);
        solo.clickOnText(name);


        // TODO Under construction

//        solo.waitForText("PUBLISH", 1, delay_time);
//
//        solo.clickInList(0);
//        solo.assertCurrentActivity("Wrong activity", MyExperimentActivity.class);
//
//        solo.clickInList(0);
//        solo.assertCurrentActivity("Wrong activity", ForumQuestionsActivity.class);
//
//        solo.clickInList(0);
//        solo.assertCurrentActivity("Wrong activity", ForumRepliesActivity.class);

    }

}

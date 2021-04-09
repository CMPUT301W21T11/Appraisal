package com.example.appraisal;

import android.app.Activity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.example.appraisal.UI.MainActivity;
import com.example.appraisal.UI.main_menu.my_experiment.MyExperimentActivity;
import com.example.appraisal.UI.main_menu.search.SearchActivity;
import com.example.appraisal.UI.main_menu.subscription.ExpSubscriptionActivity;
import com.google.android.material.tabs.TabLayout;
import com.robotium.solo.Solo;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.Random;

import static java.lang.Math.abs;

/**
 * Test class for the Questions section. All the UI tests are written here.
 * Robotium test framework is used
 *
 * Covers User stories 02.01.01, 02.02.01, 02.03.01
 */
public class ForumTest {

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
     * Posts a questions
     */
    @Test
    public void testQuestions() {
        //Generating a random exp name for intent test
        Random rn = new Random();
        final String exp_name = "Forum Test " + String.valueOf(abs(rn.nextInt()));

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
        solo.waitForText("Non-Geo", 1, 300);

        //Opening the Questions tab
        TabLayout tabs = (TabLayout) solo.getView(R.id.specific_exp_tab_layout);
        TextView tv = (TextView) (((LinearLayout) ((LinearLayout) tabs.getChildAt(0)).getChildAt(4)).getChildAt(1));
        solo.clickOnView(tv);
        TextView tv2 = (TextView) (((LinearLayout) ((LinearLayout) tabs.getChildAt(0)).getChildAt(3)).getChildAt(1));
        solo.clickOnView(tv2);
        TextView tv3 = (TextView) (((LinearLayout) ((LinearLayout) tabs.getChildAt(0)).getChildAt(4)).getChildAt(1));
        solo.clickOnView(tv3);

        //Posting a question
        View PostQuestion = solo.getView("AddQuestionButton");
        solo.clickOnView(PostQuestion);

        solo.sendKey(KeyEvent.KEYCODE_H);
        solo.sendKey(KeyEvent.KEYCODE_O);
        solo.sendKey(KeyEvent.KEYCODE_W);
        solo.sendKey(KeyEvent.KEYCODE_SPACE);
        solo.sendKey(KeyEvent.KEYCODE_D);
        solo.sendKey(KeyEvent.KEYCODE_O);
        solo.sendKey(KeyEvent.KEYCODE_SPACE);
        solo.sendKey(KeyEvent.KEYCODE_I);
        solo.sendKey(KeyEvent.KEYCODE_SPACE);
        solo.sendKey(KeyEvent.KEYCODE_F);
        solo.sendKey(KeyEvent.KEYCODE_I);
        solo.sendKey(KeyEvent.KEYCODE_N);
        solo.sendKey(KeyEvent.KEYCODE_D);
        solo.sendKey(KeyEvent.KEYCODE_SPACE);
        solo.sendKey(KeyEvent.KEYCODE_C);
        solo.sendKey(KeyEvent.KEYCODE_A);
        solo.sendKey(KeyEvent.KEYCODE_R);
        solo.sendKey(KeyEvent.KEYCODE_S);

        solo.clickOnText("Submit");
        solo.waitForText("how do i find cars", 1, 300);

    }

    /**
     * Posts a reply
     */
    @Test
    public void testReplies() {
        //Generating a random exp name for intent test
        Random rn = new Random();
        final String exp_name = "Forum Test " + String.valueOf(abs(rn.nextInt()));

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
        solo.waitForText("Non-Geo", 1, 300);

        //Opening the Questions tab
        TabLayout tabs = (TabLayout) solo.getView(R.id.specific_exp_tab_layout);
        TextView tv = (TextView) (((LinearLayout) ((LinearLayout) tabs.getChildAt(0)).getChildAt(4)).getChildAt(1));
        solo.clickOnView(tv);
        TextView tv2 = (TextView) (((LinearLayout) ((LinearLayout) tabs.getChildAt(0)).getChildAt(3)).getChildAt(1));
        solo.clickOnView(tv2);
        TextView tv4 = (TextView) (((LinearLayout) ((LinearLayout) tabs.getChildAt(0)).getChildAt(4)).getChildAt(1));
        solo.clickOnView(tv4);

        //Posting a question
        View PostQuestion = solo.getView("AddQuestionButton");
        solo.clickOnView(PostQuestion);

        solo.sendKey(KeyEvent.KEYCODE_H);
        solo.sendKey(KeyEvent.KEYCODE_O);
        solo.sendKey(KeyEvent.KEYCODE_W);
        solo.sendKey(KeyEvent.KEYCODE_SPACE);
        solo.sendKey(KeyEvent.KEYCODE_D);
        solo.sendKey(KeyEvent.KEYCODE_O);
        solo.sendKey(KeyEvent.KEYCODE_SPACE);
        solo.sendKey(KeyEvent.KEYCODE_I);
        solo.sendKey(KeyEvent.KEYCODE_SPACE);
        solo.sendKey(KeyEvent.KEYCODE_F);
        solo.sendKey(KeyEvent.KEYCODE_I);
        solo.sendKey(KeyEvent.KEYCODE_N);
        solo.sendKey(KeyEvent.KEYCODE_D);
        solo.sendKey(KeyEvent.KEYCODE_SPACE);
        solo.sendKey(KeyEvent.KEYCODE_C);
        solo.sendKey(KeyEvent.KEYCODE_A);
        solo.sendKey(KeyEvent.KEYCODE_R);
        solo.sendKey(KeyEvent.KEYCODE_S);
        solo.clickOnText("Submit");
        solo.waitForText("how do i find cars", 1, 300);

        //Posting a reply
        solo.clickOnText("how do i find cars");
        View PostReply = solo.getView("AddReplyButton");
        solo.clickOnView(PostReply);

        solo.sendKey(KeyEvent.KEYCODE_O);
        solo.sendKey(KeyEvent.KEYCODE_N);
        solo.sendKey(KeyEvent.KEYCODE_SPACE);
        solo.sendKey(KeyEvent.KEYCODE_T);
        solo.sendKey(KeyEvent.KEYCODE_H);
        solo.sendKey(KeyEvent.KEYCODE_E);
        solo.sendKey(KeyEvent.KEYCODE_SPACE);
        solo.sendKey(KeyEvent.KEYCODE_R);
        solo.sendKey(KeyEvent.KEYCODE_O);
        solo.sendKey(KeyEvent.KEYCODE_A);
        solo.sendKey(KeyEvent.KEYCODE_D);
        solo.clickOnText("Submit");
        solo.waitForText("on the road", 1, 300);

    }

}

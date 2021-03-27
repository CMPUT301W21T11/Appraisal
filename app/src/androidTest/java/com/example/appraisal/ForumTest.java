package com.example.appraisal;

import android.app.Activity;
import android.view.View;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.example.appraisal.UI.MainActivity;
import com.example.appraisal.UI.main_menu.forum.ForumRepliesActivity;
import com.example.appraisal.UI.main_menu.subscription.ExpSubscriptionActivity;
import com.robotium.solo.Solo;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

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
     * Edits the profile
     */
    @Test
    public void testForum() {
        //Asserts that the current activity is the MainActivity. Otherwise, show “Wrong Activity”
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.clickOnButton("Begin");

        //Asserts that the current activity is the ExpSubscriptionActivity. Otherwise, show “Wrong Activity”
        solo.assertCurrentActivity("Wrong activity", ExpSubscriptionActivity.class);

        View CTButton = solo.getView("forum_button");
        solo.clickOnView(CTButton);

        solo.clickInList(0);
        solo.assertCurrentActivity("Wrong activity", ForumHomeActivity.class);

        solo.clickInList(0);
        solo.assertCurrentActivity("Wrong activity", ForumQuestionsActivity.class);

        solo.clickInList(0);
        solo.assertCurrentActivity("Wrong activity", ForumRepliesActivity.class);

    }
}





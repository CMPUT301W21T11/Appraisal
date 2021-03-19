package com.example.appraisal;

import android.app.Activity;
import android.view.View;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.example.appraisal.UI.MainActivity;
import com.example.appraisal.UI.main_menu.search.SearchActivity;
import com.example.appraisal.UI.main_menu.subscription.ExpSubscriptionActivity;
import com.robotium.solo.Solo;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class SearchTest {
    private Solo solo;

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
    public void testSearch() {
        //Asserts that the current activity is the MainActivity. Otherwise, show “Wrong Activity”
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.clickOnButton("Begin");

        //Asserts that the current activity is the ExpSubscriptionActivity. Otherwise, show “Wrong Activity”
        solo.assertCurrentActivity("Wrong activity", ExpSubscriptionActivity.class);

        View CTButton = solo.getView("search_button");
        solo.clickOnView(CTButton);

        //Asserts that the current activity is the UserProfileActivity. Otherwise, show “Wrong Activity”
        solo.assertCurrentActivity("Wrong activity", SearchActivity.class);

    }
}





package com.example.appraisal;

import android.app.Activity;

import com.example.appraisal.UI.main_menu.subscription.ExpSubscriptionActivity;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import android.view.View;
import android.widget.EditText;

import com.example.appraisal.UI.MainActivity;
import com.example.appraisal.UI.main_menu.user_profile.EditProfileActivity;
import com.example.appraisal.UI.main_menu.user_profile.UserProfileActivity;
import com.robotium.solo.Solo;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * Test class for User Profiles. All the UI tests are written here. Robotium test framework is
 * used
 */
public class UserProfileTest {
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
    public void testProfile() {
        //Asserts that the current activity is the MainActivity. Otherwise, show “Wrong Activity”
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.clickOnButton("Begin");

//        //Asserts that the current activity is the BeginActivity. Otherwise, show “Wrong Activity”
//        solo.assertCurrentActivity("Wrong activity", BeginActivity.class);
//        solo.clickOnButton("GUEST");

        //Asserts that the current activity is the ExpSubscriptionActivity. Otherwise, show “Wrong Activity”
        solo.assertCurrentActivity("Wrong activity", ExpSubscriptionActivity.class);
        View CTButton = solo.getView("profile_button");
        solo.clickOnView(CTButton);

        //Asserts that the current activity is the UserProfileActivity. Otherwise, show “Wrong Activity”
        solo.assertCurrentActivity("Wrong activity", UserProfileActivity.class);
        solo.clickOnButton("Edit Profile");

        //Asserts that the current activity is the EditProfileActivity. Otherwise, show “Wrong Activity”
        solo.assertCurrentActivity("Wrong activity", EditProfileActivity.class);

        //Entering in test data

        solo.clearEditText((EditText) solo.getView(R.id.name_edittext));
        solo.clearEditText((EditText) solo.getView(R.id.email_address_edittext));
        solo.clearEditText((EditText) solo.getView(R.id.phone_number_edittext));

        solo.enterText((EditText) solo.getView(R.id.name_edittext), "Test User");
        solo.enterText((EditText) solo.getView(R.id.email_address_edittext), "test@test.ca");
        solo.enterText((EditText) solo.getView(R.id.phone_number_edittext), "+17809990000");

        //Saving it
        View SaveButton = solo.getView("apply_changes_btn");
        solo.clickOnView(SaveButton);

        //Asserts that the current activity is back to UserProfileActivity. Otherwise, show “Wrong Activity”
        solo.assertCurrentActivity("Wrong activity", UserProfileActivity.class);

        //Verify that the profile was edited
        solo.waitForText("Username: Test User", 1, 300);
        solo.waitForText("Email: test@test.ca", 1, 300);
        solo.waitForText("Phone: +17809990000", 1, 300);


    }
}
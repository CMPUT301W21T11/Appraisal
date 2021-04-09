package com.example.appraisal.UI.main_menu;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appraisal.UI.main_menu.my_experiment.MyExperimentActivity;
import com.example.appraisal.UI.main_menu.specific_experiment_details.qr_scanner.CameraScanResultActivity;
import com.example.appraisal.UI.main_menu.search.SearchActivity;
import com.example.appraisal.UI.main_menu.specific_experiment_details.qr_scanner.CameraScannerActivity;
import com.example.appraisal.UI.main_menu.subscription.ExpSubscriptionActivity;
import com.example.appraisal.UI.main_menu.user_profile.UserProfileActivity;

/**
 * Common backbone for all UI Activities in the main menu to based on. It serves as a template for
 * the MenuBar to avoid code duplication.
 */
public abstract class MainMenuCommonActivity extends AppCompatActivity {

    /**
     * This method starts the home activity: {@link ExpSubscriptionActivity}
     */
    public void toHome() {
        if (this.getClass() == ExpSubscriptionActivity.class)
            return;
        Intent intent = new Intent(this, ExpSubscriptionActivity.class);
        startActivity(intent);
    }

    /**
     * This method starts the search activity: {@link SearchActivity}
     */
    public void toSearch() {
        if (this.getClass() == SearchActivity.class)
            return;
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
    }

    /**
     * This method starts the QR scanner: {@link CameraScannerActivity}, {@link CameraScanResultActivity}
     */
    public void toCamera() {
        Intent intent = new Intent(this, CameraScanResultActivity.class);
        startActivity(intent);
    }

    /**
     * This method starts the my experiments activity: {@link MyExperimentActivity}
     */
    public void toMyExps() {
        // When click on expList button
        if (this.getClass() == MyExperimentActivity.class)
            return;
        Intent intent = new Intent(this, MyExperimentActivity.class);
        startActivity(intent);
    }

    /**
     * This method starts the user profile activity: {@link UserProfileActivity}
     */
    public void toProfile() {
        Intent intent = new Intent(this, UserProfileActivity.class);
        intent.putExtra("flag", "Main");
        startActivity(intent);
    }
}

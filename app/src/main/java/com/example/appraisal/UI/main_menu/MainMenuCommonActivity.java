package com.example.appraisal.UI.main_menu;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appraisal.UI.main_menu.my_experiment.MyExperimentActivity;
import com.example.appraisal.UI.main_menu.search.SearchActivity;
import com.example.appraisal.UI.main_menu.subscription.ExpSubscriptionActivity;
import com.example.appraisal.UI.main_menu.user_profile.UserProfileActivity;

/**
 * Common backbone for all UI Activities in the main menu to based on. It serves as a template for
 * the MenuBar to avoid code duplication.
 */
public abstract class MainMenuCommonActivity extends AppCompatActivity {
    public void toHome() {
        if (this.getClass() == ExpSubscriptionActivity.class)
            return;
        Intent intent = new Intent(this, ExpSubscriptionActivity.class);
        startActivity(intent);
    }

    public void toSearch() {
        if (this.getClass() == SearchActivity.class)
            return;
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
    }

//    public void toForum(View v) {
//        // When click on forum button
//        if (this.getClass() == ForumHomeActivity.class)
//            return;
//        Intent intent = new Intent(this, ForumHomeActivity.class);
//        startActivity(intent);
//    }

    public void toMyExps() {
        // When click on expList button
        if (this.getClass() == MyExperimentActivity.class)
            return;
        Intent intent = new Intent(this, MyExperimentActivity.class);
        startActivity(intent);
    }

    public void toProfile() {
        Intent intent = new Intent(this, UserProfileActivity.class);
        intent.putExtra("flag", "Main");
        startActivity(intent);
    }
}

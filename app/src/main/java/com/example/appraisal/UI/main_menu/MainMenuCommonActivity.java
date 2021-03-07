package com.example.appraisal.UI.main_menu;

import android.content.Intent;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import com.example.appraisal.UI.main_menu.forum.ForumHomeActivity;
import com.example.appraisal.UI.main_menu.my_experiment.MyExperimentActivity;
import com.example.appraisal.UI.main_menu.subscription.ExpSubscriptionActivity;

public class MainMenuCommonActivity extends AppCompatActivity {
    public void toHome(View v) {
        if (this.getClass() == ExpSubscriptionActivity.class)
            return;
        Intent intent = new Intent(this, ExpSubscriptionActivity.class);
        startActivity(intent);
    }

    public void toSearch(View v) {
        // TODO When click on search button
    }

    public void toForum(View v) {
        // When click on forum button
        if (this.getClass() == ForumHomeActivity.class)
            return;
        Intent intent = new Intent(this, ForumHomeActivity.class);
        startActivity(intent);
    }

    public void toMyExps(View v) {
        // When click on expList button
        if (this.getClass() == MyExperimentActivity.class)
            return;
        Intent intent = new Intent(this, MyExperimentActivity.class);
        startActivity(intent);
    }

    public void toProfile(View v) {
        // TODO When click on profile button
    }
}

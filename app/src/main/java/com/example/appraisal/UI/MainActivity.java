package com.example.appraisal.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appraisal.R;
import com.example.appraisal.UI.main_menu.SelectionActivity;
import com.example.appraisal.UI.main_menu.my_experiment.MyExperimentActivity;
import com.example.appraisal.UI.main_menu.subscription.ExpSubscriptionActivity;
import com.example.appraisal.backend.user.FirebaseAuthentication;

public class MainActivity extends AppCompatActivity {

    protected FirebaseAuthentication auth = new FirebaseAuthentication();
    protected static String user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Check if user is signed in (non-null) and update UI accordingly.
        if (auth.isLoggedIn()){
            user_id = auth.get_userID();

            // Get their information
        } else {
            auth.sign_in();
            user_id = auth.get_userID();

            // Push the ID into firebase

        }

    }

    public void toSelection(View v) {
        // TODO Bring the user from main menu, to experiment type menu
        Intent intent = new Intent(this, SelectionActivity.class);
        startActivity(intent);
    }

    public void toHome(View v) {
        // TODO Bring the user from main menu, to experiment type menu
        Intent intent = new Intent(this, ExpSubscriptionActivity.class);
        startActivity(intent);
    }

    public void toMyExperiments(View v){
        // TODO Bring the user from main menu, to experiment type menu
        Intent intent2 = new Intent(this, MyExperimentActivity.class);
        startActivity(intent);
    }
}
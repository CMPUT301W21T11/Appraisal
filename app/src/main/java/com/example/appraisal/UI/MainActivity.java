package com.example.appraisal.UI;

import android.content.Intent;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import com.example.appraisal.R;
import com.example.appraisal.UI.main_menu.subscription.ExpSubscriptionActivity;
import com.example.appraisal.UI.main_menu.MyExperimentActivity;
import com.example.appraisal.UI.main_menu.SelectionActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        Intent intent = new Intent(this, MyExperimentActivity.class);
        startActivity(intent);
    }
}
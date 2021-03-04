package com.example.appraisal.UI;

import android.content.Intent;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import com.example.appraisal.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void toSelection(View v) {
        Intent intent = new Intent(this, SelectionActivity.class);
        startActivity(intent);
    }

    public void toSubscription(View v) {
        Intent intent = new Intent(this, ExpSubscriptionActivity.class);
        startActivity(intent);
    }
}